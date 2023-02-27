USE BicycleFactory

-- generates a random string

GO 
CREATE OR ALTER PROCEDURE generateRandomString @stringValue VARCHAR(20) OUTPUT AS
BEGIN
	DECLARE @length INT
	DECLARE @charPool VARCHAR(55)
	DECLARE @charPoolLength INT
	SET @charPool = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
	SET @charPoolLength = LEN(@charPool)
	SET @length = FLOOR(RAND() * 10 + 5)
	SET @stringValue = ''
	WHILE @length > 0
	BEGIN
		SET @stringValue = @stringValue + SUBSTRING(@charPool, CONVERT(INT, RAND() * @charPoolLength) + 1, 1)
		SET @length = @length - 1
	END
END

-- generates a random phone number

GO 
CREATE OR ALTER PROCEDURE generateRandomPhoneNumber @stringValue VARCHAR(10) OUTPUT AS
BEGIN
	DECLARE @length INT
	DECLARE @charPool VARCHAR(10)
	DECLARE @charPoolLength INT
	SET @charPool = '0123456789'
	SET @charPoolLength = LEN(@charPool)
	SET @length = 8
	SET @stringValue = '07'
	WHILE @length > 0
	BEGIN
		SET @stringValue = @stringValue + SUBSTRING(@charPool, CONVERT(INT, RAND() * @charPoolLength) + 1, 1)
		SET @length = @length - 1
	END
END

-- populates the table Customers

GO
CREATE OR ALTER PROCEDURE populateTableCustomers (@numberOfRows INT) AS
	DECLARE @stringValueFirst VARCHAR(10)
	DECLARE @stringValueLast VARCHAR(10)
	DECLARE @phoneNumber VARCHAR(10)
	WHILE @numberOfRows > 0 
	BEGIN
		EXEC generateRandomString @stringValueFirst OUTPUT
		EXEC generateRandomString @stringValueLast OUTPUT
		EXEC generateRandomPhoneNumber @phoneNumber OUTPUT
		INSERT INTO Customers(FirstName, LastName, PhoneNumber, Email) 
		VALUES (@stringValueFirst, @stringValueLast, @phoneNumber, @stringValueFirst + @stringValueLast + '@gmail.com')
		SET @numberOfRows = @numberOfRows - 1
END

-- populates the table DeliveryCompanies

GO
CREATE OR ALTER PROCEDURE populateTableDeliveryCompanies (@numberOfRows INT) AS
	DECLARE @stringValue VARCHAR(10)
	WHILE @numberOfRows > 0 
	BEGIN
		EXEC generateRandomString @stringValue OUTPUT
		INSERT INTO DeliveryCompanies(NameCompany, Email) 
		VALUES (@stringValue, @stringValue + '@gmail.com')
		SET @numberOfRows = @numberOfRows - 1
END

-- populates the table Orders

GO
CREATE OR ALTER PROCEDURE populateTableOrders (@numberOfRows INT) AS
	DECLARE @stringValue VARCHAR(50)
	DECLARE @stringValue2 VARCHAR(50)
	DECLARE @customerID INT
	DECLARE @deliveryID INT
	WHILE @numberOfRows > 0 
	BEGIN
		EXEC generateRandomString @stringValue OUTPUT
		EXEC generateRandomString @stringValue2 OUTPUT
		SET @customerID = (SELECT TOP 1 CustomerID FROM Customers ORDER BY NEWID())
		SET @deliveryID = (SELECT TOP 1 DeliveryID FROM DeliveryCompanies ORDER BY NEWID())
		WHILE EXISTS (SELECT * FROM Orders WHERE DeliveryID = @deliveryID AND CustomerID = @customerID)
		BEGIN
			SET @deliveryID = (SELECT TOP 1 DeliveryID FROM Orders ORDER BY NEWID())
			SET @customerID = (SELECT TOP 1 CustomerID FROM Orders ORDER BY NEWID())
		END
		INSERT INTO Orders(CustomerID, DeliveryID, CardInfo, ETA, AddressDelivery)
		VALUES (@customerID, @deliveryID, @stringValue, '20021010', @stringValue2)
		SET @numberOfRows = @numberOfRows - 1
END

-- populates the table Discounts

GO
CREATE OR ALTER PROCEDURE populateTableDiscounts (@numberOfRows INT) AS
	DECLARE @orderID INT
	WHILE @numberOfRows > 0 
	BEGIN
		SET @orderID = (SELECT TOP 1 OrderID FROM Orders ORDER BY NEWID())
		WHILE EXISTS (SELECT * FROM Discounts WHERE OrderID = @orderID)
		BEGIN
			SET @orderID = (SELECT TOP 1 OrderID FROM Orders ORDER BY NEWID())
		END
		INSERT INTO Discounts(DiscountID, OrderID, TheDiscount, ExpirationDate)
		VALUES (@numberOfRows, @orderID, 25, '20221212')
		SET @numberOfRows = @numberOfRows - 1
