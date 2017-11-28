package me.cooper.rick.elementary.constants;

public enum ElementState {

    SOLID ("Solid"),
    LIQUID("Liquid"),
    GAS   ("Gas");

    public final String label;

    ElementState(String label) {
        this.label = label;
    }

}
