package baileya4.crossswitch;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/*
 *  AddActivity
 *  based on InsertActivity from CandyStore
 *  modified by Alexander Bailey
 *
 *  allows players to add new profiles
 */
public class AddActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbManager = new DatabaseManager(this);
    }

    public void insert( View v ) {
        EditText nameEditText = ( EditText) findViewById( R.id.input_name );
        String name = nameEditText.getText( ).toString( );

        Player player = new Player(0, name);
        dbManager.insert( player );
        Toast.makeText( this, "Player added", Toast.LENGTH_SHORT ).show( );

        nameEditText.setText( "" );
    }

    public void goBack( View v ) {
        this.finish( );
    }
}