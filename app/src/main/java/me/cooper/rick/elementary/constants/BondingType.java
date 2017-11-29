package me.cooper.rick.elementary.constants;

public enum BondingType {

    ATOMIC          ("Atomic"),
    DIATOMIC        ("Diatomic"),
    COVALENT_NETWORK("Covalent\nNetwork"),
    METALLIC        ("Metallic");

    public final String label;

    BondingType(String label) {
        this.label = label;
    }

}