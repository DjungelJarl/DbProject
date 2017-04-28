# Database project LTH 2017-04-12
## Collaborators
Erik Andersson Data 2013 erik.andersson.811@student.lu.se  
  Shan Langlais Data 2013
## Introduction
We've been assigned to implement a database for the supervision of production and delivery of cookies produced by the company Krusty Kookies Sweden AB.
## Requirements
- [x] The pallet number, product name, and date and time of production is registered in the database. The pallet number is unique.
- [x] At any time, it should be possible to check how many pallets of a product that have been produced during a specific time.
- [x] When a pallet is produced, the raw materials storage must be updated. 
- [x] It should be possible to check the amount in store of each ingredient
- [ ] It should be possible to see when, and how much of, an ingredient was last delivered into storage.
- [ ] An interface to the collection of recipes where one can study existing recipes
- [x] An interface where one can update existing recipes.
- [x] An inteface for entering new recipes
- [x] It shouldn't be possible to block already delivered palletes
It should be possible to: 
- [x] Trace each pallet. 
- [x] To see all information about a pallet with a given number 
- [x] The contents of the pallet
- [x] The location of the pallet
- [x] If the pallet is delivered 
- [x] To whom it has been delievered
- [x] To see which pallets that contain a certain product
- [x] To see which pallets that have been produced during a certain time interval
- [x] To see which products that are blocked
- [x] To see which pallets that contain a certain blocked product
- [x] Be able to check which pallets that have been delivered to a given customer
- [x] Be able to check the date and time of delivery.

- [x] Orders must be registered in the database. 
- [x] Be able to see all orders that are to be delivered during a specific time period.

The production planning is manual. At the end of each week, production for the following week is planned, using the orders for the following weeks as input. We cannot produce "on demand", since it takes time to set up a production line for a new kind of cookie (mixers have to be cleaned, for example).
# Getting started
Add the file sqlite-jdbc.jar to the build path and run.
The database is connected with a relative path so it should work 
right away, however if you don't get connected you can set your direct
path to db.db in the file FactoryGUI on line 36. The db.db is located in
Project/
## Running the program
If views aren't updated instantly try swapping between the panes.
There should be two products listed as test data: sushi and cake. There
are 9 ingridients listed to begin with. You can add more if you want to.
There are 5 panes:
* Order pallet
* Create pallet
* Overview pallets
* Create reciepe
* Overview ingridients
### Order pallet
Here you can place an order on any product that exists in the database.
If you place an order on a product that currently have no pallets ready
the order will be pending and will get pallets added to it when they are
made.
### Create pallet
Here you can create pallets of products that exists in the database if
there are enough ingridients to do so. If there are any pending orders 
to the pallet you made it will be mapped to that order directly. Otherwise
it will be placed in the freezer and wait for new orders.
### Overview pallets
Here you can view all pallets that have ever been made and check their 
status. You can filter the table by entering values. It's possible to 
block pallets by either using the checkboxes in the view or by using the
block button. If the view isn't updated try switching between the views.
### Create reciepe
Here you can create a new product by adding ingridients to it. When entering
ingridients that aren't listed in the database an options box will ask you
if you want to add them to the database. 
### Overview ingridients
Here you can see which ingridients are currently listed in the database and
their current status. If you want to add ingidients to the quantity in storage
simply use a date before todays date. Switch between any of the panes to update
the database.  
## Review code
If you want to check out the code we would recommend Database.java since
that's the file that contains all relevant code for this course. The gui
classes are quite messy and uggly but feel free to check them out if you
are bored.
## Database overview
