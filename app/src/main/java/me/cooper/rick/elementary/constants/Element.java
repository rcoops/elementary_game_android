package me.cooper.rick.elementary.constants;

import java.util.Random;

import static java.util.Arrays.copyOfRange;
import static me.cooper.rick.elementary.constants.BondingType.ATOMIC;
import static me.cooper.rick.elementary.constants.BondingType.COVALENT_NETWORK;
import static me.cooper.rick.elementary.constants.BondingType.DIATOMIC;
import static me.cooper.rick.elementary.constants.BondingType.METALLIC;
import static me.cooper.rick.elementary.constants.ElementGroup.ACTINOID;
import static me.cooper.rick.elementary.constants.ElementGroup.ALKALINE_EARTH_METAL;
import static me.cooper.rick.elementary.constants.ElementGroup.ALKALI_METAL;
import static me.cooper.rick.elementary.constants.ElementGroup.LANTHANOID;
import static me.cooper.rick.elementary.constants.ElementGroup.METALLOID;
import static me.cooper.rick.elementary.constants.ElementGroup.NOBLE_GAS;
import static me.cooper.rick.elementary.constants.ElementGroup.NON_METAL;
import static me.cooper.rick.elementary.constants.ElementGroup.POST_TRANSITION_METAL;
import static me.cooper.rick.elementary.constants.ElementGroup.TRANSITION_METAL;
import static me.cooper.rick.elementary.constants.ElementState.GAS;
import static me.cooper.rick.elementary.constants.ElementState.LIQUID;
import static me.cooper.rick.elementary.constants.ElementState.SOLID;

public enum Element {

