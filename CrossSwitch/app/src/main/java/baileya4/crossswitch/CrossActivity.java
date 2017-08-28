package baileya4.crossswitch;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Locale;

import static android.R.drawable.alert_light_frame;

/*
 * Homework 2, CSCI 412
 * Alexander Bailey
 *
 * CrossSwitch
 *
 * Contains a grid of blue buttons. Each button turns itself
 * and its neighbors from blue to green(or green to blue)
 * when tapped.
 */

public class CrossActivity extends AppCompatActivity {
    public String colorGrid[][];
    public Button lights[][];
    private DatabaseManager dbManager;
    public int side;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        side = Game.getSize();
        colorGrid = new String[side][side];
        lights = new Button[side][side];
        Player.current.reset_moves();
        dbManager = new DatabaseManager( this );

        if(Game.getType()<7) {
            setContentView(R.layout.activity_square);
            reset_colors();
            setup_grid();
        }else if(Game.getType() == 7){
            setContentView(R.layout.activity_dmnd);
            reset_colors();
            setup_grid();
        }

        showMoves();
    }

    //creates an array of the buttons in the game
    private void setup_grid(){
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        int w = 1;
        Configuration config = getResources( ).getConfiguration( );

        try {
            ColorButtonHandler bh = new ColorButtonHandler();
            TextView user = (TextView) findViewById(R.id.player);
            user.setText(Player.current.getName());

            BackHandler gb = new BackHandler();
            Button back = (Button) findViewById(R.id.back);
            back.setOnClickListener(gb);

            GridLayout grid = (GridLayout) findViewById(R.id.buttonGrid);
            if( config.orientation == Configuration.ORIENTATION_LANDSCAPE ) {
                w = (p.y / side)/2;
            }else if( config.orientation == Configuration.ORIENTATION_PORTRAIT ) {
                w = p.x / side;
            }

            grid.setColumnCount(side);
            grid.setRowCount(side);

            if(Game.getType()<7){
                setButtons_Square(bh,grid,w);
            }
            else {
                setButtons_Dmnd(bh, w);
            }

        }catch(NullPointerException e){
            showAlert(e);
        }
    }

    //configures buttons for a square layout
    private void setButtons_Square(ColorButtonHandler bh, GridLayout grid, int w){

        for(int row=0; row< side; row++){
            for(int col=0; col<side; col++) {
                String id = Integer.toString(row)+Integer.toString(col);
                lights[row][col] = new Button(this);
                lights[row][col].setOnClickListener(bh);
                lights[row][col].setId(View.generateViewId());
                lights[row][col].setBackground(getResources().getDrawable(alert_light_frame,null));
                lights[row][col].setBackgroundTintList(getResources().getColorStateList(R.color.holoblue,null));
                lights[row][col].setId(Integer.parseInt(id));
                grid.addView(lights[row][col],w,w);
            }
        }
    }

    //configures buttons for a diamond layout
    private void setButtons_Dmnd(ColorButtonHandler bh, int w){

        for(int row=0; row< side; row++){
            for(int col=0; col<side; col++) {
                String id = "button" + Integer.toString(row) + "_" + Integer.toString(col);
                View v = findViewById(getResources().getIdentifier(id,"id",getPackageName()));
                if(v instanceof Button) {
                    id = Integer.toString(row)+Integer.toString(col);
                    lights[row][col] = (Button) v;
                    ViewGroup.LayoutParams lp = v.getLayoutParams();
                    lp.width = w;
                    lp.height = w;
                    lights[row][col].requestLayout();
                    lights[row][col].setId(Integer.parseInt(id));
                    lights[row][col].setOnClickListener(bh);
                }else{
                    ViewGroup.LayoutParams lp = v.getLayoutParams();
                    lp.width = w;
                    lp.height = w;
                    v.requestLayout();
                    v.setVisibility(View.INVISIBLE);
                    colorGrid[row][col] = "empty";
                }
            }
        }
    }

    //initializes the array of color values to blue
    private void reset_colors(){
        for(int r=0;r<side;r++){
            for(int c=0;c<side;c++){
                colorGrid[r][c] = "blue";
            }
        }
    }

    //reset the game
    private void reset_game(){
        for(int r=0;r<side;r++){
            for(int c=0;c<side;c++){
                switchSpace(r,c);
            }
        }
        Player.current.reset_moves();
        enableButtons(true);
    }

    //switches the color of a space from blue to green, or from green to blue
    private void switchSpace(int r, int c){
        if(colorGrid[r][c].equals("blue")){
            lights[r][c].setBackgroundTintList(getResources().getColorStateList(R.color.hologreen,null));
            colorGrid[r][c] = "green";
        }else if(colorGrid[r][c].equals("green")){
            lights[r][c].setBackgroundTintList(getResources().getColorStateList(R.color.holoblue,null));
            colorGrid[r][c] = "blue";
        }

    }

    //switches the color of each neighbor(if the neighbor exists)
    private void setNeighbors(int r, int c){

        if(r > 0){
            if( !(colorGrid[r-1][c].equals("empty"))) {
                switchSpace(r - 1, c);
            }
        }
        if(c > 0){
            if( !(colorGrid[r][c-1].equals("empty"))) {
                switchSpace(r, c - 1);
            }
        }
        if(r < side-1){
            if( !(colorGrid[r+1][c].equals("empty"))) {
                switchSpace(r + 1, c);
            }
        }
        if(c < side-1){
            if( !(colorGrid[r][c+1].equals("empty"))) {
                switchSpace(r, c + 1);
            }
        }
    }

    //toggles the color of a space, and its neighbors
    public void toggle(View v){

        int id = v.getId();
        int row;
        int col;
        if(id<10){
            row = 0;
            col = id;
        }else {
            row = Character.getNumericValue(Integer.toString(id).charAt(0));
            col = Character.getNumericValue(Integer.toString(id).charAt(1));
        }

        Player.current.move();
        showMoves();
        switchSpace(row,col);
        setNeighbors(row,col);

        try {
            if (hasWon()) {
                enableButtons(false);
                TextView won = (TextView) findViewById(R.id.win);
                won.bringToFront();
                won.setVisibility(View.VISIBLE);
                won.setZ(1000);
                won.invalidate();
                showMsg("You have won!");
                Player.current.won();
                dbManager.updateScore(Player.current.getID(), Game.getType(), Player.current.getBest());
            }
        }catch(NullPointerException e){
            showAlert(e);
        }
    }

    //Displays the current moves taken, and the
    //least total moves used to complete the current puzzle
    public void showMoves(){

        TextView best = (TextView) findViewById(R.id.Best);
        TextView curr = (TextView) findViewById(R.id.Current);
        try {
            curr.setText(String.format(Locale.getDefault(), "%d", Player.current.getMoves()));
            best.setText(String.format(Locale.getDefault(), "%d", Player.current.getBest()));
        }catch(NullPointerException e){
            showAlert(e);
        }
    }

    //enable or disable the board, depending on boolean input
    public void enableButtons(boolean set){
        for(int r=0;r<side; r++){
            for( int c=0;c<side;c++){
                if(colorGrid[r][c].equals("blue")) {
                    lights[r][c].setEnabled(set);
                }
            }
        }
    }

    //checks if player has won
    public boolean hasWon() {
        for (int c = 0; c < side; c++) {
            if (Arrays.asList(colorGrid[c]).contains("blue")){
                return false;
            }
        }

        return true;
    }

    //return to menu
    public void goBack(View v){
        this.finishAfterTransition();
    }

    //display win message
    private void showMsg(String e){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        DoneHandler dh = new DoneHandler();
        dialog.setMessage(e);
        dialog.setButton(-2,"Reset", dh);
        dialog.setButton(-1,"Main Menu", dh);
        dialog.show();
    }

    //display an error
    private void showAlert(Exception e){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage(e.toString());
        dialog.create();
        dialog.show();
    }

    private class ColorButtonHandler implements View.OnClickListener {
        public void onClick(View v) {toggle(v);}
    }

    private class DoneHandler implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface d, int i) {
            if(i==-1){
                goBack(null);
            }else if(i==-2){
                reset_game();
                d.dismiss();
            }
        }
    }

    private class BackHandler implements View.OnClickListener {
        public void onClick(View v) {goBack(v);}
    }
}
