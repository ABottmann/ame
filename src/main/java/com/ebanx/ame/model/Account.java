package com.ebanx.ame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonPropertyOrder({ "idaccount", "balance" })
public class Account {

    private String id;
    private int balance;

    @JsonProperty("idaccount")
    public String getId() {
        return id;
    }



}

