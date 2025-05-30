DROP DATABASE IF EXISTS style_overflow;
CREATE DATABASE style_overflow;
USE style_overflow;

-- Tabela: users
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       gender ENUM('MALE', 'FEMALE') NOT NULL,
                       address TEXT,
                       email VARCHAR(150) UNIQUE NOT NULL,
                       role ENUM('CLIENT', 'ADMIN') NOT NULL DEFAULT 'CLIENT',
                       cellphone VARCHAR(20),
                       cpf VARCHAR(14) UNIQUE,
                       cep VARCHAR(9)
);

-- Tabela: products
CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          size ENUM('PP', 'P', 'M', 'G', 'GG') NOT NULL,
                          gender ENUM('MALE', 'FEMALE') NOT NULL,
                          color VARCHAR(50),
                          stock INT NOT NULL CHECK (stock >= 0),
                          price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
                          deleted BOOLEAN NOT NULL DEFAULT false
);

-- Tabela: orders
CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        client_id INT NOT NULL,
                        date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        status ENUM('PENDING', 'DELIVERED') NOT NULL DEFAULT 'PENDING',
                        payment_type ENUM('PIX', 'CARD') NOT NULL,
                        FOREIGN KEY (client_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela intermediária: product_orders
CREATE TABLE product_orders (
                                order_id INT NOT NULL,
                                product_id INT NOT NULL,
                                quantity INT NOT NULL CHECK (quantity > 0),
                                PRIMARY KEY (order_id, product_id),
                                FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                                FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);