package baileya4.crossswitch;

/**
 * PlayerSelect activity
 *  Alexander Bailey
 *
 * allows players to select a profile, create a new one,
 * or delete or update and old one.
 */

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.color.white;
import static android.R.drawable.alert_dark_frame;
import static android.R.drawable.alert_light_frame;
import static baileya4.crossswitch.R.drawable.cs_icon_small;

public class PlayerSelectActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private ScrollView scrollView;
    private int buttonWidth;
    private HashMap<Button,Integer> buttons = new HashMap<Button,Integer>();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_player_select );
        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        toolbar.setLogo(getResources().getDrawable(cs_icon_small,null));
        setSupportActionBar( toolbar );

        dbManager = new DatabaseManager( this );

        scrollView = ( ScrollView ) findViewById( R.id.scrollView );
        Point size = new Point( );
        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        buttonWidth = size.x;
        updateView( );
    }

    protected void onResume( ) {
        super.onResume( );
        updateView( );
    }

    //create a grid and buttons from all players in the database.
    public void updateView( ) {
        ArrayList<Player> players = dbManager.selectAll();
        if( players.size( ) > 0 ) {
            scrollView.removeAllViewsInLayout( );

            GridLayout grid = new GridLayout( this );
            grid.setRowCount(players.size( ));
            grid.setColumnCount(1);

            Button [] button = new Button[players.size( )];
            ButtonHandler bh = new ButtonHandler( );

            int i = 0;
            for (Player player : players ) {
                button[i] = new Button(this);
                button[i].setText(player.getName( ));
                button[i].setBackground(getResources().getDrawable(alert_light_frame,null));
                button[i].setBackgroundTintList(getResources().getColorStateList(R.color.holoblue,null));
                button[i].setOnClickListener( bh );
                buttons.put(button[i],player.getID());

                grid.addView( button[i], buttonWidth,
                        GridLayout.LayoutParams.WRAP_CONTENT );
                i++;
            }

            //create back button
            Button back = new Button(this);
            back.setText( R.string.button_back );
            back.setBackground(getResources().getDrawable(alert_dark_frame,null));
            back.setTextColor(getResources().getColor(white,null));
            back.setOnClickListener( new View.OnClickListener( ) {
                public void onClick(View v) {
                    PlayerSelectActivity.this.finishAfterTransition();
                }
            });
            grid.addView( back, buttonWidth/2,
                    GridLayout.LayoutParams.WRAP_CONTENT );

            scrollView.addView( grid );
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater( ).inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId( );
        switch ( id ) {
            case R.id.action_add:
                Intent insertIntent
                        = new Intent( this, AddActivity.class );
                this.startActivity( insertIntent );
                return true;
            case R.id.action_delete:
                Intent deleteIntent
                        = new Intent( this, DeleteActivity.class );
                this.startActivity( deleteIntent );
                return true;
            case R.id.action_update:
                Intent updateIntent
                        = new Intent( this, UpdateActivity.class );
                this.startActivity( updateIntent );
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick( View v ) {

            dbManager.selectPlayer(buttons.get((Button) v));
            String selected = "Player " + Player.current.getName() + "selected";
            Toast.makeText( PlayerSelectActivity.this, selected,
                    Toast.LENGTH_SHORT ).show( );
            PlayerSelectActivity.this.finish();
        }
    }
}
