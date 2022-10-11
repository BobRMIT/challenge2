package sept.challenge2.person.controller;


import org.junit.jupiter.api.*;
import sept.challenge2.person.dao.PersonDAO;
import sept.challenge2.person.model.Person;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerTest {
    PersonController controller = new PersonController();
    Person person = new Person(5, "Cam Day", "10 Albert St, Melbourne", 3000, 25, "Engineer", "js@email.com","03234654");
    PersonDAO personDAO = new PersonDAO();


    @BeforeAll
    public void init() throws SQLException {

        controller.index();
    }

    @Test
    @DisplayName("Calling index")
    @Order(0)
    public void index_returnsString_IfCalled() {
        // Test method to see if UserDao setups properly

        String actualMessage = "";

        try {
            // Try to call index function
            actualMessage = controller.index();
        }
        catch(Exception e) {}

        String expectedMessage = "Person DB Setup Complete";

        assertEquals(expectedMessage, actualMessage);
    }
    @Test
    @DisplayName("Create User in Database")
    @Order(1)
    public void createPerson_returnsPerson_IfPersonDoesNotExist() throws SQLException {

        personDAO.deletePerson(5);

        String expected = person.toString();
        String actual = personDAO.createPerson(person).toString();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Check If User in Database")
    @Order(2)
    public void checkUserExists_returnsBoolean_IfUsernameInDatabase() {

        Person actualPerson = null;

        try {
            // Try to check if user is in database
            actualPerson = controller.get(5);
        }
        catch(Exception e) {}

        String actual = actualPerson.toString();
        String expected = person.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check for User Not in Database")
    @Order(2)
    public void checkUserExists_returnsBool_IfUsernameNotInDatabase() throws SQLException {
        Person actual = null;
        personDAO.deletePerson(5);
        try {
            // Try to check if user is in database
            actual = controller.get(5);
        }
        catch(Exception e) {}

        Person expected = null;

        assertEquals(expected, actual);
    }


}
