package com.finora.backend.domain;

import lombok.Getter;

@Getter
public enum Type {
    FOOD("Food"),
    DRINK("Drink"),
    TRANSPORTATION("Transportation"),
    BILL("Bill"),
    TRANSFER_MONEY("Transfer Money"),
    OTHER("Other");


    private final String label;

    Type(String label) {
        this.label = label;
    }

}
