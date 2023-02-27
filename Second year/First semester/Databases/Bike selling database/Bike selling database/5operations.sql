USE BicycleFactory;

-- UNION

-- All employees and managers that were born after 12-10-1995 or that have income less than 3000

SELECT E.EmployeeID, E.LastName, E.FirstName , E.DepotID, E.BirthDate, E.Income
FROM Employees E
WHERE E.BirthDate > '19951012' or E.Income < 3000
UNION
SELECT M.ManagerID, M.LastName, M.FirstName , M.DepotID, M.BirthDate, M.Income
FROM Managers M
WHERE M.BirthDate > '19951012' or M.Income < 3000

-- All customers, employees and delivery companies that have unspecified mail, birthdate or phone number

SELECT NameCompany AS Name FROM DeliveryCompanies DC
WHERE DC.Email = 'not specified' or DC.PhoneNumber is null
UNION
SELECT LastName + ' ' +  FirstName as NameCustomer FROM Customers C
WHERE C.Email = 'not specified'
UNION
SELECT LastName + ' ' + FirstName as NameEmployee FROM Employees E
WHERE E.BirthDate is null

-- INTERSECTION

-- All the materials in ('wires', 'plastic', 'paint', 'rubber', 'metal') with price between 40 and 90 and density between 1.5 and 5

SELECT TypeOfMaterial FROM Materials
WHERE (Price BETWEEN 40 AND 90) AND TypeOfMaterial IN ('wires', 'plastic', 'paint', 'rubber', 'metal')
INTERSECT
SELECT TypeOfMaterial FROM Materials
WHERE Density between 1.5 and 5

-- All employees that have an income between 2000 and 4000, their name is one of ('Alexandru', 'Andreeas', 'Angela') and are born before 01-01-1995

SELECT LastName, FirstName FROM Employees
WHERE Income between 2000 and 4000 or FirstName in ('Alexandru', 'Andreeas', 'Angela')
INTERSECT
SELECT LastName, FirstName FROM Employees
WHERE BirthDate < '19950101'

-- EXCEPT

-- All the bicycles that have color different than red, blue and green

SELECT * FROM Bicycles
EXCEPT
SELECT * FROM Bicycles
WHERE ColorBike NOT IN ('red', 'blue', 'green')

-- All the delivery companies that do not have a shipping assigned

SELECT * FROM DeliveryCompanies
EXCEPT
SELECT * FROM DeliveryCompanies DC
WHERE DC.DeliveryID not in (SELECT O.DeliveryID FROM Orders O)

-- JOINS

-- The materials, the quantity used by Fox company to produce each part, the number of Fox parts that each bycicle uses (2 many 2 many inner join).

SELECT M.TypeOfMaterial, C.Quantity, P.PartName, NP.Quantity, B.BicycleID, B.TypeBike,  P.Producer FROM Materials M
inner join Compositions as C on
M.MaterialID = C.MaterialID
inner join Parts as P on
P.PartID = C.PartID and P.Producer = 'Fox'
inner join NumberOfPieces as NP on
P.PartID = NP.PartID
inner join Bicycles as B on
B.BicycleID = NP.BicycleID
ORDER BY C.Quantity

-- All the depots with corresponding managers

SELECT D.AddressDepot, M.ManagerID, M.LastName, M.FirstName
FROM Depots as D
full join Managers as M on
M.DepotID = D.DepotID 

-- All the bycicles with the address of the depot they are stored at

SELECT B.TypeBike, B.ColorBike, D.DepotID, D.AddressDepot
FROM Bicycles as B
left join Depots as D on
D.DepotID = B.DepotID

-- All the orders with each person's address and the name of the delivery company that will take care of the order (3 tables)

SELECT C.LastName, C.FirstName, C.Email, C.PhoneNumber, O.AddressDelivery, DC.NameCompany, B.BicycleID, B.TypeBike, B.ColorBike
FROM Customers C
right join Orders as O on
O.CustomerID = C.CustomerID
right join DeliveryCompanies as DC on
O.CustomerID = DC.DeliveryID
inner join Bicycles as B on
B.OrderID = O.OrderID

-- The orders each delivery company has to fulfill

