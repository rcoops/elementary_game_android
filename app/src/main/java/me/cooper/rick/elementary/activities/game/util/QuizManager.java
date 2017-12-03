package me.cooper.rick.elementary.activities.game.util;

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

    private static final int NO_OF_ELEMENTS = 4;

    private static final Random RAND = new Random();

    private static final Element[] ALL_ELEMENTS = Element.values();
    private static final Element[] CURRENT_ELEMENTS = new Element[NO_OF_ELEMENTS];

    private static final Property[] ALL_PROPERTIES = quizValues();
    private static final Property[] CURRENT_PROPERTIES = new Property[NO_OF_ELEMENTS];

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

    public void resetAnswers() {
        reset(ALL_ELEMENTS, CURRENT_ELEMENTS);
        reset(ALL_PROPERTIES, CURRENT_PROPERTIES);
    }

    private <T> void reset(T[] all, T[] current) {
        List<T> available = new ArrayList<>(asList(all));
        for (int i = 0; i < NO_OF_ELEMENTS; ++i) {
            int index = RAND.nextInt(available.size());
            current[i] = available.remove(index);
        }
    }

    public Element getTargetElement() {
        return CURRENT_ELEMENTS[0];
    }

    public Property getTargetProperty() {
        return targetProperty;
    }

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
        return CURRENT_ELEMENTS[0].getPropertyValue(targetProperty).equals(answer);
    }

}
