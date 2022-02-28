package MachineCoding.ParkingLot.src;
import java.util.*;

public class ParkingFloor {
    private int floorId;
    private int noOfSpots;
    Map<Integer, ParkingSpot> spotMap;

    public ParkingFloor(int floorId, int noOfSpots) {
        this.floorId = floorId;
        this.noOfSpots = noOfSpots;
        spotMap = new HashMap<Integer, ParkingSpot>();
        for(int numSpots = 1; numSpots <= noOfSpots; ++numSpots) {
            spotMap.put(numSpots, new ParkingSpot(numSpots, floorId));
        }
    }
    
    public int getFloorId() {
        return floorId;
    }

    public int getNoOfSpots() {
        return noOfSpots;
    }

    public void addParkingSpot(int spotId) {
        if(spotMap.containsKey(spotId) == true) {
            System.out.println("Spot " + spotId + " already exist");
            return;
        }
        spotMap.put(spotId, new ParkingSpot(spotId, floorId));
        noOfSpots++;
    }

    public ParkingSpot getParkingSpot(int spotId) {
        if((spotId < 1) || (spotMap.size() < spotId)) { //index out of bounds
            System.out.println("Spot " + spotId + "doesn't exist");
            return null;
        }

        return spotMap.get(spotId - 1);
    }

    public List<ParkingSpot> getOccupiedSpots() {
        List<ParkingSpot> occupiedSpots = new ArrayList<ParkingSpot>();
        for(Map.Entry<Integer, ParkingSpot> entry: spotMap.entrySet()) {
            ParkingSpot parkingSpot = entry.getValue();
            if(parkingSpot.isOccupied() == true) {
                occupiedSpots.add(parkingSpot);
            }
        }

        return occupiedSpots;
    }

    public List<ParkingSpot> getFreeSpots() {
        List<ParkingSpot> freeSpots = new ArrayList<ParkingSpot>();
        for(Map.Entry<Integer, ParkingSpot> entry: spotMap.entrySet()) {
            ParkingSpot parkingSpot = entry.getValue();
            if(parkingSpot.isOccupied() == false) {
                freeSpots.add(parkingSpot);
            }
        }

        return freeSpots;
    }

    public void occupySpot(int spotId, Vehicle vehicle) {
        if(spotMap.containsKey(spotId) == false) { // Warning!! To change
            System.out.println("Spot " + spotId + " doesn't exist");
            return;
        }
        ParkingSpot parkingSpot = spotMap.get(spotId);
        parkingSpot.assignVehicle(vehicle);
    }

    public void clearSpot(int spotId) {
        if(spotMap.containsKey(spotId) == false) {
            System.out.println("Spot " + spotId + " is already empty");
            return;
        }
        ParkingSpot parkingSpot = spotMap.get(spotId);
        parkingSpot.removeVehicle();
    }

    public ParkingSpot findVehicle(Vehicle vehicle) {
        for(Map.Entry<Integer, ParkingSpot> entry: spotMap.entrySet()) {
            ParkingSpot parkingSpot = entry.getValue();
            if(parkingSpot.getVehicle() == vehicle) {
                return parkingSpot;
            }
        }

        return null;
    }

    public void displaySpotMap() {
        for(Map.Entry<Integer, ParkingSpot> entry: spotMap.entrySet()) {
            ParkingSpot parkingSpot = entry.getValue();
            System.out.println("Slot: " + parkingSpot.getSpotId() + " Status: " + parkingSpot.isOccupied());
        }
    }
}