SELECT  DC.NameCompany, O.OrderID, O.AddressDelivery FROM Orders O
right join DeliveryCompanies DC on
DC.DeliveryID = O.DeliveryID

-- IN OPERATOR

-- All the materials with Quantity > 4 used by Hangzhou in order to produce parts

SELECT M.TypeOfMaterial, M.Price FROM Materials as M
WHERE MaterialID in (SELECT MaterialID FROM Compositions as C
					 WHERE C.MaterialID = M.MaterialID and Quantity > 4 and 
					       C.PartID in (SELECT P.PartID FROM Parts as P
						               WHERE P.Producer = 'Hangzhou')) 

-- The destination address and ETA of the orders in which the bank card number starts with "4256 0311" (are ING clients).

SELECT * FROM Orders
SELECT O.AddressDelivery, O.ETA, O.CardInfo FROM Orders as O
WHERE O.CustomerID in (SELECT C.CustomerID FROM Customers as C
					   WHERE C.CustomerID = O.CustomerID) and O.CardInfo LIKE '4256 0311%'
-- EXISTS OPERATOR

-- Selects the top 5 parts that contain metal, ordered by the production cost descending

SELECT TOP 5 P.PartName, P.Producer, P.ProductionCost FROM Parts P
WHERE EXISTS (SELECT * FROM Compositions C 
			  WHERE C.PartID = P.PartID and C.MaterialID = (SELECT M.MaterialID FROM Materials M
			                                                WHERE M.TypeOfMaterial = 'metal'))
ORDER BY P.ProductionCost DESC

-- Selects the customers that placed at least 1 order

SELECT C.FirstName, C.LastName FROM Customers C
WHERE EXISTS (SELECT O.CustomerID FROM Orders O
			  WHERE O.CustomerID = C.CustomerID)

-- FROM OPERATOR

-- Show the current salary and the salary increased by 1000 of the managers, along the address of the depots they work in

SELECT M.FirstName, M.LastName, M.Income as CurrentIncome, M.Income + 1000 as IncreasedSalary, AddressDepot
FROM (
	SELECT M.ManagerID, M.Income, M.FirstName, M.LastName, M.DepotID
	FROM Managers M
	WHERE M.BirthDate <= '19950101'
	)M
	inner join Depots D on D.DepotID = M.DepotID
ORDER BY CurrentIncome ASC
 
 -- All the bicycles in depots managed by Mantarosie and Porumbescu, along their status of shipping

 SELECT B.BicycleID, B.DepotID, B.OrderID, B.TypeBike, B.ColorBike, O.AddressDelivery, O.ETA, C.LastName, C.FirstName, C.PhoneNumber, C.Email
 FROM (
	  SELECT B.BicycleID, B.ColorBike, B.OrderID, B.TypeBike, B.DepotID
	  FROM Bicycles B
	  WHERE B.DepotID in (SELECT M.DepotID FROM Managers M
						WHERE M.LastName = 'Mantarosie' or M.LastName = 'Porumbescu'
						)
	  )B
	  full join Orders O on O.OrderID = B.OrderID
							full join Customers C on O.CustomerID = C.CustomerID

-- GROUP BY CLAUSE

-- The name of the and the email address of the customers, and how many orders each of them have

SELECT COUNT(O.OrderID) as NumberOfOrders, C.FirstName, C.LastName, C.Email FROM Orders O
full join Customers as C on O.CustomerID = C.CustomerID
GROUP BY C.FirstName, C.LastName, C.Email
HAVING C.Email in (SELECT C.Email FROM Customers C WHERE C.Email != 'not specified')
ORDER BY NumberOfOrders ASC

-- The maximum quantity of each material used in order to create a part

SELECT MAX(C.Quantity) as MaximumQuantity, M.TypeOfMaterial FROM Materials M, Compositions C
WHERE C.MaterialID = M.MaterialID
GROUP BY M.TypeOfMaterial

-- The production cost of each fork and the material that costed the minumum minimum amount in order to create it

