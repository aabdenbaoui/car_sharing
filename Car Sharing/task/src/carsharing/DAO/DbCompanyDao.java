package carsharing.DAO;

import carsharing.DbClient;
import carsharing.entities.Company;
import carsharing.entities.ConnectionFactory;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCompanyDao implements CompanyDao {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private static final String CREATE_DB =
            "CREATE TABLE IF NOT EXISTS COMPANY (id int NOT NULL AUTO_INCREMENT, name VARCHAR(255) UNIQUE NOT NULL," +
                    "PRIMARY KEY(id));";
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";
    private static final String SELECT = "SELECT * FROM COMPANY WHERE id = %d";
    private static final String INSERT_DATA = "INSERT INTO COMPANY(name) VALUES (?)";
    private static final String UPDATE_DATA = "UPDATE COMPANY SET name " +
            "= '%s' WHERE id = %d";
    private static final String DELETE_DATA = "DELETE FROM COMPANY WHERE id = %d";

    private ConnectionFactory factory;

    public DbCompanyDao(String fileName) {
        this.factory = new ConnectionFactory(fileName);
        try (
                Connection connection = this.factory.getConnection();
                PreparedStatement stmt = connection.prepareStatement(CREATE_DB);
        ) {
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();

        try (Connection con = factory.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(SELECT_ALL)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
//                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                Company company = new Company(name);
                companies.add(company);
            }

            return companies;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }

    @Override
    public Company findById(int id) {
        return null;
    }

    @Override
    public void add(Company company) {

        try (
                Connection connection = this.factory.getConnection();
                PreparedStatement stmt = connection.prepareStatement(INSERT_DATA);
        ) {
            stmt.setString(1, company.getName());
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

    }

    @Override
    public void update(Company company) {

    }

    @Override
    public void deleteById(int id) {

    }
}
