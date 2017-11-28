package me.cooper.rick.elementary.activity.fragment.game;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.cooper.rick.elementary.constants.Element;

import static java.util.Arrays.asList;

public class QuizManager {

    private static QuizManager instance;

    private static final int NO_OF_ELEMENTS = 4;

    private static final Random rand = new Random();

    private static final Element[] ALL_ELEMENTS = Element.values();
    private static final Element[] CURRENT_ELEMENTS = new Element[NO_OF_ELEMENTS];

    private Element.Property targetProperty;

    public static QuizManager getInstance() {
        if (instance == null) {
            instance = new QuizManager();
        }
        return instance;
    }

    private QuizManager() {
        resetElements();
    }

    public static void resetElements() {
        List<Element> availableElements = new ArrayList<>(getAllElements());
        for (int i = 0; i < NO_OF_ELEMENTS; ++i) {
            int index = rand.nextInt(availableElements.size());
            CURRENT_ELEMENTS[i] = availableElements.remove(index);
        }
    }

    public Element getTargetElement() {
        if (CURRENT_ELEMENTS[0] == null) {
            resetElements();
        }
        return CURRENT_ELEMENTS[0];
    }

    public Element.Property getTargetProperty() {
        return targetProperty;
    }

    public List<Pair<String, String>> getAnswers(boolean isVariedProperties) {
        List<Pair<String, String>> answers = new ArrayList<>();
        targetProperty = Element.Property.getRandomQuizProperty();
        Element.Property currentProperty = targetProperty;
        for (int i = 0; i < NO_OF_ELEMENTS; ++i) {
            answers.add(new Pair<>(currentProperty.label, CURRENT_ELEMENTS[i].getProperty(currentProperty)));
            if (isVariedProperties) {
                currentProperty = Element.Property.getRandomQuizProperty();
            }
        }
        return answers;
    }

    public boolean isCorrectAnswer(String answer) {
        return CURRENT_ELEMENTS[0].getProperty(targetProperty).equals(answer);
    }

    private static List<Element> getAllElements() {
        return asList(ALL_ELEMENTS);
    }

}
