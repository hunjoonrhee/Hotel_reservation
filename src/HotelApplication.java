import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class HotelApplication {
    private static final HotelResource hotelResource = HotelResource.getSingleton();
    public static void main(String[] args) throws ParseException {

        System.out.println("Welcome to the Hotel Reservation Application");

        // main menu
        MainMenu.mainmenu();


        String input = "";
        final Scanner scanner = new Scanner(System.in);
        try {
            do {
                input = scanner.nextLine();

                if (input.length() == 1) {
                    switch (input.charAt(0)) {
                        case '1':
                            Option1();
                            break;
                        case '2':
                            Option2();
                            break;
                        case '3':
                            Option3();
                            break;
                        case '4':
                            Option4();
                            break;
                        case '5':
                            // exit the program
                            System.out.println("Exit the application");
                            scanner.close();
                    }
                } else {
                    System.out.println("Error: Invalid action\n");
                }
            } while (input.charAt(0) != '5' || input.length() != 1);
        } catch (Exception ex) {
            System.out.println("Empty input received. Exiting program...");
        }
    }

    public static void Option1() throws ParseException {
        //Find and reserve a room and then main menu again.
        System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2022");
        Scanner sc1 = new Scanner(System.in);
        String checkIn = sc1.next();
        Date CheckIn = new SimpleDateFormat("MM/dd/yyyy").parse(checkIn);
        System.out.println("Enter CheckOut Date mm/dd/yy example 02/10/2022");
        String checkOut = sc1.next();
        Date CheckOut = new SimpleDateFormat("MM/dd/yyyy").parse(checkOut);
        Collection<IRoom> availableRooms = hotelResource.findARoom(CheckIn, CheckOut);
        if (availableRooms.isEmpty()) {
            Collection<IRoom> alternativeRooms = hotelResource.findAlternativeRooms(CheckIn, CheckOut);
            if (alternativeRooms.isEmpty()) {
                System.out.println("There are no rooms.");
            } else {
                final Date alternativeCheckIn = hotelResource.addDefaultPlusDays(CheckIn);
                final Date alternativeCheckOut = hotelResource.addDefaultPlusDays(CheckOut);
                System.out.println("with the desired checkin-date and check-out date unfortunately, there are no more rooms to book. As an alternative, we can suggest::" +
                        "\nCheck-In Date:" + alternativeCheckIn +
                        "\nCheck-Out Date:" + alternativeCheckOut);
            }
        } else {
            for (IRoom room : availableRooms) {
                System.out.println(room.toString());
            }
        }
        System.out.println("Would you like to book a room? y/n");
        String ans = sc1.next();
        if (ans.equals("y")) {
            System.out.println("Do you have an account with us? y/n");
            String ans2 = sc1.next();
            if (ans2.equals("n")) {
                System.out.println("You have to first create an account. Please go to the option 3 and create an account.");
            } else if (ans2.equals("y")) {
                System.out.println("Enter Email format: name@domain.com");
                final String customerEmail = sc1.next();
                if (hotelResource.getCustomer(customerEmail) == null) {
                    System.out.println("Customer not found.\nYou may need to create a new account.");
                } else {
                    System.out.println("What room number would you like to reserve?");
                    final String roomNumber = sc1.next();
                    if(availableRooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))){
                        final IRoom room = hotelResource.getARoom(roomNumber);
                        final Reservation reservation = hotelResource.bookARoom(customerEmail, room, CheckIn, CheckOut);
                        System.out.println("Reservation created successfully!");
                        System.out.println(reservation);
                    }else {
                        System.out.println("Error: room number not available.\nStart reservation again.");
                    }
                }
            } else {
                System.out.println("Error: Invalid Input. Please try again.");
            }
        } else if (ans.equals("n")) {
            // mainmenu();
            System.out.println("back to the main menu");
        } else {
            System.out.println("Error: Invalid Input. Please try again.");
            Option1();
        }
        MainMenu.mainmenu();
    }

    public static void Option2() {
        // See my reservation
        System.out.println("Enter Email format: name@domain.com");
        Scanner sc2 = new Scanner(System.in);
        String email = sc2.next();
        final Collection<Reservation> customersReservations=hotelResource.getCustomersReservations(email);
        if(customersReservations==null || customersReservations.isEmpty()){
            System.out.println("No reservations found.");

        }else{
            customersReservations.forEach(reservation -> System.out.println("\n" + reservation));
        }
        MainMenu.mainmenu();
    }

    public static void Option3() {
        // create an Account and then main menu again
        System.out.println("Enter Email format: name@domain.com");
        //String input3 = null;
        Scanner sc3 = new Scanner (System.in);
        String email = sc3.next();
        System.out.println("First Name");
        String firstName = sc3.next();
        System.out.println("Last Name");
        String lastName = sc3.next();
        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");

            MainMenu.mainmenu();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            Option3();
        }

    }
    public static void Option4() {
        // go to the admin menu
        AdminMenu.adminmenu();
    }
}
