package com.becomejavasenior.model;


import java.io.Serializable;

public class Currencies implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private boolean isActiveCurrency;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isActiveCurrency() {
        return isActiveCurrency;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActiveCurrency(boolean activeCurrency) {
        isActiveCurrency = activeCurrency;
    }
}
