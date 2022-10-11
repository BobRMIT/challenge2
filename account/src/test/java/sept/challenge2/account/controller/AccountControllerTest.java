package sept.challenge2.account.controller;


import org.junit.jupiter.api.*;
import sept.challenge2.account.dao.AccountDAO;
import sept.challenge2.account.model.Account;


import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountControllerTest {
    AccountController controller = new AccountController();
    Account account =   new Account();
    AccountDAO accountDAO = new AccountDAO();


    @BeforeAll
    public void init() throws SQLException {
//
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

        String expectedMessage = "Setup Complete";

        assertEquals(expectedMessage, actualMessage);
    }
    @Test
    @DisplayName("Check If User in Database")
    @Order(1)
    public void createPerson_returnsAccount_IfAccountDoesNotExist() throws SQLException {

        accountDAO.deleteAccount(2);

        String expected = account.toString();
        String actual = accountDAO.createAccount(account).toString();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Check If User in Database")
    @Order(2)
    public void checkUserExists_returnsBoolean_IfUsernameInDatabase() {

        Account actualAccount = null;

        try {
            // Try to check if user is in database
            actualAccount = controller.get(5);
        }
        catch(Exception e) {}

        String actual = actualAccount.toString();
        String expected = account.toString();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check for User Not in Database")
    @Order(2)
    public void checkUserExists_returnsBool_IfUsernameNotInDatabase() throws SQLException {
        Account actual = null;
        accountDAO.deleteAccount(5);
        try {
            // Try to check if user is in database
            actual = controller.get(5);
        }
        catch(Exception e) {}

        Account expected = null;

        assertEquals(expected, actual);
    }


}
