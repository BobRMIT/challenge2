package sept.challenge2.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sept.challenge2.account.dao.AccountDAO;
import sept.challenge2.account.model.Account;

import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountDAO accountDAO;
    private Account account;

    @RequestMapping("/")
    public String index() throws SQLException {
        accountDAO = new AccountDAO();
        accountDAO.setup();
        return "Setup Complete";

    }

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public Account createAccount(
            @RequestHeader(name = "X-COM-PERSIST", required = false) String headerPersist,
            @RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA") String headerLocation,
            @RequestBody Account account)
            throws Exception
    {
        System.out.println(account);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        account.setAccountDate(formatter.format(date));
        //add resource
        accountDAO.createAccount(account);
        return account;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Account get(@PathVariable Integer id) throws SQLException {
        account = accountDAO.getAccount(id);

        return account;
    }

    @PutMapping(value = "/update", consumes = "application/json", produces =  "application/json")
    @ResponseBody
    public Account update(
            @RequestBody Account account
    ) throws SQLException{
        account = accountDAO.updateAccount(account.getID(), account.getAccountType(),
                account.getAccountNo(), account.getAccountName(),
                account.getAccountBal());

        return account;

    }

    @DeleteMapping(value = "/delete", consumes = "application/json")
    @ResponseBody
    public String delete(@RequestBody Account account) throws SQLException{
        Boolean result = accountDAO.deleteAccount(account.getID());
        System.out.println(account);
        if(result == true){
            return "Account Deleted";
        }
        else{
            return "Account Does Not Exist";
        }
    }

}