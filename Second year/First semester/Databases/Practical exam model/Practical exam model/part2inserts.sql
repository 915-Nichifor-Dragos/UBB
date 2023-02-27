USE PS

INSERT INTO TrainTypes
VALUES
(1, 'type a'),
(2, 'type b'),
(3, 'type c')

INSERT INTO Stations
VALUES
(1, 'Sighisoara'),
(2, 'Turda'),
(3, 'Ploiesti')

INSERT INTO Trains 
VALUES 
(1, 'Number 1', 1),
(2, 'Number 2', 2),
(3, 'Number 3', 1)

INSERT INTO TRoutes
VALUES
(1, 'Cj-Bv', 1),
(2, 'Cj-Buc', 2),
(3, 'Bv-Buc', 3)

INSERT INTO RoutesStations
VALUES
(1, 1, 2, '10:00:00', '10:10:00'),
(2, 1, 1, '13:30:00', '13:45:00'),
(3, 2, 2, '12:30:00', '10:40:00'),
(4, 2, 1, '16:00:00', '16:15:00'),
(5, 2, 3, '20:30:00', '22:40:00'),
(6, 3, 3, '12:00:00', '12:10:00')

SELECT * FROM TrainTypes
SELECT * FROM Trains
SELECT * FROM Stations
SELECT * FROM TRoutes
SELECT * FROM RoutesStations

-- addStationToRoute (@routeID int, @stationID int, @arrival time, @departure time) as

EXEC addStationToRoute 1, 2, '10:20:00', '10:30:00'
EXEC addStationToRoute 3, 2, '15:00:00', '15:10:00'

SELECT * FROM SpecialRoutes

SELECT * FROM stationRoutes(2)
SELECT * FROM stationRoutes(3)
SELECT * FROM stationRoutes(4)