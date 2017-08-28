package baileya4.crossswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
 * Final Project, CSCI 412
 * Alexander Bailey
 *
 * ColorSwitch
 *
 * contains several colored squares which can be moved around by dragging
 */

public class SpheresActivity extends AppCompatActivity {

    private View mMovingView;
    private float mInitialX;
    private float mInitialY;
    private int mInitialLeft;
    private int mInitialTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spheres);

        try {
            setupSquares();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    //creates an array of the buttons in the game
    private void setupSquares() throws NullPointerException{
        MoveListener ml = new MoveListener();
        TextView b1 = (TextView) findViewById(R.id.blue1);
        b1.setOnTouchListener(ml);
        TextView b2 = (TextView) findViewById(R.id.blue2);
        b2.setOnTouchListener(ml);
        TextView b3 = (TextView) findViewById(R.id.blue3);
        b3.setOnTouchListener(ml);
        TextView b4 = (TextView) findViewById(R.id.blue4);
        b4.setOnTouchListener(ml);
        //green
        TextView g1 = (TextView) findViewById(R.id.green1);
        g1.setOnTouchListener(ml);
        TextView g2 = (TextView) findViewById(R.id.green2);
        g2.setOnTouchListener(ml);
        TextView g3 = (TextView) findViewById(R.id.green3);
        g3.setOnTouchListener(ml);
        TextView g4 = (TextView) findViewById(R.id.green4);
        g4.setOnTouchListener(ml);
        //orange
        TextView o1 = (TextView) findViewById(R.id.orange1);
        o1.setOnTouchListener(ml);
        TextView o2 = (TextView) findViewById(R.id.orange2);
        o2.setOnTouchListener(ml);
        TextView o3 = (TextView) findViewById(R.id.orange3);
        o3.setOnTouchListener(ml);
        TextView o4 = (TextView) findViewById(R.id.orange4);
        o4.setOnTouchListener(ml);
        //purple
        TextView p1 = (TextView) findViewById(R.id.purple1);
        p1.setOnTouchListener(ml);
        TextView p2 = (TextView) findViewById(R.id.purple2);
        p2.setOnTouchListener(ml);
        TextView p3 = (TextView) findViewById(R.id.purple3);
        p3.setOnTouchListener(ml);
        TextView p4 = (TextView) findViewById(R.id.purple4);
        p4.setOnTouchListener(ml);

        BackHandler gb = new BackHandler();
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(gb);
    }

    private void goBack(){this.finishAfterTransition();}

    private class BackHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            goBack();
        }
    }

    private class MoveListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            RelativeLayout.LayoutParams mLayoutParams;

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mMovingView = view;
                    mLayoutParams = (RelativeLayout.LayoutParams) mMovingView.getLayoutParams();
                    mInitialX = motionEvent.getRawX();
                    mInitialY = motionEvent.getRawY();
                    mInitialLeft = mLayoutParams.leftMargin;
                    mInitialTop = mLayoutParams.topMargin;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mMovingView != null) {
                        mLayoutParams = (RelativeLayout.LayoutParams) mMovingView.getLayoutParams();
                        mLayoutParams.leftMargin = (int) (mInitialLeft + motionEvent.getRawX() - mInitialX);
                        mLayoutParams.topMargin = (int) (mInitialTop + motionEvent.getRawY() - mInitialY);
                        mMovingView.setLayoutParams(mLayoutParams);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    mMovingView = null;
                    break;
            }

            return true;
        }
    }
}
