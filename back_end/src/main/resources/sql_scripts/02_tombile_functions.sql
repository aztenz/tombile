USE tombile;

DELIMITER //

CREATE FUNCTION add_order_items(p_order_id INT, p_user_id INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	INSERT INTO order_items (order_id, product_id, quantity, subtotal)
	SELECT p_order_id, product_id, quantity, subtotal
	FROM carts
	WHERE user_id = p_user_id;
	
	RETURN TRUE;
END//

CREATE FUNCTION add_to_user_wallet(p_user_id INT, p_new_amount DECIMAL(10, 2)) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	UPDATE user_data ud SET ud.wallet_balance = ud.wallet_balance + p_new_amount WHERE user_id = p_user_id;
	
	RETURN TRUE;
END//

CREATE FUNCTION calculate_total_cart_amount(p_user_id INT) RETURNS DECIMAL(10, 2)
DETERMINISTIC
BEGIN
	DECLARE total_amount DECIMAL(10, 2);
	
	SELECT IFNULL(SUM(subtotal), 0) INTO total_amount FROM carts WHERE user_id = p_user_id;
	
	RETURN total_amount;
END//

CREATE FUNCTION check_cart_items(p_user_id INT) RETURNS VARCHAR(255)
DETERMINISTIC
BEGIN
	DECLARE product_quantity INT;
	DECLARE cart_product_id INT;
	DECLARE cart_quantity INT;
	DECLARE done INT DEFAULT 0;
	DECLARE cart_item_count INT DEFAULT 0;
	DECLARE error_message VARCHAR(255);
	
	DECLARE cur CURSOR FOR
		SELECT c.product_id, c.quantity, p.quantity
		FROM carts c
		JOIN products p ON c.product_id = p.id
		WHERE c.user_id = p_user_id;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	OPEN cur;

	-- Check if there are any items in the cart
	SELECT COUNT(*) INTO cart_item_count
	FROM carts
	WHERE user_id = p_user_id;

	IF cart_item_count = 0 THEN
		SET error_message = 'Order failed: No cart items found';
		CLOSE cur;
		RETURN error_message;
	ELSE
		read_loop: LOOP
			FETCH cur INTO cart_product_id, cart_quantity, product_quantity;
			
			IF done THEN
				LEAVE read_loop;
			END IF;

			IF cart_quantity > product_quantity THEN
				SET error_message = 'Order failed: Insufficient product quantity';
				CLOSE cur;
				RETURN error_message;
			END IF;
		END LOOP;

		CLOSE cur;

		RETURN NULL;
	END IF;
END//

CREATE FUNCTION check_product_quantity(p_product_id INT, p_quantity INT) RETURNS VARCHAR(255)
DETERMINISTIC
BEGIN
	DECLARE product_quantity INT;
	DECLARE error_message VARCHAR(255);

	SELECT quantity INTO product_quantity FROM products WHERE id = p_product_id;

	IF product_quantity < p_quantity THEN
		SET error_message = 'Insufficient product quantity';
		RETURN error_message;
	ELSE
		RETURN NULL;
	END IF;
END//

CREATE FUNCTION check_user_wallet_balance(p_user_id INT, p_total_amount DECIMAL(10, 2)) RETURNS VARCHAR(255)
DETERMINISTIC
BEGIN
	DECLARE user_wallet DECIMAL(10, 2);
	DECLARE error_message VARCHAR(255);

	SELECT wallet_balance INTO user_wallet FROM user_data WHERE user_id = p_user_id;

	IF p_total_amount > user_wallet THEN
		SET error_message = 'Insufficient wallet balance';
		RETURN error_message;
	ELSE
		RETURN NULL;
	END IF;
END//

CREATE FUNCTION clear_user_cart(p_user_id INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	DELETE FROM carts WHERE user_id = p_user_id;
	
	RETURN TRUE;
END//

CREATE FUNCTION deduct_from_user_wallet(p_user_id INT, p_total_amount DECIMAL(10, 2)) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	UPDATE user_data SET wallet_balance = wallet_balance - p_total_amount WHERE user_id = p_user_id;
	
	RETURN TRUE;
END//

CREATE FUNCTION update_cart_prices(p_user_id INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	UPDATE carts c
	JOIN products p ON c.product_id = p.id
	SET c.subtotal = c.quantity * p.price
	WHERE c.user_id = p_user_id;
	
	RETURN TRUE;
END//

CREATE FUNCTION update_product_quantity(p_user_id INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	DECLARE done INT DEFAULT 0;
	DECLARE product_id INT;
	DECLARE cart_quantity INT;
	DECLARE cur CURSOR FOR
		SELECT c.product_id, c.quantity
		FROM carts c
		WHERE c.user_id = p_user_id;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	OPEN cur;
	read_loop: LOOP
		FETCH cur INTO product_id, cart_quantity;
		IF done THEN
			LEAVE read_loop;
		END IF;

		UPDATE products
		SET quantity = quantity - cart_quantity
		WHERE id = product_id;
	END LOOP;
	CLOSE cur;

	RETURN TRUE;
END//

CREATE FUNCTION get_buyer_id(p_order_id INT) RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE buyer_id INT;

	SELECT o.buyer_id INTO buyer_id
	FROM orders o
	WHERE o.id = p_order_id;
	
	RETURN buyer_id;
END//

CREATE FUNCTION calculate_refund_amount(p_order_id INT, p_supplier_id INT) RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
	DECLARE refund_amount DECIMAL(10,2);

	SELECT IFNULL(SUM(oi.subtotal), 0) INTO refund_amount
	FROM order_items oi
	JOIN products p ON oi.product_id = p.id
	WHERE oi.order_id = p_order_id
		AND p.supplier_id = p_supplier_id
		AND oi.item_status = 'PENDING';
	
	RETURN refund_amount;
END//

CREATE FUNCTION move_rejected_items_to_history(p_order_id INT, p_supplier_id INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	INSERT INTO order_items_history (order_id, product_id, quantity, subtotal)
	SELECT oi.order_id, oi.product_id, oi.quantity, oi.subtotal
	FROM order_items oi
	JOIN products p ON oi.product_id = p.id
	WHERE oi.order_id = p_order_id
		AND p.supplier_id = p_supplier_id
		AND oi.item_status = 'PENDING';
	
	RETURN TRUE;
END//

CREATE FUNCTION delete_rejected_order_items(p_order_id INT, p_supplier_id INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	DELETE oi
	FROM order_items oi
	JOIN products p ON oi.product_id = p.id
	WHERE oi.order_id = p_order_id
		AND p.supplier_id = p_supplier_id
		AND oi.item_status = 'PENDING';
	
	RETURN TRUE;
END//

CREATE FUNCTION check_remaining_order_items(p_order_id INT) RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE remaining_items INT;

	SELECT COUNT(*) INTO remaining_items
	FROM order_items
	WHERE order_id = p_order_id;
	
	RETURN remaining_items;
END//

CREATE FUNCTION move_order_to_history(p_order_id INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	INSERT INTO orders_history
	SELECT * FROM orders WHERE id = p_order_id;

	DELETE FROM orders WHERE id = p_order_id;
	
	RETURN TRUE;
END//

DELIMITER ;