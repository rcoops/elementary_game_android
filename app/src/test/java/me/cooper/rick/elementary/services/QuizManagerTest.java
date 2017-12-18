package me.cooper.rick.elementary.services;

import android.support.v4.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import me.cooper.rick.elementary.constants.element.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class QuizManagerTest {

    @Test
    public void isSingleton() throws Exception {
        // Given two calls to getInstance
        QuizManager quizManager = QuizManager.getInstance();
        QuizManager quizManager1 = QuizManager.getInstance();

        // Then the two managers are the same object
        assertEquals(quizManager, quizManager1);

        // When all object references to quizManager are removed
        String quizManagerSig = quizManager.toString();
        quizManager = null;
        quizManager1 = null;

        // The same object is still persisted statically
        assertEquals(quizManagerSig, QuizManager.getInstance().toString());
    }

    @Test
    public void resetAnswersGivesANewTargetElement() throws Exception {
        // Given a quiz manager
        final QuizManager quizManager = QuizManager.getInstance();
        // And it's current target element
        Element oldTarget = quizManager.getTargetElement();

        // When resetting the answers held by the manager
        quizManager.resetAnswers();

        // Then the new element does not match the old
        assertNotEquals(quizManager.getTargetElement(), oldTarget);
    }

    @Test
    public void resetAnswersAlwaysHasNewElement() throws Exception {
        // Given a quiz manager
        final QuizManager quizManager = QuizManager.getInstance();
        // And it's current target element
        Element oldTarget = quizManager.getTargetElement();

        // When resetting the answers held by the manager
        quizManager.resetAnswers();

        // Then the new element does not match the old
        assertNotEquals(quizManager.getTargetElement(), oldTarget);
    }

    @Test
    public void isCorrectAnswer() throws Exception {
        // Given a quiz manager
        final QuizManager quizManager = QuizManager.getInstance();
        // And its answers have been reset
        quizManager.resetAnswers();

        // When we retrieve references to the target element and its property answer
        Element target = quizManager.getTargetElement();
        String answer = target.getPropertyValue(quizManager.getTargetProperty());

        // Then the quiz manager confirms that the answer is correct
        assertTrue(quizManager.isCorrectAnswer(answer));
    }

    @Test
    public void resetAnswersAlwaysHasOnlyOneMatchingProperty() throws Exception {
        // Given a quiz manager
        final QuizManager quizManager = QuizManager.getInstance();
        // And its answers have been reset
        quizManager.resetAnswers();

        // When retrieving the answers
        List<Pair<String, String>> answers = quizManager.getAnswers();

        // Then the first one matches the target element
        assertTrue(quizManager.isCorrectAnswer(answers.get(0).second));
        // And the others do not
        for (int i = 1; i < answers.size(); ++i) {
            assertFalse(quizManager.isCorrectAnswer(answers.get(i).second));
        }
    }

}
