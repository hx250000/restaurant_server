ALTER TABLE restaurant_dish
    CHANGE COLUMN is_spicy spicy TINYINT(1) NOT NULL DEFAULT 0;
ALTER TABLE restaurant_dish
    DROP COLUMN is_spicy;

