source /sql/nacos_config.sql
source /sql/ddl.sql

CREATE USER 'cosd'@'%' IDENTIFIED BY 'cosd@123.com';
GRANT ALL PRIVILEGES ON *.* TO 'cosd'@'%';
FLUSH PRIVILEGES;
