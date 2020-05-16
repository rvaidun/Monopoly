package Monopoly;

public class FreeParking extends SquareType implements Square{
    public FreeParking(int position) {
        this.setName("Free Parking");
        this.setTileType("Free Parking");
        this.setPosition(position);
    }
}
