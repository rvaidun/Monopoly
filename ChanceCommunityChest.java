package Monopoly;

public class ChanceCommunityChest extends SquareType{
    static int cardCount = 0;

    Player player;
    public ChanceCommunityChest(String name,int position) {
        this.setName(name);
        this.setTileType("Card");
        this.setPosition(position);


//            Random rand = new Random();
//            for(int i = 0; i < cardActions.length; i++){
//                int r1 = rand.nextInt(cardActions.length);
//                int r2 = rand.nextInt(cardActions.length);
//                Action temp = cardActions[r1];
//                cardActions[r1] = cardActions[r2];
//                cardActions[r2] = temp;
//            }

    }
    interface Action {
        void action();
    }
    private Action[] cardActions = new Action[] {
            new Action() { public void action() { advanceToGo(); } },
            new Action() { public void action() { back3Spaces(); } },
            new Action() { public void action() {
                System.out.println("Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200."); goToJail(); } },
            new Action() { public void action() { getOutOfJail(); } },
            new Action() { public void action() { boardwalk(); } },
            new Action() { public void action() { chairman(); } },
            new Action() { public void action() { bank50(); } }
    };

    public void advanceToGo() {
        System.out.println("Advanced to go. Collected $200");
        Monopoly.board.movePlayer(player, 0);
    }

    public void back3Spaces() {
        System.out.println("Move back 3 spaces");

        int fp = player.getSquare().getIndex() - 3;
        if (fp < 0) {
            fp += 40;
        }
        Monopoly.board.movePlayer(player,fp);
    }

    public void boardwalk() {
        System.out.println("Take a walk on the boardwalk");
        Monopoly.board.movePlayer(player,39);
    }

    public void bank50() {
        System.out.println("Bank pays you dividend of $50.");
        Board.bank.payMoney(player,50);
    }

    public void chairman() {
        System.out.println("You have been elected Chairman of the Board. Pay each player $50.");
        for(Player p: Monopoly.players) {
            if (p == player) continue;
            player.payMoney(p,50);
        }
    }

    public void getOutOfJail() {
        System.out.println("Get of of jail");
        player.addGetOutOfJailCard();
    }

    public void goToJail() {
        player.setInJail(true);
        Monopoly.board.movePlayer(player, 10);
        player.setCurTurn(false);
    }

    public void landingAction(Player p) {
        player = p;
        if (cardCount == cardActions.length) {
            cardCount = 0;
        }
        for(Action a: cardActions)
            a.action();
//        cardActions[cardCount++].action();
    }
}
