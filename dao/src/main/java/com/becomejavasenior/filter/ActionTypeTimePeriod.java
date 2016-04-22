package com.becomejavasenior.filter;

public enum ActionTypeTimePeriod {
    ALL("All"),
    FUTURE("Future"),
    NEXT7DAYS("Next 7 Days"),
    NEXT14DAYS("Next 14 Days"),
    NEXT30DAYS("Next 30 Days"),
    PAST("Past"),
    LAST7DAYS("Last 7 Days"),
    LAST14DAYS("Last 14 Days"),
    LAST30DAYS("Last 30 Days");

    private final String name;

    private ActionTypeTimePeriod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
