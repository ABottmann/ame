# AmE Project - Checking Account - Assignment 

The project is intended to make very simple Checking Account.

You can do:
- Deposit
- Withdraw
- Transfer
- Get balance

### API

The API consists of 3 endpoints: 

1. POST /reset:
   - **For**: To delete the created accounts 
   - **No parameter**
   - **Success Return**: 200 

2. GET /balance: 
   - **For**: Get account balance, if exists
   - **Parameter**: 
          - **account_id**: Account identification
   - **Success Return**: 200
  
3. POST /event. 
    - **For**: Request event (deposit, withdraw or transfer) in accounts 
    - **Paramenters**: 
        - **type**: Type of event, as below:
            - **deposit**: Credit money to the destination account. If account does not exists, a new one will be created.
            - **withdraw**: Debit money from the origin account. If account does not exists, this operation will fail.
            - **transfer**: Transfer money from origin account to destination account. If origin account does not exists, this operation will be fail. If destination account does not exists, a new one will be created.

        - **destination**: Account destination for operation

        - **origin**: Account origin for operation

        - **amount**: Total value of the transaction

     - **Success Return**: 201


### TECH

- Java 8
- Spring Boot 2.5.3
- Maven Project

### TESTS

For tests [Ipkiss Tester](https://ipkiss.pragmazero.com/) was used, with the following steps:

```--
# Reset state before starting tests

POST /reset

200 OK


--
# Get balance for non-existing account

GET /balance?account_id=1234

404 0


--
# Create account with initial balance

POST /event {"type":"deposit", "destination":"100", "amount":10}

201 {"destination": {"id":"100", "balance":10}}


--
# Deposit into existing account

POST /event {"type":"deposit", "destination":"100", "amount":10}

201 {"destination": {"id":"100", "balance":20}}


--
# Get balance for existing account

GET /balance?account_id=100

200 20

--
# Withdraw from non-existing account

POST /event {"type":"withdraw", "origin":"200", "amount":10}

404 0

--
# Withdraw from existing account

POST /event {"type":"withdraw", "origin":"100", "amount":5}

201 {"origin": {"id":"100", "balance":15}}

--
# Transfer from existing account

POST /event {"type":"transfer", "origin":"100", "amount":15, "destination":"300"}

201 {"origin": {"id":"100", "balance":0}, "destination": {"id":"300", "balance":15}}

--
# Transfer from non-existing account

POST /event {"type":"transfer", "origin":"200", "amount":15, "destination":"300"}

404 0

````




