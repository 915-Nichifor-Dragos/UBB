
USE BicycleFactory

-- FUNCTIONS

GO
CREATE OR ALTER FUNCTION functionNameScalar (@nrIncome int)
RETURNS INT
AS
BEGIN
	RETURN @nrIncome
END



GO
CREATE OR ALTER FUNCTION functionNameTable (@nrIncome int)
RETURNS TABLE
AS
	RETURN SELECT * FROM Bicycles

GO



CREATE OR ALTER FUNCTION functionNameTableMore (@nrIncome int)
RETURNS @TableInfo TABLE(income int, occupation varchar(20))
AS
BEGIN
	INSERT INTO @TableInfo VALUES (20, 'abc')
	RETURN
END

-- PROCEDURES

GO
CREATE OR ALTER PROCEDURE procedureExample (@nrIncome int) AS
BEGIN
	PRINT 'ABCD' + CONVERT(VARCHAR, @nrIncome)
	RETURN
END


GO
CREATE OR ALTER PROCEDURE uspNoOfBicycles(@NoBic INT OUTPUT)
AS 
BEGIN
	SELECT @NoBic = COUNT(*)
	FROM Bicycles
	IF @NoBic = 0
	 RAISERROR ('There are no students of the specified age.',10,1)
END
GO

-- VIEWS

GO
CREATE OR ALTER VIEW ViewExample AS
SELECT * FROM Employees

-- TRIGGERS

GO
CREATE TRIGGER When_adding_prod
 ON Products
 FOR INSERT
AS
BEGIN
 INSERT INTO BuyLog(PName, OperationDate, Quantity)
 SELECT PName, GETDATE(), Quantity
 FROM inserted
END



GO
CREATE TRIGGER When_deleting_prod
 ON Products
 FOR DELETE
 AS
BEGIN
 SET NOCOUNT ON;
 INSERT INTO SellLog(PName, OperationDate, Quantity)
 SELECT PName, GETDATE(), Quantity
 FROM deleted
END



CREATE TRIGGER When_changing_prod
 ON Products
 FOR UPDATE
AS
BEGIN
 SET NOCOUNT ON;
 INSERT INTO SellLog(PName, OperationDate, Quantity)
 SELECT d.PName, GETDATE(), d.Quantity - i.Quantity
 FROM deleted d INNER JOIN inserted i ON d.PID = i.PID
 WHERE i.Quantity < d.Quantity
 INSERT INTO BuyLog(PName, OperationDate, Quantity)
 SELECT i.PName, GETDATE(), i.Quantity - d.Quantity
 FROM deleted d INNER JOIN inserted i ON d.PID = i.PID
 WHERE i.Quantity > d.Quantity
END
GO


@@ROWCOUNT -- the number of rows affected by the last statement