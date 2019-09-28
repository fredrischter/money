# Summary

Testing minimalist approach for implementing HTTP API

```
   ______________   
 / \             \
 \_ | Minimalist |
    |    Stuff   |
    |  __________|_
    \_/dc__________/
```

# Setup

## Pre requisites

* Java
* Maven

## Build and run

```
mvn clean package
java -jar target/minimalist-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Run using docker compose
```
docker-compose up
```
## Example requests
```
curl -X POST 'http://localhost:4567/deposit?account=1&amount=1000'
curl 'http://localhost:4567/balance?account=1'
```
# Features

## Depositing

Adds value to account. Creates account if it does not exist.

## Transfering

Will try to transfer from one account to another. It requires source account enough available balance.

## Get balance

Returns the balance of given account.

## Further improvements

To make the NotEnoughBalanceException (403 - Forbidden) case to return message explaining that the user is out of balance.

To force two digits inputs, throwing 400 when get numbers with more than two digits of precision.
