package baileya4.crossswitch;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static baileya4.crossswitch.R.drawable.cs_icon_small;

/*
 * Final Project, CSCI 412
 * Alexander Bailey
 *
 * PuzzleBox
 *
 * Contains several puzzle games, a database of players,
 * and a highscore table.
 */

public class MainMenuActivity extends AppCompatActivity {
    private Button cross;
    private Button match;
    private Button col;
    private Button ttt;
    private Button score;
    private Button sel;
    private Button[] info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        DatabaseManager dbManager = new DatabaseManager(this);
        dbManager.clearDB();

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setLogo(getResources().getDrawable(cs_icon_small, null));
            setSupportActionBar(toolbar);
        }catch(NullPointerException e){
            e.printStackTrace();
        }

        info = new Button[4];

        updateView();
    }

    public void onStart(){
        super.onStart();
        try {
            updateView();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    //sets button listeners
    public void updateView() throws NullPointerException{
        CrossHandler ch = new CrossHandler();
        cross = (Button) findViewById(R.id.cross_switch);
        cross.setOnClickListener(ch);

        MatchHandler mh = new MatchHandler();
        match = (Button) findViewById(R.id.calc);
        match.setOnClickListener(mh);

        ColorHandler clh = new ColorHandler();
        col = (Button) findViewById(R.id.color);
        col.setOnClickListener(clh);

        TTTHandler th = new TTTHandler();
        ttt = (Button) findViewById(R.id.ttt);
        ttt.setOnClickListener(th);

        SelectHandler ps = new SelectHandler();
        sel = (Button) findViewById(R.id.btnPlayerSelect);
        sel.setOnClickListener(ps);

        ScoreHandler sh = new ScoreHandler();
        score = (Button) findViewById(R.id.btnHighScore);
        score.setOnClickListener(sh);

        InfoHandler ih = new InfoHandler();
        info[0] = (Button) findViewById(R.id.infoCS);
        info[1] = (Button) findViewById(R.id.infoCC);
        info[2] = (Button) findViewById(R.id.infoColor);
        info[3] = (Button) findViewById(R.id.infoTTT);
        info[0].setOnClickListener(ih);
        info[1].setOnClickListener(ih);
        info[2].setOnClickListener(ih);
        info[3].setOnClickListener(ih);

        TextView user = (TextView) findViewById(R.id.user_entry);
        user.setText(Player.current.getName());
    }

    //shows a message dialog
    private void showMsg(String m,String t){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(t);
        dialog.setMessage(m);
        dialog.show();
    }

    private class InfoHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String name = getResources().getResourceName(v.getId());

            if(name.contains("infoCS")) {
                showMsg(getResources().getString(R.string.help_cs),getResources().getString(R.string.crossswitch));
            }else if(name.contains("infoCC")) {
                showMsg(getResources().getString(R.string.help_cc),getResources().getString(R.string.calc));
            }else if(name.contains("infoColor")) {
                showMsg(getResources().getString(R.string.help_color),getResources().getString(R.string.color));
            }else if(name.contains("infoTTT")) {
                showMsg(getResources().getString(R.string.help_ttt),getResources().getString(R.string.ttt));
            }
        }
    }

    private class CrossHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            getWindow().setExitTransition(new Explode());
            Intent select = new Intent(MainMenuActivity.this, CsMenuActivity.class);
            startActivity(select, ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this).toBundle());
        }
    }

    private class MatchHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            getWindow().setExitTransition(new Explode());
            Intent select = new Intent(MainMenuActivity.this, MineMenuActivity.class);
            startActivity(select, ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this).toBundle());
        }
    }

    private class ColorHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            getWindow().setExitTransition(new Explode());
            Intent select = new Intent(MainMenuActivity.this, SpheresActivity.class);
            startActivity(select, ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this).toBundle());
        }
    }

    private class TTTHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            getWindow().setExitTransition(new Explode());
            Intent select = new Intent(MainMenuActivity.this, TTTActivity.class);
            startActivity(select, ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this).toBundle());
        }
    }

    private class SelectHandler implements View.OnClickListener {
        public void onClick(View v) {
            getWindow().setExitTransition(new Slide());
            Intent select = new Intent(MainMenuActivity.this, PlayerSelectActivity.class);
            startActivity(select, ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this).toBundle());
        }
    }

    private class ScoreHandler implements View.OnClickListener {
        public void onClick(View v) {
            getWindow().setExitTransition(new Slide());
            Intent select = new Intent(MainMenuActivity.this, ScoreActivity.class);
            startActivity(select, ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this).toBundle());
        }
    }
}
