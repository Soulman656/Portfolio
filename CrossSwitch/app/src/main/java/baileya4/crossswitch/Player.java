package baileya4.crossswitch;


import static java.lang.Math.min;

/*
 * Homework 2, CSCI 412
 * Alexander Bailey
 *
 * CrossSwitch
 *
 * Player class
 * contains the player's name and scores
 */

public class Player {
    public static Player current = new Player(1000, "Guest");
    private int id;
    private String name;             //player name
    private int current_moves = 0;   //number of moves taken in current game
    private int diamond_moves = 100; //best number of moves for each pattern
    private int moves3 = 100;
    private int moves4 = 100;
    private int moves5 = 100;
    private int moves6 = 100;
    private long match_time = 60000;

    public Player(int i, String n){
        id = i;
        name = n;
    }

    public void setScores(int three, int four, int five, int six, int d,long time){
        diamond_moves = d;
        moves3 = three;
        moves4 = four;
        moves5 = five;
        moves6 = six;
        match_time = time;
    }

    public void setName(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

    public void setID(int i){ id = i; }

    public int getID(){
        return id;
    }

    public void move(){
        current_moves++;
    }

    public int getMoves(){
        return current_moves;
    }

    public long getTime(){
        return match_time;
    }

    public void setTime(long time){
        match_time = time;
    }

    public void reset_moves(){
        current_moves = 0;
    }

    public String get_diamond(){
        return Integer.toString(diamond_moves);
    }

    public String get_three(){
        return Integer.toString(moves3);
    }

    public String get_four(){
        return Integer.toString(moves4);
    }

    public String get_five(){
        return Integer.toString(moves5);
    }

    public String get_six(){
        return Integer.toString(moves6);
    }

    //set the new best moves
    public void won(){

        switch(Game.getType()){
            case 3:
                moves3 = min(current_moves,moves3);
                break;
            case 4:
                moves4 = min(current_moves,moves4);
                break;
            case 5:
                moves5 = min(current_moves,moves5);
                break;
            case 6:
                moves6 = min(current_moves,moves6);
                break;
            case 7:
                diamond_moves = min(current_moves,diamond_moves);
                break;
        }
    }

    //get best score for current game type
    public int getBest(){

        switch(Game.getType()){
            case 3: return moves3;
            case 4: return moves4;
            case 5: return moves5;
            case 6: return moves6;
            case 7: return diamond_moves;
            default: return 0;
        }
    }
}
