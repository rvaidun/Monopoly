package Monopoly;

import java.util.*;

@SuppressWarnings("unchecked")
public class Player {
    private String name;
    private Square square;
    private int money;
    private final List<Square>[] cardsOwned;
    private boolean inJail;
    private int getOutOfJailCard;
    private boolean curTurn;
    private int turnsinJail;


    public Player(Square square, int money, String n) {
        this.square = square;
        this.money = money;
        name = n;
        if (square != null) square.addPlayer(this);
        inJail = false;
        cardsOwned = new List[10];
        for (int i = 0; i < cardsOwned.length; i++) {
            cardsOwned[i] = new ArrayList<Square>();
        }
    }

    public int getTurnsinJail() {
        return turnsinJail;
    }

    public void setTurnsinJail(int turnsinJail) {
        this.turnsinJail = turnsinJail;
    }

    public boolean isCurTurn() {
        return curTurn;
    }

    public void setCurTurn(boolean curTurn) {
        this.curTurn = curTurn;
    }
    public String getName() {
        return name;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public int getMoney() {
        return money;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
        if (!inJail) turnsinJail = 0;
    }
    public boolean isInJail() {return inJail;}

    // player paying amount.
    public void payMoney(Player to, int amount) {
        while (money < amount) {
            System.out.println("not enough money");
            mortgage(true);
            if (getPropertyNames(false).equals("")) {
                Monopoly.GameOver(this);
            }
        }
        to.money += amount;
        money -= amount;
        System.out.println("Player " + name + " paid $" + amount + " to player " + to.getName());
        Board.updatePlayerInfo();
    }

    public boolean hasGetOutOfJailCard() {
        return (getOutOfJailCard > 0);
    }

    public void useGetOutOfJailCard() {
        getOutOfJailCard--;
    }

    public void addGetOutOfJailCard() {
        getOutOfJailCard++;
    }

    public void addProperty(Square sq) {
        int id = sq.getSetId();
        if (this != Board.bank) System.out.println("Player " + name + " bought " + sq.getName());
        if (id > 0) {
            cardsOwned[sq.getSetId()-1].add(sq);
            if (sq.getOwner() != null)
                sq.getOwner().removeProperty(sq);
            sq.setOwner(this);
        }
    }

    public void removeProperty(Square sq) {
        int id = sq.getSetId();
        if (id > 0) {
            cardsOwned[sq.getSetId() - 1].remove(sq);
        }
    }

    public String getPropertyNames(boolean mortgage) {
        String str = "";
        for (List<Square> squares : cardsOwned) {
            String setStr = "";
            if (!squares.isEmpty()) {
                setStr = "{";

                for (Square s : squares) {
                    if (s.isMortgaged() == mortgage) {
                        setStr += s.getIndex() + ",";
                    }
                }
                setStr = (setStr.length() > 1) ? setStr.substring(0, setStr.length() - 1) + "}" : "";
            }
            str += setStr;
        }
        if (str.length() > 0)
            str = ((mortgage) ? "Mortgaged: " : "Properties: ") + str;
        return str;
    }

    public int getCount(int id) {
        return cardsOwned[id-1].size();
    }

    public List<Square>[] getCardsOwned() {
        return cardsOwned;
    }

    public List<Square> getCardsOwned(int i) {
        return cardsOwned[i-1];
    }

    public ArrayList<Square> getBuildableProperties () {
        ArrayList<Square> props = new ArrayList<Square>();
        for (int i = 0; i < 8; i++) {
            List<Square> set = cardsOwned[i];
            if (set.size() > 0) {
//                Square sq = set.get(0);
                if (set.size() == set.get(0).getTotalInSet()) {
                    int minHouses = 100;
                    for (Square sq : set) {
                        if (sq.isMortgaged()) { // cannot build if any of the properties in the set is mortgaged.
                            minHouses = -1;
                            break;
                        }
                        int nh = sq.getNumHouses();
                        if ( nh < minHouses) minHouses = nh;

                    }
                    if ((minHouses >= 0) && (minHouses < 5)){
                        for (Square sq: set) {
                            if (sq.getNumHouses() == minHouses) props.add(sq);
                        }
                    }
                }
            }
        }
        return props;
    }

    public ArrayList<Square> getSellableHouses () {
        ArrayList<Square> props = new ArrayList<Square>();
        for(List<Square> set : cardsOwned) {
            int maxHouses = -1;
            for (Square sq : set) {
                int nh = sq.getNumHouses();
                if ( nh > maxHouses) maxHouses = nh;
            }
            for (Square sq: set) {
                int nh = sq.getNumHouses();
                if ((nh > 0) && (nh == maxHouses)) props.add(sq);
            }
        }
        return props;
    }

    public void buildProperty(ArrayList<Square> props) {
        while (true) {
            System.out.println("Pick a property to build");
            System.out.println("Enter 0 when done with building");
            for (int i = 0; i < props.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + props.get(i).getName());
            }
            int choice = Board.readInt();
            if (choice <= props.size() && choice > 0) {
                props.get(choice - 1).addHouse();
                props = getBuildableProperties();
                if (props.size() == 0) break;
            }
            else break;
        }
    }

    public void sellHouse() {
        while (true) {
            ArrayList<Square> props = getSellableHouses();
            if (props.size() == 0) break;
            System.out.println("Pick a house to sell");
            System.out.println("Enter 0 when done with selling");
            for (int i = 0; i < props.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + props.get(i).getName());
            }
            int choice = Board.readInt();
            if (choice <= props.size() && choice > 0) {
                props.get(choice - 1).removeHouse();
            }
            else break;
        }
    }

    public void mortgage(boolean mtg) {
        String properties = getPropertyNames(!mtg);
        if (!properties.equals("")) {
            System.out.println(properties);
            System.out.println("Enter property to mortgage");
            int cardChosen = Board.readInt();
            Square sq = Board.squares[cardChosen];
            if (this == sq.getOwner() && sq.isMortgaged() != mtg) {
                if (mtg) {
                    for (Square s: cardsOwned[sq.getSetId()-1]) {
                        if (s.getNumHouses() > 0) {
                            System.out.println(sq.getName() + " can't be mortgaged with house(s)");
                            sellHouse();
                            return;
                        }
                    }
                    int amt = sq.getValue()/2;
                    Board.bank.payMoney(this, amt);
                    System.out.println("Player " + name + " mortgaged " + sq.getName() + " and got $" + amt);
                }
                else {
                    double d = (sq.getValue() / 2) * 1.1;
                    int amt = (int) d;
                    this.payMoney(Board.bank, amt);
                    System.out.println("Player " + name + " unmortgaged " + sq.getName() + " by paying $" + amt);
                }
                sq.setMortgaged(mtg);
            }
        }
    }


}