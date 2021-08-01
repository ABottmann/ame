package com.ebanx.ame.service;

import com.ebanx.ame.helper.Transfer;
import com.ebanx.ame.model.Account;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private LinkedList<Account> accounts;
    private Account account;

    public Account CreateAccount(String idAccount, int amount ) {

        if (accounts == null)
            accounts = new LinkedList<>();

        accounts.add(new Account(idAccount, amount));

        return getAccountById(idAccount);

    }


    public void reset(){
        if (accounts != null)
            accounts.clear();
    }


    public void Transaction(Account account, int amount){
        amount = account.getBalance() + amount;
        account.setBalance(amount);
    }

    public Account getAccountById(String account_id){
        this.account = null;

        try {
             this.account = accounts.stream().filter(t -> account_id.equals(t.getId())).collect(Collectors.toList()).get(0);
        }
        catch(Exception e){

        }
        return this.account;
    }

    public Transfer Transfer(Account origin, Account destination, int amount) {
        this.Transaction(origin, amount * -1);
        this.Transaction(destination, amount );

        return new Transfer(origin, destination);
    }
}

