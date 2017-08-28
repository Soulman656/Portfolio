package baileya4.crossswitch;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScoreActivity extends FragmentActivity implements
        ScoreFragment.OnFragmentInteractionListener,
        MatchScoreFragment.OnFragmentInteractionListener {

    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        BackHandler gb = new BackHandler();
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(gb);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void goBack(){this.finishAfterTransition();}

    private class BackHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            goBack();
        }
    }
}
