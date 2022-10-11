package sept.challenge2.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import sept.challenge2.person.dao.PersonDAO;
import sept.challenge2.person.model.Person;

import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonDAO personDAO;
    private Person person;

    @RequestMapping("/")
    public String index() throws SQLException {
        personDAO = new PersonDAO();
        personDAO.setup();
        return "Person DB Setup Complete";

    }

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseBody
    public Person createBooking(
            @RequestHeader(name = "X-COM-PERSIST", required = false) String headerPersist,
            @RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA") String headerLocation,
            @RequestBody Person person)
            throws Exception
    {


        System.out.println(person);

        personDAO.createPerson(person);


        return person;
    }
    @GetMapping(value = "/{id}", produces = "application/json")
    public Person get(@PathVariable Integer id) throws SQLException {
        person = personDAO.getPerson(id);
        System.out.println(person);
        return person;
    }

    @PutMapping(value = "/update", consumes = "application/json", produces =  "application/json")
    @ResponseBody
    public Person update(
            @RequestBody Person person
    ) throws SQLException{
        person = personDAO.updatePerson(person.getID(), person.getName(), person.getAddress(),
        person.getPostcode(), person.getAge(), person.getJob(), person.getEmail(), person.getPhoneno());

        return person;

    }

    @DeleteMapping(value = "/delete", consumes = "application/json")
    @ResponseBody
    public String delete(@RequestBody Person person) throws SQLException{
        Boolean result = personDAO.deletePerson(person.getID());
        System.out.println(person);
        if(result == true){
            return "Person Deleted";
        }
        else{
            return "Person Does Not Exist";
        }
    }




}