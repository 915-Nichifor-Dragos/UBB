use practical

insert into LocationTypes
values
(1, 'seaside'),
(2, 'mountainside')

insert into Agencyes
values
(1, 'a1', 'str 1'),
(2, 'a2', 'str 2')

insert into Locations
values
(1, 'l1', 'abcd', 1),
(2, 'l2', '3213', 1),
(3, 'l3', '1adsag', 2)

insert into Holidays
values
(1, 1, 'h1'),
(2, 1, 'h2'),
(3, 1, 'h3'),
(4, 2, 'h4'),
(5, 2, 'h5')

insert into VacationsLocations
values
(1, 1, 3, 10.5),
(2, 2, 5, 20.8),
(3, 1, 2, 10.5),
(4, 2, 8, 50.2),
(5, 2, 7, 40.3),
(1, 2, 4, 5.5)

select * from LocationTypes
select * from Agencyes
select * from Locations
select * from Holidays
select * from VacationsLocations