CREATE TABLE simple_ecommerce.orders(
    id                      UUID NOT NULL,
    status                  VARCHAR(50) NOT NULL,
    created_on              TIMESTAMP WITHOUT TIME ZONE,
    last_updated_on         TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT pk_orders PRIMARY KEY (id),
    CONSTRAINT ck_status CHECK(status IN ('IN_CREATION', 'WAITING_PAYMENT', 'SENT', 'DONE'))
);