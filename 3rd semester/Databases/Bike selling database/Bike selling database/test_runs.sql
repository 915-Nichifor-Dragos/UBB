USE BicycleFactory

-- CREATE THE TESTS

-- First test
-- 1) A table with no foreign key and a single-column primary key (Depot)
-- 2) A table with a foreign key and a single-column primary key and (Employees)
-- 3) A table with a multi-column primary key 
-- 4) A view with a SELECT statement that has a GROUP BY clause and operates on at least 2 tables
EXEC addToViews 'ordersAndDiscounts'
EXEC addToTests 'Test1'
EXEC addToTables 'Customers'
EXEC connectTableToTest 'Customers', 'Test1', 1000, 1
EXEC addToTables 'DeliveryCompanies'
EXEC connectTableToTest 'DeliveryCompanies', 'Test1', 1000, 2
EXEC addToTables 'Orders'
EXEC connectTableToTest 'Orders', 'Test1', 1000, 3
EXEC addToTables 'Discounts'
EXEC connectTableToTest 'Discounts', 'Test1', 1000, 4
EXEC connectViewToTest 'ordersAndDiscounts', 'Test1'

-- Second test
-- 1) A table with a single-column primary key 
-- 2) A view with a SELECT statement operating on one table
EXEC addToViews 'employeesWithIncomeHigherThan3000'
EXEC addToTests 'Test2'
EXEC addToTables 'Depots'
EXEC connectTableToTest 'Depots', 'Test2', 1000, 1
EXEC addToTables 'Employees'
EXEC connectTableToTest 'Employees', 'Test2', 1000, 2
EXEC connectViewToTest 'employeesWithIncomeHigherThan3000', 'Test2'

-- Third test
-- 1) A view with a SELECT statement operating on at least two tables
-- 2) A table with no foreign key and a single-column primary key and a table with a foreign key
EXEC addToViews 'materialsUsedForPartsWithQuantity'
EXEC addToTests 'Test3'
EXEC addToTables 'Parts'
EXEC connectTableToTest 'Parts', 'Test3', 1000, 1
EXEC addToTables 'Materials'
EXEC connectTableToTest 'Materials', 'Test3', 1000, 2
EXEC addToTables 'Compositions'
EXEC connectTableToTest 'Compositions', 'Test3', 1000, 3
EXEC connectViewToTest 'materialsUsedForPartsWithQuantity', 'Test3'

EXEC runAllTests

SELECT *
FROM [Views]

SELECT *
FROM [Tables]

SELECT *
FROM [Tests]

SELECT *
FROM [TestTables]

SELECT *
FROM [TestViews]

SELECT *
FROM [TestRuns]

SELECT *
FROM [TestRunTables]

SELECT *
FROM [TestRunViews]