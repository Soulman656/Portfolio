/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package baileya4.crossswitch;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static baileya4.crossswitch.R.drawable.cs_icon_small;
import static baileya4.crossswitch.R.string.help_cs;

/*
 * Final Project, CSCI 412
 * Alexander Bailey
 *
 * CrossSwitch
 *
 * Contains a grid of blue buttons. Each button turns itself
 * and its neighbors from blue to green(or green to blue)
 * when tapped.
 */

public class MineMenuActivity extends AppCompatActivity {

    public final static String MA = "MenuActivity";
    private Button[] buttons = new Button[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = getResources().getConfiguration();
        setContentView(R.layout.activity_mine_menu);

        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        toolbar.setLogo(getResources().getDrawable(cs_icon_small,null));
        setSupportActionBar( toolbar );
        setButtons();
        updateView();
    }

    //set buttons behaviors
    public void setButtons(){

        SquareHandler sq = new SquareHandler();
        DmndHandler d = new DmndHandler();
        BackHandler bh = new BackHandler();

        buttons[0] = (Button) findViewById(R.id.btnSquare4);
        buttons[0].setOnClickListener(sq);

        buttons[1] = (Button) findViewById(R.id.btnSquare5);
        buttons[1].setOnClickListener(sq);

        buttons[2] = (Button) findViewById(R.id.btnSquare6);
        buttons[2].setOnClickListener(sq);

        buttons[3] = (Button) findViewById(R.id.btnSquare7);
        buttons[3].setOnClickListener(sq);

        buttons[4] = (Button) findViewById(R.id.btnSquare8);
        buttons[4].setOnClickListener(sq);

        buttons[5] = (Button) findViewById(R.id.btnBack);
        buttons[5].setOnClickListener(bh);
        //setupButton();

    }

    //display an error
    private void showAlert(Exception e){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage(e.toString());
        dialog.create();
        dialog.show();
    }

    private void showHelp(View v){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage(getResources().getString(help_cs));
        dialog.create();
        dialog.show();
    }

    public void onStart(){
        super.onStart();
        updateView();
    }

    //shows the best number of moves for each pattern
    public void updateView() throws NullPointerException{
//        TextView m4 = (TextView) findViewById(R.id.moves4);
//        m4.setText(Player.current.get_four());
//        TextView m5 = (TextView) findViewById(R.id.moves5);
//        m5.setText(Player.current.get_five());
//        TextView mD = (TextView) findViewById(R.id.moves3);
//        mD.setText(Player.current.get_diamond());
        TextView user = (TextView) findViewById(R.id.player);
        String msg = "Player: " + Player.current.getName();
        user.setText(msg);
    }

    //start up a square patterned game
    public void startSquare(View v){
        Button b = (Button) v;
        String label = b.getText().toString();
        int size = Character.getNumericValue(label.charAt(0));
        Game.newGame("square",size);

        getWindow().setExitTransition(new Explode());
        Intent square = new Intent(MineMenuActivity.this, MatchActivity.class);
        startActivity(square, ActivityOptions.makeSceneTransitionAnimation(MineMenuActivity.this).toBundle());
    }

    //start up a diamond patterned game
    public void startDmnd(View v){
        Game.newGame("diamond",5);

        getWindow().setExitTransition(new Explode());
        Intent dmnd = new Intent(MineMenuActivity.this, MatchActivity.class);
        startActivity(dmnd, ActivityOptions.makeSceneTransitionAnimation(MineMenuActivity.this).toBundle());
    }

    private class SquareHandler implements View.OnClickListener {
        public void onClick(View v) {startSquare(v);}
    }

    private class DmndHandler implements View.OnClickListener {
        public void onClick(View v) {startDmnd(v);}
    }

    private class HelpHandler implements View.OnClickListener {
        public void onClick(View v) {showHelp(v);}
    }

    private void goBack(){this.finishAfterTransition();}

    private class BackHandler implements View.OnClickListener {
        public void onClick(View v) {
            goBack();
        }
    }



}
