use practical

GO
CREATE OR ALTER PROCEDURE addLocationToHoliday (@holidayID int, @locationID int, @duration int, @price decimal(5,2)) AS
BEGIN
	if @holidayID not in (select HolidayID from Holidays)
	begin
		print 'Holiday does not exist'
		return
	end

	if @locationID not in (select LocationID from Locations)
	begin
		print 'Location does not exist'
		return
	end

	declare @nr int = (select count(*) from VacationsLocations where LocationID = @locationID and HolidayID = @holidayID)

	if @nr = 1
	begin
		update VacationsLocations
		set Duration = @duration, Price = @price
		where LocationID = @locationID and HolidayID = @holidayID
		print 'Holiday and location were updated'
		return
	end

	insert into VacationsLocations values (@holidayID, @locationID, @duration, @price)
END

GO
select * from VacationsLocations
exec addLocationToHoliday 1, 1, 5, 12.5
exec addLocationToHoliday 1, 3, 8, 25.5
select * from VacationsLocations

GO
CREATE OR ALTER VIEW nameAgencyesAllLocations AS
	select A.NameAgency
	from Agencyes A
	where (select count(VL.LocationID) from VacationsLocations VL, Holidays H where H.AgencyID = A.AgencyID and H.HolidayID = VL.HolidayID) = (select count(*) from Locations)
GO

SELECT * FROM nameAgencyesAllLocations

GO
CREATE OR ALTER FUNCTION holidaysWithPriceBetween(@A int, @b int)
RETURNS TABLE
AS
	RETURN select H.NameHoliday
	from Holidays H
	where (select SUM(Duration * Price) as total from VacationsLocations VL where Vl.HolidayID = H.HolidayID) > @A and(select SUM(Duration * Price) as total from VacationsLocations VL where Vl.HolidayID = H.HolidayID) < @B
GO

SELECT * FROM holidaysWithPriceBetween(0, 100)
SELECT * FROM holidaysWithPriceBetween(50, 100)
SELECT * FROM holidaysWithPriceBetween(20, 400)
SELECT * FROM holidaysWithPriceBetween(20, 410)