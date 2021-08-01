package com.ebanx.ame.helper;

import com.ebanx.ame.model.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Destination {

    private Account Destination;

    public Destination(Account account){
       this.Destination = account;
   }

}
