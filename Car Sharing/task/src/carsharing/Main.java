package carsharing;
import carsharing.entities.Company;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";

    //  Database credentials
    public static void main(String[] args) {
        createConnection();
        mainMenu();
   }

   public static void mainMenu(){
      System.out.println("1. Log in as a manager");
      System.out.println("0. Exit");
      Scanner sc =  new Scanner(System.in);
      int userInput = sc.nextInt();
      while(userInput != 0){
          if(userInput == 1){
              //login as manager
              managerChoices();
          }else if(userInput == 0){
              System.exit(0);
          }else{
              System.out.println("Wrong input");
          }
          System.out.println("1. Log in as a manager");
          System.out.println("0. Exit");
          userInput = sc.nextInt();
      }
   }
   public static void managerChoices() {
      System.out.println("1. Company list");
      System.out.println("2. Create a company");
      System.out.println("0. Back");
      Scanner sc =  new Scanner(System.in);
      int userInput = sc.nextInt();
      while(userInput != 0){
          if(userInput == 1){
              System.out.println("Company list");
              System.out.println("The company list is empty!");
          }else if (userInput == 2){
              System.out.println("Create a company");
              createCompany();
          }else if (userInput == 0){
              System.out.println("back");
          }else{
              System.out.println("Wrong input");
          }
          System.out.println("1. Company list");
          System.out.println("2. Create a company");
          System.out.println("0. Back");
          userInput = sc.nextInt();
      }

   }
   public static long createCompany(){
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter the company name:");
      String name;
      name = sc.nextLine();
      Company company = new Company(name);
      String insertCompany = "INSERT INTO COMPANY(id, name) VALUES (default, ?)";

       System.out.println("The company was created!");
       long id = 0;

       try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(insertCompany,
                    Statement.RETURN_GENERATED_KEYS)) {

           pstmt.setString(1, company.getName());

           int affectedRows = pstmt.executeUpdate();
           // check the affected rows
           if (affectedRows > 0) {
               // get the ID back
               try (ResultSet rs = pstmt.getGeneratedKeys()) {
                   if (rs.next()) {
                       id = rs.getLong(1);
                   }
               } catch (SQLException ex) {
                   System.out.println(ex.getMessage());
               }
           }
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
       }
       return id;
   }
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
   public static void createConnection(){
      Connection connection = null;
      Statement stmt = null;
      try{
          // write your code here
          Class.forName(JDBC_DRIVER);

          //STEP 2: Open a connection
          System.out.println("Connecting to database...");
          connection = DriverManager.getConnection(DB_URL);

          //STEP 3: Execute a query
          System.out.println("Creating table in given database...");
          stmt = connection.createStatement();
          String createTable = "CREATE TABLE IF NOT EXISTS COMPANY " +
                  "(id INT not NULL AUTO_INCREMENT, " +
                  " NAME VARCHAR(255) NOT NULL UNIQUE, " +
                  " PRIMARY KEY ( id ))";
          stmt.execute(createTable);
          connection.setAutoCommit(true);
          stmt.close();
          connection.close();
      } catch(SQLException se) {
          //Handle errors for JDBC
          se.printStackTrace();
      } catch(Exception e) {
          //Handle errors for Class.forName
          e.printStackTrace();
      } finally {
          //finally block used to close resources
          try{
              if(stmt!=null) stmt.close();
          } catch(SQLException se2) {
          } // nothing we can do
          try {
              if(connection!=null) connection.close();
          } catch(SQLException se){
              se.printStackTrace();
          } //end finally try
      } //end try
      System.out.println("Goodbye!");
  }

}


