package baileya4.crossswitch;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.R.color.holo_green_light;
import static android.R.color.holo_red_light;

public class TTTActivity extends AppCompatActivity {
    private TicTacToe game;
    private TTTView tttView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt);
        game = new TicTacToe();
        Point size = new Point();
        LinearLayout root = (LinearLayout) findViewById(R.id.frame);
        getWindowManager().getDefaultDisplay().getSize( size );
        int w = (size.x) / TicTacToe.SIDE;
        ButtonHandler bh = new ButtonHandler();
        tttView = new TTTView(this, w, TicTacToe.SIDE, bh);
        tttView.setStatusText(game.result());

        BackHandler bl = new BackHandler();
        tttView.setBackHandler(bl);
        try {
            root.addView(tttView);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void showNewGameDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(game.result());
        alert.setMessage("Play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            for (int row = 0; row < TicTacToe.SIDE; row++) {
                for (int column = 0; column < TicTacToe.SIDE; column++) {
                    if (tttView.isButton((Button) v, row, column)) {
                        int play = game.play(row, column);
                        if (play == 1)
                            tttView.setButtonText(row, column, "X");
                        else if (play == 2)
                            tttView.setButtonText(row, column, "O");
                        if (game.isGameOver()) {
                            tttView.setStatusBackgroundColor(getResources().getColor(holo_red_light,null));
                            tttView.enableButtons(false);
                            tttView.setStatusText(game.result());
                            showNewGameDialog();    // offer to play again
                        }
                    }
                }
            }
        }
    }

    private void goBack(){this.finishAfterTransition();}

    private class BackHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            goBack();
        }
    }

    private class PlayDialog implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            if (id == -1) /* YES button */ {
                game.resetGame();
                tttView.enableButtons(true);
                tttView.resetButtons();
                tttView.setStatusBackgroundColor(getResources().getColor(holo_green_light,null));
                tttView.setStatusText(game.result());
            } else if (id == -2) // NO button
                TTTActivity.this.finishAfterTransition();
        }
    }
}