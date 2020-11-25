SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE orders ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE order_menu ALTER COLUMN SEQ RESTART WITH 1;
ALTER TABLE menu ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE menu_group ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE menu_product ALTER COLUMN SEQ RESTART WITH 1;
ALTER TABLE tables ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE table_group ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE product ALTER COLUMN ID RESTART WITH 1;

TRUNCATE TABLE orders;
TRUNCATE TABLE order_menu;
TRUNCATE TABLE menu;
TRUNCATE TABLE menu_group;
TRUNCATE TABLE menu_product;
TRUNCATE TABLE tables;
TRUNCATE TABLE table_group;
TRUNCATE TABLE product;

SET FOREIGN_KEY_CHECKS = 1;