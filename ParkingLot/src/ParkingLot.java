package MachineCoding.ParkingLot.src;
import java.util.List;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Map;

public class ParkingLot {
    private String parkingLotId;
    private int noOfFloors;
    private int noOfSpotsPerFloor;
    private int totalAmountCollected;
    private int totalVehicleParked;
    private Map<Integer, ParkingFloor> floorMap; // Stores entire floor for a specific floor number
    private Map<Integer, Ticket> ticketMap; // Stores ticket for a particular vehicle
    
    // Fixed constraints
    public static int RESERVED_SPOT_FOR_TRUCK = 1;
    public static List<Integer> RESERVED_SPOT_FOR_BIKE = Arrays.asList(2, 3);
    public static int BIKE_FEES = 10;
    public static int CAR_FEES = 20;
    public static int TRUCK_FEES = 30;

    public ParkingLot(String parkingLotId, int noOfFloors, int noOfSpotsPerFloor) {
        this.parkingLotId = parkingLotId;
        this.noOfFloors = noOfFloors;
        this.noOfSpotsPerFloor = noOfSpotsPerFloor;
        totalAmountCollected = 0;
        totalVehicleParked = 0;
        floorMap = new TreeMap<Integer, ParkingFloor>();
        ticketMap = new TreeMap<Integer, Ticket>();

        // generating floorMap
        for(int floorNum = 1; floorNum <= noOfFloors; ++floorNum) {
            ParkingFloor parkingFloor = new ParkingFloor(floorNum, noOfSpotsPerFloor);
            floorMap.put(floorNum, parkingFloor);
        }
        System.out.println("Created Parking Lot " + parkingLotId + " with floors: " + noOfFloors + " spots: " + noOfSpotsPerFloor);
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public int getFloors() {
        return noOfFloors;
    }

    public int getSpotsPerFloor() {
        return noOfSpotsPerFloor;
    }

    public void displayFloorMap() {
        for(Map.Entry<Integer, ParkingFloor> entry: floorMap.entrySet()) {
            ParkingFloor parkingFloor = entry.getValue();
            System.out.println("Floor: " + parkingFloor.getFloorId());
            parkingFloor.displaySpotMap();
            System.out.println("-------------");
        }
    }

    public int getTotalAmountCollected() {
        return totalAmountCollected;
    }

    public int getTotalVehicleParked() {
        return totalVehicleParked;
    }

    public void addFloor(int floorId) {
        if(floorMap.containsKey(floorId) == true) {
            System.out.println("Floor " + floorId + " already present");
            return;
        }
        noOfFloors++;
        floorMap.put(floorId, new ParkingFloor(floorId, noOfSpotsPerFloor));
    }

    public void addSpot(int floorId, int spotId) {
        if(floorMap.containsKey(floorId) == false) {
            System.out.println("Floor " + floorId + "doesn't exists. Couldn't add spot");
            return;
        }
        ParkingFloor parkingFloor = floorMap.get(floorId);
        parkingFloor.addParkingSpot(spotId); //since java objects are references, this will be updated in floorMap
    }

    public void parkVehicle(Vehicle vehicle) {
        int calculatedSpotId = -1;
        ParkingFloor calculatedFloor = null;
        // finding first empty spot
        for(Map.Entry<Integer, ParkingFloor> entry: floorMap.entrySet()) {
            ParkingFloor parkingFloor = entry.getValue();
            List<ParkingSpot> emptySpots = parkingFloor.getFreeSpots();
            boolean spotFound = false;
            // matching types
            for(ParkingSpot parkingSpot: emptySpots) {
                if(parkingSpot.getType() == vehicle.getSize()) {
                    spotFound = true;
                    calculatedFloor = parkingFloor;
                    calculatedSpotId = parkingSpot.getSpotId();
                    break;
                }
            }
            if(spotFound == true) {
                break;
            }
        }

        //updating spots
        if(calculatedFloor == null) {
            System.out.println("Sorry!! Parking Lot is full");
            return;
        }
        System.out.println("calculatedFloor: " + calculatedFloor.getFloorId() + " calculatedSpot: " + calculatedSpotId);
        calculatedFloor.occupySpot(calculatedSpotId, vehicle);

        // generating tickets
        Ticket ticket = generateTicket(vehicle, calculatedFloor.getFloorId(), calculatedSpotId);
        totalAmountCollected += ticket.getAmount();
        totalVehicleParked++;
        ticketMap.put(vehicle.getVehicleRegNo(), ticket);
    }

    public void unparkVehicle(Vehicle vehicle) {
        int calculatedSpotId = -1;
        ParkingFloor calculatedFloor = null;
        // finding vehicle
        for(Map.Entry<Integer, ParkingFloor> entry: floorMap.entrySet()) {
            ParkingFloor parkingFloor = entry.getValue();
            ParkingSpot parkingSpot = parkingFloor.findVehicle(vehicle);
            if(parkingSpot != null) {
                calculatedSpotId = parkingSpot.getSpotId();
                calculatedFloor = parkingFloor;
                break;
            }
        }

        //updating spots
        if(calculatedFloor == null) {
            System.out.println("Vehicle " + vehicle.getVehicleRegNo() + " doesn't exists");
            return;
        }
        calculatedFloor.clearSpot(calculatedSpotId);
        totalVehicleParked--;

        //updating ticket
        Ticket ticket = ticketMap.get(vehicle.getVehicleRegNo());
        ticket.finishTicket();
    }

    public void locateVehicle(Vehicle vehicle) {
        int calculatedSpotId = -1;
        int calculatedFloorId = -1;
        for(Map.Entry<Integer, ParkingFloor> entry: floorMap.entrySet()) {
            ParkingFloor parkingFloor = entry.getValue();
            ParkingSpot parkingSpot = parkingFloor.findVehicle(vehicle);
            if(parkingSpot != null) {
                calculatedSpotId = parkingSpot.getSpotId();
                calculatedFloorId = parkingFloor.getFloorId();
                break;
            }
        }
        
        if(calculatedFloorId == -1) {
            System.out.println("Vehicle " + vehicle.getVehicleRegNo() + " is not present");
            return;
        }

        System.out.println("Vehicle " + vehicle.getVehicleRegNo() + " present on floor: " + calculatedFloorId + " inside spot: " + calculatedSpotId);
    }

    protected Ticket generateTicket(Vehicle vehicle, int floorId, int spotId) {
        String ticketId = parkingLotId + "_" + floorId + "_" + spotId;
        int amount = 0;
        switch(vehicle.getType()) {
            case "BIKE":
                amount = BIKE_FEES;
                break;
            case "CAR":
                amount = CAR_FEES;
                break;
            case "TRUCK":
                amount = TRUCK_FEES;
                break;
        }
        return(new Ticket(ticketId, vehicle, floorId, spotId, amount));
    }

    public void displayTicket(Vehicle vehicle) {
        Ticket ticket = ticketMap.get(vehicle.getVehicleRegNo());
        System.out.println("Printing Ticket....");
        System.out.println("Ticket id: " + ticket.getTicketId());
        System.out.println("Vehicle: " + ticket.getVehicle().getVehicleRegNo() + " type: " + ticket.getVehicle().getType() + " color: " + ticket.getVehicle().getColor());
        System.out.println("Floor: " + ticket.getFloorId());
        System.out.println("Spot: " + ticket.getSpotId());
        System.out.println("Amount paid: " + ticket.getAmount());
        System.out.println("Entry Time: " + ticket.getEntryTime());
        System.out.println("Exit Time " + ticket.getExitTime());
    }
}
