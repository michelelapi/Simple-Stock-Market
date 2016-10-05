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

I used Maven to help the user to run the application start-up in the IDE (Eclipse in this case).


### 3. Start-up

#### Requirements
1. Eclipse Mars
2. J2SE 1.8 or higher
3. Maven

#### Steps
1. Download the source of the project from GitHub
2. Import the source into Eclipse as Maven Project
    
    2.1. Click the right button of the mouse into the tab "Package Explorer" and select the command Import and "Maven/Existing Maven Projects" (see the picture below).
![Simple Stocks Market - Import](https://github.com/michelelapi/Simple-Stock-Market/blob/master/Eclipse_import.png "Simple Stocks Market- Import")

    2.2. Click the right button of the mouse on project name and select the command Run AS/Maven install (see the picture below).

![Simple Stocks Market - Install](https://github.com/michelelapi/Simple-Stock-Market/blob/master/Eclipse_maven.png "Simple Stocks Market- Install")

    2.3. To run the application, click the right button of the mouse on project name and select the command Run AS/Java Application.
        2.3.1 Remember that the application requires J2SE 1.8, otherwise the functional programming don't run.
    2.4. To run tests, click the right button of the mouse on project name and select the command Run AS/JUnit Test.
         
The logs are shown in the console or in the "logs/log4j-application.log" file, automatically created by Log4J.
