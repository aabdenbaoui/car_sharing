package carsharing;

import carsharing.entities.Company;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbClient {
    private final DataSource dataSource;

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void run(String str) {
        try (Connection con = dataSource.getConnection(); // Statement creation
             PreparedStatement preparedStatement = con.prepareStatement(str)
        ) {
//            statement.executeUpdate(str); // Statement execution
            int count = preparedStatement.executeUpdate();
            if(count > 0){
                System.out.println("Company was added successfully ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void run(String str, Company company) {
        try (Connection con = dataSource.getConnection(); // Statement creation
             PreparedStatement preparedStatement = con.prepareStatement(str)
        ) {
//            statement.executeUpdate(str); // Statement execution
            preparedStatement.setString(1, company.getName());
            int count = preparedStatement.executeUpdate();

            if(count > 0){
                System.out.println("Company was added successfully ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Company select(String query) {
        List<Company> developers = selectForList(query);
        if (developers.size() == 1) {
            return developers.get(0);
        } else if (developers.size() == 0) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Company> selectForList(String query) {
        List<Company> companies = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
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
}