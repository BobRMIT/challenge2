package sept.challenge2.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yaml.snakeyaml.events.Event;
import sept.challenge2.account.dao.AccountDAO;
import sept.challenge2.account.model.Account;

import java.net.URI;
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
    public ResponseEntity<Account> createBooking(
            @RequestHeader(name = "X-COM-PERSIST", required = false) String headerPersist,
            @RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA") String headerLocation,
            @RequestBody Account account)
            throws Exception
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();


        //Generate resource id
        Integer id = accountDAO.getCount() + 1;
        account.setID(id);
        account.setAccountDate(formatter.format(date));
        //add resource
        System.out.println(account);

        accountDAO.createAccount(account);

//        accountDAO.createAccount(account.getID(), account.getAccountType(), account.getAccountNo(),
//                account.getAccountName(), account.getAccountBal(), account.getAccountDate());

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.getID())
                .toUri();

        //Send location in response
        //System.out.println(ResponseEntity.created(location).body(0));
        //System.out.println(ResponseEntity.created(location).build());
        return ResponseEntity.created(location).build();
    }
    @GetMapping(value = "/{id}", produces = "application/json")
    public Account get(@PathVariable Integer id) throws SQLException {
        account = accountDAO.getAccount(id);

        return account;
    }

    @PutMapping(value = "/update", consumes = "application/json", produces =  "application/json")
    public Account update(
            @RequestBody Account account
    ) throws SQLException{
        account = accountDAO.updateAccount(account.getID(), account.getAccountType(),
                account.getAccountNo(), account.getAccountName(),
                account.getAccountBal());

        return account;

    }

    @DeleteMapping(value = "/delete", consumes = "application/json")
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