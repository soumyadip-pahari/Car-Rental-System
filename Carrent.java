package JavaProject;

import java.util.Scanner;
import java.util.ArrayList;

class Car {
    private String carid;
    private String brand;
    private String model;
    private int rentperday;
    private boolean IsAvailable;

    public Car(String carid, String brand, String model, int rentperday) {
        this.carid = carid;
        this.brand = brand;
        this.model = model;
        this.rentperday = rentperday;
        this.IsAvailable = true;
    }

    public String getCarid() {
        return carid;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public int totalPrice(int rentdays) {
        int totalprice = rentdays * rentperday;
        return totalprice;
    }

    public boolean isAvailable() {
        return IsAvailable;
    }

    public void rentCar() {
        IsAvailable = false;
    }

    public void returnCar() {
        IsAvailable = true;
    }
}

class Customer {
    private String custid;
    private String custName;

    public Customer(String custid, String custName) {
        this.custid = custid;
        this.custName = custName;
    }

    public String getCustid() {
        return custid;
    }

    public String getCustName() {
        return custName;
    }

}

class Rent {
    private Car car;
    private Customer customer;
    private int days;

    public Rent(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getdays() {
        return days;
    }

}

class Rentmanagementsys {
    private ArrayList<Car> carlist;
    private ArrayList<Customer> customerlist;
    private ArrayList<Rent> rentlist;

    public Rentmanagementsys() {
        carlist = new ArrayList<Car>();
        customerlist = new ArrayList<Customer>();
        rentlist = new ArrayList<Rent>();
    }

    public void addCar(Car cars) {
        carlist.add(cars);
    }

    public void addCust(Customer customers) {
        customerlist.add(customers);
    }

    public void carRental(Car car, Customer customer, int days, boolean IsAvailable) {
        car.rentCar();
        rentlist.add(new Rent(car, customer, days));
    }

    public void returnCar(Car rcar) {
        Rent r = null;
        for (Rent rent : rentlist) {
            if (rent.getCar() == rcar) {
                r = rent;
                break;
            }
        }
        if (r != null) {
            rentlist.remove(r);
            rcar.returnCar();
        } 
        else {
            System.out.println("This Car was not rented from here.");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("=====Welcome to Car Rental System =====");
            System.out.println("1. Rent a Car!");
            System.out.println("2. Return a Car!");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String custname = sc.nextLine();
                System.out.println("\nAvailable Cars:");
                for (Car cars : carlist) {
                    if (cars.isAvailable()) {
                        System.out.println(cars.getCarid() + " - " + cars.getBrand() + " " + cars.getModel());
                    }
                }
                System.out.print("\nEnter the car ID you want to rent: ");
                String carid = sc.nextLine();
                System.out.print("Enter the number of days for rental: ");
                int days = sc.nextInt();
                Car selectedCar = null;
                for (Car car : carlist) {
                    if (car.getCarid().equals(carid) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    int totalprice = selectedCar.totalPrice(days);
                    System.out.println("\n== Rental Information ==\n");
                    Customer newCustomer = new Customer("CUS" + (customerlist.size()), custname);
                    System.out.println("Customer ID: " + newCustomer.getCustid());
                    System.out.println("Customer Name: " + newCustomer.getCustName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + days);
                    System.out.println("Total Price: Rs." + totalprice);
                    System.out.print("\nConfirm rental (True/False): ");
                    boolean confirm = sc.nextBoolean();
                    if (confirm == true) {
                        carRental(selectedCar, newCustomer, days, confirm);
                        System.out.println("\nCar "+carid+" rented successfully by "+custname+"..\n");
                        addCust(newCustomer);
                        
                    } 
                    else {
                        System.out.println("\nRental canceled.\n");
                        
                    }
                } 
                else {
                    System.out.println("\nInvalid car selection or car is not available for rent.\n");
                   
                }
            } 
            else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.println("Enter the car ID you want to return (C001/C002/C003): ");
                String carId = sc.nextLine();
                System.out.println("Enter your Name");
                String cName=sc.nextLine();
                Car carToReturn = null;
                Customer theCust=null;
                for (Car car : carlist) {
                    if (car.getCarid().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                for (Customer customer : customerlist) {
                    if (customer.getCustName().equals(cName)) {
                        theCust=customer;
                        break;
                    }
                }
                if (carToReturn != null && theCust!=null) {
                    for (Rent rent : rentlist) {
                        if (rent.getCar() == carToReturn) {
                            if(rent.getCustomer()==theCust){
                                returnCar(carToReturn);
                                System.out.println("\nThank You! Car "+carId+" returned successfully by " +cName+"...\n");
                            } 
                            else{
                                 System.out.println("\n"+cName+" not rented this "+carId+" Car...\n\n");
                            } 
                        }
                    }  
                } else {
                    System.out.println("\n"+carId+" was not rented from here or" +cName+ "was not rented any car from here....\n");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("\nInvalid choice. Please enter a valid option.\n\n");
                
            }
        }
        System.out.println("\nThank you for using our Car Rental System!\n");
    }
}

public class Carrent {
    public static void main(String[] args) {
        Rentmanagementsys obj = new Rentmanagementsys();
        Car c1 = new Car("C001", "Mahindra", "Thar", 7800);
        Car c2 = new Car("C002", "TATA", "ALTROZ 2022", 5000);
        Car c3 = new Car("C003", "Hyundai", "Creta", 7200);
        Car c4= new Car("C004", "Fortuner", "Legender", 2880);
        obj.addCar(c1);
        obj.addCar(c2);
        obj.addCar(c3);
        obj.addCar(c4);
        obj.menu();
    }
}
