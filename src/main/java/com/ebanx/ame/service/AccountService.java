package com.ebanx.ame.service;

import com.ebanx.ame.helper.Transfer;
import com.ebanx.ame.model.Account;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());

    private LinkedList<Account> accounts;
    private Account account;

    public Account CreateAccount(String id, int amount ) {

        if (accounts == null)
            accounts = new LinkedList<>();

        accounts.add(new Account(id, amount));
        logger.info(" Account " + id + " has been created");

        return getAccountById(id);
    }


    public void reset(){
        if (accounts != null)
            accounts.clear();

        logger.info(" Reseted");
    }


    public void Transaction(Account account, int amount){
        amount = account.getBalance() + amount;
        account.setBalance(amount);
        logger.info(" Transaction for account " + account.getId() + " has been realized");
    }

    public Account getAccountById(String account_id){
        this.account = null;

        if (account_id != null) {
            logger.info(" Looking for account " + account_id);

            try {
                this.account = accounts.stream().filter(t -> account_id.equals(t.getId())).collect(Collectors.toList()).get(0);
            } catch (Exception e) {
                logger.warning(" Account " + account_id + " not found ");

            }
        }

        return this.account;
    }

    public Transfer Transfer(Account origin, Account destination, int amount) {
        this.Transaction(origin, amount * -1);
        this.Transaction(destination, amount );

        logger.info(" Transfer from account " + origin.getId() + " to account " + destination.getId() + " realized");

        return new Transfer(origin, destination);
    }
}

