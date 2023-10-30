package seedu.address.model;

import java.util.Comparator;
import java.util.HashMap;

import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority.Level;

public class PersonComparator implements Comparator<Person> {
    public static final HashMap<Level, Integer> LEVEL_TO_INT_MAP = new HashMap<>() {
        {
            put(Level.HIGH, 3);
            put(Level.MEDIUM, 2);
            put(Level.LOW, 1);
            put(Level.NONE, 0);
        }
    };

    @Override
    public int compare(Person p1, Person p2) {
        Level firstPersonLevel = p1.getPriorityLevel();
        Level secondPersonLevel = p2.getPriorityLevel();

        return LEVEL_TO_INT_MAP.get(secondPersonLevel) - LEVEL_TO_INT_MAP.get(firstPersonLevel);
    }
}
