package baileya4.crossswitch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.R.color.black;
import static android.R.color.holo_blue_bright;
import static android.R.drawable.alert_dark_frame;
import static android.R.drawable.alert_light_frame;

/*
 * Final Project, CSCI 412
 * Alexander Bailey
 *
 * CrossCalc
 *
 * Contains a grid of empty spaces, with hints on the side and top.
 * The hints indicate how many squares of each color are in each row
 * or column, and in what order.
 */

public class MatchActivity extends AppCompatActivity {
    private int side = Game.getSize()+1;
    private Button[][] cells;
    private Hint[] hintsV;
    private Hint[] hintsH;
    private String[][] solution;
    private boolean[][] correct;
    private ArrayList<String> colorList;
    private int w = 1;
    private int shades = 4;
    private ColorButtonHandler bh;
    private CountDownTimer timer;
    private long timeleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        cells = new Button[side][side];
        hintsV = new Hint[side];
        hintsH = new Hint[side];
        solution = new String[side][side];
        correct = new boolean[side][side];

        colorList = new ArrayList<>();
        //colorList.add("blue");
        colorList.add("green");
        colorList.add("orange");
        colorList.add("purple");

        reset_sol();

        setup_grid();
        startTimer();
    }

    //times the current game
    private void startTimer(){
        final TextView _tv = (TextView) findViewById( R.id.timer );
        timer = new CountDownTimer(20*60000, 1000) {

            public void onTick(long millisUntilFinished) {
                _tv.setText(new SimpleDateFormat("mm:ss",Locale.getDefault()).format(new Date( millisUntilFinished)));
                timeleft = millisUntilFinished;
            }

            public void onFinish() {
                _tv.setText("Time up!");
            }
        }.start();
    }

    //creates the board
    private void setup_grid(){
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);

        try {
            bh = new ColorButtonHandler();
            TextView user = (TextView) findViewById(R.id.player);
            String msg = "Player: " + Player.current.getName();
            user.setText(msg);

            BackHandler gb = new BackHandler();
            Button back = (Button) findViewById(R.id.back);
            back.setOnClickListener(gb);

            GridLayout grid = (GridLayout) findViewById(R.id.puzzle_grid);
            w = p.x / side;
            float h = w-1;

            grid.setColumnCount(side);
            grid.setRowCount(15);

            setButtons_Square(bh,grid,w);


        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    //set the grid buttons to a random pattern for a new game, and set hints accordingly.
    private void setButtons_Square(MatchActivity.ColorButtonHandler bh, GridLayout grid, int w){

        Button tv = new Button(this);
        String corner = "NM";
        tv.setText(String.format(Locale.getDefault(),"%s", corner));
        tv.setTextSize(36);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setBackground(getResources().getDrawable(alert_light_frame,null));
        tv.setTextColor(getResources().getColorStateList(R.color.black,null));
        tv.setBackgroundTintList(getResources().getColorStateList(R.color.black,null));
        grid.addView(tv,w,w);

        for(int c=1; c< side; c++) {
            hintsH[c] = new Hint();
            setHint(hintsH[c],c);
        }

        for(int row=1; row< side; row++){
            hintsV[row] = new Hint();
            setHint(hintsV[row],row);

            for(int col=1; col<side; col++) {
                String id = Integer.toString(row)+Integer.toString(col);
                cells[row][col] = new Button(this);
                cells[row][col].setOnClickListener(bh);
                cells[row][col].setId(View.generateViewId());
                getSquare(row,col);
                cells[row][col].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                cells[row][col].setBackground(getResources().getDrawable(alert_light_frame,null));
                cells[row][col].setBackgroundTintList(getResources().getColorStateList(R.color.holoblue,null));
                cells[row][col].setId(Integer.parseInt(id));
            }
        }

        parseHints();

        for(int c=1; c< side; c++) {
            hintsH[c].view.setText(Html.fromHtml(hintsH[c].getHint()),Button.BufferType.SPANNABLE);
            grid.addView(hintsH[c].view,w,w);
        }

        for(int row=1; row< side; row++){
            hintsV[row].view.setText(Html.fromHtml(hintsV[row].getHint()),Button.BufferType.SPANNABLE);
            grid.addView(hintsV[row].view,w,w);

            for(int col=1; col<side; col++) {
                grid.addView(cells[row][col],w,w);
            }
        }

    }

    //set up a hint
    private void setHint(Hint h, int n){
        h.view = new Button(this);
        h.view.setBackground(getResources().getDrawable(alert_dark_frame,null));
        h.view.setBackgroundTintList(getResources().getColorStateList(R.color.black,null));
        h.view.setAllCaps(false);
        h.view.setTextSize(20);
        h.view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        h.view.setId(n+100);
    }

    //determine the target color for the current square
    private int getSquare(int r, int c) {
        int draw = R.drawable.blank;
        int num = (genRandom(shades, 1));
        cells[r][c].setTag("blue");
        if (row_full(r) || col_full(c)) {
            solution[r][c] = "blue";
        }else {
            switch (num) {
                case 1:
                    solution[r][c] = "blue";
                    break;
                case 2:
                    solution[r][c] = "green";
                    break;
                case 3:
                    solution[r][c] = "orange";
                    break;
                case 4:
                    solution[r][c] = "purple";
                    break;
            }
        }

        return draw;
    }

    private boolean row_full(int r){
        int total = 0;
        for(int c=1; c<side; c++){
            if(solution[r][c] != null) {
                if (!solution[r][c].equals("blue")) {
                    total++;
                }
            }
        }
        return (total >= side-2);
    }

    private boolean col_full(int c){
        int total = 0;
        for(int r=1; r<side; r++){
            if(solution[r][c] != null) {
                if (!solution[r][c].equals("blue")) {
                    total++;
                }
            }
        }
        return (total >= side-2);
    }

    //traverse the solution grid to determine the hints for each row and column
    private void parseHints(){
        for(int row=1; row< side; row++) {
            for (int col = 1; col < side; col++) {
                for(String c : colorList) {
                    if (col > 1) {
                        if (solution[row][col - 1].equals(c) && !solution[row][col].equals(c)
                                && hintsV[row].getRecent(c)!=0) {
                            hintsV[row].addHint(c, this);
                            hintsV[row].addColor(c);
                        }
                    }
                    if (row > 1) {
                        if (solution[row - 1][col].equals(c) && !solution[row][col].equals(c)
                                && hintsH[col].getRecent(c)!=0) {
                            hintsH[col].addHint(c, this);
                            hintsH[col].addColor(c);
                        }
                    }
                    if (solution[row][col].equals(c)) {
                        hintsV[row].bumpColor(c);
                        hintsH[col].bumpColor(c);
                    }
                }
                for(String c : colorList) {
                    if (col == side - 1 && hintsV[row].getRecent(c)!=0) {
                        hintsV[row].addHint(c, this);
                    }
                    if (row == side - 1 && hintsH[col].getRecent(c)!=0) {
                        hintsH[col].addHint(c, this);
                    }
                }
            }
        }
    }

    private void checkSquare(Button b){
        int id = b.getId();
        int row;
        int col;
        if(id<10){
            col = 0;
            row = id;
        }else {
            col = Character.getNumericValue(Integer.toString(id).charAt(0));
            row = Character.getNumericValue(Integer.toString(id).charAt(1));
        }

        if(isSolved()){
                showMsg("You have won! Play again?");
                return;
        }

        TextView sol = (TextView) findViewById(R.id.sol_grid);
        sol.setText(printSol());
    }

    //print the solution grid -- for debugging
    public String printSol(){
        String grid = new String();
        for(int r=0;r<side;r++){
            for(int c=0;c<side;c++){
                if(solution[r][c] == null){
                    grid += "X|";
                }else {
                    if (solution[r][c].equals("blue")) {
                        grid += "B|";
                    } else if (solution[r][c].equals("green")) {
                        grid += "G|";
                    } else if (solution[r][c].equals("orange")){
                        grid += "O|";
                    } else{
                        grid += "P|";
                    }
                }
            }
            grid += "\n";
        }

        return grid;
    }

    //determine the next color the button should change to.
    public int findColor(Button b){
        String tag = b.getTag().toString();
        switch (tag) {
            case "blue":
                b.setTag("green");
                return R.color.hologreen;
            case "green":
                b.setTag("orange");
                return R.color.holoorange;
            case "orange":
                b.setTag("purple");
                return R.color.holopurple;
            case "purple":
                b.setTag("blue");
                return R.color.holoblue;
        }

        return R.color.black;
    }

    public void lose_game(){
        for(int row = 1; row<side;row++){
            for(int col = 1; col<side;col++){
                cells[row][col].setBackgroundTintList(getResources().getColorStateList(R.color.holoblue,null));
            }
        }

        showMsg("You have lost! Play Again?");
    }

    //resets the solution matrix
    private void reset_sol(){
        for(int i=0; i< side; i++) {
            correct[0][i] = true;
            correct[i][0] = true;
        }

        for(int r=1; r< side; r++){
            for(int c=1; c< side; c++){
                correct[r][c] = false;
            }
        }
    }

    //resets all game parameters
    private void reset_game(){
        reset_sol();
        GridLayout grid = (GridLayout) findViewById(R.id.puzzle_grid);
        try {
            grid.removeAllViews();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        setButtons_Square(bh,grid,w);
        startTimer();
    }

    //generate a random number
    private int genRandom(int max, int min){
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    //checks if game is solved
    private boolean isSolved(){
        for(int col=1;col<side;col++){
            for(int row=1;row<side;row++){
                if(!solution[row][col].equals(cells[row][col].getTag().toString())){
                    return false;
                }
            }
        }

        return true;
    }

    //actions for each button in the puzzle grid
    private class ColorButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button selected = (Button) v;
            selected.setBackgroundTintList(getResources().getColorStateList(findColor(selected),null));

            if(isSolved()){
                showMsg("You have won! Play again?");
                return;
            }
            //checkSquare(selected);
        }
    }

    //display a pop-up
    private void showMsg(String e){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        DoneHandler dh = new DoneHandler();
        dialog.setMessage(e);
        dialog.setButton(-2,"New Game", dh);
        dialog.setButton(-1,"Main Menu", dh);
        dialog.show();
    }

    //return to previous menu
    private void goBack(){this.finishAfterTransition();}

    private class BackHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            goBack();
        }
    }

    //actions for winning the pop-up
    private class DoneHandler implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface d, int i) {
            if(i==-1){
                goBack();
            }else if(i==-2){
                reset_game();
                d.dismiss();
            }
        }
    }
}
