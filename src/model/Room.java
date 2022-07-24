package model;

import java.util.Objects;

public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private final RoomType roomtype;
    public Room(String roomNumber, Double price, RoomType roomtype){
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomtype = roomtype;
    }


    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomtype;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Room)) {
            return false;
        }

        final Room room = (Room) obj;
        return Objects.equals(this.roomNumber, room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    public String toString(){
        return "Room: " + roomNumber + ", Room type: " + roomtype + ", Room price: $" + price;
    }
}


