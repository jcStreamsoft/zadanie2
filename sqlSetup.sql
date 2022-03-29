CREATE DATABASE "CurrencyRateDataBase"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Polish_Poland.1250'
    LC_CTYPE = 'Polish_Poland.1250'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;


CREATE TABLE currency (
  currency_id             SERIAL PRIMARY KEY,
  currency_code           CHAR(3) NOT NULL 	
);
CREATE TABLE country (
  country_id             SERIAL PRIMARY KEY,
  country_name           VARCHAR(100) NOT NULL,
  currency_id 			INT NOT NULL,
  FOREIGN KEY (currency_id) REFERENCES Currency(currency_id)
);

CREATE TABLE rate (
  rate_id        SERIAL PRIMARY KEY,
  value          DECIMAL(60, 20) NOT NULL,
  date 			 DATE NOT NULL,
 currency_id 			INT NOT NULL,
  FOREIGN KEY (currency_id) REFERENCES Currency(currency_id)
);

//////////////////



CREATE TABLE currency (
currency_id SERIAL PRIMARY KEY,
currency_code CHAR(3) NOT NULL
);
CREATE TABLE country (
country_id SERIAL PRIMARY KEY,
country_name VARCHAR(100) NOT NULL,
currency_id INT NOT NULL,
FOREIGN KEY (currency_id) REFERENCES Currency(currency_id)
);CREATE TABLE rate (
rate_id SERIAL PRIMARY KEY,
value DECIMAL(60, 20) NOT NULL,
date DATE NOT NULL,
currency_id INT NOT NULL,
FOREIGN KEY (currency_id) REFERENCES Currency(currency_id)
);

