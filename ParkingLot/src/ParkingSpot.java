package MachineCoding.ParkingLot.src;

public class ParkingSpot {

    private int spotId;
    private int floorId;
    private String type; // SMALL, MEDIUM, LARGE
    private Vehicle vehicle;
    private boolean is_occupied;

    public ParkingSpot(int spotId, int floorId) {
        this.spotId = spotId;
        this.floorId = floorId;
        if(spotId == 1) {
            type = "TRUCK";
        }
        else if(spotId == 2 || spotId == 3) {
            type = "MEDIUM";
        }
        else {
            type = "SMALL";
        }
        vehicle = null;
        is_occupied = false;
    }
    
    public int getSpotId() {
        return spotId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isOccupied() {
        return is_occupied;
    }

    public int getFloorId() {
        return floorId;
    }

    public String getType() {
        return type;
    }
    
    public void assignVehicle(Vehicle vehicle) {
        if(vehicle.getSize() != type) {
            System.out.println("Vehicle cannot be assigned to spot: " + spotId + " due to type mismatch");
            return;
        }
        if(is_occupied == true) {
            System.out.println("Vehicle cannot be assigned to spot: " + spotId + " due to spot is not empty");
            return;
        }
        this.vehicle = vehicle;
        is_occupied = true;
    }

    public void removeVehicle() {
        if(is_occupied == false) {
            System.out.println("Vehicle cannot be removed from spot: " + spotId + " due to spot is empty");
            return;
        }
        System.out.println("Vehicle " + vehicle.getVehicleRegNo() + " removed from spot " + spotId);
        this.vehicle = null;
        is_occupied = false;
    }
}
