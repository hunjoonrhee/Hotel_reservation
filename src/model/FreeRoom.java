package model;

public class FreeRoom extends Room{

    public FreeRoom(final String roomNumber, final RoomType roomtype) {
        super(roomNumber, 0.0, roomtype);
    }

    @Override
    public String toString() {
        return "FreeRoom => " + super.toString();
    }
}
