package me.cooper.rick.elementary.constants;

public enum ElementGroup {

    ALKALI_METAL         ("Alkali Metal"),
    ALKALINE_EARTH_METAL ("Alkaline Earth Metal"),
    LANTHANOID           ("Lanthanoid"),
    ACTINOID             ("Actinoid"),
    TRANSITION_METAL     ("Transition Metal"),
    POST_TRANSITION_METAL("Post-Transition Metal"),
    METALLOID            ("Metalloid"),
    NON_METAL            ("Non-Metal"),
    NOBLE_GAS            ("Noble Gas");

    public final String label;

    ElementGroup(String label) {
        this.label = label;
    }

}
