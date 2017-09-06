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
    private int diamond_moves = 1000; //best number of moves for each pattern
    private int moves3 = 1000;
    private int moves4 = 1000;
    private int moves5 = 1000;
    private int moves6 = 1000;
    private int time4 = 3500;
    private int time5 = 3500;
    private int time6 = 3500;
    private int time7 = 3500;
    private int time8 = 3500;
    //private int current_time = 0;
    //private int match_time = 3600;

    public Player(int i, String n){
        id = i;
        name = n;
    }

    public void setScores(int three, int four, int five, int six, int d,int t4,int t5,int t6, int t7, int t8){
        diamond_moves = d;
        moves3 = three;
        moves4 = four;
        moves5 = five;
        moves6 = six;
        time4 = t4;
        time5 = t5;
        time6 = t6;
        time7 = t7;
        time8 = t8;
        //match_time = time;
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

//    public int getTime(){
//        return current_time;
//    }
//
//    public void setTime(int time){
//        current_time = time;
//    }

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
                moves4 = min(current_moves,moves5);
                break;
            case 6:
                moves5 = min(current_moves,moves6);
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


    public String get_Tfour(){return Integer.toString(time4); }

    public String get_Tfive(){
        return Integer.toString(time5);
    }

    public String get_Tsix(){
        return Integer.toString(time6);
    }

    public String get_Tseven(){
        return Integer.toString(time7);
    }

    public String get_Teight(){ return Integer.toString(time8); }

    //set the new best time
    public void wonMatch(int time){

        switch(Game.getType()){
            case 4:
                time4 = min(time,time4);
                break;
            case 5:
                time5 = min(time,time5);
                break;
            case 6:
                time6 = min(time,time6);
                break;
            case 7:
                time7 = min(time,time7);
                break;
            case 8:
                time8 = min(time,time8);
                break;
        }
    }

    //get best score for current game type
    public int getBestTime(){

        switch(Game.getType()){
            case 4: return time4;
            case 5: return time5;
            case 6: return time6;
            case 7: return time7;
            case 8: return time8;
            default: return 0;
        }
    }
}
