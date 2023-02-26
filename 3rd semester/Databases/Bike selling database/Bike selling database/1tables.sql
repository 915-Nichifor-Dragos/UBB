-- CREATE DATABASE BicycleFactory
-- go

-- drop database BicycleFactory

use BicycleFactory
go

DROP TABLE IF EXISTS NumberOfPieces
DROP TABLE IF EXISTS Bicycles
DROP TABLE IF EXISTS Orders
DROP TABLE IF EXISTS Compositions
DROP TABLE IF EXISTS Managers
DROP TABLE IF EXISTS Employees
DROP TABLE IF EXISTS Customers
DROP TABLE IF EXISTS DeliveryCompanies
DROP TABLE IF EXISTS Depots 
DROP TABLE IF EXISTS Parts
DROP TABLE IF EXISTS Materials

CREATE TABLE Materials
(MaterialID INT PRIMARY KEY IDENTITY,
TypeOfMaterial varchar(50) NOT NULL,
Price int,
Density float check(Density <= 10) default 1,
)

CREATE TABLE Parts
(PartID INT PRIMARY KEY IDENTITY,
PartName varchar(50) NOT NULL,
Producer varchar(50) default 'not specified',
ProductionCost int default 500,
)

CREATE TABLE Depots
(DepotID INT PRIMARY KEY IDENTITY,
AddressDepot varchar(50) default 'not specified',
)

CREATE TABLE DeliveryCompanies
(DeliveryID INT PRIMARY KEY IDENTITY,
NameCompany varchar(50) UNIQUE NOT NULL,
PhoneNumber varchar(50),
Email varchar(50) default 'not specified',
)

CREATE TABLE Customers
(CustomerID INT PRIMARY KEY IDENTITY,
FirstName varchar(50) NOT NULL,
LastName varchar(50) NOT NULL,
PhoneNumber varchar(50) UNIQUE NOT NULL,
Email varchar(50) default 'not specified',
)

CREATE TABLE Employees
(EmployeeID INT PRIMARY KEY IDENTITY,
DepotID INT FOREIGN KEY REFERENCES Depots(DepotID) NOT NULL,
FirstName varchar(50) NOT NULL,
LastName varchar(50) NOT NULL,
BirthDate date,
Income int,
)

CREATE TABLE Managers
(ManagerID INT PRIMARY KEY IDENTITY,
DepotID INT FOREIGN KEY REFERENCES Depots(DepotID) NOT NULL UNIQUE,
FirstName varchar(50) NOT NULL,
LastName varchar(50) NOT NULL,
BirthDate date,
Income int
)

CREATE TABLE Compositions
(MaterialID INT FOREIGN KEY REFERENCES Materials(MaterialID) NOT NULL,
PartID INT FOREIGN KEY REFERENCES Parts(PartID) NOT NULL,
Quantity int default 5,
)

CREATE TABLE Orders
(OrderID INT PRIMARY KEY IDENTITY,
CustomerID INT FOREIGN KEY REFERENCES Customers(CustomerID) NOT NULL,
DeliveryID INT FOREIGN KEY REFERENCES DeliveryCompanies(DeliveryID) NOT NULL,
CardInfo varchar(50) default 'payment by cash',
ETA date,
AddressDelivery varchar(50) NOT NULL,
)

CREATE TABLE Bicycles
(BicycleID INT PRIMARY KEY IDENTITY,
OrderID INT FOREIGN KEY REFERENCES Orders(OrderID),
DepotID INT FOREIGN KEY REFERENCES Depots(DepotID) NOT NULL,
ColorBike varchar(50) default 'not specified',
TypeBike varchar(50) default 'not specified',
)

CREATE TABLE NumberOfPieces
(PartID INT FOREIGN KEY REFERENCES Parts(PartID) NOT NULL,
BicycleID INT FOREIGN KEY REFERENCES Bicycles(BicycleID) NOT NULL,
Quantity int default 1,
)

-- Material <--> Parts many to many
-- Parts --> Bicycles one to many
-- Depots --> Bicycles one to many 
-- Employees --> Depots one to many
-- Depots --> Managers one to one
-- Depots --> Emplyees one to many
-- Orders --> Bicycles one to many
-- Delivery Company --> Orders one to many
-- Customers --> Orders one to many


-- DROP DATABASE BicycleFactory
