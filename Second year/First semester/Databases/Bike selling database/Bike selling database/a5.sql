USE BicycleFactory

DROP TABLE IF EXISTS Transport
DROP TABLE IF EXISTS MasinaTransport
DROP TABLE IF EXISTS Produs

-- Table creation

CREATE TABLE MasinaTransport ( 
	MasinaTransportID int primary key,
	NumarPlacutaInmatriculare int unique,
	ToneSuportate int
)

CREATE TABLE Produs (
	ProdusID int primary key,
	Pret int
)

CREATE TABLE Transport (
	TransportID int primary key,
	MasinaTransportID int foreign key references MasinaTransport(MasinaTransportID),
	ProdusID int foreign key references Produs(ProdusID)
)

-- Procedures to generate data for the tables

GO
CREATE OR ALTER PROCEDURE populateMasinaTransport(@rows INT) as
BEGIN
	DECLARE @var int 
	SET @var = 3 * @rows
	WHILE @rows > 0
	BEGIN
		INSERT INTO MasinaTransport VALUES (@rows, @var, @var % 101)
		SET @rows = @rows - 1
		SET @var = @var - 3
	END
END


GO
CREATE OR ALTER PROCEDURE populateProdus(@rows INT) as
BEGIN
	DECLARE @var int
	SET @var = 8 * @rows
	WHILE @rows > 0
	BEGIN
		INSERT INTO Produs VALUES (@rows, @var % 501)
		SET @rows = @rows - 1
		SET @var = @var - 8
	END
END


GO
CREATE OR ALTER PROCEDURE populateTransport(@rows INT) as
BEGIN
	DECLARE @aid int
	DECLARE @bid int
	WHILE @rows > 0
	BEGIN
			SET @aid = (SELECT TOP 1 MasinaTransportID FROM MasinaTransport ORDER BY NEWID())
			SET @bid = (SELECT TOP 1 ProdusID FROM Produs ORDER BY NEWID())
			INSERT INTO Transport VALUES (@rows, @aid, @bid)
			SET @rows = @rows - 1
	END
END

-- Insert data

EXEC populateMasinaTransport 1000
EXEC populateProdus 1000
EXEC populateTransport 1000

SELECT * FROM MasinaTransport
SELECT * FROM Produs
SELECT * FROM Transport

/* 
TASKS
OBS: 
- We have a clustered index automatically created for the aid column from MasinaTransport
- We have a nonclustered index automatically created for the a2 column from MasinaTransport
- We have a clustered index automatically created for the bid column from Produs
- We have a clustered index automatically created for the cid column from Transport
*/

-- a. Write queries on MasinaTransport such that their execution plans contain the following operators:

-- clustered index scan - scan the entire table 
-- Cost: 0.0058635
SELECT * FROM MasinaTransport

-- clustered index seek - return a specific subset of rows from a clustered index
-- Cost: 0.0034481
SELECT *
FROM MasinaTransport
WHERE MasinaTransportID < 152

-- nonclustered index scan - scan the entire nonclustered index
-- Cost: 0.0051227
SELECT NumarPlacutaInmatriculare
FROM  MasinaTransport
ORDER BY NumarPlacutaInmatriculare

-- nonclustered index seek - return a specific subset of rows from a nonclustered index
-- Cost: 0.0032862
SELECT NumarPlacutaInmatriculare
FROM MasinaTransport
WHERE NumarPlacutaInmatriculare BETWEEN 120 AND 130

-- key lookup - nonclustered index seek + key lookup - the data is found in a nonclustered index, but additional data is needed
-- Cost: 0.0032831 (index seek nonclustered) + 0.0032831 (key lookup clustered) = 0.0065662
SELECT ToneSuportate, NumarPlacutaInmatriculare
FROM MasinaTransport
WHERE NumarPlacutaInmatriculare = 544

-- b. Write a query on table Tb with a WHERE clause of the form WHERE b2 = value and analyze its execution plan. 
--  Create a nonclustered index that can speed up the query. Examine the execution plan again.

SELECT *
FROM Produs
WHERE Pret = 154

-- Before creating a nonclustered index we have a clustered index scan with the cost: 0.0058635

DROP INDEX Tb_Pret_index ON Produs
CREATE NONCLUSTERED INDEX Tb_Pret_index ON Produs(Pret)

-- After creating a nonclustered index we have a nonclustered index seek with the cost: 0.0032831

-- c. Create a view that joins at least 2 tables. Check whether existing indexes are helpful; if not, reassess 
--  existing indexes / examine the cardinality of the tables.

GO
CREATE OR ALTER VIEW View1 AS
	SELECT A.MasinaTransportID, B.ProdusID, C.TransportID
	FROM Transport C
	INNER JOIN MasinaTransport A ON A.MasinaTransportID = C.MasinaTransportID
	INNER JOIN Produs B ON B.ProdusID = C.ProdusID
	WHERE B.Pret > 10 AND A.ToneSuportate < 8

GO
SELECT *
FROM View1

-- With existing indexes(the automatically created ones + nonclustered index on b2): 0.04294
-- When adding a nonclustered index on a3 to the existing indexes: 0.0424601
-- Without the nonclustered index on b2 and the nonclustered index on a3: 0.04294
-- Automatically created indexes + nonclustered index on b2 + nonclustered index on a3 + nonclustered index on (aid, bid) from Tc: 0.0424601

DROP INDEX Ta_ToneSuportate_index ON Ta
CREATE NONCLUSTERED INDEX Ta_ToneSuportate_index ON MasinaTransport(ToneSuportate)

DROP INDEX Tc_index ON Tc
CREATE NONCLUSTERED INDEX Tc_index ON Transport(MasinaTransportID, ProdusID)