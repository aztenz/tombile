USE tombile;

DELIMITER //

CREATE PROCEDURE persist_to_cart(
	IN product_id INT,
	IN quantity INT,
	IN user_id INT,
	OUT new_subtotal DECIMAL(10, 2)
)
BEGIN
	DECLARE product_error_message VARCHAR(255);
	DECLARE total_cart_amount DECIMAL(10, 2);
	DECLARE wallet_error_message VARCHAR(255);

	-- Check product quantity
	SET product_error_message = check_product_quantity(product_id, quantity);
	IF product_error_message IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = product_error_message;
	END IF;

	-- Calculate total cart amount
	SET total_cart_amount = calculate_total_cart_amount(user_id) + (quantity * (SELECT price FROM products WHERE id = product_id));

	-- Check user wallet balance
	SET wallet_error_message = check_user_wallet_balance(user_id, total_cart_amount);
	IF wallet_error_message IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = wallet_error_message;
	END IF;

	SET new_subtotal = quantity * (SELECT price FROM products WHERE id = product_id);
END//

CREATE PROCEDURE create_order(
	IN p_buyer_id INT,
	IN p_shipping_address_id INT,
	OUT p_order_id INT
)
BEGIN
	DECLARE error_message VARCHAR(255);
	DECLARE total_price DECIMAL(10,2);

	-- Validate cart items and calculate total amount
	SET error_message = check_cart_items(p_buyer_id);
	IF error_message IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_message;
	END IF;

	-- Calculate total order amount
	SET total_price = calculate_total_cart_amount(p_buyer_id);

	-- Check user wallet balance
	SET error_message = check_user_wallet_balance(p_buyer_id, total_price);
	IF error_message IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_message;
	END IF;

	-- Create the order
	INSERT INTO orders (buyer_id, shipping_address_id, total_price)
		VALUES (p_buyer_id, p_shipping_address_id, total_price);

	-- Get the newly inserted order ID
	SET p_order_id = LAST_INSERT_ID();

	-- Add order items
	SET @dummy = add_order_items(p_order_id, p_buyer_id);

	-- Update product quantities
	SET @dummy = update_product_quantity(p_buyer_id);

	-- Update user wallet balance
	SET @dummy = deduct_from_user_wallet(p_buyer_id, total_price);

	-- Clear user cart
	SET @dummy = clear_user_cart(p_buyer_id);

	-- Update cart prices
	SET @dummy = update_cart_prices(p_buyer_id);
END//

CREATE PROCEDURE confirm_order_items(
	IN p_order_id INT,
	IN p_supplier_id INT
)
BEGIN
	DECLARE total_items INT;
	DECLARE confirmed_items INT;

	-- Confirm order items for the given supplier in the order
	UPDATE order_items
	SET item_status = 'CONFIRMED'
	WHERE order_id = p_order_id
		AND product_id IN (SELECT id FROM products WHERE supplier_id = p_supplier_id)
		AND item_status = 'PENDING';

	-- Check if all items in the order are confirmed
	SELECT COUNT(*) INTO total_items FROM order_items WHERE order_id = p_order_id;
	SELECT COUNT(*) INTO confirmed_items FROM order_items WHERE order_id = p_order_id AND item_status = 'CONFIRMED';

	IF total_items = confirmed_items THEN
		-- Update order status to confirmed
		UPDATE orders SET order_status = 'CONFIRMED' WHERE id = p_order_id;
	END IF;
END//

