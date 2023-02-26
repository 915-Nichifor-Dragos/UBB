USE BicycleFactory;

-- INSERTS THAT WORK WELL

INSERT INTO Materials (
TypeOfMaterial,
Density,
Price
) VALUES
('metal', 7.85, 100),
('rubber', 1.06, 50), 
('wires', 5.25, 160),
('paint', 1.50, 80),
('plastic', 0.917, 50),
('carbon', 4.23, 400),
('sponge', 1.93, 20)

INSERT INTO Parts (
PartName,
ProductionCost,
Producer
) VALUES
('wheel' ,150, 'Fox'),
('fork' ,500, 'Hangzhou'),
('breaks' ,600, 'Alpina'),
('suspension' ,1000, 'Lezyne'),
('saddle' ,50, 'Bell'),
('wheel' ,300, 'Hangzhou'),
('wheel' ,850, 'Lezyne'),
('suspension' ,1200, 'Lezyne'),
('suspension' ,900, 'Alpina'),
('saddle' ,120, 'Alpina'),
('fork' ,1500, 'Lezyne'),
('fork' ,800, 'Fox'),
('breaks' ,300, 'Hangzhou')

INSERT INTO Depots (
AddressDepot
) VALUES
('Stada Fabricii de Zahar 23, Cluj-Napoca'),
('Strada 1 Decembrie 1918 24, Brasov'),
('Calea Victoriei 25, Bucuresti'),
('Strada Arad 30, Arad'),
('Strada Constantin cel Mare, Bistrita')

INSERT INTO DeliveryCompanies (
NameCompany,
Email,
PhoneNumber
) VALUES
('Fast Curier Romania', 'fastcurier@email.ro', '0313123432'),
('Speedy', 'speedycurier@email.ro', '0314325665'),
('Posta Romana', 'postaromana@email.ro', '0312132654'),
('Fast Express', 'fastexpress@email.ro', '0313324432')

-- DeliveryCompanies with 'not specified' email

INSERT INTO DeliveryCompanies (
NameCompany,
PhoneNumber
) VALUES
('AliExpress', '0312321321'),
('eMag curier', '0314332421')

-- Done

INSERT INTO Customers (
FirstName,
LastName,
Email,
PhoneNumber
) VALUES
('Alex', 'Matei', 'alexmatei@gmail.com', '0734234632'),
('Silviu', 'Dorobant', 'abcdefg@gmail.com', '0789284855'),
('Sergiu', 'Afrei', 'sergiuafrei@gmail.com', '0744234338'),
('Ana', 'Gheorghe', 'anagheorghe@gmail.com', '0767902043'),
('Radu', 'Apreotesei', 'raduapreotesei@gmail.com', '0794334311'),
('Ana', 'Irina', 'anairina@gmail.com', '0799837543'),
('Sergiu', 'Munteanu', 'sergiumunteanu@gmail.com', '0788132312')

INSERT INTO Employees (
DepotID,
FirstName,
LastName,
BirthDate,
Income
) VALUES
(1, 'Alexandru', 'Simon', '20021014', 4000),
(1, 'Iulian', 'Andrici', '19830321', 3750),
(2, 'Angela', 'Ontrea', '19970603', 2200),
(3, 'Razvan', 'Petruta', '19881014', 8000),
(2, 'Iulia', 'Escobar', '19920228', 3500),
(4, 'Ciprian', 'Stelea', '19850427', 4700),
(3, 'Aurelian', 'Lopataru', '19911005', 8300),
(2, 'Sebastian', 'Tataru', '19700210', 10200)

INSERT INTO Managers (
FirstName,
LastName,
DepotID,
Income,
BirthDate
) VALUES
('Aurel', 'Mantarosie', 1, 10000, '19700321'),
('Ciprian', 'Porumbescu', 2, 15000, '20010509'),
('Ilie', 'Dughet', 3, 8000, '19830322'),
('Mirel', 'Luntraru', 4, 8000, '19910829')

