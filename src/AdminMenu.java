import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getSingleton();

    public static void adminmenu(){
        printadminMenu();

        String  input = "";
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
                            MainMenu.mainmenu();
                    }
                } else {
                    System.out.println("Error: Invalid action\n");
                }
            } while (input.charAt(0) != '5' || input.length() != 1);
        } catch (Exception ex) {
            System.out.println("Empty input received. Exiting program...");
        }

    }

    public static void Option1(){
        // display all customers
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            adminResource.getAllCustomers().forEach(System.out::println);
        }
        printadminMenu();
    }
    public static void Option2(){
        // see all rooms
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if(rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            adminResource.getAllRooms().forEach(System.out::println);
        }
        printadminMenu();
    }
    public static void Option3(){
        // display all reservations
        adminResource.displayAllReservations();
        printadminMenu();
    }
    public static void Option4() {
        // add a room
        String answer = "y";
        String a = null;
        do{
            System.out.println("Enter room number");
            Scanner sc1 = new Scanner(System.in);
            int number = sc1.nextInt();
            String roomNumber = Integer.toString(number);
            System.out.println("Enter price per night");
            int price = sc1.nextInt();
            Double roomPrice = (double) price;
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            int type = sc1.nextInt();
            String roomType = Integer.toString(type);
            RoomType Roomtype = null;
            if (roomType.equals("1")) {
                Roomtype = RoomType.SINGLE;
            } else if (roomType.equals("2")) {
                Roomtype = RoomType.DOUBLE;
            } else {
                System.out.println("Error: Invalid input. Try again.");
            }
            Room room = new Room(roomNumber, roomPrice, Roomtype);
            List<IRoom> rooms = new ArrayList<IRoom>();
            rooms.add(room);
            AdminResource.addRoom(rooms);
            System.out.println("Would you like to add another room? y/n");
            a= sc1.next();
            while(!a.equals("y")&&!a.equals("n")){
                System.out.println("Please enter y for Yes or n for No");
                a = sc1.next();
            }
        } while(a.equals("y"));
        printadminMenu();

    }
    public static void printadminMenu(){
        System.out.println("Admin Menu");
        System.out.println("----------------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("----------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }

}