    H ("H",  "Hydrogen",        1,  1,      "FFFFFF",   GAS,    DIATOMIC,           NON_METAL),
    HE("He", "Helium",          2,  4,      "D9FFFF",   GAS,    ATOMIC,             NOBLE_GAS),
    LI("Li", "Lithium",         3,  7,      "CC80FF",   SOLID,  METALLIC,           ALKALI_METAL),
    BE("Be", "Beryllium",       4,  9,      "C2FF00",   SOLID,  METALLIC,           ALKALINE_EARTH_METAL),
    B ("B",  "Boron",           5,  11,     "FFB5B5",   SOLID,  COVALENT_NETWORK,   METALLOID),
    C ("C",  "Carbon",          6,  12,     "909090",   SOLID,  COVALENT_NETWORK,   NON_METAL),
    N ("N",  "Nitrogen",        7,  14,     "3050F8",   GAS,    DIATOMIC,           NON_METAL),
    O ("O",  "Oxygen",          8,  16,     "FF0D0D",   GAS,    DIATOMIC,           NON_METAL),
    F ("F",  "Fluorine",        9,  19,     "90E050",   GAS,    ATOMIC,             NON_METAL),
    NE("Ne", "Neon",            10, 20,     "B3E3F5",   GAS,    ATOMIC,             NOBLE_GAS),
    NA("Na", "Sodium",          11, 23,     "AB5CF2",   SOLID,  METALLIC,           ALKALI_METAL),
    MG("Mg", "Magnesium",       12, 24,     "8AFF00",   SOLID,  METALLIC,           ALKALINE_EARTH_METAL),
    AL("Al", "Aluminum",        13, 27,     "BFA6A6",   SOLID,  METALLIC,           POST_TRANSITION_METAL),
    SI("Si", "Silicon",         14, 28,     "F0C8A0",   SOLID,  METALLIC,           METALLOID),
    P ("P",  "Phosphorus",      15, 31,     "FF8000",   SOLID,  COVALENT_NETWORK,   NON_METAL),
    S ("S",  "Sulfur",          16, 32,     "FFFF30",   SOLID,  COVALENT_NETWORK,   NON_METAL),
    CL("Cl", "Chlorine",        17, 35,     "1FF01F",   GAS,    COVALENT_NETWORK,   NON_METAL),
    AR("Ar", "Argon",           18, 40,     "80D1E3",   GAS,    ATOMIC,             NOBLE_GAS),
    K ("K",  "Potassium",       19, 39,     "8F40D4",   SOLID,  METALLIC,           ALKALI_METAL),
    CA("Ca", "Calcium",         20, 40,     "3DFF00",   SOLID,  METALLIC,           ALKALINE_EARTH_METAL),
    SC("Sc", "Scandium",        21, 45,     "E6E6E6",   SOLID,  METALLIC,           TRANSITION_METAL),
    TI("Ti", "Titanium",        22, 48,     "BFC2C7",   SOLID,  METALLIC,           TRANSITION_METAL),
    V ("V",  "Vanadium",        23, 51,     "A6A6AB",   SOLID,  METALLIC,           TRANSITION_METAL),
    CR("Cr", "Chromium",        24, 52,     "8A99C7",   SOLID,  METALLIC,           TRANSITION_METAL),
    MN("Mn", "Manganese",       25, 55,     "9C7AC7",   SOLID,  METALLIC,           TRANSITION_METAL),
    FE("Fe", "Iron",            26, 56,     "E06633",   SOLID,  METALLIC,           TRANSITION_METAL),
    CO("Co", "Cobalt",          27, 59,     "F090A0",   SOLID,  METALLIC,           TRANSITION_METAL),
    NI("Ni", "Nickel",          28, 59,     "50D050",   SOLID,  METALLIC,           TRANSITION_METAL),
    CU("Cu", "Copper",          29, 64,     "C88033",   SOLID,  METALLIC,           TRANSITION_METAL),
    ZN("Zn", "Zinc",            30, 65,     "7D80B0",   SOLID,  METALLIC,           TRANSITION_METAL),
    GA("Ga", "Gallium",         31, 70,     "C28F8F",   SOLID,  METALLIC,           POST_TRANSITION_METAL),
    GE("Ge", "Germanium",       32, 73,     "668F8F",   SOLID,  METALLIC,           METALLOID),
    AS("As", "Arsenic",         33, 75,     "BD80E3",   SOLID,  METALLIC,           METALLOID),
    SE("Se", "Selenium",        34, 79,     "FFA100",   SOLID,  METALLIC,           NON_METAL),
    BR("Br", "Bromine",         35, 80,     "A62929",   LIQUID, COVALENT_NETWORK,   NON_METAL),
    KR("Kr", "Krypton",         36, 84,     "5CB8D1",   GAS,    ATOMIC,             NOBLE_GAS),
    RB("Rb", "Rubidium",        37, 85,     "702EB0",   SOLID,  METALLIC,           ALKALI_METAL),
    SR("Sr", "Strontium",       38, 88,     "00FF00",   SOLID,  METALLIC,           ALKALINE_EARTH_METAL),
    Y ("Y",  "Yttrium",         39, 89,     "94FFFF",   SOLID,  METALLIC,           TRANSITION_METAL),
    ZR("Zr", "Zirconium",       40, 91,     "94E0E0",   SOLID,  METALLIC,           TRANSITION_METAL),
    NB("Nb", "Niobium",         41, 93,     "73C2C9",   SOLID,  METALLIC,           TRANSITION_METAL),
    MO("Mo", "Molybdenum",      42, 96,     "54B5B5",   SOLID,  METALLIC,           TRANSITION_METAL),
    TC("Tc", "Technetium",      43, 98,     "3B9E9E",   SOLID,  METALLIC,           TRANSITION_METAL),
    RU("Ru", "Ruthenium",       44, 101,    "248F8F",   SOLID,  METALLIC,           TRANSITION_METAL),
    RH("Rh", "Rhodium",         45, 103,    "0A7D8C",   SOLID,  METALLIC,           TRANSITION_METAL),
    PD("Pd", "Palladium",       46, 106,    "006985",   SOLID,  METALLIC,           TRANSITION_METAL),
    AG("Ag", "Silver",          47, 108,    "C0C0C0",   SOLID,  METALLIC,           TRANSITION_METAL),
    CD("Cd", "Cadmium",         48, 112,    "FFD98F",   SOLID,  METALLIC,           TRANSITION_METAL),
    IN("In", "Indium",          49, 115,    "A67573",   SOLID,  METALLIC,           POST_TRANSITION_METAL),
    SN("Sn", "Tin",             50, 119,    "668080",   SOLID,  METALLIC,           POST_TRANSITION_METAL),
    SB("Sb", "Antimony",        51, 122,    "9E63B5",   SOLID,  METALLIC,           METALLOID),
    TE("Te", "Tellurium",       52, 128,    "D47A00",   SOLID,  METALLIC,           METALLOID),
    I ("I",  "Iodine",          53, 127,    "940094",   SOLID,  COVALENT_NETWORK,   NON_METAL),
    XE("Xe", "Xenon",           54, 131,    "429EB0",   GAS,    ATOMIC,             NOBLE_GAS),
    CS("Cs", "Cesium",          55, 133,    "57178F",   SOLID,  METALLIC,           ALKALI_METAL),
    BA("Ba", "Barium",          56, 137,    "00C900",   SOLID,  METALLIC,           ALKALINE_EARTH_METAL),
    LA("La", "Lanthanum",       57, 139,    "70D4FF",   SOLID,  METALLIC,           LANTHANOID),
    CE("Ce", "Cerium",          58, 140,    "FFFFC7",   SOLID,  METALLIC,           LANTHANOID),
    PR("Pr", "Praseodymium",    59, 141,    "D9FFC7",   SOLID,  METALLIC,           LANTHANOID),
    ND("Nd", "Neodymium",       60, 144,    "C7FFC7",   SOLID,  METALLIC,           LANTHANOID),
    PM("Pm", "Promethium",      61, 145,    "A3FFC7",   SOLID,  METALLIC,           LANTHANOID),
    SM("Sm", "Samarium",        62, 150,    "8FFFC7",   SOLID,  METALLIC,           LANTHANOID),
    EU("Eu", "Europium",        63, 152,    "61FFC7",   SOLID,  METALLIC,           LANTHANOID),
    GD("Gd", "Gadolinium",      64, 157,    "45FFC7",   SOLID,  METALLIC,           LANTHANOID),
    TB("Tb", "Terbium",         65, 159,    "30FFC7",   SOLID,  METALLIC,           LANTHANOID),
    DY("Dy", "Dysprosium",      66, 163,    "1FFFC7",   SOLID,  METALLIC,           LANTHANOID),
    HO("Ho", "Holmium",         67, 165,    "00FF9C",   SOLID,  METALLIC,           LANTHANOID),
    ER("Er", "Erbium",          68, 167,    "000000",   SOLID,  METALLIC,           LANTHANOID),
    TM("Tm", "Thulium",         69, 169,    "00D452",   SOLID,  METALLIC,           LANTHANOID),
    YB("Yb", "Ytterbium",       70, 173,    "00BF38",   SOLID,  METALLIC,           LANTHANOID),
    LU("Lu", "Lutetium",        71, 175,    "00AB24",   SOLID,  METALLIC,           LANTHANOID),
    HF("Hf", "Hafnium",         72, 178,    "4DC2FF",   SOLID,  METALLIC,           TRANSITION_METAL),
    TA("Ta", "Tantalum",        73, 181,    "4DA6FF",   SOLID,  METALLIC,           TRANSITION_METAL),
    W ("W",  "Tungsten",        74, 184,    "2194D6",   SOLID,  METALLIC,           TRANSITION_METAL),
    RE("Re", "Rhenium",         75, 186,    "267DAB",   SOLID,  METALLIC,           TRANSITION_METAL),
    OS("Os", "Osmium",          76, 190,    "266696",   SOLID,  METALLIC,           TRANSITION_METAL),
    IR("Ir", "Iridium",         77, 192,    "175487",   SOLID,  METALLIC,           TRANSITION_METAL),
    PT("Pt", "Platinum",        78, 195,    "D0D0E0",   SOLID,  METALLIC,           TRANSITION_METAL),
    AU("Au", "Gold",            79, 197,    "FFD123",   SOLID,  METALLIC,           TRANSITION_METAL),
    HG("Hg", "Mercury",         80, 201,    "B8B8D0",   LIQUID, METALLIC,           TRANSITION_METAL),
    TL("Tl", "Thallium",        81, 204,    "A6544D",   SOLID,  METALLIC,           POST_TRANSITION_METAL),
    PB("Pb", "Lead",            82, 207,    "575961",   SOLID,  METALLIC,           POST_TRANSITION_METAL),
    BI("Bi", "Bismuth",         83, 209,    "9E4FB5",   SOLID,  METALLIC,           POST_TRANSITION_METAL),
    PO("Po", "Polonium",        84, 209,    "AB5C00",   SOLID,  METALLIC,           METALLOID),
    AT("At", "Astatine",        85, 210,    "754F45",   SOLID,  COVALENT_NETWORK,   METALLOID),
    RN("Rn", "Radon",           86, 222,    "428296",   GAS,    ATOMIC,             NOBLE_GAS),
    FR("Fr", "Francium",        87, 223,    "420066",   SOLID,  METALLIC,           ALKALI_METAL),
    RA("Ra", "Radium",          88, 226,    "007D00",   SOLID,  METALLIC,           ALKALINE_EARTH_METAL),
    AC("Ac", "Actinium",        89, 227,    "70ABFA",   SOLID,  METALLIC,           ACTINOID),
    TH("Th", "Thorium",         90, 232,    "00BAFF",   SOLID,  METALLIC,           ACTINOID),
    PA("Pa", "Protactinium",    91, 231,    "00A1FF",   SOLID,  METALLIC,           ACTINOID),
    U ("U",  "Uranium",         92, 238,    "008FFF",   SOLID,  METALLIC,           ACTINOID),
    NP("Np", "Neptunium",       93, 237,    "0080FF",   SOLID,  METALLIC,           ACTINOID),
    PU("Pu", "Plutonium",       94, 244,    "006BFF",   SOLID,  METALLIC,           ACTINOID),
    AM("Am", "Americium",       95, 243,    "545CF2",   SOLID,  METALLIC,           ACTINOID),
    CM("Cm", "Curium",          96, 247,    "785CE3",   SOLID,  METALLIC,           ACTINOID),
    BK("Bk", "Berkelium",       97, 247,    "8A4FE3",   SOLID,  METALLIC,           ACTINOID),
    CF("Cf", "Californium",     98, 251,    "A136D4",   SOLID,  METALLIC,           ACTINOID);

    public final String chemicalSymbol;
    public final String fullName;
    public final int atomicNumber;
    public final int atomicMass;
    public final String hexColourCode;
    public final ElementState naturalState;
    public final BondingType bondingType;
    public final ElementGroup group;

    private static final Random RAND = new Random();

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

    public static Element rand() {
        Element[] elements = Element.values();
        int index = RAND.nextInt(elements.length);

        return elements[index];
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
                throw new UnsupportedOperationException("That property is not implemented");
        }
    }

    public enum Property {

        SYMBOL("Symbol"),
        FULL_NAME("Full\nName"),
        ATOMIC_NUMBER("Atomic\nNumber"),
        ATOMIC_MASS("Atomic\nMass"),
        NATURAL_STATE("Natural\nState"),
        BONDING_TYPE("Bonding\nType"),
        GROUP("Element\nGroup");

        private static final Random rand = new Random();
        private static Property[] allProperties = Property.values();

        public final String label;

        Property(String label) {
            this.label = label;
        }

        public static Property[] quizValues() {
            return copyOfRange(allProperties, 1, allProperties.length - 1);
        }

    }

}
