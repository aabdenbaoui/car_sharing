package carsharing.DAO;

import carsharing.entities.Car;
import carsharing.entities.Company;
import carsharing.entities.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCompanyDao implements CompanyDao {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private static final String CREATE_DB =
            "DROP TABLE IF EXISTS CAR;"+ "DROP TABLE IF EXISTS COMPANY;"+

            "CREATE TABLE IF NOT EXISTS COMPANY (id int NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(255) UNIQUE NOT NULL," +
                    "PRIMARY KEY(id));" +
            "CREATE TABLE IF NOT EXISTS CAR(id int NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(255) UNIQUE NOT NULL," +
                    "company_id int not null," +
                    "PRIMARY KEY(id)," +
                    "FOREIGN KEY(company_id) REFERENCES COMPANY(id));";
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";
    private static final String SELECT_CARS_ALL = "SELECT * FROM CAR where company_id = ?";

    private static final String SELECT = "SELECT * FROM COMPANY WHERE id = ?";
    private static final String INSERT_COMPANY_DATA = "INSERT INTO COMPANY(name) VALUES (?)";
    private static final String INSERT_CAR_DATA = "INSERT INTO CAR(name, company_id) VALUES (?, ?) ";

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
    public List<Company> findAllComapnies() {
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
        Company company = new Company();
        try (Connection connection = this.factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT);

        ){
            stmt.setLong(1, id);
            ResultSet resultSetItem = stmt.executeQuery();
            while(resultSetItem.next()){
                company.setName(resultSetItem.getString("name"));
            }
        }
     catch (SQLException e) {
         e.printStackTrace();
      }
        return company;
    }
    public void add(Car car) {

        try (
                Connection connection = this.factory.getConnection();
                PreparedStatement stmt = connection.prepareStatement(INSERT_CAR_DATA);
        ) {
            stmt.setString(1, car.getName());
            stmt.setLong(2, car.getCompanyId());

            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

    }    @Override
    public void add(Company company) {

        try (
                Connection connection = this.factory.getConnection();
                PreparedStatement stmt = connection.prepareStatement(INSERT_COMPANY_DATA);
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

    public List<Car> findAllCars(long id) {
        List<Car> cars = new ArrayList<>();

        try (Connection connection = this.factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_CARS_ALL);

        ){
            stmt.setLong(1, id);
            ResultSet resultSetItem = stmt.executeQuery();

            while (resultSetItem.next()) {
                // Retrieve column values
//                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                Car car = new Car(name);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }
}
