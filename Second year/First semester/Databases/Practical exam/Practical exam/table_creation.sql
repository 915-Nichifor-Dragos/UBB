-- Create database practical

Use practical

drop table if exists VacationsLocations
drop table if exists Holidays
drop table if exists Locations
drop table if exists Agencyes
drop table if exists LocationTypes

create table LocationTypes (
	LocationTypeID int primary key,
	NameType varchar(50)
)

create table Agencyes (
	AgencyID int primary key,
	NameAgency varchar(50) unique,
	AddressAgency varchar(50)
)

create table Locations (
	LocationID int primary key,
	NameLocation varchar(50),
	DescriptionLocation varchar(50),
	LocationTypeID int foreign key references LocationTypes(LocationTypeID)
)

create table Holidays (
	HolidayID int primary key,
	AgencyID int foreign key references Agencyes(AgencyID),
	NameHoliday varchar(50) unique
)

create table VacationsLocations (
	HolidayID int foreign key references Holidays(HolidayID),
	LocationID int foreign key references Locations(LocationID),
	Duration int,
	Price decimal(5,2)
	constraint pk_VacationsLocations primary key(HolidayID, LocationID)
)
