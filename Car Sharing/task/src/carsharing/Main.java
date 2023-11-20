package carsharing;
import carsharing.DAO.DbCompanyDao;
import carsharing.entities.Company;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {


    //  Database credentials
    public static void main(String[] args) {
        String databaseFileName = "carsharing.mv.db";
        if (args[0].equals("-databaseFileName") &&
                args.length > 1) {
            databaseFileName = args[1];
        }

        DbCompanyDao dao = new DbCompanyDao(databaseFileName);
        mainMenu(dao);

   }

   public static void mainMenu(DbCompanyDao dbCompanyDao){
      System.out.println("1. Log in as a manager");
      System.out.println("0. Exit");
      Scanner sc =  new Scanner(System.in);
      int userInput = sc.nextInt();
      while(userInput != 0){
          if(userInput == 1){
              //login as manager
              managerChoices(dbCompanyDao);
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
   public static void managerChoices(DbCompanyDao dbCompanyDao) {
      System.out.println("1. Company list");
      System.out.println("2. Create a company");
      System.out.println("0. Back");
      Scanner sc =  new Scanner(System.in);
      int userInput = sc.nextInt();
      while(userInput != 0){
          if(userInput == 1){
              getAllCompanies(dbCompanyDao);
          }else if (userInput == 2){
              System.out.println("Create a company");
              createCompany(dbCompanyDao);
          }else if (userInput == 0){
              System.out.println("back");
          }else{
              System.out.println("Wrong input");
          }
          System.out.println("1. Company list");
          System.out.println("2. Create a company");
          System.out.println("0. Back");
          userInput = sc.
                  nextInt();
      }

   }
   public static long createCompany(DbCompanyDao dbCompanyDao){

      Scanner sc = new Scanner(System.in);
      System.out.println("Enter the company name:");
      String name;
      name = sc.nextLine();
      Company company = new Company(name);
       dbCompanyDao.add(company);
        return 1;
   }
   public static void getAllCompanies(DbCompanyDao dbCompanyDao){
        List<Company> companies = dbCompanyDao.findAll();
        if(companies.size() == 0){
            System.out.println("The company list is empty!");
            return;
        }
       System.out.println("Company list:");
        int i = 1;
        for(Company company: companies){
            System.out.println(i + ". " + company.getName());
            i++;
        }
       System.out.println();
   }

}


