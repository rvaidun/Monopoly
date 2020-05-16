package Monopoly;

public class Go extends SquareType{
    public Go(int position) {
        this.setName("Go");
        this.setTileType("Go");
        this.setPosition(position);
    }

    public void landingAction(Player p) {
        Board.bank.payMoney(p, 200);
    }
}
