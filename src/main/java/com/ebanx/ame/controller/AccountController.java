package com.ebanx.ame.controller;

import com.ebanx.ame.helper.Destination;
import com.ebanx.ame.helper.Origin;
import com.ebanx.ame.helper.Transfer;
import com.ebanx.ame.model.Account;
import com.ebanx.ame.model.Event;
import com.ebanx.ame.model.enums.EventType;
import com.ebanx.ame.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class AccountController
{
    private static final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private AccountService accountService;
    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        logger.info("Reset requested");
        accountService.reset();
        return  ResponseEntity.ok("OK");
    }

    @GetMapping("/balance")
    public ResponseEntity<Integer> getBalance(@RequestParam("account_id") String account_id){
        logger.info("Balance requested for account " + account_id);

        Account account = accountService.getAccountById(account_id);
        if (account == null)
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);

        logger.info(" Balance for account " + account_id + " sent");

        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<String> event(@RequestBody Event event) throws JsonProcessingException {
        String eventType = event.getType();
        logger.info("Event " + eventType + " requested ");

        String destinationAccountNumber = event.getDestination();
        Account destination = accountService.getAccountById(destinationAccountNumber);
        Account origin = accountService.getAccountById(event.getOrigin());

        int amount = event.getAmount();

        if (event.getType().equals(EventType.DEPOSIT.getTypeCode()))
            return Deposit(destinationAccountNumber, destination, amount);

        if (event.getType().equals(EventType.WITHDRAW.getTypeCode()))
            return Withdraw(origin, amount);

        if (event.getType().equals(EventType.TRANSFER.getTypeCode()))
            return Transfer(destinationAccountNumber, origin, destination, amount);

        logger.warning("Event " + eventType + " not valid ");
        return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<String> Deposit(String destinationAccountNumber, Account destination, int amount) throws JsonProcessingException {
        if (destination == null) {
            destination = accountService.CreateAccount(destinationAccountNumber, amount);
            logger.info(" Destination account created with credit of " + amount);
        }
        else {
            accountService.Transaction(destination, amount);
        }

        return new ResponseEntity<>(mapper.writeValueAsString(new Destination(destination)), HttpStatus.CREATED);
    }


    private ResponseEntity<String> Withdraw(Account origin, int amount) throws JsonProcessingException {
        if (origin == null) {
            logger.warning(" Withdraw was not realized");
            return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
        }

        accountService.Transaction(origin, amount * -1);

        return new ResponseEntity<>(mapper.writeValueAsString(new Origin(origin)), HttpStatus.CREATED);
    }


    private ResponseEntity<String> Transfer(String destinationAccountNumber, Account origin, Account destination, int amount) throws JsonProcessingException {
        if (origin == null) {
            logger.warning(" Transfer was not realized");
            return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
        }

        if (destination == null)
            destination = accountService.CreateAccount(destinationAccountNumber, 0);

        Transfer transfer = accountService.Transfer(origin, destination, amount);

        return new ResponseEntity<>(mapper.writeValueAsString(transfer), HttpStatus.CREATED);
    }

}
