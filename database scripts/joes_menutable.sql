#category table
INSERT INTO `categories` (`description`) VALUES ('drinks');
INSERT INTO `categories` (`description`) VALUES ('appetier');
INSERT INTO `categories` (`description`) VALUES ('entrees');


#menu table
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('water','tap water in a glass',0.00,NULL,1);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('coke','coke poured in glass',1.99,NULL,1);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('pepsi','pepsi poured in glass',1.99,NULL,1);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('lemonade','freshly made lemonnade in a glass',1.99,NULL,1);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('fries','sliced and deep fried potatos',4.99,NULL,3);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('sweet potato fries','sliced and deep fried sweet potatos',4.99,NULL,3);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('chicken dinner','seasoned chicken breast grilled with side of fries',19.99,NULL,3);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('burger','burger grilled medium with a side of fries',12.99,NULL,3);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('cheese burger','cheese burger grilled medium with a side of fries',13.50,NULL,3);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('chicken finger','chicken fingers served in a tray',9.99,NULL,3);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('caeser salad','caeser salad with sliced tomatos',6.99,NULL,2);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('chicken soup','chicken soup served hot',7.99,NULL,2);
INSERT INTO `menu` (`item_name`,`item_description`,`item_price`,`image`,category_id) VALUES ('ribs','seasond ribs grilled and served',14.99,NULL,3);
