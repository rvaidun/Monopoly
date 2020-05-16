package Monopoly;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class Board {
    private static final char[][] board = new char[33][77];
    private static final int[] medit = {2, 10, 30, 90, 160, 250};
    private static final int [] baltic = {4,20,60,320,450};
    private static final int [] oriental ={6,30,90,270,400,550};
    private static final int [] vermont = {6,30,90,270,400,550};
    private static final int [] connecticut = {8,40,100,300,450,600};
    private static final int [] stcharles = {10,50,150,450,625,750};
    private static final int [] virginina = {12,60,180,500,700,900};
    private static final int [] stjames = {14,70,200,550,750,950};
    private static final int [] ny = {16,80,220,600,800,1000};
    private static final int [] kentucky = {18,90,250,700,875,1050};
    private static final int [] illinois = {20,100,300,750,925,1100};
    private static final int [] atlantic = {22,110,330,800,975,1150};
    private static final int [] marvin = {24,120,360,850,1025,1200};
    private static final int [] pacific = {26,130,390,900,1100,1275};
    private static final int [] parkplace = {35,175,500,1100,1300};
    private static final int [] boardwalk = {50,200,600,1400,1700,2000};
    private static final int [] penn = {28,150,450,1000,1200,1400};
    private final static Scanner pen = new Scanner(System.in);
    private static Scanner input;
    private static Scanner input2;
    private static PrintWriter writer;
    private static int rv1;
    private static int rv2;
    final static Player bank = new Player(null, 100000, "Bank");
    final static Square[] squares = {
            new Go(0),//0
            new Property("Mediterranean Ave", medit, 7, 60, 1, 2),//1
            new ChanceCommunityChest("Community Chest", 14),//2
            new Property("Baltic Ave", baltic, 21, 60, 1, 2),//3
            new Tax("Income Tax", 200, 28),//4
            new Railroad("Reading", 200, 35, 9),//5
            new Property("Oriental Ave", oriental, 42, 100, 2, 3),//6
            new ChanceCommunityChest("Chance", 49),//7
            new Property("Vermont Ave", vermont, 56, 100, 2, 3),//8
            new Property("Connecticut Avenue", connecticut, 63, 120, 2, 3),//9
            new Jail(70),//10
            new Property("St. Charles Place", stcharles, 370, 140, 3, 3),
            new Utilities("Electric Company", 670, 150, 10),
            new Property("States", stcharles, 970, 140, 3, 3),
            new Property("Virginia", virginina, 1270, 160, 3, 3),
            new Railroad("Pennsylvania", 200, 1570, 9),
            new Property("St. James", stjames, 1870, 180, 4, 3),
            new ChanceCommunityChest("Community Chest", 2170),
            new Property("Tennessee Ave", stjames, 2470, 180, 4, 3),
            new Property("New York Ave", ny, 2770, 200, 4, 3),
            new FreeParking(3070),
            new Property("Kentucky Ave", kentucky, 3063, 220, 5, 3),
            new ChanceCommunityChest("Chance", 3056),
            new Property("Indiana Ave", kentucky, 3049, 220, 5, 3),
            new Property("Illinois Ave", illinois, 3042, 240, 5, 3),
            new Railroad("B & O", 200, 3035, 9),
            new Property("Atlantic Ave", atlantic, 3028, 260, 6, 3),
            new Property("Ventor Ave", atlantic, 3021, 260, 6, 3),
            new Utilities("WaterWorks", 3014, 150, 10),
            new Property("Marvin Gardens", marvin, 3007, 280, 6, 3),
            new GoToJail(3000),
            new Property("Pacific Ave", pacific, 2700, 300, 7, 3),
            new Property("North Carolina Ave", pacific, 2400, 300, 7, 3),
            new ChanceCommunityChest("Community Chest", 2100),
            new Property("Pennsylvania Ave", penn, 1800, 320, 7, 3),
            new Railroad("Short line", 200, 1500, 9),
            new ChanceCommunityChest("Chance", 1200),
            new Property("Park Place", parkplace, 900, 350, 8, 2),
            new Tax("Luxury Tax", 100, 600),
            new Property("Boardwalk", boardwalk, 300, 400, 8, 2)
    };

    public Board() {

        for (int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }

        for (int i = 0; i < squares.length; i++) {
            Square s = squares[i];
            s.setIndex(i);
            if (s.getSetId() != 0)
                bank.addProperty(s);
        }

        try {
            // Anand C:\Users\Anand\Desktop\MonopolyInput.txt
            // Rahul
            File file = new File("/Users/rahul/JavaPrograms/APCS/src/Monopoly/MonopolyInput");
            input = new Scanner(file);
            writer = new PrintWriter("/Users/rahul/JavaPrograms/APCS/src/Monopoly/MonopolyRecording", "UTF-8");
            File file2 = new File("/Users/rahul/JavaPrograms/APCS/src/Monopoly/MonopolyInput2");
            input2 = new Scanner(file2);

        } catch(IOException e) {
            // do something
            e.printStackTrace();
        }
    }

    private static int getDiceValue() {
        if (input != null) {
            while (input.hasNext()) {
                if (input.hasNextInt()) {
                    return (input.nextInt());
                } else {
                    input.next();
                }
            }
        }
        return (int) (Math.random() * ((6 - 1) + 1)) + 1;
    }

    public static int readInt() {
        if (input2 != null) {
            if (input2.hasNext()) {
                return (input2.nextInt());
            }
        }
        int i = pen.nextInt();
        pen.nextLine();
        writer.println(i);
        return i;
    }

    public static String readLine() {
        if (input2 != null) {
            if (input2.hasNext()) {
                return (input2.nextLine());
            }
        }
        String str = pen.nextLine();
        if (str.equals("exit")) {
            writer.close();
            return "";
        }
        writer.println(str);
        return str;
    }

    public static void rollDice(Player p, int rollNum) {
        rv1 = getDiceValue();
        rv2 = getDiceValue();
        Board.updateRoll(p, rollNum);
    }

    public static int getRv1() {
        return rv1;
    }


    public static int getRv2() {
        return rv2;
    }

    private static int min(int a, int b) {
        if (a > b){
            return b;
        }
        return a;
    }

    public static void printBoard() {
        for (char[] chars : board) {
            System.out.println(new String(chars));
        }
    }

    public static void updateBoard() {
        for (Square square : squares) {
            int pos = square.getPosition();
            int row = pos / 100;
            int col = pos % 100;
            String name = square.getName();
            name = name.substring(0, min(name.length(), 7));
            for (int j = 0; j < name.length(); j++) {
                board[row][col + j] = name.charAt(j);
            }
            board[row][col + 6] = '|';
            updateSquare(square);
        }
    }

    public static void updatePlayerInfo() {
        putString(4, 9, bank.getName()+ " $" + bank.getMoney());
        putString(5,9,bank.getPropertyNames(false));
        for (int i = 0; i < Monopoly.players.length; i++) {
            Player p = Monopoly.players[i];
            int r = 8 + 5*i;
            putString(r, 9, p.getName()+ " $" + p.getMoney());
            putString(r+1,9,p.getPropertyNames(false));
            putString(r+2,9,p.getPropertyNames(true));
        }
    }

    public static void updateSquare(Square s) {
        int pos = s.getPosition();
        int row = pos/100;
        int col = pos%100;
        ArrayList<Player> players = s.getPlayers();
        String q = "";
        for (Player player: players) {
            q = q + player.getName() + " ";
        }
        int scount = q.length();
        int numHouse = s.getNumHouses();
        int v = s.getValue();
        String val = (numHouse > 0) ? (numHouse == 5) ? "  H" : "  " + Integer.toString(numHouse) + "h" : (v != 0) ? Integer.toString(s.getValue()) : "";
        for(int j = 0; j < val.length(); j++){
            board[row+1][col+j] = val.charAt(j);
        }
        board[row+1][col+6] = '|';
        for(int j = 0; j < 7; j++){
            if(scount > 0){
                board[row+2][col+j] = q.charAt(j);
                scount--;
            } else{
                board[row+2][col+j] = '-';
            }

        }
    }

    public static void updateRoll(Player p, int rN){
        int r = 18;
        putString(r,10,"Roll number " + rN + " Player: " + p.getName());
        board[r][40] = Integer.toString(rv1).charAt(0);
        board[r-1][40] = '-';
        board[r+1][40] = '-';
        board[r][39] = '|';
        board[r][41] = '|';
        board[r][42] = Integer.toString(rv2).charAt(0);
        board[r-1][42] = '-';
        board[r+1][42] = '-';
        board[r][43] = '|';
    }

    public static void putString(int row, int col, String str) {
        int c = 0;
        for(int j = 0; j < str.length(); j++,c++){
            board[row][col+c] = str.charAt(j);
            if(c > 68-col) {
                row +=1;
                c=-1;
            }
        }
        for(int i = col + c; i<68;i++) {
            board[row][i] = ' ';
        }
    }

    public static void movePlayer(Player p, int finalPos) {
        int source = p.getSquare().getIndex();
        finalPos %= 40;
        squares[source].removePlayer(p);
        p.setSquare(squares[finalPos]);
        squares[finalPos].addPlayer(p);
        updateSquare(squares[source]);
        updateSquare(squares[finalPos]);
        printBoard();
        p.getSquare().landingAction(p);
    }
}
