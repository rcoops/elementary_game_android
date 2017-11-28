package me.cooper.rick.elementary.constants;

public enum BondingType {

    ATOMIC          ("Atomic"),
    DIATOMIC        ("Diatomic"),
    COVALENT_NETWORK("Covalent Network"),
    METALLIC        ("Metallic");

    public final String label;

    BondingType(String label) {
        this.label = label;
    }

}