source /sql/cosd.sql

CREATE USER 'cosd'@'%' IDENTIFIED BY 'cosd@123.com';
GRANT ALL PRIVILEGES ON *.* TO 'cosd'@'%';
FLUSH PRIVILEGES;