INSERT INTO Compositions (
PartID,
MaterialID,
Quantity
) VALUES
(1, 1, 5),
(1, 2, 5),
(2, 1, 10),
(2, 4, 1),
(2, 6, 5),
(3, 1, 8),
(3, 3, 1),
(3, 5, 5),
(4, 1, 5),
(4, 4, 1),
(4, 5, 2),
(5, 2, 5),
(5, 7, 3),
(6, 1, 5),
(6, 2, 4),
(7, 1, 6),
(7, 2, 3),
(7, 4, 1),
(8, 1, 10),
(8, 5, 2),
(9, 1, 10),
(9, 5, 2),
(9, 3, 1),
(10, 2, 5),
(10, 7, 8),
(11, 1, 15),
(11, 4, 1),
(11, 6, 7),
(11, 3, 1),
(12, 1, 12),
(12, 4, 1),
(12, 6, 4),
(12, 3, 2),
(13, 1, 5),
(13, 5, 2),
(13, 3, 2),
(13, 6, 3)

INSERT INTO Orders (
CustomerID,
DeliveryID,
AddressDelivery,
CardInfo,
ETA
) VALUES
(1, 1, 'Strada General Traian Mosoiu 45', '4256 0311 6754 5433', '20221011'),
(2, 1, 'Bulevardul Eroilor 12', '2343 3249 9478 7438', '20221012'),
(3, 3, 'Strada Iuliu Maniu 67', '4256 0311 5768 9075', '20221017'),
(4, 2, 'Strada Virgil Fulicea 23', '3748 7546 2105 1941', '20221014'),
(3, 2, 'Strada Nuferilor 6', '0193 0493 9543 0124', '20221221'),
(3, 5, 'Strada Axente Banciu 21', '4256 0311 0693 4332', '20221112')

INSERT INTO Bicycles (
DepotID,
OrderID,
ColorBike,
TypeBike
) VALUES
(1, 2, 'red', 'mountain'),
(1, 1, 'blue', 'mountain'),
(2, 4, 'green', 'downhill'),
(1, 3, 'black', 'city'),
(3, 5, 'grey', 'downhill'),
(4, 5, 'black', 'mountain'),
(4, 6, 'green', 'city')

INSERT INTO NumberOfPieces(
BicycleID,
PartID,
Quantity 
) VALUES
(1, 1, 2),
(1, 2, 1),
(1, 3, 2),
(1, 4, 2),
(1, 5, 1),
(2, 6, 2),
(2, 12, 1),
(2, 13, 2),
(2, 4, 2),
(2, 10, 1),
(3, 1, 2),
(3, 11, 1),
(3, 3, 2),
(3, 4, 2),
(3, 10, 1),
(4, 7, 2),
(4, 12, 1),
(4, 3, 2),
(4, 9, 2),
(4, 5, 1),
(5, 6, 2),
(5, 11, 1),
(5, 13, 2),
(5, 9, 2),
(5, 5, 1),
(6, 6, 2),
(6, 11, 1),
(6, 13, 2),
(6, 9, 2),
(6, 5, 1),
(7, 6, 2),
(7, 11, 1),
(7, 13, 2),
(7, 9, 2),
(7, 5, 1)

-- INSERTS THAT VIOLATE REFERENTIAL INTEGRITY CONSTRAINTS

INSERT INTO Materials (
TypeOfMaterial,
Density,
Price
) VALUES
('water', 10.5, 20)

INSERT INTO Employees (
BirthDate
) VALUES
('20221014')

INSERT INTO Compositions (
Quantity
) VALUES
(10),
(20),
(15)

-- INSERTS THAT USE DEFAULT PARAMETERS

INSERT INTO Bicycles (
DepotID,
ColorBike,
TypeBike
) VALUES 
(4, 'grey', 'city'),
(1, 'red', 'city')

INSERT INTO Employees (
DepotID,
FirstName,
LastName
) VALUES
(1, 'Andrei', 'Ciobanu'),
(1, 'Laura', 'Nufar')

INSERT INTO Customers (
FirstName,
LastName,
PhoneNumber
) VALUES
('Alex', 'Iasmin', '0734433362'),
('Andreea', 'Botoroaga', '0794358455')

INSERT INTO DeliveryCompanies (
NameCompany,
Email
) VALUES
('Fashion', 'fashion@email.ro'),
('DPD', 'dpd@email.ro')

-- SHOWS EVERYTHING FOR ALL THE TABLES

SELECT * FROM Employees
SELECT * FROM Depots
SELECT * FROM Managers
SELECT * FROM Orders
SELECT * FROM DeliveryCompanies
SELECT * FROM Materials
SELECT * FROM Parts
SELECT * FROM Compositions
SELECT * FROM Bicycles
SELECT * FROM NumberOfPieces
SELECT * FROM Customers