CREATE TABLE simple_ecommerce.products(
    id                      UUID NOT NULL,
    name                    VARCHAR(50) NOT NULL,
    description             VARCHAR(500) NOT NULL,
    price                   NUMERIC(20,2) NOT NULL,
    created_on              TIMESTAMP WITHOUT TIME ZONE,
    last_updated_on         TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT pk_products PRIMARY KEY (id)
);