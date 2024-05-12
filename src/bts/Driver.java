package bts;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import bts.Trip;
import bts.User;

public class Driver extends User {

    public static ArrayList<Savable> instances = new ArrayList<>();
    public final static String className = "Driver";
    public final static String savedPath = "drivers.csv";    

    public Driver( String name, String username, String password, String Email) {
        super(generateId(Driver.instances), name, username, password, Email);
        Driver.instances.add(this);

    }

    public Driver(int id, String name, String username, String password, String Email) {
        super(id, name, username, password, Email);
        Driver.instances.add(this);
    }

    public Driver() {
        super(Driver.instances, Driver.savedPath);
        Driver.instances.add(this);
    }

    public static void listDrivers() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("drivers.csv"));
        bf.readLine();
        String line = bf.readLine();
        while ((line = bf.readLine()) != null) {
            String[] driverData = line.split(",");
            System.out.println("\n\nId: " + driverData[0] + "\nName: " + driverData[1]);
        }
        }
        catch (IOException e) {
            System.out.println("There was an error while lising drivers");
        }
        
    }

    public void startFlow() {
        /*
        If you log in with a driver credentials you are directed to the drivers profile with some basic information about the driver and the trips that are assigned to him by the manager. 
        */

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n\nPlease choose one of the following:\n\n(P) Personal info\n(T) Trips\n");

            switch (scanner.nextLine().toUpperCase()) {
                case "P":
                    System.out.println(toString());
                    break;
                case "T":
                     System.out.println("\n\nHere's the trips assigned to you: \n");
                     for (short i = 0; i < Trip.instances.size(); i++) {
                         Trip trip = (Trip) Trip.instances.get(i);
                         if (this.id ==trip.driverId) {
                             System.out.println(trip.toString(trip.info()));
                         }
                     }
                     break;
                default:
                    break;
            }
        }

    }

    public String toString() {
        return "\n==============\nDriver details:\n\nName:      " + this.name + "\nEmail:     " + this.Email
                + "\nusername:  " + this.username
                + "\n==============\n";
    }
    
    public static void initiateClass() {
        initiateClass(Driver.savedPath, Driver.className, Driver.instances);
    }

    public static void newInstance(String line) {
        String data[] = line.split(",");
        new Driver(Integer.parseInt(data[0]), data[1], data[3], data[4], data[2]);
    }
    
}