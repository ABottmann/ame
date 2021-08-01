package com.ebanx.ame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Event {

    private String type;
    private String destination;
    private int amount;
    private String origin;

}
