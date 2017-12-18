package me.cooper.rick.elementary.services;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.cooper.rick.elementary.constants.element.Element;
import me.cooper.rick.elementary.constants.element.Element.Property;

import static java.util.Arrays.asList;
import static me.cooper.rick.elementary.constants.element.Element.Property.quizValues;

public class QuizManager {

    private static QuizManager instance;

    public static final int NO_OF_ELEMENTS = 4;

    private static final Element[] ALL_ELEMENTS = Element.values();
    private static final Element[] CURRENT_ELEMENTS = new Element[NO_OF_ELEMENTS];

    private static final Property[] ALL_PROPERTIES = quizValues();
    private static final Property[] CURRENT_PROPERTIES = new Property[NO_OF_ELEMENTS];

    private static final Random RAND = new Random();

    private Property targetProperty;

    public static QuizManager getInstance() {
        if (instance == null) {
            instance = new QuizManager();
        }
        return instance;
    }

    private QuizManager() {
        resetAnswers();
    }

    /**
     * Resets the current list of selected answers to a new list with randomly chosen elements and
     * properties thereof.
     */
    public void resetAnswers() {
        Element lastTarget = CURRENT_ELEMENTS[0];
        // Ensure a new element is chosen each time
        do {
            reset(ALL_ELEMENTS, CURRENT_ELEMENTS);
        } while (CURRENT_ELEMENTS[0].equals(lastTarget));

        // Re-shuffle properties if more than one answer matches target
        do {
            reset(ALL_PROPERTIES, CURRENT_PROPERTIES);
        } while (!CURRENT_ELEMENTS[0].matchesOneElementProperty(CURRENT_ELEMENTS, CURRENT_PROPERTIES));
    }

    public Element getTargetElement() {
        return CURRENT_ELEMENTS[0];
    }

    public Property getTargetProperty() {
        return targetProperty;
    }

    /**
     * Cycles through all currently chosen elements and properties and returns a list of their
     * name and value for each element.
     *
     * @return a list of property (names) paired with the value of that property for a
     * randomly chosen element. The first property value will be the correct one.
     */
    public List<Pair<String, String>> getAnswers() {
        List<Pair<String, String>> answers = new ArrayList<>();
        targetProperty = CURRENT_PROPERTIES[0];
        for (int i = 0; i < NO_OF_ELEMENTS; ++i) {
            answers.add(new Pair<>(CURRENT_PROPERTIES[i].label,
                    CURRENT_ELEMENTS[i].getPropertyValue(CURRENT_PROPERTIES[i])));
        }
        return answers;
    }

    public boolean isCorrectAnswer(String answer) {
        return CURRENT_ELEMENTS[0].hasPropertyValue(targetProperty, answer);
    }

    // Generic so it can assign both properties and elements
    private <T> void reset(T[] all, T[] current) {
        List<T> available = new ArrayList<>(asList(all)); // Make list of all elements / properties
        for (int i = 0; i < NO_OF_ELEMENTS; ++i) {
            int index = RAND.nextInt(available.size()); // Pick an index at random
            current[i] = available.remove(index); // remove from the list of available and add to recorded
        }
    }

}
