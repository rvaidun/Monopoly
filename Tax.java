package Monopoly;

public class Tax extends SquareType{
    public Tax(String name,int value,int position) {
        this.setName(name);
        this.setTileType("Tax");
        this.setPosition(position);
        this.setValue(value);

    }

    public void landingAction(Player p) {
        p.payMoney(Board.bank,this.getValue());
    }
}
