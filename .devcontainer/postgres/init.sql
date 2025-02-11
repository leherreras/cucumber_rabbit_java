CREATE TABLE customers (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL DEFAULT FALSE,
    phishing_detection BOOLEAN NOT NULL DEFAULT FALSE
);


INSERT INTO customers (name, status, phishing_detection) VALUES ('CUSTOMER1', true, true);
INSERT INTO customers (name, status, phishing_detection) VALUES ('CUSTOMER2', true, false);
INSERT INTO customers (name, status, phishing_detection) VALUES ('CUSTOMER3', false, true);
INSERT INTO customers (name, status, phishing_detection) VALUES ('CUSTOMER4', false, false);
