package Monopoly;

public class Utilities extends SquareType{
    public Utilities(String name, int position, int value, int setId) {
        this.setName(name);
        this.setTileType("Utility");
        this.setPosition(position);
        this.setValue(value);
        this.setSetId(setId);
    }

    public void landingAction(Player p) {
        int rv = Board.getRv1() + Board.getRv2();
        Player o = this.getOwner();
        if (o == Board.bank) {
            buyCard(p);
        } else if(o != p) {
            int amt = o.getCount(this.getSetId()) == 2 ? rv*10 : rv*4;
            p.payMoney(o,amt);
        }
    }
}
