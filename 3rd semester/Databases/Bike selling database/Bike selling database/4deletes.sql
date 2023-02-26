USE BicycleFactory;

-- DELETES

SELECT * FROM Managers
DELETE FROM Managers
WHERE FirstName LIKE '_l%'
SELECT * FROM Managers

SELECT * FROM Employees
DELETE FROM Employees
WHERE DepotID not in (2, 1, 4, 5)
SELECT * FROM Employees

SELECT * FROM Bicycles
UPDATE Bicycles
SET DepotID = 2
WHERE DepotID = 3
SELECT * FROM Bicycles

SELECT * FROM Employees
DELETE FROM Employees
WHERE Income BETWEEN 2000 and 3500
SELECT * FROM Employees