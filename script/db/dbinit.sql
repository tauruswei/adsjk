source /sql/cosd.sql
source /sql/pool.sql

CREATE USER 'cosd'@'%' IDENTIFIED BY 'bm5jenZpY3ppb3Z1Y3puZyA';
GRANT ALL PRIVILEGES ON *.* TO 'cosd'@'%';
FLUSH PRIVILEGES;
