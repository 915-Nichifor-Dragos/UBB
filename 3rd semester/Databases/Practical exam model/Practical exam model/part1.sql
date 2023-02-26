USE PS

DROP TABLE IF EXISTS P

CREATE TABLE P (

	FK1 int,
	FK2 int,
	C1 varchar(500),
	C2 varchar(500),
	C3 int,
	C4 int,
	C5 varchar(5)
	CONSTRAINT pk_P PRIMARY KEY(FK1, FK2)

)

INSERT INTO P 
VALUES
(1, 1, 'Pisica pe acoperisul fierbinte', 'Tennessee Wiliams', 100, 20, 'AB'),
(1, 2, 'Conul Leonida fata cu reactiunea', 'Ion Luca Caragiale', 50, 50, 'CQ'),
(1, 3, 'Concert din muzica de Bach', 'Hortensia Papadat-Bengescu', 50, 10, 'QC'),
(2, 1, 'Fata babei si fata mosneagului', 'Ion Creanga', 100, 100, 'QM'),
(2, 2, 'Frumosii nebuni ai marilor orase', 'Fanus Neagu', 10, 10, 'BA'),
(2, 3, 'Frumoasa calatorie a ursilor panda povestita de un saxofonist care avea o iubita la Frankfurt', 'Matei Visiniec', 100, 20, 'MQ'),
(3, 1, 'Mansarda la Paris cu vedere spre moarte', 'Matei Visiniec', 200, 20, 'PQ'),
(3, 2, 'Richard al III-lea se interzice sau Scene din viata lui Meyerlord', 'Matei Visiniec', 100, 50, 'PQ'),
(3, 3, 'Masinaria Cehov. Nina sau despre fragilitatea pescarusilor impaiati', 'Matei Visiniec', 100, 100, 'AZ'),
(4, 1, 'Omul de zapada care voia sa intalneasca soarele', 'Matei Visiniec', 100, 100, 'CP'),
(4, 2, 'Extraterestrul care isi dorea ca amintire o pijama', 'Matei Visiniec', 50, 10, 'CQ'),
(4, 3, 'O femeie draguta cu o floare si ferestre spre nord', 'Edvard Radzinski', 10, 100, 'CP'),
(4, 4, 'Trenul din zori nu mai opreste aici', 'Tennessee Wiliams', 200, 200, 'MA')

SELECT C2, SUM(C3) TotalC3, AVG(C3) AvgC3
FROM P
WHERE C3 >= 100 OR C1 LIKE '%Pisica%'
GROUP BY C2
HAVING SUM(C3) > 100

-- 1. c

SELECT *
FROM
(SELECT FK1, FK2, C3+C4 TotalC3C4 FROM P
WHERE FK1 = FK2) r1
INNER JOIN (SELECT FK1, FK2, C5 
FROM P
WHERE C5 LIKE '%Q%') r2 ON r1.FK1 = r2.FK1 AND r1.FK2 = r2.FK2

-- 2. c

CREATE OR ALTER TRIGGER TrOnUpdate
ON P
FOR UPDATE AS
DECLARE @total INT = 0
SELECT @total = SUM(i.C3 - d.C3)
FROM deleted d INNER JOIN inserted i ON d.FK1 = i.FK1 AND d.FK2 = i.FK2
WHERE d.C3 < i.C3
PRINT @total

UPDATE P
SET C3 = 300
WHERE FK1 < FK2

-- 3. b