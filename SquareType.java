package Monopoly;

import java.util.ArrayList;
import java.util.List;

public abstract class SquareType implements Square{
    //Can be made by person creating one class for properties, jail block, etc...

    protected String name; //Name of the tile
    private int[] rent;
    private String tileType; //eg. Utility / Taxes / Land
    protected Player owner; //To determine who owns it. 0 for neutral, 1 for player 1... etc.
    private int position; // position in the char 2D array
    private int index;
    private int value;
    protected int setId; // PropertySet id of the property.
    private int totalInSet;
    private final ArrayList<Player> arr = new ArrayList<Player>();
    protected int numHouses;
    private boolean mortgaged = false;

//Standard setters and getters

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public String getName(){
    return name;
}
    void setName(String name){this.name=name;}
    public void setRent(int[] arr) {
        rent = arr;
    }
    protected int getRent(){
        if (owner.getCount(setId) == totalInSet && numHouses == 0)
            return rent[numHouses]*2;
        return rent[numHouses];
    }

    void setTileType(String tileType){this.tileType=tileType;}

    public Player getOwner(){return owner;}
    public void setOwner(Player owner){this.owner=owner;}

    public int getPosition() {
        return position;
    }
    void setPosition(int position) {
        this.position = position;
    }

    public int getIndex() { return index; }
    public void setIndex(int index) {this.index = index;}

    public int getValue(){
        return this.value;
    }
    void setValue(int value) {
        this.value = value;
    }

    public int getSetId() {return setId;}
    void setSetId(int setId) {this.setId = setId;}

    public int getTotalInSet() {return totalInSet;}
    void setTotalInSet(int totalInSet) {this.totalInSet = totalInSet;}

    public void addPlayer(Player p) {arr.add(p);}
    public void removePlayer(Player p) {arr.remove(p);}

    public ArrayList<Player> getPlayers() {
        return this.arr;
    }

    public int getNumHouses() {
        return numHouses;
    }

        //TODO trade properties
    public void preRollChoices(Player p) {
        String str;
        while(true){
            ArrayList<Square> props = p.getBuildableProperties();
            String validChoices = "R";
            System.out.println("<R>oll or default 'Enter'");
            if(props.size() > 0) {
                System.out.println("<B>uild house");
                validChoices += "B";
            }
            if (!p.getPropertyNames(true).equals("")) {
                System.out.println("<U>nmortgage");
                validChoices += "U";
            }
            while (true) {
                str = Board.readLine().toUpperCase();
                if (str.isEmpty()|| (str.length() == 1 && validChoices.contains(str))) break;
                System.out.println("Invalid input: " + str);
            }
            switch (str) {
                case "B":
                    p.buildProperty(props);
                    break;
                case "U":
                    p.mortgage(false);
                    break;
                case "R":
                default:
                    return;
            }
        }
    }

    public int rollAction(Player p, int d1, int d2){
        int rollValue = d1 +d2;
        int dest = p.getSquare().getIndex() + rollValue;
        if (dest > 40) {
            Board.bank.payMoney(p, 200);
        }
        dest %=40;
        return dest;
    }

    void buyCard(Player p) {
        Player o = this.getOwner();
        if (o == Board.bank) {
            System.out.println("Buy? <y/n>" + p.getSquare().getName());
            String str = Board.readLine().toLowerCase();
            if (str.equals("y") || str.equals("")) {
                p.payMoney(Board.bank, this.getValue());
                p.addProperty(this);
            }
        }
    }

    public void landingAction(Player p) {
    }
    public void addHouse() {}
    public void removeHouse() {}


}