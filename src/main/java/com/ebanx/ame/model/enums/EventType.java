package com.ebanx.ame.model.enums;

public enum EventType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    TRANSFER("transfer");

    private final String typeCode;

    EventType(String typeCode) { this.typeCode = typeCode;}

    public String getTypeCode() { return typeCode;}
}
