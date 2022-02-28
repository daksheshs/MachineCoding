package MachineCoding.ParkingLot.src;
import java.time.*;
public class Ticket {

    private String ticketId;
    private Vehicle vehicle;
    private int floorId;
    private int spotId;
    private int amount;
    LocalDateTime entryTime;
    LocalDateTime exitTime;

    public Ticket(String ticketId, Vehicle vehicle, int floorId, int spotId, int amount) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.floorId = floorId;
        this.spotId = spotId;
        this.amount = amount;
        LocalDateTime now = LocalDateTime.now();
        entryTime = now;
        exitTime = null;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getFloorId() {
        return floorId;
    }

    public int getSpotId() {
        return spotId;
    }
    public int getAmount() {
        return amount;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void finishTicket() {
        LocalDateTime now = LocalDateTime.now();
        exitTime = now;
    }

    public boolean isExpired() {
       if(exitTime == null) {
           return false;
       }
        
        return true;
    }
}