CREATE PROCEDURE reject_order_items(
	IN p_order_id INT,
	IN p_supplier_id INT
)
BEGIN
    DECLARE total_price DECIMAL(10, 2);
    DECLARE remaining_items INT;
    DECLARE temp_order_id INT;
    DECLARE temp_product_id INT;
    
    IF NOT EXISTS (SELECT 1 FROM orders WHERE id = p_order_id) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'couldn\'t find order';
	END IF;
    
	START TRANSACTION;
		IF (NOT (SELECT EXISTS (SELECT 1 FROM orders_history WHERE id = p_order_id))) THEN
			INSERT INTO orders_history
			SELECT 
				id,
				buyer_id,
				shipping_address_id,
				total_price,
				'NOT_FINISHED' AS order_status,
				order_date
			FROM orders WHERE id = p_order_id;
		END IF;
    
		DROP TEMPORARY TABLE IF EXISTS temp_order_items;
		CREATE TEMPORARY TABLE temp_order_items AS
		SELECT
			oi.order_id,
			oi.product_id,
			'REJECTED' AS 'item_status',
			oi.quantity,
			oi.subtotal
		FROM order_items oi
		JOIN products p ON oi.product_id = p.id
		WHERE p.supplier_id = p_supplier_id
			AND oi.order_id = p_order_id
			AND oi.item_status = 'PENDING';
		
        
		SET total_price = (SELECT IFNULL(SUM(subtotal), 0) FROM temp_order_items);
		SET remaining_items = 
			(SELECT COUNT(order_id) FROM order_items WHERE order_id = p_order_id) - 
			(SELECT COUNT(order_id) FROM temp_order_items);

		UPDATE user_data SET wallet_balance = wallet_balance + total_price WHERE user_id = p_supplier_id;
		
		INSERT INTO order_items_history SELECT * FROM temp_order_items;
		
        SELECT DISTINCT order_id INTO temp_order_id FROM temp_order_items;
        SELECT DISTINCT product_id INTO temp_product_id FROM temp_order_items;
        
		DELETE FROM order_items WHERE order_id = temp_order_id AND product_id = temp_product_id;
		
		IF remaining_items = 0 THEN
            UPDATE orders_history 
            SET
				order_status = 'FINISHED',
				total_price = (SELECT IFNULL(SUM(subtotal), 0) FROM order_items_history WHERE order_id = p_order_id AND item_status = 'FINISHED')
            WHERE id = p_order_id;
			DELETE FROM orders WHERE id = p_order_id;
		END IF;
        
        DROP TEMPORARY TABLE temp_order_items;
    COMMIT;
END//

CREATE PROCEDURE cancel_order_items_buyer(
	IN p_order_id INT,
    IN p_buyer_id INT
)
BEGIN
	DECLARE total_price DECIMAL(10, 2);
    DECLARE remaining_items INT;
    
    IF NOT EXISTS (SELECT 1 FROM orders WHERE id = p_order_id) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'couldn\'t find order';
	END IF;
    
	START TRANSACTION;
		IF (NOT (SELECT EXISTS (SELECT 1 FROM orders_history WHERE id = p_order_id))) THEN
			INSERT INTO orders_history
			SELECT
				id,
				buyer_id,
				shipping_address_id,
				total_price,
				'NOT_FINISHED' AS order_status,
				order_date
			FROM orders WHERE id = p_order_id;
		END IF;

		DROP TEMPORARY TABLE IF EXISTS temp_order_items;
		CREATE TEMPORARY TABLE temp_order_items AS
		SELECT
			oi.order_id,
			oi.product_id,
			'CANCELLED' AS 'item_status',
			oi.quantity,
            CASE
				WHEN oi.item_status = 'CONFIRMED' THEN 0.9 * oi.subtotal
				WHEN oi.item_status = 'PENDING' THEN oi.subtotal
			END AS subtotal
		FROM order_items oi
		WHERE oi.order_id = p_order_id
			AND (oi.item_status = 'CONFIRMED' OR oi.item_status = 'PENDING');
            
		SET total_price = (SELECT IFNULL(SUM(subtotal), 0) FROM temp_order_items);
		
        UPDATE user_data SET wallet_balance = wallet_balance + total_price WHERE user_id = p_buyer_id;
        
        UPDATE products p
        JOIN temp_order_items toi ON p.id = toi.product_id
        SET p.quantity = p.quantity + toi.quantity
        WHERE toi.order_id = p_order_id;
        
		SET remaining_items = 
			(SELECT COUNT(order_id) FROM order_items WHERE order_id = p_order_id) - 
			(SELECT COUNT(order_id) FROM temp_order_items);
		
		INSERT INTO order_items_history SELECT * FROM temp_order_items;
		
		DELETE FROM order_items 
        WHERE order_id = p_order_id 
			AND product_id IN (SELECT DISTINCT product_id FROM temp_order_items);
		
		IF remaining_items = 0 THEN
            UPDATE orders_history 
            SET
				order_status = 'FINISHED',
				total_price = (SELECT IFNULL(SUM(subtotal), 0) FROM order_items_history WHERE order_id = p_order_id AND item_status = 'FINISHED')
                WHERE id = p_order_id;
			DELETE FROM orders WHERE id = p_order_id;
		END IF;
        
        DROP TEMPORARY TABLE temp_order_items;
    COMMIT;
END//

