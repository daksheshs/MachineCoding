package MachineCoding.ParkingLot.test;
import MachineCoding.ParkingLot.src.ParkingLot;
import MachineCoding.ParkingLot.src.Vehicle;

public class Test1 {
    public static void main(String[] args) {
        ParkingLot parkingLot1 = new ParkingLot("1", 4, 4);
        parkingLot1.displayFloorMap();

        // Adding floor
        parkingLot1.addFloor(5);
        parkingLot1.displayFloorMap();
        int Floors = parkingLot1.getFloors();
        System.out.println("Total floors: " + Floors);

        // parking a bike
        Vehicle bike = new Vehicle(1000, "BIKE", "black");
        parkingLot1.parkVehicle(bike);
        parkingLot1.displayFloorMap();
        parkingLot1.displayTicket(bike);

        //parking a car
        Vehicle car = new Vehicle(1001, "CAR", "white");
        parkingLot1.parkVehicle(car);
        parkingLot1.displayFloorMap();
        parkingLot1.displayTicket(car);

        //un-parking car
        parkingLot1.unparkVehicle(car);
        parkingLot1.displayFloorMap();
        parkingLot1.displayTicket(car);

        //un-parking bike
        parkingLot1.unparkVehicle(bike);
        parkingLot1.displayFloorMap();
        parkingLot1.displayTicket(bike);

    }
}
