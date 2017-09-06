package baileya4.crossswitch;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.R.color.black;
import static android.R.color.darker_gray;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchScoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseManager dbManager;

    private OnFragmentInteractionListener mListener;

    public MatchScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchScoreFragment newInstance(String param1, String param2) {
        MatchScoreFragment fragment = new MatchScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void build_table(View root){
        ArrayList<Player> players = dbManager.selectAll( );
        if( players.size( ) > 0 ) {

            GridLayout grid = (GridLayout) root.findViewById(R.id.match_table);

            // create arrays of components
            TextView[] names = new TextView[players.size( )];
            TextView[] scoreT5 = new TextView[players.size( )];
            TextView[] scoreT6 = new TextView[players.size( )];
            TextView[] scoreT7 = new TextView[players.size( )];

            // retrieve width of screen
            Point size = new Point( );
            getActivity().getWindowManager( ).getDefaultDisplay( ).getSize( size );
            int width = size.x;

            int i = 0;

            for ( Player player : players ) {

                names[i] = new TextView(getActivity());
                names[i].setText( player.getName() );
                names[i].setTextSize(20);
                names[i].setTextColor(getResources().getColor(black,null));
                names[i].setGravity(17);
                if(i%2==0) {
                    names[i].setBackgroundColor(getResources().getColor(darker_gray, null));
                }

                scoreT5[i] = new TextView(getActivity());
                scoreT5[i].setText(CountUpTimer.ConvertToTime(Integer.parseInt(player.get_Tfive())));
                scoreT5[i].setTextSize(20);
                scoreT5[i].setTextColor(getResources().getColor(black,null));
                scoreT5[i].setGravity(17);
                if(i%2==0) {
                    scoreT5[i].setBackgroundColor(getResources().getColor(darker_gray, null));
                }

                scoreT6[i] = new TextView(getActivity());
                scoreT6[i].setText(CountUpTimer.ConvertToTime(Integer.parseInt(player.get_Tsix())));
                scoreT6[i].setTextSize(20);
                scoreT6[i].setTextColor(getResources().getColor(black,null));
                scoreT6[i].setGravity(17);
                if(i%2==0) {
                    scoreT6[i].setBackgroundColor(getResources().getColor(darker_gray, null));
                }

                scoreT7[i] = new TextView(getActivity());
                scoreT7[i].setText(CountUpTimer.ConvertToTime(Integer.parseInt(player.get_Tseven())));
                scoreT7[i].setTextSize(20);
                scoreT7[i].setTextColor(getResources().getColor(black,null));
                scoreT7[i].setGravity(17);
                if(i%2==0) {
                    scoreT7[i].setBackgroundColor(getResources().getColor(darker_gray, null));
                }

                // add the elements to grid
                grid.addView( names[i], width / 4,
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( scoreT5[i], ( int ) ( width / 4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( scoreT6[i], ( int ) ( width / 4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( scoreT7[i], ( int ) ( width / 4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );


                i++;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_match_score, null);

        build_table(root);
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
