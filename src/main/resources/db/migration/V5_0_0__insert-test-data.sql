-- Insert products
INSERT INTO simple_ecommerce.products
    (id, name, description, price, created_on, last_updated_on) values
    (uuid_in('b00e182d-852d-47c0-9feb-cb8c32eb04e3'), 'VortexBlade Pro-X', 'High-performance processor for gamers and creators.', 2999.99, now(), now());

INSERT INTO simple_ecommerce.products
    (id, name, description, price, created_on, last_updated_on) values
    (uuid_in('fa0463fa-e196-42f2-a369-b5e92d563ea7'), 'TurboGlide Zephyr', 'Stylish hoverboard with advanced propulsion.', 1499, now(), now());

INSERT INTO simple_ecommerce.products
    (id, name, description, price, created_on, last_updated_on) values
    (uuid_in('e48d9c39-7ec6-4c81-8f50-6fcfcf1585a7'), 'NanoFlex Prismatica', 'Flexible smartphone with high-resolution camera.', 3499, now(), now());

INSERT INTO simple_ecommerce.products
    (id, name, description, price, created_on, last_updated_on) values
    (uuid_in('f519d3a2-ff54-4df9-8c45-b8599e027c58'), 'HyperFusion Velocity', 'High-performance sports shoe.', 249.99, now(), now());

INSERT INTO simple_ecommerce.products
    (id, name, description, price, created_on, last_updated_on) values
    (uuid_in('8e1dee4e-759a-405c-b2f5-ad11b7c259c6'), 'QuantumPulse Synthetron', 'Digital synthesizer for creating unique sounds.', 799, now(), now());

 -- Insert Orders
INSERT INTO simple_ecommerce.orders
    (id, status, created_on, last_updated_on) values
    (uuid_in('8ec0fb6f-1fcf-4b27-9ad7-a00ae8a9f512'), 'IN_CREATION', now(), now());

INSERT INTO simple_ecommerce.orders
    (id, status, created_on, last_updated_on) values
    (uuid_in('1a0e3344-7bc6-479b-bcc6-2327b4575068'), 'WAITING_PAYMENT', now(), now());

INSERT INTO simple_ecommerce.orders
    (id, status, created_on, last_updated_on) values
    (uuid_in('1e1f2131-20e5-44dc-879c-4e596ada5123'), 'SENT', now(), now());

INSERT INTO simple_ecommerce.orders
    (id, status, created_on, last_updated_on) values
    (uuid_in('da0863eb-07ba-44cd-8eb6-d05d41e088fc'), 'DONE', now(), now());

-- Insert order items
INSERT INTO simple_ecommerce.order_items
    (id, order_id, product_id, product_quantity, created_on, last_updated_on) values
    (uuid_in('dbef6695-e825-4ca7-8671-706c2c448ff1'), uuid_in('8ec0fb6f-1fcf-4b27-9ad7-a00ae8a9f512'), uuid_in('b00e182d-852d-47c0-9feb-cb8c32eb04e3'), 2, now(), now());

INSERT INTO simple_ecommerce.order_items
    (id, order_id, product_id, product_quantity, created_on, last_updated_on) values
    (uuid_in('c32378bb-a15c-4e1e-bf23-e356ca6a767b'), uuid_in('1a0e3344-7bc6-479b-bcc6-2327b4575068'), uuid_in('fa0463fa-e196-42f2-a369-b5e92d563ea7'), 1, now(), now());

INSERT INTO simple_ecommerce.order_items
    (id, order_id, product_id, product_quantity, created_on, last_updated_on) values
    (uuid_in('d3dee60c-e79c-41fc-91b8-a24a4592fce1'), uuid_in('1a0e3344-7bc6-479b-bcc6-2327b4575068'), uuid_in('e48d9c39-7ec6-4c81-8f50-6fcfcf1585a7'), 1, now(), now());

INSERT INTO simple_ecommerce.order_items
    (id, order_id, product_id, product_quantity, created_on, last_updated_on) values
    (uuid_in('27263177-e7f5-4f8c-b14a-00ea8569b26d'), uuid_in('1e1f2131-20e5-44dc-879c-4e596ada5123'), uuid_in('f519d3a2-ff54-4df9-8c45-b8599e027c58'), 3, now(), now());

INSERT INTO simple_ecommerce.order_items
    (id, order_id, product_id, product_quantity, created_on, last_updated_on) values
    (uuid_in('02e53741-6fc1-4baf-98a9-332649f641ff'), uuid_in('da0863eb-07ba-44cd-8eb6-d05d41e088fc'), uuid_in('8e1dee4e-759a-405c-b2f5-ad11b7c259c6'), 1, now(), now());