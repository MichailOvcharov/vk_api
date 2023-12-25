CREATE TABLE user_info
(
  user_id  		integer  UNIQUE,
  user_f_name 	varchar(100),
  user_l_name 	varchar(100),
  user_b_date 	date,
  user_city 	varchar(100),
  user_contacts varchar(100),
  CONSTRAINT id_user_id_pkey PRIMARY KEY (user_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_info
  OWNER TO admin;