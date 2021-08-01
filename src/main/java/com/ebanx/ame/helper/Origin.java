package com.ebanx.ame.helper;

import com.ebanx.ame.model.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Origin {

    private Account Origin;

    public Origin(Account account){
        this.Origin = account;
    }

}
