package Monopoly;

class Property extends SquareType{

    public Property(String name, int[] rent, int position, int value, int setId, int totalInSet) {
        this.setName(name);
        this.setRent(rent);
        this.setTileType("Property");
        this.setPosition(position);
        this.setValue(value);
        this.setSetId(setId);
        this.setTotalInSet(totalInSet);
    }

    public void landingAction(Player p) {
        if (owner == Board.bank) {
            buyCard(p);
        } else if(owner != p) {
            int rent = getRent();
            p.payMoney(owner,rent);
        }
    }
    public void addHouse() {
        if (numHouses == 5) {
            System.out.println("cannot add house - already a hotel");
            return;
        }
        owner.payMoney(Board.bank,((setId+1)/2)*50);
        numHouses++;
        if (numHouses == 5)
            System.out.println("converted to Hotel @ " + name);
        else
            System.out.println("added a house @ " + name);
        Board.updateSquare(this);
        Board.printBoard();
    }

    public void removeHouse() {
        if (numHouses == 0) {
            System.out.println("No house to sell");
            return;
        }
        Board.bank.payMoney(owner,((setId+1)/2)*25);
        if (numHouses == 5)
            System.out.println("converted Hotel to 4 houses @ " + name);
        else
            System.out.println("removed a house @ " + name);
        numHouses--;
        Board.updateSquare(this);
        Board.printBoard();
    }
}
