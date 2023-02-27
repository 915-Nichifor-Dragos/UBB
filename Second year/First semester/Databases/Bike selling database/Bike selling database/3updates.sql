USE BicycleFactory;

-- UPDATES

SELECT * FROM DeliveryCompanies
UPDATE DeliveryCompanies
SET NameCompany = 'eMAG'
WHERE NameCompany = 'eMag curier'
SELECT * FROM DeliveryCompanies

SELECT * FROM Employees
UPDATE Employees
SET Income = Income + 500
WHERE BirthDate < '19950101' or BirthDate is NULL
SELECT * FROM Employees

SELECT * FROM Customers
UPDATE Customers
SET Email = lower(FirstName) + lower(LastName) + '@gmail.com'
WHERE Email = 'not specified' or PhoneNumber = '0789284855'
SELECT * FROM Customers