USE BicycleFactory;

-- modify the type of a column

GO
CREATE OR ALTER PROCEDURE modifyMaterialPriceToSmallint
AS
	ALTER TABLE Materials ALTER COLUMN Price SMALLINT

GO
CREATE OR ALTER PROCEDURE modifyMaterialPriceToInt
AS
	ALTER TABLE Materials ALTER COLUMN Price INT

-- exec modifyMaterialPriceToDecimal
-- exec modifyMaterialPriceToInt



-- add/remove a column

GO
CREATE OR ALTER PROCEDURE deletePhoneNumberDelivery
AS
	ALTER TABLE DeliveryCompanies DROP COLUMN PhoneNumber

GO
CREATE OR ALTER PROCEDURE addPhoneNumberDelivery
AS
	ALTER TABLE DeliveryCompanies ADD PhoneNumber varchar(50)

-- exec deletePhoneNumberDelivery
-- exec addPhoneNumberDelivery



-- add/remove a DEFAULT constraint

GO
CREATE OR ALTER PROCEDURE addIncomes
AS
	ALTER TABLE Employees ADD CONSTRAINT default_income_employees default(3000) FOR Income
	ALTER TABLE Managers ADD CONSTRAINT default_income_managers default(1000) FOR Income


GO
CREATE OR ALTER PROCEDURE deleteIncomes
AS
	ALTER TABLE Employees DROP CONSTRAINT default_income_employees
	ALTER TABLE Managers DROP CONSTRAINT default_income_managers

 -- exec addIncomes
 -- exec deleteIncomes
 


-- create/drop a table

GO
CREATE OR ALTER PROCEDURE addTableDiscounts
AS
	CREATE TABLE Discounts (
		DiscountID INT NOT NULL,
		OrderID int NOT NULL,
		TheDiscount int not null,
		ExpirationDate varchar(50)  NOT NULL,
		CONSTRAINT PK_DISCOUNT PRIMARY KEY(DiscountID)
		)

GO
CREATE OR ALTER PROCEDURE deleteTableDiscounts
AS
	DROP TABLE Discounts

-- exec addTableDiscounts
-- exec deleteTableDiscounts



-- add/remove a primary key

GO
CREATE OR ALTER PROCEDURE addPrimaryKeyOrderIDDiscount
AS
	ALTER TABLE Discounts
		DROP CONSTRAINT PK_DISCOUNT
	ALTER TABLE Discounts
		ADD CONSTRAINT PK_DISCOUNT PRIMARY KEY(DiscountID, TheDiscount)
	
GO
CREATE OR ALTER PROCEDURE removePrimaryKeyOrderIDDiscount
AS
	ALTER TABLE Discounts
		DROP CONSTRAINT PK_DISCOUNT
	ALTER TABLE Discounts
		ADD CONSTRAINT PK_DISCOUNT PRIMARY KEY(DiscountID)

-- exec addPrimaryKeyOrderIDDiscount
-- exec removePrimaryKeyOrderIDDiscount



--  add/remove a foreign key

GO
CREATE OR ALTER PROCEDURE newForeignKeyDiscounts
AS
	ALTER TABLE Discounts
		ADD CONSTRAINT FK_DISCOUNT_ORDER FOREIGN KEY(OrderID) REFERENCES Orders(OrderID)

GO
CREATE OR ALTER PROCEDURE removeForeignKeyDiscounts
AS
	ALTER TABLE Discounts
		DROP CONSTRAINT FK_DISCOUNT_ORDER

-- exec newForeignKeyDiscounts
-- exec removeForeignKeyEmployee



-- add/remove a candidate key

GO
CREATE OR ALTER PROCEDURE newCandidateKeyDiscounts
AS	
	ALTER TABLE Discounts
		ADD CONSTRAINT FAN_DISCOUNT_KEY UNIQUE(OrderID, TheDiscount, ExpirationDate)

GO
CREATE OR ALTER PROCEDURE removeCandidateKeyDiscounts
AS
	ALTER TABLE Discounts
		DROP CONSTRAINT FAN_DISCOUNT_KEY

-- exec newCandidateKeyDiscount 
-- exec removeCandidateKeyDiscount


-- DROP TABLE VersionTable;


CREATE TABLE VersionTable (
	CurrentVersion INT
);
 
INSERT INTO VersionTable VALUES (1); -- initial version
 

 
-- a table that contains the initial version, the version after the execution of the procedure
-- and the procedure name

-- DROP TABLE ProcedureTable

CREATE TABLE ProcedureTable (
	InitialVersion INT NOT NULL,
	FinalVersion INT NOT NULL,
	ProcedureName VARCHAR(150) NOT NULL,
	PRIMARY KEY (InitialVersion, FinalVersion)
)
 
INSERT INTO ProcedureTable VALUES
	(1, 2, 'modifyMaterialPriceToSmallint'),
	(2, 1, 'modifyMaterialPriceToInt'),
	(2, 3, 'deletePhoneNumberDelivery'),
	(3, 2, 'addPhoneNumberDelivery'),
	(3, 4, 'addIncomes'),
	(4, 3, 'deleteIncomes'),
	(4, 5, 'addTableDiscounts'),
	(5, 4, 'deleteTableDiscounts'),
	(5, 6, 'addPrimaryKeyOrderIDDiscount'),
	(6, 5, 'removePrimaryKeyOrderIDDiscount'),
	(6, 7, 'newForeignKeyDiscounts'),
	(7, 6, 'removeForeignKeyDiscounts'),
	(7, 8, 'newCandidateKeyDiscounts'),
	(8, 7, 'removeCandidateKeyDiscounts')
 


-- procedure to bring the database to the specified version

GO
CREATE OR ALTER PROCEDURE goToVersion(@newVersion INT) AS
	DECLARE @currentVersion INT
	DECLARE @procedureName VARCHAR(150)
	SELECT @currentVersion = CurrentVersion FROM VersionTable
 
	IF (@newVersion > (SELECT MAX(FinalVersion) FROM ProcedureTable) OR @newVersion < 1)
		RAISERROR ('Invalid Version', 10, 1)
	ELSE BEGIN
		IF @newVersion = @currentVersion
			PRINT('The version already selected')
		ELSE BEGIN
			WHILE @currentVersion > @newVersion BEGIN
				SELECT @procedureName = ProcedureName FROM ProcedureTable WHERE InitialVersion = @currentVersion AND FinalVersion = @currentVersion - 1
				PRINT('Executing: ' + @procedureName)
				EXEC(@procedureName)
				SET @currentVersion = @currentVersion - 1
			END
			WHILE @currentVersion < @newVersion BEGIN
				SELECT @procedureName = ProcedureName FROM ProcedureTable WHERE InitialVersion = @currentVersion AND FinalVersion = @currentVersion + 1
				PRINT('Executing: ' + @procedureName)
				EXEC(@procedureName)
				SET @currentVersion = @currentVersion + 1
			END
			UPDATE VersionTable SET CurrentVersion = @newVersion
		END
	END
 
EXEC goToVersion 8;
 
SELECT * FROM VersionTable;
 
SELECT * FROM ProcedureTable;