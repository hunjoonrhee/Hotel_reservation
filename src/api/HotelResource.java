package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class HotelResource {

    private static final HotelResource SINGLETON = new HotelResource();

    private final CustomerService customerService = CustomerService.getSingleton();
    private final ReservationService reservationService = ReservationService.getSingleton();


    public HotelResource(){}

    public static HotelResource getSingleton() {
        return SINGLETON;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);

    }
    public IRoom getARoom(String roomNumber){
       return reservationService.getARoom(roomNumber);

    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        final Customer customer = getCustomer(customerEmail);
        if (customer == null) {
            return Collections.emptyList();
        }
        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
       return reservationService.findRooms(checkIn, checkOut);
    }
    public Collection<IRoom> findAlternativeRooms(final Date checkIn, final Date checkOut) { return reservationService.alternativeRooms(checkIn, checkOut);}
    public Date addDefaultPlusDays(final Date date) {
        return reservationService.getAlternativeDates(date);
    }
}
