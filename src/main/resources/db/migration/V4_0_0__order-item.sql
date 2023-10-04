CREATE TABLE simple_ecommerce.order_items(
    id                      UUID NOT NULL,
    order_id                UUID NOT NULL,
    product_id              UUID NOT NULL,
    product_quantity         INTEGER NOT NULL,
    created_on              TIMESTAMP WITHOUT TIME ZONE,
    last_updated_on         TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT pk_order_items PRIMARY KEY (id),
    CONSTRAINT fk_order FOREIGN KEY(order_id) REFERENCES simple_ecommerce.orders(id),
    CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES simple_ecommerce.products(id)
);