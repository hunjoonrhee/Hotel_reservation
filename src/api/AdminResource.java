package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {

    public static final AdminResource SINGLETON = new AdminResource();
    private final CustomerService customerService = CustomerService.getSingleton();
    private static final ReservationService reservationService = ReservationService.getSingleton();
    public AdminResource(){

    }

    public static AdminResource getSingleton (){
        return SINGLETON;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);

    }
    public static void addRoom(List<IRoom> rooms){
        rooms.forEach(reservationService::addRoom);
    }
    public Collection<IRoom> getAllRooms(){
        return reservationService.getRooms();
    }
    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    public void displayAllReservations(){
        reservationService.printAllReservation();
    }
}