CREATE PROCEDURE cancel_order_items_supplier(
	IN p_order_id INT,
    IN p_supplier_id INT
)
BEGIN
	DECLARE total_price DECIMAL(10, 2);
    DECLARE remaining_items INT;
    
    IF NOT EXISTS (SELECT 1 FROM orders WHERE id = p_order_id) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'couldn\'t find order';
	END IF;
    
	START TRANSACTION;
		IF (NOT (SELECT EXISTS (SELECT 1 FROM orders_history WHERE id = p_order_id))) THEN
			INSERT INTO orders_history
			SELECT
				id,
				buyer_id,
				shipping_address_id,
				total_price,
				'NOT_FINISHED' AS order_status,
				order_date
			FROM orders WHERE id = p_order_id;
		END IF;

		DROP TEMPORARY TABLE IF EXISTS temp_order_items;
		CREATE TEMPORARY TABLE temp_order_items AS
		SELECT
			oi.order_id,
			oi.product_id,
			'CANCELLED' AS 'item_status',
			oi.quantity,
            oi.subtotal
		FROM order_items oi
		JOIN products p ON oi.product_id = p.id
		WHERE p.supplier_id = p_supplier_id
			AND oi.order_id = p_order_id
			AND (oi.item_status = 'CONFIRMED' OR oi.item_status = 'PENDING');
            
		SET total_price = (SELECT IFNULL(SUM(subtotal), 0) FROM temp_order_items);
		
        UPDATE user_data SET wallet_balance = wallet_balance + total_price WHERE user_id = (SELECT buyer_id FROM orders WHERE id = p_order_id);
        
        UPDATE products p
        JOIN temp_order_items toi ON p.id = toi.product_id
        SET p.quantity = p.quantity + toi.quantity
        WHERE toi.order_id = p_order_id;
        
        INSERT INTO order_items_history SELECT * FROM temp_order_items;
        
		SET remaining_items = 
			(SELECT COUNT(order_id) FROM order_items WHERE order_id = p_order_id) - 
			(SELECT COUNT(order_id) FROM temp_order_items);
		
		DELETE FROM order_items 
        WHERE order_id = p_order_id 
			AND product_id IN (SELECT DISTINCT product_id FROM temp_order_items);
		
		IF remaining_items = 0 THEN
            UPDATE orders_history 
            SET
				order_status = 'FINISHED',
				total_price = (SELECT IFNULL(SUM(subtotal), 0) FROM order_items_history WHERE order_id = p_order_id AND item_status = 'FINISHED')
                WHERE id = p_order_id;
			DELETE FROM orders WHERE id = p_order_id;
		END IF;
        
        DROP TEMPORARY TABLE temp_order_items;
    COMMIT;
END//

CREATE PROCEDURE complete_order_items(
    IN p_order_id INT,
    IN p_supplier_id INT
)
BEGIN
    DECLARE total_price DECIMAL(10, 2);
    DECLARE remaining_items INT;
    DECLARE temp_order_id INT;
    DECLARE temp_product_id INT;
    
    IF NOT EXISTS (SELECT 1 FROM orders WHERE id = p_order_id) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'couldn\'t find order';
	END IF;
    
	START TRANSACTION;
		IF (NOT (SELECT EXISTS (SELECT 1 FROM orders_history WHERE id = p_order_id))) THEN
			INSERT INTO orders_history
			SELECT 
				id,
				buyer_id,
				shipping_address_id,
				total_price,
				'NOT_FINISHED' AS order_status,
				order_date
			FROM orders WHERE id = p_order_id;
		END IF;
    
		DROP TEMPORARY TABLE IF EXISTS temp_order_items;
		CREATE TEMPORARY TABLE temp_order_items AS
		SELECT
			oi.order_id,
			oi.product_id,
			'FINISHED' AS 'item_status',
			oi.quantity,
			oi.subtotal
		FROM order_items oi
		JOIN products p ON oi.product_id = p.id
		WHERE p.supplier_id = p_supplier_id
			AND oi.order_id = p_order_id
			AND oi.item_status = 'CONFIRMED';
		
        
		SET total_price = (SELECT IFNULL(SUM(subtotal), 0) FROM temp_order_items);
		SET remaining_items = 
			(SELECT COUNT(order_id) FROM order_items WHERE order_id = p_order_id) - 
			(SELECT COUNT(order_id) FROM temp_order_items);

		UPDATE user_data SET wallet_balance = wallet_balance + total_price WHERE user_id = p_supplier_id;
		
		INSERT INTO order_items_history SELECT * FROM temp_order_items;
		
        SELECT DISTINCT order_id INTO temp_order_id FROM temp_order_items;
        SELECT DISTINCT product_id INTO temp_product_id FROM temp_order_items;
        
		DELETE FROM order_items WHERE order_id = temp_order_id AND product_id = temp_product_id;
		
		IF remaining_items = 0 THEN
            UPDATE orders_history 
            SET
				order_status = 'FINISHED',
				total_price = (SELECT IFNULL(SUM(subtotal), 0) FROM order_items_history WHERE order_id = p_order_id)
            WHERE id = p_order_id;
			DELETE FROM orders WHERE id = p_order_id;
		END IF;
        
        DROP TEMPORARY TABLE temp_order_items;
    COMMIT;
