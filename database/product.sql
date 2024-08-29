CREATE TABLE products(
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL,
    PRIMARY KEY(id)
);