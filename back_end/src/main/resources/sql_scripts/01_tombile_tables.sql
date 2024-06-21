DROP DATABASE IF EXISTS tombile;
CREATE DATABASE IF NOT EXISTS tombile;
USE tombile;

CREATE TABLE users (
	id int NOT NULL AUTO_INCREMENT,
	username varchar(50) DEFAULT NULL,
	password varchar(100) DEFAULT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY username (username)
);

CREATE TABLE user_data (
	user_id int NOT NULL,
	first_name varchar(50) DEFAULT NULL,
	last_name varchar(50) DEFAULT NULL,
	email varchar(100) NOT NULL,
	role varchar(50) DEFAULT NULL,
	wallet_balance decimal(10,2) DEFAULT '0.00',
	verification_status varchar(50) DEFAULT 'NOT_VERIFIED',
	registration_date datetime DEFAULT CURRENT_TIMESTAMP,
	last_login_date datetime DEFAULT NULL,
	PRIMARY KEY (user_id),
	UNIQUE KEY email (email),
	CONSTRAINT FK_UserData_User FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE addresses (
	id int NOT NULL AUTO_INCREMENT,
	user_id int DEFAULT NULL,
	street varchar(100) DEFAULT NULL,
	city varchar(50) DEFAULT NULL,
	zip_code varchar(20) DEFAULT NULL,
	country varchar(50) DEFAULT NULL,
	address_type varchar(20) DEFAULT NULL,
	PRIMARY KEY (id),
	KEY FK_Addresses_User (user_id),
	CONSTRAINT FK_Addresses_User FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE products (
	id int NOT NULL AUTO_INCREMENT,
    supplier_id int NOT NULL,
	product_type varchar(20) DEFAULT NULL,
	quantity int DEFAULT NULL,
	name varchar(100) DEFAULT NULL,
	description text,
	price decimal(10,2) DEFAULT NULL,
	PRIMARY KEY (id),
	KEY FK_Products_Supplier (supplier_id),
	CONSTRAINT FK_Products_Supplier FOREIGN KEY (supplier_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cars (
	id int NOT NULL AUTO_INCREMENT,
	make varchar(50) DEFAULT NULL,
	model varchar(50) DEFAULT NULL,
	year int DEFAULT NULL,
	mileage int DEFAULT NULL,
	car_state varchar(20) DEFAULT NULL,
	PRIMARY KEY (id),
	CONSTRAINT FK_Cars_Product FOREIGN KEY (id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE parts (
	id int NOT NULL AUTO_INCREMENT,
	manufacturer varchar(100) DEFAULT NULL,
	compatibility varchar(100),
	PRIMARY KEY (id),
	CONSTRAINT FK_Parts_Product FOREIGN KEY (id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE reviews (
	id int NOT NULL AUTO_INCREMENT,
	product_id int DEFAULT NULL,
	user_id int DEFAULT NULL,
	rating int DEFAULT NULL,
	comment text,
	review_date datetime DEFAULT NULL,
	PRIMARY KEY (id),
	KEY FK_Reviews_Product (product_id),
	KEY FK_Reviews_User (user_id),
	CONSTRAINT FK_Reviews_Product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_Reviews_User FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE services (
	id int NOT NULL AUTO_INCREMENT,
	contact_info varchar(100) DEFAULT NULL,
	location varchar(100) DEFAULT NULL,
	service_type varchar(50) DEFAULT NULL,
	PRIMARY KEY (id),
	CONSTRAINT FK_Services_Product FOREIGN KEY (id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE carts (
	user_id int NOT NULL,
	product_id int NOT NULL,
	quantity int DEFAULT NULL,
	subtotal decimal(10,2) DEFAULT NULL,
	added_date datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (user_id, product_id),
	KEY FK_Carts_Product (product_id),
	CONSTRAINT FK_Carts_Product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_Carts_User FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders (
	id int NOT NULL AUTO_INCREMENT,
	buyer_id int NOT NULL,
	shipping_address_id int NOT NULL,
	total_price decimal(10,2) DEFAULT NULL,
	order_status varchar(20) NOT NULL DEFAULT 'PENDING',
	order_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	KEY FK_Orders_Buyer (buyer_id),
	KEY FK_Orders_ShippingAddress (shipping_address_id),
	CONSTRAINT FK_Orders_Buyer FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_Orders_ShippingAddress FOREIGN KEY (shipping_address_id) REFERENCES addresses (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders_history (
	id int NOT NULL AUTO_INCREMENT,
	buyer_id int NOT NULL,
	shipping_address_id int NOT NULL,
	total_price decimal(10,2) DEFAULT NULL,
	order_status varchar(20) NOT NULL DEFAULT 'FINISHED',
	order_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	KEY FK_OrdersHistory_Buyer (buyer_id),
	KEY FK_OrdersHistory_ShippingAddress (shipping_address_id),
	CONSTRAINT FK_OrdersHistory_Buyer FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_OrdersHistory_ShippingAddress FOREIGN KEY (shipping_address_id) REFERENCES addresses (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE order_items (
	order_id int NOT NULL,
	product_id int NOT NULL,
	item_status varchar(20) NOT NULL DEFAULT 'PENDING',
	quantity int DEFAULT NULL,
	subtotal decimal(10,2) DEFAULT NULL,
	PRIMARY KEY (order_id, product_id),
	KEY FK_OrderItems_Order (order_id),
	KEY FK_OrderItems_Product (product_id),
	CONSTRAINT FK_OrderItems_Order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_OrderItems_Product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE order_items_history (
	order_id int NOT NULL,
	product_id int NOT NULL,
	item_status varchar(20) NOT NULL DEFAULT 'FINISHED',
	quantity int DEFAULT NULL,
	subtotal decimal(10,2) DEFAULT NULL,
	PRIMARY KEY (order_id, product_id),
	KEY FK_OrderItemsHistory_OrderHistory (order_id),
	KEY FK_OrderItemsHistory_Product (product_id),
	CONSTRAINT FK_OrderItemsHistory_OrderHistory FOREIGN KEY (order_id) REFERENCES orders_history (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_OrderItemsHistory_Product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE otp (
	user_id int NOT NULL,
    otp_type varchar(50) NOT NULL,
	otp_code int DEFAULT NULL,
	expiration datetime DEFAULT NULL,
	PRIMARY KEY (user_id, otp_type),
	CONSTRAINT FK_Otp_User FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tokens (
	user_id int NOT NULL,
	token varchar(256) DEFAULT NULL,
	token_type varchar(50) DEFAULT NULL,
	PRIMARY KEY (user_id),
	UNIQUE KEY token (token),
	CONSTRAINT FK_Tokens_User FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);
