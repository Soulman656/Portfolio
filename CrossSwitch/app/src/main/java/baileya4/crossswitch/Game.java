package baileya4.crossswitch;

/*
 * Homework 2, CSCI 412
 * Alexander Bailey
 *
 * CrossSwitch
 *
 * Game class
 * contains the game type and size of the board
 */

public class Game {
    private static String style;
    private static int size;

    public static void setStyle(String s){
        style = s;
    }

    public static int getType(){
        if(style.equals("diamond")){
            return 7;
        }else {
            return size;
        }
    }

    public static int getSize(){
        return size;
    }

    public static void newGame(String s,int z){
        style = s;
        size = z;
    }
}
