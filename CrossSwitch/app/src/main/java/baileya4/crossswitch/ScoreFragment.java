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

import java.util.ArrayList;

import static android.R.color.black;
import static android.R.color.darker_gray;
import static android.R.color.white;
import static baileya4.crossswitch.R.attr.colorControlHighlight;
import static baileya4.crossswitch.R.attr.theme;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseManager dbManager;

    private OnFragmentInteractionListener mListener;

    public ScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
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
            GridLayout grid = (GridLayout) root.findViewById(R.id.score_table);

            // create arrays of components
            TextView[] names = new TextView[players.size( )];
            TextView[] score4 = new TextView[players.size( )];
            TextView[] score5 = new TextView[players.size( )];
            TextView[] scoreD = new TextView[players.size( )];

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

                score4[i] = new TextView(getActivity());
                score4[i].setText( player.get_four() );
                score4[i].setTextSize(20);
                score4[i].setTextColor(getResources().getColor(black,null));
                score4[i].setGravity(17);
                if(i%2==0) {
                    score4[i].setBackgroundColor(getResources().getColor(darker_gray, null));
                }

                score5[i] = new TextView(getActivity());
                score5[i].setText( player.get_five() );
                score5[i].setTextSize(20);
                score5[i].setTextColor(getResources().getColor(black,null));
                score5[i].setGravity(17);
                if(i%2==0) {
                    score5[i].setBackgroundColor(getResources().getColor(darker_gray, null));
                }

                scoreD[i] = new TextView(getActivity());
                scoreD[i].setText( player.get_diamond() );
                scoreD[i].setTextSize(20);
                scoreD[i].setTextColor(getResources().getColor(black,null));
                scoreD[i].setGravity(17);
                if(i%2==0) {
                    scoreD[i].setBackgroundColor(getResources().getColor(darker_gray, null));
                }

                // add the elements to grid
                grid.addView( names[i], width / 4,
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( score4[i], ( int ) ( width / 4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( score5[i], ( int ) ( width / 4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( scoreD[i], ( int ) ( width / 4 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );

                i++;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_score, null);

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