SELECT SUM(C.Quantity * M.Price) as TotalProductionCost, P.PartName, P.Producer, MIN(C.Quantity * M.Price) as MinimumCostMaterial FROM Parts P, Compositions C, Materials M
WHERE P.PartID = C.PartID and C.MaterialID = M.MaterialID
GROUP BY P.PartName, P.Producer
HAVING P.PartName = 'fork'

-- The average income of the employees working at each depot

SELECT AVG(E.Income) as AverageIncomeEmployee, D.DepotID, D.AddressDepot FROM Employees E, Depots D
WHERE E.DepotID = D.DepotID
GROUP BY D.DepotID, D.AddressDepot
ORDER BY D.DepotID ASC

-- All the legitimate delivery companies and the number of orders they have to fulfill (a company is legitimate if it has an email address)

SELECT COUNT(O.DeliveryID) as NrOrders, DC.NameCompany, DC.Email FROM Orders O, DeliveryCompanies DC
WHERE DC.DeliveryID = O.DeliveryID
GROUP BY NameCompany, DC.Email
HAVING DC.Email != 'not specified'
ORDER BY NrOrders ASC

-- ALL/ANY

-- Materials with a density between 4 and 10 which are used for creating suspensions

SELECT M.TypeOfMaterial FROM Materials M
WHERE (M.Density between 4 and 10) and M.TypeOfMaterial = ANY (SELECT M.TypeOfMaterial FROM Compositions C
															  WHERE C.MaterialID = M.MaterialID and C.PartID = ANY (SELECT P.PartID FROM Parts P
																												 WHERE P.PartID = C.PartID and P.PartName = 'suspension'))

-- The materials used to create forks

SELECT M.TypeOfMaterial FROM Materials M
WHERE M.MaterialID = ANY (SELECT M.MaterialID FROM Compositions C
						  WHERE C.MaterialID = M.MaterialID and C.PartID = ANY (SELECT P.PartID FROM Parts P
																			WHERE P.PartID = C.PartID and P.PartName = 'fork'))

-- All the names and email addresses of the customers that have not yet purchased a bike

SELECT C.LastName, C.FirstName, C.Email FROM Customers C
WHERE C.CustomerID != ALL (SELECT O.CustomerID FROM Orders O)

-- All the delivery companies that do not have orders to fulfill

SELECT DC.NameCompany FROM DeliveryCompanies DC
WHERE DC.DeliveryID != ALL (SELECT O.DeliveryID FROM Orders O)

-- Rewrite ALL/ANY

-- 1)
SELECT M.TypeOfMaterial FROM Materials M
WHERE (M.Density between 4 and 10) and M.TypeOfMaterial in (SELECT M.TypeOfMaterial FROM Compositions C
															WHERE C.MaterialID = M.MaterialID and C.PartID in(SELECT P.PartID FROM Parts P
																												WHERE P.PartID = C.PartID and P.PartName = 'suspension'))

-- 2)
SELECT C.LastName, C.FirstName, C.Email FROM Customers C
WHERE C.CustomerID not in (SELECT O.CustomerID FROM Orders O)

-- TOP STATEMENT

-- Top 25% of employees, ordered by income

SELECT TOP 25 PERCENT * FROM Employees E
ORDER BY E.Income


-- ALL/ ANY WITH AGREGATION OPERATORS

--1)
SELECT * FROM Employees E1
WHERE E1.Income > ALL (SELECT E2.Income FROM Employees E2 WHERE E1.EmployeeID != E2.EmployeeID)

SELECT * FROM Employees E1
WHERE E1.Income > (SELECT MAX(E2.Income) FROM Employees E2 WHERE E1.EmployeeID != E2.EmployeeID)

--2)
SELECT DISTINCT * FROM Materials M
WHERE M.MaterialID = ANY (SELECT C.MaterialID FROM Compositions C 
											  WHERE C.PartID in (SELECT P.PartID FROM Parts P
																 WHERE P.Producer = 'Hangzhou'))

SELECT DISTINCT * FROM Materials M
WHERE 0 < (SELECT COUNT(*) from (SELECT C.MaterialID FROM Compositions C 
											  WHERE M.MaterialID = C.MaterialID and C.PartID in (SELECT P.PartID FROM Parts P
																 WHERE P.Producer = 'Hangzhou')) ABC)