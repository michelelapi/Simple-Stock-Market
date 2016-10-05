# Super Simple Stocks
Super simple stocks is an application to manage trades on a set of stocks and it's a technical test as part of 
the hiring process for a very important company.

### 1. Assignment Description

##### Requirements

1.	Provide working source code that will:

    a.	For a given stock:
    
        i.    Calculate the dividend yield.
        ii.   Calculate the P/E Ratio.
        iii.  Record a trade, with timestamp, quantity of shares, buy or sell indicator and price.
        iv.   Calculate Stock Price based on trades recorded in past 15 minutes.

    b.	Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

##### Constraints & Notes

1.	Written in one of these languages:
    
    * Java, C#, C++, Python.
    
2.	No database or GUI is required, all data need only be held in memory.

3.	Formulas and data provided are simplified representations for the purpose of this exercise.

##### Global Beverage Corporation Exchange

Stock Symbol  | Type | Last Dividend | Fixed Dividend | Par Value
------------- | ---- | ------------: | :------------: | --------: 
TEA           | Common    | 0  |    | 100
POP           | Common    | 8  |    | 100
ALE           | Common    | 23 |    | 60
GIN           | Preferred | 8  | 2% | 100
JOE           | Common    | 13 |    | 250



### 2. Solution
In most projects I managed, related to web applications, I always used the same three-layer architecture (front-end, business, data source).
In this case, since there are no requirements for the UI and the structure of the back-end,
I limited myself to create a simple standalone application using J2SE.

With regard to the back-end I created a singleton class to access the business functionality.
For the datasource, as required in the test, I memorized all the information in memory in a HashMap (for faster searching) using as the key hashcode returned by the two classes Stock and Trade (representing a stock and a trade).

I used J2SE 1.8 to use functional programming in the business logic.

I used Maven to help the user to run the application start-up in the IDE (Eclipse in this case)


### 3. Start-up

#### Requirements
1. Eclipse Mars
2. J2SE 1.8 or higher
3. Maven

#### Steps
1. Download the source of the project from GitHub
2. Import the source into Eclipse as Maven Project
    2.1. Click the right button of the mouse on project name and select the command Run AS/Maven install (see the picture below).

![Simple Stocks - Install](https://github.com/michelelapi/Simple-Stock-Market/blob/master/Eclipse_maven.png "Simple Stocks Market- Install")

    2.2. To run the application, click the right button of the mouse on project name and select the command Run AS/Java Application.
    2.3. To run tests, click the right button of the mouse on project name and select the command Run AS/JUnit Test.
         


##### Technical Design

The first technical decision in our implementation strategy is to provide a unique access to all services in the application. This is accomplished by defining the component **SimpleStockServicesFactory**, which implements the factory pattern and acts as the interface to create all services in the Super Simple Stocks application. The services built by the factory are considered as _**border services**_ and they will be the entry point to the business functionalities for all the external applications that wants to integrate with the stocks library. Each border service is mapped to one unique method in the class SimpleStockServicesFactory to creates the corresponding service instance, using as helper the service _**SpringService**_. This service is responsible to load the Spring context making available all services, architecture components, business model objects and utils defined to support the business functionalities. Additionally, provides a generic mechanism to gets all the beans configured in the Spring context.


![Super Simple Stocks - Technical Design Modeling](https://github.com/jainebri/Super-Simple-Stocks/blob/master/super-simple-stock/src/main/resources/images/super-simple-stocks-model.png "Super Simple Stocks - Technical Design Modeling")

For this technical test, the factory component just has one method _**getSimpleStockService**_, that creates a singleton instance of the **SimpleStockService**, which is the main service in the app and contains all method for the calculations. The class SimpleStocksServicesFactoryImpl is the implementation of the factory and implements a thread safe singleton pattern proposed by Bill Pugh. The next snippet of code ilustrates how to use the factory to create a service:

```java
SimpleStockService simpleStockService = SimpleStockServicesFactory.INSTANCE.getSimpleStockService();
```

As all services are configured in the Spring framework, there are many possibilities to design and build the structure of the services, but for this application we have defined that the border services only can use the services in the backend layer. So, The service _**StocksEntityManager**_, is injected by IoC into the border service SimpleStockService. As one of the constraints of the technical test is 'no database', the entity manager service represents the persistence layer of the application holding all data in memory and providing the methods to recover and store socks and trades in the app. The SimpleStocksService use the entity manager to simulate the database operations for the stocks application.

Finally, the **SimpleStockServiceImpl** implements all the functionalities coding the bussiness rules to make the calculations of the dividend yield, P/E Ratio, stock price, and GBCE All Share index.

##### Unit Test

To test the code of the technical test, it has been used Test Driven Approach provided by maven, coding some junit test for each requirement. Additionally, it has been coded junit test to verify the availability of the services as the factory service and the simple stock service.

##### Try Yourself

The code for the technical test was built as an Eclipse project with a embedded version of Maven. To compile the code, download the folder super-simple-stock and import the project in Eclipse as a maven project. Alternatively, by console run the next command, working in the folder super-simple-stock:

     maven clean install

This will compile the code and will execute the unit test.