END//

DELIMITER ;




-- CREATE PROCEDURE complete_order_items(
--   IN p_order_id INT,
--   IN p_supplier_id INT
-- )
-- BEGIN
--   DECLARE total_price DECIMAL(10, 2);
--   DECLARE remaining_items INT;

--   -- Check for order existence with single query
--   SELECT 1 FROM orders WHERE id = p_order_id;
--   IF @@ROWCOUNT = 0 THEN
--     SIGNAL SQLSTATE '45000';
--     SET MESSAGE_TEXT = 'Order not found';
--   END IF;

--   START TRANSACTION;

--   -- Insert into orders_history only if not already completed
--   INSERT INTO orders_history (id, buyer_id, shipping_address_id, total_price, order_status, order_date)
--   SELECT o.id, o.buyer_id, o.shipping_address_id, o.total_price, 'FINISHED', o.order_date
--   FROM orders o
--   WHERE o.id = p_order_id
--   HAVING NOT EXISTS (SELECT 1 FROM orders_history oh WHERE oh.id = o.id);

--   -- Create temporary table with primary key for efficiency
--   CREATE TEMPORARY TABLE temp_order_items (
--     order_id INT,
--     product_id INT,
--     item_status ENUM('CONFIRMED', 'FINISHED'), -- Consider using ENUM for status
--     quantity INT,
--     subtotal DECIMAL(10, 2),
--     PRIMARY KEY (order_id, product_id) -- Improve DELETE performance
--   );

--   -- Insert confirmed items for the supplier into temporary table
--   INSERT INTO temp_order_items (order_id, product_id, item_status, quantity, subtotal)
--   SELECT oi.order_id, oi.product_id, 'FINISHED', oi.quantity, oi.subtotal
--   FROM order_items oi
--   JOIN products p ON oi.product_id = p.id
--   WHERE p.supplier_id = p_supplier_id
--     AND oi.order_id = p_order_id
--     AND oi.item_status = 'CONFIRMED';

--   -- Calculate total price and remaining items in a single query
--   SELECT SUM(IFNULL(subtotal, 0)) AS total_price, COUNT(*) - COUNT(temp_order_items.order_id) AS remaining_items
--   FROM order_items oi
--   LEFT JOIN temp_order_items toi ON oi.order_id = toi.order_id AND oi.product_id = toi.product_id
--   WHERE oi.order_id = p_order_id;

--   SET total_price = (SELECT total_price);
--   SET remaining_items = (SELECT remaining_items);

--   -- Update supplier wallet balance in a single statement (if applicable)
--   UPDATE user_data
--   SET wallet_balance = wallet_balance + total_price
--   WHERE user_id = p_supplier_id AND wallet_balance >= total_price;  -- Avoid negative balances

--   -- Insert confirmed items into history (consider bulk insert for efficiency)
--   INSERT INTO order_items_history (order_id, product_id, item_status, quantity, price, subtotal)
--   SELECT * FROM temp_order_items;

--   -- Delete completed items using primary key for efficiency
--   DELETE FROM order_items
--   USING temp_order_items toi
--   INNER JOIN order_items oi ON toi.order_id = oi.order_id AND toi.product_id = oi.product_id;

--   IF remaining_items = 0 THEN
--     UPDATE orders_history oh
--     SET oh.total_price = (SELECT SUM(subtotal) FROM order_items_history oih WHERE oih.order_id = oh.id AND oih.item_status = 'FINISHED')
--     WHERE oh.id = p_order_id;

--     DELETE FROM orders WHERE id = p_order_id;
--   END IF;

--   DROP TEMPORARY TABLE temp_order_items;
--   COMMIT;
-- END;
