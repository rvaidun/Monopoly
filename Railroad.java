package Monopoly;

public class Railroad extends SquareType implements Square{
    public Railroad(String name, int value, int position, int setId) {
        this.setName(name);
        this.setTileType("Railroad");
        this.setValue(value);
        this.setPosition(position);
        this.setSetId(setId);
    }
    public void landingAction(Player p) {
        Player o = this.getOwner();
        if (o == Board.bank) {
           buyCard(p);
        } else if(o != p) {
            int amtOfRailroads = o.getCount(this.getSetId());
            int cost = 25;
            for(int i = 0; i < amtOfRailroads-1; i++)
                cost *= 2;
            p.payMoney(o,cost);
        }
    }
}
