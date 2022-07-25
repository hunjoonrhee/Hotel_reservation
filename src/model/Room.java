package model;

import java.util.Objects;

public class Room implements IRoom{
    String roomNumber;
    Double price;
    RoomType roomtype;
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

    public String toString(){
        return "Room: " + roomNumber + ", Room type: " + roomtype + ", Room price: $" + price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
