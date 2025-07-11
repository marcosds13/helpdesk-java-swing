Simple Java Ticket System

WIP




MySQL Database Setup:

SQL Script
```
-- Create database
CREATE DATABASE IF NOT EXISTS ticket_system_db;
USE ticket_system_db;

-- Roles table
CREATE TABLE roles (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE
);

-- Status Types table
CREATE TABLE status_types (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE
);

-- Users table
CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
name VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
role_id INT NOT NULL,
FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Tickets table
CREATE TABLE tickets (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(100) NOT NULL,
description TEXT,
status_id INT NOT NULL,
created_by INT NOT NULL,
assigned_to INT,
FOREIGN KEY (status_id) REFERENCES status_types(id),
FOREIGN KEY (created_by) REFERENCES users(id),
FOREIGN KEY (assigned_to) REFERENCES users(id)
);

-- Comments table
CREATE TABLE comments (
id INT AUTO_INCREMENT PRIMARY KEY,
ticket_id INT NOT NULL,
user_id INT NOT NULL,
message TEXT NOT NULL,
sent_at DATETIME DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (ticket_id) REFERENCES tickets(id),
FOREIGN KEY (user_id) REFERENCES users(id)
);
-- Insert Roles
INSERT INTO roles (name) VALUES
('ADMIN'),
('TECHNICIAN'),
('USER');

-- Insert Status Types
INSERT INTO status_types (name) VALUES
('OPEN'),
('ASSIGNED'),
('CLOSED');

-- Insert Example Users
INSERT INTO users (username, password, role_id, name, email) VALUES
('admin', 'adminpass', 1, 'admin', 'admin@test.com'),
('tech1', 'techpass', 2, 'tech1', 'tech1@test.com'),
('user1', 'userpass', 3, 'user1', 'user1@test.com');

```