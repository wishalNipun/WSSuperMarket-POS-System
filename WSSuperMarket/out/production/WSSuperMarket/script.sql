DROP DATABASE IF EXISTS WSSuperMarket;
CREATE DATABASE IF NOT EXISTS  WSSuperMarket;
SHOW DATABASES ;
USE  WSSuperMarket;
DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User`(
    id VARCHAR(10) NOT NULL,
    name VARCHAR (30)NOT NULL,
    role VARCHAR (20)NOT NULL,
    telNo VARCHAR (13),
    email VARCHAR (30),
    userName VARCHAR (20)NOT NULL,
    password VARCHAR (20)NOT NULL,
    CONSTRAINT PRIMARY KEY (id)

    );
INSERT INTO `User` values('U00-001','wishal Nipun','Administrator','+94701234567','wishal@gmail.com','wisha','1234');
INSERT INTO `User` values('U00-002','Nipun Siriwardana','Cashier','+94703456789','nipun@gmail.com','nip','1234');

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    CustId VARCHAR(10),
    CustTitle VARCHAR(5),
    CustName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    CustAddress VARCHAR(30),
    City VARCHAR(20),
    Province VARCHAR(20),
    PostalCode VARCHAR(9),
    CONSTRAINT PRIMARY KEY (CustId)
    );



DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    ItemCode VARCHAR(10),
    Description VARCHAR(50),
    PackSize VARCHAR(20),
    UnitPrice DOUBLE(6,2) DEFAULT 0.00,
    QtyOnHand INT(5) DEFAULT 0,
    CONSTRAINT PRIMARY KEY (ItemCode)
    );

SHOW TABLES ;
DESCRIBE Item;
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    OrderId VARCHAR(10),
    CustId VARCHAR(10),
    OrderDate DATE,
    Time VARCHAR(15),
    Cost DOUBLE,
    CONSTRAINT PRIMARY KEY (OrderId),
    CONSTRAINT FOREIGN KEY (CustId) REFERENCES Customer(CustId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order`;
DROP TABLE IF EXISTS `OrderDetail`;
CREATE TABLE IF NOT EXISTS `OrderDetail`(

    OrderId VARCHAR(10),
    ItemCode VARCHAR(10),
    OrderQty INT(11),
    price DOUBLE,
    Discount DECIMAL(6,2),
    CONSTRAINT PRIMARY KEY (ItemCode, OrderId),
    CONSTRAINT FOREIGN KEY (ItemCode) REFERENCES Item(ItemCode) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (OrderId) REFERENCES `Order`(OrderId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `OrderDetail`;