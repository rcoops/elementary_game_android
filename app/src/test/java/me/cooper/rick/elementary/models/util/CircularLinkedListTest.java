package me.cooper.rick.elementary.models.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CircularLinkedListTest {

    @Test
    public void circularLinkedListWorks() throws Exception {
        // Given a circular linked list with 3 elements
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.add(2);
        list.add(3);
        list.add(4);

        // Before cycle, current points to 2
        assertEquals(list.getCurrent().intValue(), 2);
        // Before cycle, next points to 3
        assertEquals(list.getNext().intValue(), 3);

        // The list cycles once
        list.cycleCurrentToNext();
        // Current is now 3
        assertEquals(list.getCurrent().intValue(), 3);
        // Next is 4
        assertEquals(list.getNext().intValue(), 4);

        // The list cycles once
        list.cycleCurrentToNext();
        // Current is now 4
        assertEquals(list.getCurrent().intValue(), 4);
        // Current is now 2 - 'head' of the list
        assertEquals(list.getNext().intValue(), 2);

        // The list cycles once
        list.cycleCurrentToNext();
        // current is now pointing back to 'head' of list
        assertEquals(list.getCurrent().intValue(), 2);
        // next still points to 3
        assertEquals(list.getNext().intValue(), 3);
    }

}
