package baileya4.crossswitch;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by soulo_000 on 8/7/2017.
 */

public class Hint {
    public Button view;
    private int blue;
    private ArrayList<Integer> green = new ArrayList<Integer>();
    private HashMap<String,ArrayList<Integer>> colors = new HashMap<>();
    private HashMap<String,String> colorVal = new HashMap<>();
    private String hint;

    public Hint(){
        hint = new String();
        colors.put("green",new ArrayList<Integer>());
        colors.put("orange",new ArrayList<Integer>());
        colors.put("purple",new ArrayList<Integer>());
        colors.get("green").add(0);
        colors.get("orange").add(0);
        colors.get("purple").add(0);

        colorVal.put("blue","#33b5e5");
        colorVal.put("green","#99cc00");
        colorVal.put("orange","#ffbb33");
        colorVal.put("purple","#aa66cc");
    }

    public String getHint(){
        return hint;
    }

    public int getBlue(){
        return blue;
    }

    public ArrayList<Integer> getColor(String c){
        return colors.get(c);
    }

    public int getColorTotal(String c){
        int total = 0;
        for(int i=0;i<colors.get(c).size();i++){
            total += colors.get(c).get(i);
        }
        return total;
    }

    public void bumpBlue(){
        blue++;
    }

    public void bumpColor(String c){
        int i = colors.get(c).size()-1;
        int n = colors.get(c).get(i);
        n++;
        colors.get(c).set(i,n);
    }

    public int getRecent(String c){
        return colors.get(c).get(colors.get(c).size()-1);
    }

    public void addHint(String c, Context con){
        if(colors.get(c).size() > 0){
            hint += "<font color='" + colorVal.get(c) + "'>";
            hint += colors.get(c).get(colors.get(c).size()-1).toString();
            hint += "</font> ";
        }
    }

    public void addColor(String c){
        colors.get(c).add(0);
    }

    public void reset(){
        blue = 0;
        Collection<ArrayList<Integer>> cl = colors.values();
        for(ArrayList<Integer> color : cl) {
            color.clear();
        }
    }
}
