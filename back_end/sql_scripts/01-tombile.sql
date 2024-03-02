DROP DATABASE IF EXISTS tombile;
CREATE DATABASE IF NOT EXISTS tombile;
USE tombile;

CREATE TABLE user_data (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE,
    role VARCHAR(50),
    wallet_balance DECIMAL(10 , 2 ),
    verification_status BOOLEAN,
    registration_date DATETIME,
    last_login_date DATETIME
);

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_data_id INT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    CONSTRAINT FK_User_UserData FOREIGN KEY (user_data_id)
        REFERENCES user_data (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tokens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    token VARCHAR(256) UNIQUE,
    token_type VARCHAR(50),
    expired BOOLEAN,
    revoked BOOLEAN,
    CONSTRAINT FK_Tokens_User FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE addresses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    street VARCHAR(100),
    city VARCHAR(50),
    zip_code VARCHAR(20),
    country VARCHAR(50),
    address_type VARCHAR(20),
    CONSTRAINT FK_Addresses_User FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_type VARCHAR(20),
    supplier_id INT,
    name VARCHAR(100),
    description TEXT,
    price DECIMAL(10 , 2 ),
    CONSTRAINT FK_Products_Supplier FOREIGN KEY (supplier_id)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    order_date DATETIME,
    total_amount DECIMAL(10 , 2 ),
    payment_status VARCHAR(20),
    order_status VARCHAR(20),
    shipping_address_id INT,
    payment_method VARCHAR(50),
    CONSTRAINT FK_Orders_User FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_Orders_ShippingAddress FOREIGN KEY (shipping_address_id)
        REFERENCES addresses (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10 , 2 ),
    subtotal DECIMAL(10 , 2 ),
    CONSTRAINT FK_OrderItems_Order FOREIGN KEY (order_id)
        REFERENCES orders (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_OrderItems_Product FOREIGN KEY (product_id)
        REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE services (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    description TEXT,
    contact_info VARCHAR(100),
    location VARCHAR(100),
    service_type VARCHAR(50),
    CONSTRAINT FK_Services_Product FOREIGN KEY (id)
        REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cars (
    id INT PRIMARY KEY AUTO_INCREMENT,
    make VARCHAR(50),
    model VARCHAR(50),
    year INT,
    mileage INT,
    car_state VARCHAR(20),
    CONSTRAINT FK_Cars_Product FOREIGN KEY (id)
        REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE parts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    description TEXT,
    manufacturer VARCHAR(100),
    compatibility VARCHAR(100),
    CONSTRAINT FK_Parts_Product FOREIGN KEY (id)
        REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    product_id INT,
    quantity INT,
    added_date DATETIME,
    CONSTRAINT FK_Cart_User FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_Cart_Product FOREIGN KEY (product_id)
        REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    user_id INT,
    rating INT,
    comment TEXT,
    review_date DATETIME,
    CONSTRAINT FK_Reviews_Product FOREIGN KEY (product_id)
        REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_Reviews_User FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE review_mappings (
    review_id INT,
    entity_id INT,
    entity_type VARCHAR(20),
    PRIMARY KEY (review_id , entity_id , entity_type),
    CONSTRAINT FK_ReviewMappings_Review FOREIGN KEY (review_id)
        REFERENCES reviews (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ReviewMappings_Entity FOREIGN KEY (entity_id)
        REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);