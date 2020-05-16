package Monopoly;

import java.util.ArrayList;

public interface Square {
    String getName();

    Player getOwner();
    void setMortgaged(boolean mortgaged);
    boolean isMortgaged();
    void setOwner(Player p);
    int getPosition();
    int getIndex();
    void setIndex(int i);
    int getValue();
    int getSetId();
    int getTotalInSet();
    ArrayList<Player> getPlayers();
    void addPlayer(Player p);
    void removePlayer(Player p);
    void preRollChoices(Player p);
    int rollAction(Player p, int d1, int d2);
    void landingAction(Player p);
    void addHouse();
    void removeHouse();
    int getNumHouses();

    //public void setOwner(Player player);
}
