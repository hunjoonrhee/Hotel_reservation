package service;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    private static final ReservationService SINGLETON = new ReservationService();
    private static final Map<String, IRoom> rooms = new HashMap<>();;
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();
    private static Map<Customer, Reservation> CustomerReservation;

    private static ArrayList<Reservation> CustomersReservations;

    public ReservationService(){}

    public static ReservationService getSingleton() {
        return SINGLETON;
    }

    public void addRoom(IRoom room){
        if(rooms.isEmpty()){
            rooms.put(room.getRoomNumber(), room);
        }else{
            if(!rooms.containsKey(room.getRoomNumber())){
                rooms.put(room.getRoomNumber(), room);
            }else{
                System.out.println("There is already a room with room number: " + room.getRoomNumber());
            }
        }


    }

    public IRoom getARoom(String roomId){
        if(!rooms.containsKey(roomId)){
            System.out.println("There is no room with room number: " + roomId);
            return null;
        }
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        final Reservation reservation = new Reservation (customer, room, checkInDate, checkOutDate);
        Collection<Reservation> customerReservations = getCustomersReservation(customer);
        if(customerReservations == null)
            customerReservations = new ArrayList<>();
        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);
        return reservation;

    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        return avaliableRooms(checkInDate, checkOutDate);
    }

    public Collection<IRoom> avaliableRooms(Date checkInDate, Date checkOutDate){
        Collection <Reservation> allReservations = getAllReservations();
        Collection <IRoom> notAvaliableRooms = new ArrayList<>();

        for (Reservation reservation:allReservations){
            if(isreserved(reservation, checkInDate, checkOutDate)){
                notAvaliableRooms.add(reservation.getRoom());
            }
        }
        return rooms.values().stream().filter(room-> notAvaliableRooms.stream()
                .noneMatch(notAvailableRoom -> notAvailableRoom.equals(room)))
                .collect(Collectors.toList());
    }

    public Collection<IRoom> alternativeRooms(final Date checkInDate, final Date checkOutDate) {
        return avaliableRooms(getAlternativeDates(checkInDate), getAlternativeDates(checkOutDate));
    }

    public Date getAlternativeDates(final Date date) {
        Calendar alternativDate = Calendar.getInstance();
        alternativDate.setTime(date);
        alternativDate.add(Calendar.DATE, 7);

        return alternativDate.getTime();
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        return reservations.get(customer.getEmail());
    }

    public Collection<IRoom> getRooms() {
        ArrayList<IRoom> allRooms = new ArrayList<IRoom>();
        if(!rooms.isEmpty()) {
            for (String key : rooms.keySet()) {
                allRooms.add(rooms.get(key));
            }
        }
        return allRooms;
    }

    private boolean isreserved(final Reservation reservation, final Date checkInDate, final Date checkOutDate){
        if(checkInDate.equals(reservation.getCheckInDate())&&checkOutDate.before(reservation.getCheckOutDate())){
            return true;
        }
        if(checkInDate.after(reservation.getCheckInDate())&&checkOutDate.equals(reservation.getCheckOutDate())){
            return true;
        }
        if(checkInDate.before(reservation.getCheckInDate())&&checkOutDate.after(reservation.getCheckOutDate())){
            return true;
        }
        if(checkInDate.after(reservation.getCheckInDate())&&checkOutDate.before(reservation.getCheckOutDate())){
            return true;
        }
        if(checkInDate.equals(reservation.getCheckInDate())&&checkOutDate.equals(reservation.getCheckOutDate())){
            return true;
        }
        return false;
    }

    private Collection<Reservation> getAllReservations() {
        final Collection<Reservation> allReservations = new ArrayList<>();

        for(Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }

        return allReservations;
    }

    public void printAllReservation(){
        final Collection<Reservation> Reservations = getAllReservations();
        if(!Reservations.isEmpty()){
            System.out.println("-------------------------------------");
            for(Reservation reservation:Reservations){
                System.out.println(reservation + "\n");
            }
        }else {
            System.out.println("There are no Reservations");
        }
    }


}
