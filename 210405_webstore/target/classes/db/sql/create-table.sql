DROP TABLE IF EXISTS customers ;
CREATE TABLE PRODUCTS (
ID VARCHAR(25) PRIMARY KEY,
PROD_NAME VARCHAR(50),
DESCRIPTION VARCHAR(250),
UNIT_PRICE DECIMAL,
MANUFACTURER VARCHAR(50),
CATEGORY VARCHAR(50),
PROD_CONDITION VARCHAR(50),
UNITS_IN_STOCK BIGINT,
UNITS_IN_ORDER BIGINT,
DISCONTINUED BOOLEAN
);
