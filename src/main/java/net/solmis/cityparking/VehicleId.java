package net.solmis.cityparking;

public class VehicleId {
    public String licensePlate;

    public VehicleId(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public boolean equals(VehicleId other) {
        return this.licensePlate.equals((other.licensePlate));
    }

    public int hashCode() {
        return this.licensePlate.hashCode();
    }

    public boolean isEmpty() {
        return this.licensePlate == null;
    }
}
