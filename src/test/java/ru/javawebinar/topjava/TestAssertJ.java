package ru.javawebinar.topjava;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.fail;


class TestAssertJ {

    @Test
    void generalMatchersForLists( ) {
        List<Integer> list = Arrays.asList(5, 2, 4);
        // test has list has size of 3
        // contains the elements 2, 4, 5 in any order
        // That every item is greater than 1
        fail("not implemented yet");
    }

    @Test
    void tolkienCharacterShouldHaveProperties() {
//        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        // ensure that TolkienCharacter has the bean property "name"
        // ensure that TolkienCharacter has the bean property "age" with is greater than 0

        // Hint: maybe you need to change your data model
        fail("not implemented yet");
    }

    @Test
    void validateTolkeinCharactorsInitializationWorks() {
        //TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        //age is 33
        //name is "Frodo"
        //name is not "Frodon"
        fail("not implemented yet");
    }

    @Test
    void ensureThatFellowsAreNotOrcs() {
        //List<TolkienCharacter> fellowship = new DataService().getFellowship();
        // ensure that no fellows is a orc
        fail("not implemented yet");
    }


    @Test
    void ensureFrodoIsAHobbitWithCondition() {
        // TolkienCharacter frodo = new DataService().getFellowshipCharacter("Frodo");
        fail("Use aHobbit from below in an assert statement is()");
    }
}

