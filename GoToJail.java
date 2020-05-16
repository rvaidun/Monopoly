package Monopoly;

public class GoToJail extends SquareType{
    public GoToJail(int position) {
        this.setName("Go To Jail");
        this.setTileType("Go To Jail");
        this.setPosition(position);
    }
    public void landingAction(Player p) {
        p.setInJail(true);
        Monopoly.board.movePlayer(p, 10);
        p.setCurTurn(false);
        //System.out.println("Player " + p.getName() + "landed in Jail");
    }

}
