package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, Double price, RoomType roomtype){
        super(roomNumber, price, roomtype);
        this.price = 0.0;

    }

    public String toString(){
        return "Free Rooms are " + roomNumber + " " + roomtype + " " + price;
    }
}
