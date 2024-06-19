USE tombile;

DELIMITER //

CREATE TRIGGER before_insert_cart
BEFORE INSERT ON carts
FOR EACH ROW
BEGIN
	DECLARE new_subtotal DECIMAL(10, 2);
	CALL persist_to_cart(NEW.product_id, NEW.quantity, NEW.user_id, new_subtotal);
	SET NEW.subtotal = new_subtotal;
END//

CREATE TRIGGER before_update_cart
BEFORE UPDATE ON carts
FOR EACH ROW
BEGIN
	DECLARE new_subtotal DECIMAL(10, 2);
	CALL persist_to_cart(NEW.product_id, NEW.quantity, NEW.user_id, new_subtotal);
	SET NEW.subtotal = new_subtotal;
END//

DELIMITER ;