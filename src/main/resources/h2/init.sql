create table purchase_order (
  id bigint auto_increment,
  user_id bigint,
  product_id varchar(50),
  amount decimal(20, 2),
  status varchar(50),
  primary key (id)
);