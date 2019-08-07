#TRADING APP
# Introduction
-  It is  an online stock trading simulation REST API which can be  used by traders  to sell or buy stocks in the market. and who can use this API .It supplies IES quoting and trading data.
- Technologies used in building this micro-service is:-
  * SpringBoot:- Spring Boot enables building production-ready applications quickly and provides non-functional features:
       
      * Embedded servers which are easy to deploy with the containers
       * It helps in monitoring the multiples components
       * It helps in configuring the components externally.
  * PSQL Database:-PostgreSQL is a powerful, open source object-relational database system with over 30 years of active development that has earned it a strong
   reputation for reliability, feature robustness, and performance.
  
  * IEX market data:-IEX Cloud is a flexible financial data platform connecting a 
    wide array of developers with curated financial data.
    
# Quick Start
- Prequiresites: 
  * Java
   * Docker
    * CentOS 7
    * Maven 
    * Git clone 
- PSQL database:- We are going to create psql database and correspondings tables using ddl files.
you can find sql file under `sql_ddl` folder.You can use following line of code to create database from sql files.
```
psql -h $PSQL_HOST -U $PSQL_USER -f ./sql_ddl/init_db.sql(file name or path to the file)
```
We need  define environment var
<br/> PSQL_HOSTNAME="host of psql database"
<br/>PSQL_USER="database user name"
 <br/>PSQL_PASSWORD="database password"
<br/>IEX_TOKEN="IEX Market credential"

- How to consume REST API:-a controller class files which is used to redirects into the HTML file to consumes the RESTful web services.


# REST API Usage
## Swagger
Swagger allows you to describe the structure of your APIs so that machines can read them. The ability of APIs to describe their own structure is the root of all awesomeness in Swagger. Why is it so great? Well, by reading your APIs structure, we can automatically build beautiful and interactive API documentation. We can also automatically generate client libraries for your API in many languages and explore other possibilities like automated testing. Swagger does this by asking your API to return a YAML or JSON that contains a detailed description of your entire API. This file is essentially a resource listing 
of your API which adheres to OpenAPI Specification.
## Quote Controller
- Controllers typically do one thing; they receive input, and generate output.This controller is responsible for fetching data from IEX market data 
and storing it in `quote` table under `jrvstrading`
database created.
- Endpoints in this controller
  - GET `/quote/dailyList`: list all securities that are available to trading in this trading system
  - PUT `/quote/iexMarketData`: Update all quotes from IEX which is an external market data source
  - GET `/quote/iex/ticker/{ticker}:` fetch a quote form IEX by ticker name
  - POST `/quote/tickerId/{tickerId}:` post a quote from IEX to database by tickerId
  - PUT `/quote/:` update a quote manually
## Trader Controller
- This controller  manages trader and account information. it can deposit and withdraw fund from a given account.
- Endpoints in this controller
  - POST `/trader:` create a trader account and initialize an account with an default zero amount
  - POST `/trader/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}`: initialize a trader and account by specified parameters
  - PUT `/trader/deposit/traderId/{traderId}/amount/{amount}`:deposit fund to a specified trader account
  - PUT `/trader/withdraw/traderId/{traderId}/amount/{amount}`:withdraw fund to a specified trader account
  - DELETE `/trader/traderId/{traderId}`: delete all accounts of a specified trader if there is no account balance or open position
##Order Controller
-  This controller is used to buy or sell market order .
-  Endpoints in this controller
  -  POST `/order/MarketOrder`:A market order is a buy or sell order to be executed immediately at the current market prices. As long as there are willing sellers and buyers, market orders are filled. Market orders are used when certainty of execution is a priority over the price of execution.
     handles buy or sell orders. if we are going to buy the order is 
  fisrt going to check the account balance or if we are going to sell order it is going to check position.  
## App controller
- GET `/health` to make sure SpringBoot app is up and running

# Architecture
- Diagram:-
![]assets/architectuer.png


- briefly explain the following logic layers or components (3-5 sentences for each)
  - Controller :-When writing endpoints it can be helpful to use a controller class to handle the functionality of an endpoint. Controller classes will provide a standard way to interact with the API and 
  also a more maintainable way to interact with the API
  - Service:-It implements the business logic.
  - Dao:-The Data Access Object (DAO) pattern is a structural pattern that allows us to isolate the application/business layer from the persistence layer (usually a relational database, but it could be 
  any other persistence mechanism) using an abstract API
  - SpringBoot: webservlet/TomCat and IoC:-
  - PSQL and IEX:- IEX is the one from where we are getting data and PSQL is where
  we are persisting data.

# Improvements
- how to manage quote data  when market closes.
- how to close and open positions.
- Mobile tool to improve trading results.

- 
