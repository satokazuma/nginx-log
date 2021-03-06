DROP SCHEMA IF EXISTS ranceworks;
CREATE SCHEMA ranceworks;

CREATE TABLE ranceworks.access_log(
  id INT AUTO_INCREMENT,
  date_gmt DATE,
  time_gmt TIME,
  city VARCHAR(64),
  country_code VARCHAR(3),
  status_code VARCHAR(3),
  uri VARCHAR(255),
  remote_addr VARCHAR(39),
  http_referer varchar(512),
  http_user_agent VARCHAR(255),
  store_time TIMESTAMP,
  PRIMARY KEY (id));
  
  CREATE INDEX ACCESS_LOG_URI ON ranceworks.access_log(uri);