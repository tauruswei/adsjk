source /sql/nacos_config.sql
source /sql/ddl.sql
source /sql/cosd.sql

CREATE USER 'cosd'@'%' IDENTIFIED BY 'cosd@123.com';
GRANT ALL PRIVILEGES ON *.* TO 'cosd'@'%';
FLUSH PRIVILEGES;
