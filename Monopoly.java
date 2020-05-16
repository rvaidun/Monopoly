package Monopoly;
public class Monopoly {
    final static Board board = new Board();
    final static Player[] players = {
            new Player(board.squares[0], 1500, "P1"),
            new Player(board.squares[0], 1500, "P2")
    };


    public static void GameOver(Player p) {
        System.out.println("GAME OVER: Player " + p.getName() + " is bankrupt");
        System.exit(0);
    }

    public static void main(String[] args) {
        Board.updateBoard();
        int turn = 0;
        int rollNum = 1;
        while(players[0].getMoney() >= 0 && players[1].getMoney() >= 0) {
            Board.updatePlayerInfo();
            Player p = players[turn];
            p.setCurTurn(false);

            Board.putString(20,10,"Player:" + p.getName() + "'s turn");
            Board.printBoard();
            p.getSquare().preRollChoices(p);
            // roll dice
            Board.rollDice(p, rollNum);
            int d1 = Board.getRv1();
            int d2 = Board.getRv2();
            if (d1 == d2)  {
                if (rollNum == 3){
                    Board.putString(18, 50, "Go to Jail");
                    p.setInJail(true);
                    Board.movePlayer(p, 10);
                    turn = (turn + 1) % players.length;
                    rollNum = 1;
                    continue;
                } else {
                    p.setCurTurn(true);
                    Board.putString(18, 50, "Roll again");
                    rollNum++;
                }
            }

            int destNum = p.getSquare().rollAction(p, d1,d2);// find. Destination

            Board.movePlayer(p, destNum);
            if (!p.isCurTurn()) {
                Board.putString(16, 50, "                ");
                turn = (turn + 1) % players.length;
                rollNum = 1;
            }
        }
    }
}
