package me.cooper.rick.elementary;

import org.junit.Test;

import me.cooper.rick.elementary.constants.element.Element;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ElementUnitTest {

    @Test
    public void elementHasMatches() throws Exception {
        // Given An array of Elements
        Element[] elements = new Element[] {Element.AC, Element.TH, Element.PA, Element.KR};
        // And an array of properties for which property values are shared between the first three elements
        Element.Property[] properties = new Element.Property[] {Element.Property.SYMBOL, Element.Property.NATURAL_STATE, Element.Property.GROUP, Element.Property.BONDING_TYPE};
        // When checking for the number of matches
        int noOfMatches = elements[0].getPropertyValueMatches(elements, properties);
        // Then the correct answer is returned
        assertEquals(noOfMatches, 3);
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

}