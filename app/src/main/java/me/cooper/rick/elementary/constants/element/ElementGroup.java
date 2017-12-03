package me.cooper.rick.elementary.constants.element;

public enum ElementGroup {

    ALKALI_METAL         ("Alkali\nMetal"),
    ALKALINE_EARTH_METAL ("Alkaline\nEarth\nMetal"),
    LANTHANOID           ("Lanthanoid"),
    ACTINOID             ("Actinoid"),
    TRANSITION_METAL     ("Transition\nMetal"),
    POST_TRANSITION_METAL("Post-\nTransition\nMetal"),
    METALLOID            ("Metalloid"),
    NON_METAL            ("Non-\nMetal"),
    NOBLE_GAS            ("Noble\nGas");

    public final String label;

    ElementGroup(String label) {
        this.label = label;
    }

}
