package sept.challenge2.person.dao;

import org.springframework.stereotype.Repository;
import sept.challenge2.person.model.Person;

import java.sql.*;

@Repository
public class PersonDAO{
    private final String TABLE_NAME = "person";

    public void setup() throws SQLException {
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER NOT NULL , name VARCHAR(255) NOT NULL," +
                    "address VARCHAR(255) NOT NULL," + "postcode INTEGER NOT NULL," + "age INTEGER NOT NULL,"
                    + "job VARCHAR(255) NOT NULL,"+ "email VARCHAR(255) NOT NULL," + "phoneno VARCHAR(255) NOT NULL," + "PRIMARY KEY (ID))";

            stmt.executeUpdate(sql);
        }
    }

    public Person createPerson(Person person) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Integer id = person.getID();
        String name = person.getName();
        String address = person.getAddress();
        Integer postcode = person.getPostcode();
        Integer age = person.getAge();
        String job = person.getJob();
        String email = person.getEmail();
        String phoneno = person.getPhoneno();
        try (Connection connection = Database.getConnection();

             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, address);
            stmt.setInt(4, postcode);
            stmt.setInt(5, age);
            stmt.setString(6, job);
            stmt.setString(7, email);
            stmt.setString(8, phoneno);


            stmt.executeUpdate();
            return new Person(id, name, address, postcode, age, job, email, phoneno);

        }
    }

    public Person getPerson(Integer id) throws SQLException{
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setInt(1, id);
            stmt.executeQuery();
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())  {
                    Person Person = new Person();
                    Person.setID(rs.getInt("ID"));
                    Person.setName(rs.getString("name"));
                    Person.setAddress(rs.getString("address"));
                    Person.setPostcode(rs.getInt("postcode"));
                    Person.setAge(rs.getInt("age"));
                    Person.setJob(rs.getString("job"));
                    Person.setEmail(rs.getString("email"));
                    Person.setPhoneno(rs.getString("phoneno"));
                    return Person;
                }
                return null;
            }
        }
    }

    public Person updatePerson(Integer ID, String name, String address, Integer postcode, Integer age, String job, String email, String phoneno) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET name = ?, address = ?, postcode = ?, age = ?, job = ?, email = ?, phoneno = ? WHERE ID = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setInt(3, postcode);
            stmt.setInt(4, age);
            stmt.setString(5, job);
            stmt.setString(6, email);
            stmt.setString(7, phoneno);
            stmt.setInt(8, ID);
            stmt.executeUpdate();

            Person Person = getPerson(ID);
            if (Person == null) {
                return null;
            }
            Person.setID(ID);
            Person.setName(name);
            Person.setAddress(address);
            Person.setPostcode(postcode);
            Person.setAge(age);
            Person.setJob(job);
            Person.setEmail(email);
            Person.setPhoneno(phoneno);
            return Person;

        }
    }

    public boolean deletePerson(Integer ID) throws SQLException{
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, ID);
            stmt.execute();
            return true;
        }
    }

    public Integer getCount() throws SQLException{
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;

    }


}
