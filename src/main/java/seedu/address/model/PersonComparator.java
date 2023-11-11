package seedu.address.model;

import static seedu.address.model.priority.Level.HIGH;
import static seedu.address.model.priority.Level.LOW;
import static seedu.address.model.priority.Level.MEDIUM;
import static seedu.address.model.priority.Level.NONE;

import java.util.Comparator;
import java.util.HashMap;

import seedu.address.model.person.Person;
import seedu.address.model.priority.Level;

/** Compares two Persons according to their priority level. **/
public class PersonComparator implements Comparator<Person> {
    public static final HashMap<Level, Integer> LEVEL_TO_INT_MAP = new HashMap<>() {
        {
            put(HIGH, 3);
            put(MEDIUM, 2);
            put(LOW, 1);
            put(NONE, 0);
        }
    };

    @Override
    public int compare(Person p1, Person p2) {
        Level firstPersonLevel = p1.getPriorityLevel();
        Level secondPersonLevel = p2.getPriorityLevel();

        return LEVEL_TO_INT_MAP.get(secondPersonLevel) - LEVEL_TO_INT_MAP.get(firstPersonLevel);
    }
}
