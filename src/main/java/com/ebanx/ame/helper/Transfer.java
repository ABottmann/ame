package com.ebanx.ame.helper;

import com.ebanx.ame.model.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Transfer {

    private Account Origin;
    private Account Destination;

    public Transfer(Account origin, Account destination) {
        Origin = origin;
        Destination = destination;
    }

}