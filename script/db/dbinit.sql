source /sql/cosd.sql
source /sql/pool.sql

CREATE USER 'cosd'@'%' IDENTIFIED BY 'cosd@123.com';
GRANT ALL PRIVILEGES ON *.* TO 'cosd'@'%';
FLUSH PRIVILEGES;
