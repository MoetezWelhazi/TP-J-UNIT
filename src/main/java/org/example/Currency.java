package org.example;

public enum Currency {
    USD("US Dollar","USD"),
    EUR("Euro","EUR"),
    TND("Tunisian Dinar","TND");
    private final String displayName;
    private final String name;
    Currency(String displayName, String name) {
        this.displayName = displayName;
        this.name = name;
    }
    public String getDisplayName() {
        return displayName;
    }
    public String getName() {
        return name;
    }
}
