package Monopoly;

public class Jail extends SquareType{
    public Jail(int position) {
        this.setName("Jail");
        this.setTileType("Jail");
        this.setPosition(position);
    }

    public int rollAction(Player p, int d1, int d2){
        if (p.isInJail()) {
            int turns = p.getTurnsinJail();
            if (d1 == d2 || turns == 3) {
                if (d1 != d2) p.payMoney(Board.bank, 50);
                p.setInJail(false);
                p.setCurTurn(false);
                return super.rollAction(p, d1, d2);
            }
            else{
                p.setTurnsinJail(turns+1);
                return p.getSquare().getIndex();
            }
        }
        return super.rollAction(p, d1, d2);
    }

    //    You get out of Jail by… (1) throwing doubles on any of your next
//    three turns; if you succeed in doing this you immediately move
//    forward the number of spaces shown by your doubles throw; even
//    though you had thrown doubles, you do not take another turn;
//(2) using the “Get Out of Jail Free” card if you have it; (3) purchasing
//    the “Get Out of Jail Free” card from another player and playing it;
//(4) paying a fine of $50 before you roll the dice on either of your next
//    two turns.
//    If you do not throw doubles by your third turn, you must pay the
//    $50 fine. You then get out of Jail and immediately move forward the
//    number of spaces shown by your throw
    public void preRollChoices(Player p) {
        String str;
        super.preRollChoices(p);
        if (!p.isInJail()) {
            return;
        }
        String validChoices = "RP";
        System.out.println("Turns in Jail = " + p.getTurnsinJail());
        System.out.println("<R>oll and hope for double");
        System.out.println("<P>ay $50");
        if (p.hasGetOutOfJailCard()) {
            System.out.println("<G>et out jail card");
            validChoices += "G";
        }
        while (true) {
            str = Board.readLine().toUpperCase();
            if (str.isEmpty()|| (str.length() == 1 && validChoices.contains(str))) break;
            System.out.println("Invalid input: " + str);
        }
        switch (str) {
            case "R":
                break;
            case "P":
                p.payMoney(Board.bank,50);
                p.setInJail(false);
                break;
            case "G":
                if (p.hasGetOutOfJailCard()) {
                    p.useGetOutOfJailCard();
                    p.setInJail(false);
                } else
                    System.out.println("No get out of jail card.");
        }
    }

    public void landingAction(Player p) {
    }
}
