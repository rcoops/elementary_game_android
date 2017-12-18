package me.cooper.rick.elementary.constants.element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ElementUnitTest {

    @Test
    public void getPropertyValueReturnsCorrectValues() throws Exception {
        // Given an element
        Element element = Element.AS;
        // When getting the value of each property assigned to the element
        for (Element.Property property : Element.Property.values()) {
            // Then the property value is correct
            assertEquals(getArsenicPropertyValue(property), element.getPropertyValue(property));
        }

    }

    @Test
    public void hasPropertyValueReturnsCorrectResult() throws Exception {
        // Given an element
        Element element = Element.AU;
        // When checking if it has a property value it should have
        boolean hasValue = element.hasPropertyValue(Element.Property.SYMBOL, element.chemicalSymbol);
        // Then returns true
        assertTrue(hasValue);
        // When checking if it has a property value it should NOT have
        hasValue = element.hasPropertyValue(Element.Property.FULL_NAME, Element.XE.naturalState.label);
        // Then returns false
        assertFalse(hasValue);
        // When checking if it has a property value that doesn't exist
        hasValue = element.hasPropertyValue(Element.Property.FULL_NAME, "not a full name");
        // Then returns false
        assertFalse(hasValue);
    }

    @Test
    public void quizValuesGetsAllButSymbol() throws Exception {
        // When a list of Quiz Properties is retrieved
        Element.Property[] properties = Element.Property.quizValues();
        // Then the list is one less in size than all properties
        assertEquals(properties.length, Element.Property.values().length - 1);

        for (int i = 0; i < properties.length; ++i) {
            // And none are the symbol
            assertNotEquals(properties[i], Element.Property.SYMBOL);
            for (int j = 0; j < properties.length; ++j) {
                if(i==j) continue;
                // And each is unique
                assertNotEquals(properties[i], properties[j]);
            }
        }
    }

    @Test
    public void correctlyShowsIfOtherThanOnePropertyMatches() throws Exception {
        // Given an element
        Element element = Element.H;
        // And an array of properties
        Element.Property[] properties = new Element.Property[] {
                Element.Property.NATURAL_STATE,
                Element.Property.BONDING_TYPE,
                Element.Property.GROUP
        };
        // And an array of elements, 2 of which have matching properties
        Element[] elements = new Element[] {
                Element.HE, // 1 GAS
                Element.LI, // 0
                Element.P // 1 - NON METAL
        };
        // When checking if the properties match only one of that element
        boolean matches = element.matchesOneElementProperty(elements, properties);
        // The number of matches is not one
        assertFalse(matches);
        // When testing against itself
        elements = new Element[] {element};
        properties = new Element.Property[] { Element.Property.BONDING_TYPE };
        matches = element.matchesOneElementProperty(elements, properties);
        // The number of matches is not one
        assertTrue(matches);
    }


    private String getArsenicPropertyValue(Element.Property property) {
        switch (property) {
            case SYMBOL:
                return Element.AS.chemicalSymbol;
            case FULL_NAME:
                return Element.AS.fullName;
            case ATOMIC_NUMBER:
                return Integer.toString(Element.AS.atomicNumber);
            case ATOMIC_MASS:
                return Integer.toString(Element.AS.atomicMass);
            case NATURAL_STATE:
                return Element.AS.naturalState.label;
            case BONDING_TYPE:
                return Element.AS.bondingType.label;
            case GROUP:
                return Element.AS.group.label;
            default:
                throw new UnsupportedOperationException("That property is not implemented"); // Unreachable
        }
    }

}