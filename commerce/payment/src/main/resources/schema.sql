CREATE TABLE IF NOT EXISTS payments
(
    payment_id     UUID PRIMARY KEY,
    order_id       UUID    NOT NULL,
    total_payment  REAL,
    delivery_total REAL,
    fee_total      REAL,
    payment_state  VARCHAR NOT NULL
);
