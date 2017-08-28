package baileya4.crossswitch;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import static android.R.color.black;
import static android.R.color.holo_blue_light;
import static android.R.drawable.alert_dark_frame;

public class TTTView extends GridLayout {
    private int side;
    private Button [][] buttons;
    private TextView status;
    private Button back;

    public TTTView( Context context, int w, int newSide,
                                  OnClickListener listener ) {
        super( context );
        side = newSide;
        // Set # of rows and columns of this GridLayout
        setColumnCount( side );
        setRowCount( side + 1 );

        int width = w-60;
        // Create the buttons and add them to this GridLayout
        buttons = new Button[side][side];
        for( int row = 0; row < side; row++ ) {
            for( int col = 0; col < side; col++ ) {
                buttons[row][col] = new Button( context );
                buttons[row][col].setTextSize( ( int ) ( width * .2 ) );
                buttons[row][col].setBackground(getResources().getDrawable(alert_dark_frame,null));
                buttons[row][col].setBackgroundTintList(getResources().getColorStateList(R.color.holoblue,null));
                buttons[row][col].setOnClickListener( listener );
                addView( buttons[row][col], width, width );
            }
        }

        // set up layout parameters of 4th row of gridLayout
        status = new TextView( context );
        Spec rowSpec = GridLayout.spec( side, 1 );
        Spec columnSpec = GridLayout.spec( 1, 1 );
        LayoutParams lpStatus
                = new LayoutParams( rowSpec, columnSpec );
        status.setLayoutParams( lpStatus );

        // set up status' characteristics
        status.setWidth( width );
//        status.setHeight( width );
        status.setGravity( Gravity.CENTER );
        status.setBackground(getResources().getDrawable(alert_dark_frame,null));
        status.setBackgroundTintList(getResources().getColorStateList(R.color.hologreen,null));
        status.setTextSize(24);

        addView( status );

        back = new Button( context );
        rowSpec = GridLayout.spec( side, 1 );
        columnSpec = GridLayout.spec( 0, 1 );
        lpStatus = new LayoutParams( rowSpec, columnSpec );
        back.setLayoutParams( lpStatus );

        back.setGravity( Gravity.CENTER );
        back.setBackground(getResources().getDrawable(alert_dark_frame,null));
        back.setBackgroundTintList(getResources().getColorStateList(black,null));
        back.setTextColor(getResources().getColor(holo_blue_light,null));
        back.setText("Back");
        back.setTextSize(18);

        addView(back);
    }

    public void setBackHandler(OnClickListener l){
        back.setOnClickListener(l);
    }

    public void setStatusText( String text ) {
        status.setText( text );
    }

    public void setStatusBackgroundColor( int color ) {
        status.setBackgroundColor( color );
    }

    public void setButtonText( int row, int column, String text ) {
        buttons[row][column].setText( text );
    }

    public boolean isButton( Button b, int row, int column ) {
        return ( b == buttons[row][column] );
    }

    public void resetButtons( ) {
        for( int row = 0; row < side; row++ )
            for( int col = 0; col < side; col++ )
                buttons[row][col].setText( "" );
    }

    public void enableButtons( boolean enabled ) {
        for( int row = 0; row < side; row++ )
            for( int col = 0; col < side; col++ )
                buttons[row][col].setEnabled( enabled );
    }
}
