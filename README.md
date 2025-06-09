# Inventory Management System

this is a project for the univercity using javaFx and SQLite

# Requierments

## Required knowledge

- Java Programing language
- JavaFX Library
- SQL queries
- CSS Syntax
- FXML format

## Requierd Tools

- IDE
- SQLite
- Maven
- JDK
- Scene builder

# Structur

the folder that you will work with is **src/main** and it contains:

- [database](##database)
- [java](##java)
- [resources](##resources)

## database

It contains the database file(s)

## java

It contains java code inside the pakage **com.project** and it will containe :

- App.java (main function)
- ViewFactory.java (the Scene Handler)
- Controllers (it will connect the Scene with its functions)
- Database (it will contain the database functions)
- Model (it will contain the Model of Data ex:Products)
- Utils (it will contain the rest of the function)
- Events (it used when you need to connect functions to an Event and fire theme all at once)
- Enums (To be honst i can't explane it cuz i am too tired)

## resources

It will contain The folders :

- FXML (*.*fxml\*)
- Styles (*.*css\*)
- SQL (*.*sql\*)

# Commands

To Build and Run use:

```
make
```

To Run use:

```
make run
```

To Build use:

```
make build
```

If some thing is not working try using:

```
make clean
make
```
