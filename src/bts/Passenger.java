package bts;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;

import bts.Ticket;
import bts.User;

public class Passenger extends User {

    public static ArrayList<Savable> instances = new ArrayList<>();
    public final static String savedPath = "passengers.csv";
    public final static String className = "Passenger";

    public Passenger(int id, String name, String username, String password, String Email) {
        super(id, name, username, password, Email);
        Passenger.instances.add(this);
    }
    public Passenger() {
        super(Passenger.instances, Passenger.savedPath);
        Passenger.instances.add(this);
    }

    public void startFlow() {
        /* 
        From the passenger profile he is able to select the trip he wants to make (source,destination,one-way,round-trip,number of stops … etc) from a list of available trips.
        */

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println(
                    "Please choose one of the following:\n\n(B) book a ticket\n(L) List tickets\n(R) Remove a ticket\n(Q) Quit\n\n");

            switch (scanner.nextLine().toUpperCase()) {
                case "B":
                    bookATicket(scanner);
                    break;
                case "L":
                    Ticket.listTickets(this.id);
                    break;
                case "R":
                    removeTicket(scanner);
                    break;
                case "Q":
                    return;
                default:
                    break;
            }

        }

    }
    
    private void removeTicket(Scanner scanner) {
        System.out.println("Press enter to view available tickets");
        scanner.nextLine();
        Ticket.listTickets(this.id);


        System.out.println("Please type the id of the ticket you want to delete, or type nothing to exit");

        String ans = scanner.nextLine();
        if (ans.isEmpty()) {
            return;
        }
        int id = Integer.parseInt(ans);
        
        for (short i = 0; i < Ticket.instances.size(); i++) {
            if (Ticket.instances.get(i).id == id) {
                removeInstance(id, Ticket.instances, Ticket.savedPath, Ticket.csvHeader);
            }
        }
    }
    

    private void bookATicket(Scanner scanner) {

        System.out.println("Press enter to view the list of available trips");
        scanner.nextLine();

        System.out.println("List of the available trips:\n\n");

        for (short i = 0; i < Trip.instances.size(); i++) {
            Trip trip = (Trip) Trip.instances.get(i);
            System.out.println(trip.toString("Index: " + i + "\n" + trip.info()));
        }

        System.out.println("please type the index of the trip you want to select, type nothing to exit: ");
        String ans = scanner.nextLine();
        if (ans.isEmpty()) {
            return;
        }
        int index = Integer.parseInt(ans);
        Trip trip = (Trip) Trip.instances.get(index);

        if (trip.seats == 0) {
            System.out.println(
                    "\n\nSorry, the requested trip doesn't have enough seats, please choose another one! \n\n");

            bookATicket(scanner);
            return;
        }

        System.out.println("\n\nHere's a detailed information about the selected trip:\n\n");
        System.out.println(trip.toString(trip.allInfo()));

        /*
        When the passenger books a ticket (if there are available seats) he is shown a price for the selected ticket(s) and then proceeds to buy them.
        */

        System.out.println("Book a ticket? (Y/N)  ");

        switch (scanner.nextLine().toUpperCase()) {
            case "Y":
                new Ticket(this.id, trip.id);
                Ticket.saveInstances(Ticket.instances, Ticket.savedPath, Ticket.csvHeader);
                ((Trip) Trip.instances.get(index)).seats -= 1;
                Trip.saveInstances(Trip.instances, Trip.savedPath, Trip.csvHeader);
                break;

            default:
                return;
        }

    }
    
    public static void initiateClass() {
        initiateClass(Passenger.savedPath, Passenger.className, Ticket.instances);
    }

    public static void newInstance(String line) {
        String data[] = line.split(",");
        new Passenger(Integer.parseInt(data[0]), data[1], data[3], data[4], data[2]);
    }
}
