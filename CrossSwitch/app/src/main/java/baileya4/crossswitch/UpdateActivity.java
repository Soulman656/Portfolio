package baileya4.crossswitch;

/**
 * UpdateActivity
 * based on UpdateActivity from CandyStore
 * modified by Alexander Bailey
 *
 * allows players to change the name of a profile
 */
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.color.white;
import static android.R.drawable.alert_dark_frame;

public class UpdateActivity extends AppCompatActivity {
    DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        dbManager = new DatabaseManager( this );
        updateView( );
    }

    // Build a View dynamically with all the players
    public void updateView( ) {
        ArrayList<Player> players = dbManager.selectAll( );
        if( players.size( ) > 0 ) {
            // create ScrollView and GridLayout
            ScrollView scrollView = new ScrollView( this );
            GridLayout grid = new GridLayout( this );
            grid.setRowCount( players.size( ) );
            grid.setColumnCount( 3 );

            // create arrays of components
            TextView [] ids = new TextView[players.size( )];
            EditText [] names = new EditText[players.size( )];
            Button [] buttons = new Button[players.size( )];
            ButtonHandler bh = new ButtonHandler( );

            // retrieve width of screen
            Point size = new Point( );
            getWindowManager( ).getDefaultDisplay( ).getSize( size );
            int width = size.x;

            int i = 0;

            for ( Player player : players ) {

                ids[i] = new TextView( this );
                ids[i].setGravity( Gravity.CENTER );
                ids[i].setText( "" + player.getID( ) );

                names[i] = new EditText( this );
                names[i].setText( player.getName() );
                names[i].setId( 10 * player.getID( ) );

                // create the button
                buttons[i] = new Button(this);
                buttons[i].setText("Update");
                buttons[i].setId(player.getID());
                buttons[i].setBackground(getResources().getDrawable(alert_dark_frame,null));
                buttons[i].setTextColor(getResources().getColor(white,null));
                buttons[i].setOnClickListener( bh );

                // add the elements to grid
                grid.addView( ids[i], width / 10,
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( names[i], ( int ) ( width * .4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( buttons[i], ( int ) ( width * .35 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );

                i++;
            }

            // create a back button
            Button backButton = new Button( this );
            backButton.setBackground(getResources().getDrawable(alert_dark_frame,null));
            backButton.setTextColor(getResources().getColor(white,null));
            backButton.setText( R.string.button_back );

            backButton.setOnClickListener( new View.OnClickListener( ) {
                public void onClick(View v) {
                    UpdateActivity.this.finish();
                }
            });

            grid.addView(backButton, (int) (width * .2),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            scrollView.addView( grid );
            setContentView( scrollView );
        }
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick( View v ) {
            // retrieve name
            int id = v.getId( );
            EditText nameET = ( EditText ) findViewById( 10 * id );
            String name = nameET.getText( ).toString( );

            dbManager.updateById(id, name);
            Toast.makeText( UpdateActivity.this, "Player updated",
                        Toast.LENGTH_SHORT ).show( );

            updateView( );
        }
    }
}