END

-- populates the table Depots

GO
CREATE OR ALTER PROCEDURE populateTableDepots (@numberOfRows INT) AS
	DECLARE @stringValue VARCHAR(10)
	WHILE @numberOfRows > 0 
	BEGIN
		EXEC generateRandomString @stringValue OUTPUT
		INSERT INTO Depots(AddressDepot) 
		VALUES (@stringValue)
		SET @numberOfRows = @numberOfRows - 1
END

-- populates the table Employees

GO
CREATE OR ALTER PROCEDURE populateTableEmployees (@numberOfRows INT) AS
	DECLARE @stringValueFirst VARCHAR(50)
	DECLARE @stringValueLast VARCHAR(50)
	DECLARE @depotID INT
	WHILE @numberOfRows > 0 
	BEGIN
		EXEC generateRandomString @stringValueFirst OUTPUT
		EXEC generateRandomString @stringValueLast OUTPUT
		SET @depotID = (SELECT TOP 1 DepotID FROM Depots ORDER BY NEWID())
		WHILE EXISTS (SELECT * FROM Employees WHERE DepotID = @depotID)
		BEGIN
			SET @depotID = (SELECT TOP 1 DepotID FROM Depots ORDER BY NEWID())
		END
		INSERT INTO Employees(DepotID, FirstName, LastName, BirthDate, Income)
		VALUES (@depotID, @stringValueFirst, @stringValueLast, '19950408', 4299)
		SET @numberOfRows = @numberOfRows - 1
END

-- populates the table Parts

GO
CREATE OR ALTER PROCEDURE populateTableParts (@numberOfRows INT) AS
	DECLARE @stringValue VARCHAR(10)
	WHILE @numberOfRows > 0 
	BEGIN
		EXEC generateRandomString @stringValue OUTPUT
		INSERT INTO Parts(PartName) 
		VALUES (@stringValue)
		SET @numberOfRows = @numberOfRows - 1
END

-- populates the table Materials

GO
CREATE OR ALTER PROCEDURE populateTableMaterials (@numberOfRows INT) AS
	DECLARE @stringValue VARCHAR(10)
	WHILE @numberOfRows > 0 
	BEGIN
		EXEC generateRandomString @stringValue OUTPUT
		INSERT INTO Materials(TypeOfMaterial) 
		VALUES (@stringValue)
		SET @numberOfRows = @numberOfRows - 1
END

-- populates the table Compositions

GO
CREATE OR ALTER PROCEDURE populateTableCompositions (@numberOfRows INT) AS
	DECLARE @materialID INT
	DECLARE @partID INT
	WHILE @numberOfRows > 0 
	BEGIN
		SET @materialID = (SELECT TOP 1 MaterialID FROM Materials ORDER BY NEWID())
		SET @partID = (SELECT TOP 1 PartID FROM Parts ORDER BY NEWID())
		WHILE EXISTS (SELECT * FROM Compositions WHERE PartID = @partID AND MaterialID = @materialID)
		BEGIN
			SET @partID = (SELECT TOP 1 PartID FROM Parts ORDER BY NEWID())
			SET @materialID = (SELECT TOP 1 MaterialID FROM Materials ORDER BY NEWID())
		END
		INSERT INTO Compositions(MaterialID, PartID, Quantity)
		VALUES (@materialID, @partID, 4)
		SET @numberOfRows = @numberOfRows - 1
END

-- CREATE THE VIEWS

-- a view with a SELECT statement operating on one table
GO
CREATE OR ALTER VIEW employeesWithIncomeHigherThan3000 AS
	SELECT * FROM Employees E
	WHERE E.Income > 3000

--  a view with a SELECT statement operating on at least 2 tables
GO
CREATE OR ALTER VIEW ordersAndDiscounts AS
	SELECT D.OrderID, MAX(D.TheDiscount) as MaxDiscount, D.ExpirationDate FROM Discounts D
	INNER JOIN Orders O on O.OrderID = D.OrderID
	GROUP BY D.OrderID, D.ExpirationDate

-- a view with a SELECT statement that has a GROUP BY clause and operates on at least 2 tables
GO
CREATE OR ALTER VIEW materialsUsedForPartsWithQuantity AS
	SELECT M.TypeOfMaterial, P.PartName, C.Quantity FROM Materials M
	INNER JOIN Compositions C ON C.MaterialID = M.MaterialID
	INNER JOIN Parts P ON P.PartID = C.PartID

GO