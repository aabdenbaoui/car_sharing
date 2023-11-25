package carsharing;
import carsharing.DAO.DbCompanyDao;
import carsharing.entities.Car;
import carsharing.entities.Company;

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
       System.out.println("The company was created!");

        return 1;
   }
   public static void getAllCompanies(DbCompanyDao dbCompanyDao){
        List<Company> companies = dbCompanyDao.findAllComapnies();
        if(companies.size() == 0){
            System.out.println("The company list is empty!");
            return;
        }
       System.out.println("Choose a company:");
       Scanner sc = new Scanner(System.in);

       int i = 1;
        for(Company company: companies){
            System.out.println(i + ". " + company.getName());
            i++;
        }
       System.out.println("0. Back");
       int userInput = sc.nextInt();
       if(userInput > 0 && userInput <= companies.size()){
            Company company = dbCompanyDao.findById(userInput);
           System.out.println(company.getName() + " company");
           carListMenu(dbCompanyDao, userInput);
       } else if (userInput == 0) {
           return;
       }
       System.out.println();
   }
   public static void carListMenu(DbCompanyDao dbCompanyDao, long companyId){
       System.out.println("1. Car list");
       System.out.println("2. Create a car");
       System.out.println("0. Back");
       Scanner sc =  new Scanner(System.in);
       int userInput = sc.nextInt();
       while(userInput != 0){
           if(userInput == 1){
               getAllCars(dbCompanyDao, companyId);
           }else if (userInput == 2){
               System.out.println("Create a car");
               createCar(dbCompanyDao,companyId);
           }else if (userInput == 0){
               System.out.println("back");
           }else{
               System.out.println("Wrong input");
           }
           System.out.println("1. car list");
           System.out.println("2. Create a car");
           System.out.println("0. Back");
           userInput = sc.
                   nextInt();
       }
   }

    private static void getAllCars(DbCompanyDao dbCompanyDao, long id) {

        List<Car> cars = dbCompanyDao.findAllCars(id);

        if(cars.size() == 0){
            System.out.println("The car list is empty!");
            return;
        }
//        System.out.println("Choose a company:");
        Scanner sc = new Scanner(System.in);

        int i = 1;
        for(Car car: cars){
            System.out.println(i + ". " + car.getName());
            i++;
        }
//        System.out.println("0. back");
//        int userInput = sc.nextInt();
//        if(userInput > 0 && userInput <= cars.size()){
//            Company company = dbCompanyDao.findById(userInput);
//            System.out.println(company.getName() + " company");
//            carListMenu(dbCompanyDao, userInput);
//        } else if (userInput == 0) {
//            return;
//        }
        System.out.println();
    }

    private static void createCar(DbCompanyDao dbCompanyDao, long companyId) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the car name:");
        String name;
        name = sc.nextLine();
        Car car = new Car(name, companyId);
        dbCompanyDao.add(car);
        System.out.println("The car was added!");
    }

}


