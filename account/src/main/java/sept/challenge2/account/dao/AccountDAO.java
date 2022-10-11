package sept.challenge2.account.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sept.challenge2.account.model.Account;

import javax.persistence.Table;
import java.sql.*;

@Repository
public class AccountDAO{
    private final String TABLE_NAME = "account";

    public void setup() throws SQLException {
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER NOT NULL , AccountType VARCHAR(255) NOT NULL," +
                    "AccountNo VARCHAR(255) NOT NULL," + "AccountName VARCHAR(255) NOT NULL,"
                    + "AccountBal VARCHAR(255) NOT NULL,"+ "AccountDate VARCHAR(255) NOT NULL," + "PRIMARY KEY (ID))";

            stmt.executeUpdate(sql);
        }
    }

    public Account createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?)";
        Integer id = account.getID();
        String AccountType = account.getAccountType();
        String AccountNo = account.getAccountNo();
        String AccountName = account.getAccountName();
        String AccountBal = account.getAccountBal();
        String AccountDate = account.getAccountDate();
        try (Connection connection = Database.getConnection();

             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, id);
            stmt.setString(2, AccountType);
            stmt.setString(3, AccountNo);
            stmt.setString(4, AccountName);
            stmt.setString(5, AccountBal);
            stmt.setString(6, AccountDate);

            stmt.executeUpdate();
            return new Account(id, AccountType, AccountNo, AccountName, AccountBal, AccountDate);

        }
    }

    public Account getAccount(Integer id) throws SQLException{
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);){
            stmt.setInt(1, id);
            stmt.executeQuery();
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())  {
                    Account account = new Account();
                    account.setID(rs.getInt("ID"));
                    account.setAccountType(rs.getString("AccountType"));
                    account.setAccountNo(rs.getString("AccountNo"));
                    account.setAccountName(rs.getString("AccountName"));
                    account.setAccountBal(rs.getString("AccountBal"));
                    account.setAccountDate(rs.getString("AccountDate"));
                    return account;
                }
                return null;
            }
        }
    }

    public Account updateAccount(Integer ID, String AccountType, String AccountNo, String AccountName, String AccountBal) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET AccountType = ?, AccountNo = ?, AccountName = ?, AccountBal = ? WHERE ID = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, AccountType);
            stmt.setString(2, AccountNo);
            stmt.setString(3, AccountName);
            stmt.setString(4, AccountBal);
            stmt.setInt(5, ID);
            stmt.executeUpdate();

            Account account = getAccount(ID);
            if (account == null) {
                return null;
            }
            account.setAccountType(AccountType);
            account.setAccountNo(AccountNo);
            account.setAccountName(AccountName);
            account.setAccountBal(AccountBal);
            return account;

        }
    }

    public boolean deleteAccount(Integer ID) throws SQLException{
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
