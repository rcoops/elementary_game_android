package me.cooper.rick.elementary.constants.element;

import android.util.Property;

import static java.util.Arrays.copyOfRange;

public enum Element {

    H ("H",  "Hydrogen",        1,  1,      "FFFFFF",   ElementState.GAS,    BondingType.DIATOMIC,           ElementGroup.NON_METAL),
    HE("He", "Helium",          2,  4,      "D9FFFF",   ElementState.GAS,    BondingType.ATOMIC,             ElementGroup.NOBLE_GAS),
    LI("Li", "Lithium",         3,  7,      "CC80FF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALI_METAL),
    BE("Be", "Beryllium",       4,  9,      "C2FF00",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALINE_EARTH_METAL),
    B ("B",  "Boron",           5,  11,     "FFB5B5",   ElementState.SOLID,  BondingType.COVALENT_NETWORK,   ElementGroup.METALLOID),
    C ("C",  "Carbon",          6,  12,     "909090",   ElementState.SOLID,  BondingType.COVALENT_NETWORK,   ElementGroup.NON_METAL),
    N ("N",  "Nitrogen",        7,  14,     "3050F8",   ElementState.GAS,    BondingType.DIATOMIC,           ElementGroup.NON_METAL),
    O ("O",  "Oxygen",          8,  16,     "FF0D0D",   ElementState.GAS,    BondingType.DIATOMIC,           ElementGroup.NON_METAL),
    F ("F",  "Fluorine",        9,  19,     "90E050",   ElementState.GAS,    BondingType.ATOMIC,             ElementGroup.NON_METAL),
    NE("Ne", "Neon",            10, 20,     "B3E3F5",   ElementState.GAS,    BondingType.ATOMIC,             ElementGroup.NOBLE_GAS),
    NA("Na", "Sodium",          11, 23,     "AB5CF2",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALI_METAL),
    MG("Mg", "Magnesium",       12, 24,     "8AFF00",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALINE_EARTH_METAL),
    AL("Al", "Aluminum",        13, 27,     "BFA6A6",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.POST_TRANSITION_METAL),
    SI("Si", "Silicon",         14, 28,     "F0C8A0",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.METALLOID),
    P ("P",  "Phosphorus",      15, 31,     "FF8000",   ElementState.SOLID,  BondingType.COVALENT_NETWORK,   ElementGroup.NON_METAL),
    S ("S",  "Sulfur",          16, 32,     "FFFF30",   ElementState.SOLID,  BondingType.COVALENT_NETWORK,   ElementGroup.NON_METAL),
    CL("Cl", "Chlorine",        17, 35,     "1FF01F",   ElementState.GAS,    BondingType.COVALENT_NETWORK,   ElementGroup.NON_METAL),
    AR("Ar", "Argon",           18, 40,     "80D1E3",   ElementState.GAS,    BondingType.ATOMIC,             ElementGroup.NOBLE_GAS),
    K ("K",  "Potassium",       19, 39,     "8F40D4",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALI_METAL),
    CA("Ca", "Calcium",         20, 40,     "3DFF00",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALINE_EARTH_METAL),
    SC("Sc", "Scandium",        21, 45,     "E6E6E6",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    TI("Ti", "Titanium",        22, 48,     "BFC2C7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    V ("V",  "Vanadium",        23, 51,     "A6A6AB",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    CR("Cr", "Chromium",        24, 52,     "8A99C7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    MN("Mn", "Manganese",       25, 55,     "9C7AC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    FE("Fe", "Iron",            26, 56,     "E06633",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    CO("Co", "Cobalt",          27, 59,     "F090A0",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    NI("Ni", "Nickel",          28, 59,     "50D050",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    CU("Cu", "Copper",          29, 64,     "C88033",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    ZN("Zn", "Zinc",            30, 65,     "7D80B0",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    GA("Ga", "Gallium",         31, 70,     "C28F8F",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.POST_TRANSITION_METAL),
    GE("Ge", "Germanium",       32, 73,     "668F8F",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.METALLOID),
    AS("As", "Arsenic",         33, 75,     "BD80E3",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.METALLOID),
    SE("Se", "Selenium",        34, 79,     "FFA100",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.NON_METAL),
    BR("Br", "Bromine",         35, 80,     "A62929",   ElementState.LIQUID, BondingType.COVALENT_NETWORK,   ElementGroup.NON_METAL),
    KR("Kr", "Krypton",         36, 84,     "5CB8D1",   ElementState.GAS,    BondingType.ATOMIC,             ElementGroup.NOBLE_GAS),
    RB("Rb", "Rubidium",        37, 85,     "702EB0",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALI_METAL),
    SR("Sr", "Strontium",       38, 88,     "00FF00",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALINE_EARTH_METAL),
    Y ("Y",  "Yttrium",         39, 89,     "94FFFF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    ZR("Zr", "Zirconium",       40, 91,     "94E0E0",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    NB("Nb", "Niobium",         41, 93,     "73C2C9",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    MO("Mo", "Molybdenum",      42, 96,     "54B5B5",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    TC("Tc", "Technetium",      43, 98,     "3B9E9E",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    RU("Ru", "Ruthenium",       44, 101,    "248F8F",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    RH("Rh", "Rhodium",         45, 103,    "0A7D8C",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    PD("Pd", "Palladium",       46, 106,    "006985",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    AG("Ag", "Silver",          47, 108,    "C0C0C0",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    CD("Cd", "Cadmium",         48, 112,    "FFD98F",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    IN("In", "Indium",          49, 115,    "A67573",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.POST_TRANSITION_METAL),
    SN("Sn", "Tin",             50, 119,    "668080",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.POST_TRANSITION_METAL),
    SB("Sb", "Antimony",        51, 122,    "9E63B5",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.METALLOID),
    TE("Te", "Tellurium",       52, 128,    "D47A00",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.METALLOID),
    I ("I",  "Iodine",          53, 127,    "940094",   ElementState.SOLID,  BondingType.COVALENT_NETWORK,   ElementGroup.NON_METAL),
    XE("Xe", "Xenon",           54, 131,    "429EB0",   ElementState.GAS,    BondingType.ATOMIC,             ElementGroup.NOBLE_GAS),
    CS("Cs", "Cesium",          55, 133,    "57178F",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALI_METAL),
    BA("Ba", "Barium",          56, 137,    "00C900",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALINE_EARTH_METAL),
    LA("La", "Lanthanum",       57, 139,    "70D4FF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    CE("Ce", "Cerium",          58, 140,    "FFFFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    PR("Pr", "Praseodymium",    59, 141,    "D9FFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    ND("Nd", "Neodymium",       60, 144,    "C7FFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    PM("Pm", "Promethium",      61, 145,    "A3FFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    SM("Sm", "Samarium",        62, 150,    "8FFFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    EU("Eu", "Europium",        63, 152,    "61FFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    GD("Gd", "Gadolinium",      64, 157,    "45FFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    TB("Tb", "Terbium",         65, 159,    "30FFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    DY("Dy", "Dysprosium",      66, 163,    "1FFFC7",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    HO("Ho", "Holmium",         67, 165,    "00FF9C",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    ER("Er", "Erbium",          68, 167,    "000000",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    TM("Tm", "Thulium",         69, 169,    "00D452",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    YB("Yb", "Ytterbium",       70, 173,    "00BF38",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    LU("Lu", "Lutetium",        71, 175,    "00AB24",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.LANTHANOID),
    HF("Hf", "Hafnium",         72, 178,    "4DC2FF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    TA("Ta", "Tantalum",        73, 181,    "4DA6FF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    W ("W",  "Tungsten",        74, 184,    "2194D6",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    RE("Re", "Rhenium",         75, 186,    "267DAB",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    OS("Os", "Osmium",          76, 190,    "266696",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    IR("Ir", "Iridium",         77, 192,    "175487",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    PT("Pt", "Platinum",        78, 195,    "D0D0E0",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    AU("Au", "Gold",            79, 197,    "FFD123",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    HG("Hg", "Mercury",         80, 201,    "B8B8D0",   ElementState.LIQUID, BondingType.METALLIC,           ElementGroup.TRANSITION_METAL),
    TL("Tl", "Thallium",        81, 204,    "A6544D",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.POST_TRANSITION_METAL),
    PB("Pb", "Lead",            82, 207,    "575961",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.POST_TRANSITION_METAL),
    BI("Bi", "Bismuth",         83, 209,    "9E4FB5",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.POST_TRANSITION_METAL),
    PO("Po", "Polonium",        84, 209,    "AB5C00",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.METALLOID),
    AT("At", "Astatine",        85, 210,    "754F45",   ElementState.SOLID,  BondingType.COVALENT_NETWORK,   ElementGroup.METALLOID),
    RN("Rn", "Radon",           86, 222,    "428296",   ElementState.GAS,    BondingType.ATOMIC,             ElementGroup.NOBLE_GAS),
    FR("Fr", "Francium",        87, 223,    "420066",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALI_METAL),
    RA("Ra", "Radium",          88, 226,    "007D00",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ALKALINE_EARTH_METAL),
    AC("Ac", "Actinium",        89, 227,    "70ABFA",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    TH("Th", "Thorium",         90, 232,    "00BAFF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    PA("Pa", "Protactinium",    91, 231,    "00A1FF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    U ("U",  "Uranium",         92, 238,    "008FFF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    NP("Np", "Neptunium",       93, 237,    "0080FF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    PU("Pu", "Plutonium",       94, 244,    "006BFF",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    AM("Am", "Americium",       95, 243,    "545CF2",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    CM("Cm", "Curium",          96, 247,    "785CE3",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    BK("Bk", "Berkelium",       97, 247,    "8A4FE3",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID),
    CF("Cf", "Californium",     98, 251,    "A136D4",   ElementState.SOLID,  BondingType.METALLIC,           ElementGroup.ACTINOID);

    public final String chemicalSymbol;
    public final String fullName;
    public final int atomicNumber;
    public final int atomicMass;
    public final String hexColourCode;
    public final ElementState naturalState;
    public final BondingType bondingType;
    public final ElementGroup group;

    Element(String chemicalSymbol, String fullName, int atomicNumber, int atomicMass, String hexColourCode,
            ElementState naturalState, BondingType bondingType, ElementGroup group) {
        this.chemicalSymbol = chemicalSymbol;
        this.fullName = fullName;
        this.atomicNumber = atomicNumber;
        this.atomicMass = atomicMass;
        this.hexColourCode = hexColourCode;
        this.naturalState = naturalState;
        this.bondingType = bondingType;
        this.group = group;
    }

    public String getPropertyValue(Property property) {
        switch (property) {
            case SYMBOL:
                return chemicalSymbol;
            case FULL_NAME:
                return fullName;
            case ATOMIC_NUMBER:
                return Integer.toString(atomicNumber);
            case ATOMIC_MASS:
                return Integer.toString(atomicMass);
            case NATURAL_STATE:
                return naturalState.label;
            case BONDING_TYPE:
                return bondingType.label;
            case GROUP:
                return group.label;
            default:
                throw new UnsupportedOperationException("That property is not implemented"); // Unreachable
        }
    }

    /**
     * Counts the number of property values shared with a list of elements.
     *
     * @param elements      a list of elements to check against
     * @param properties    a list of properties belonging to the above elements
     * @return              number of property values for element that match those held by the
     *                      elements in the above list
     */
    private int countPropertyValueMatches(Element[] elements, Property[] properties) {
        int matches = 0;
        for (int i = 0; i < elements.length; ++i) {
            if (hasPropertyValue(properties[i], elements[i].getPropertyValue(properties[i]))) {
                matches++;
            }
        }
        return matches;
    }

    public boolean matchesOneElementProperty(Element[] elements, Property[] properties) {
        return countPropertyValueMatches(elements, properties) == 1;
    }

    /**
     * Checks to see if an element has the value of a specific property.
     *
     * @param property  the actual property to check value for
     * @param value     the value to check against
     * @return          true if the value matches the property of that element
     */
    public boolean hasPropertyValue(Property property, String value) {
        return getPropertyValue(property).equals(value);
    }

    public enum Property {

        SYMBOL("Symbol"),
        FULL_NAME("Full\nName"),
        ATOMIC_NUMBER("Atomic\nNumber"),
        ATOMIC_MASS("Atomic\nMass"),
        NATURAL_STATE("Natural\nState"),
        BONDING_TYPE("Bonding\nType"),
        GROUP("Element\nGroup");
        private static Property[] allProperties = Property.values();

        public final String label;

        Property(String label) {
            this.label = label;
        }

        /**
         * Retrieves all properties except symbol (as that is shown in the element.
         *
         * @return  an array of all property values minus symbol
         */
        public static Property[] quizValues() {
            return copyOfRange(allProperties, 1, allProperties.length ); // Exclude symbol
        }

    }

}
