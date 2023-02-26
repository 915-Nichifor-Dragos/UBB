-- CREATE DATABASE PS
-- DROP DATABASE PS

Use PS

DROP TABLE IF EXISTS RoutesStations
DROP TABLE IF EXISTS TRoutes
DROP TABLE IF EXISTS Trains
DROP TABLE IF EXISTS Stations
DROP TABLE IF EXISTS TrainTypes

CREATE TABLE TrainTypes(

	IDType int PRIMARY KEY,
	descriptiontype varchar(20) unique

)

CREATE TABLE Stations(

	IDStation int primary key,
	namestation varchar(20)

)

CREATE TABLE Trains(

	IDTrain int primary key,
	nametrain varchar(20),
	typetrain int FOREIGN KEY REFERENCES TrainTypes(IDType),

)


CREATE TABLE TRoutes(

	IDRoute int primary key,
	nameroute varchar(20) unique,
	IDTrain int foreign key references Trains(IDTrain),

)

CREATE TABLE RoutesStations(

	IDRouteStation int primary key,
	IDRoute int foreign key references TRoutes(IDRoute),
	IDStation int foreign key references Stations(IDStation),
	arrival time,
	departure time

)

GO
CREATE OR ALTER PROCEDURE addStationToRoute (@routeID int, @stationID int, @arrival time, @departure time) as
BEGIN
	
	IF @stationID not in (SELECT IDStation FROM Stations)
	BEGIN

		PRINT 'Station ' + CONVERT(varchar(10), @stationID) + ' does not exist!'
		RETURN

	END

	IF @routeID not in (SELECT IDRoute FROM TRoutes)
	BEGIN
		
		PRINT 'Route ' +  CONVERT(varchar(10), @routeID) + ' does not exist!'
		RETURN

	END

	IF @stationID in (SELECT IDStation FROM RoutesStations WHERE IDRoute = @routeID)
	BEGIN

		PRINT 'Station ' +  CONVERT(varchar(10), @stationID) + ' is already added to the route and has been updated!'
		UPDATE RoutesStations 
		SET arrival = @arrival, departure = @departure
		WHERE IDRoute = @routeID AND IDStation = @stationID
		RETURN

	END

	DECLARE @nextID int = (SELECT MAX(IDRouteStation) + 1 FROM RoutesStations)
	PRINT 'Station ' +  CONVERT(varchar(10), @stationID) + ' was added to the route ' +  CONVERT(varchar(10), @routeID)
	INSERT INTO RoutesStations VALUES (@nextID, @routeID, @stationID, @arrival, @departure)

END

GO
CREATE OR ALTER VIEW SpecialRoutes AS
	SELECT R.nameroute
	FROM TRoutes R
	WHERE (SELECT COUNT(RS.IDStation) FROM RoutesStations RS WHERE RS.IDRoute = R.IDRoute) = (SELECT COUNT(S.IDStation) FROM Stations S)

GO

GO
CREATE OR ALTER FUNCTION stationRoutes(@nrRoutes int)
RETURNS TABLE AS
	RETURN
		SELECT DISTINCT S.namestation FROM Stations S WHERE @nrRoutes <= (SELECT COUNT(RS.IDRoute) FROM RoutesStations RS WHERE RS.IDStation = S.IDStation) 

