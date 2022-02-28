package MachineCoding.ParkingLot.src;

public class Vehicle {

    private int vehicleRegNo;
    private String type; // BIKE, CAR, BUS
    private String color;

    public Vehicle(int vehicleRegNo, String type, String color) {
        this.vehicleRegNo = vehicleRegNo;
        this.type = type;
        this.color = color;
    }
    
    public int getVehicleRegNo() {
        return vehicleRegNo;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        String size;
        switch(type) {
            case "BUS":
                size = "LARGE";
                break;
            case "CAR":
                size = "MEDIUM";
                break;
            case "BIKE":
                size = "SMALL";
                break;
            default:
                size = "UNKNOWN";
                break;
        }

        return size;
    }
}
