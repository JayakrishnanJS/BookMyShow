package com.jkdev.bookmyshow.enums;

public enum MovieFormat {
    TWO_D("2D"),
    THREE_D("3D"),
    FOUR_DX("4DX");

    private final String displayValue;

    // Constructor
    MovieFormat(String displayValue) {
        this.displayValue = displayValue;
    }

    // Getter for display value
    public String getDisplayValue() {
        return displayValue;
    }

    // Custom toString for displaying value
    @Override
    public String toString() {
        return displayValue;
    }
}