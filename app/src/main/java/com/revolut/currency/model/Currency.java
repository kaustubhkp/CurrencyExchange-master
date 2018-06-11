package com.revolut.currency.model;

public class Currency {
    public final String name;
    public final Float value;

    public Currency(String name, Float value) {
        this.name = name;
        this.value = value;
    }
}
