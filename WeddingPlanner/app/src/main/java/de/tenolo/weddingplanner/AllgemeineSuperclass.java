package de.tenolo.weddingplanner;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

abstract class AllgemeineSuperclass {

    Object[] types;
    String[] hints;
    String listenname;
    int anzahlFelder;
    String name;
    String specials;
    String groupName;

    SharedPreferences prefs;

    public AllgemeineSuperclass(AllgemeinesObject object, Context context){
        try {
            String typesExtra = object.types;
            JSONArray typesArray = (new JSONArray(typesExtra));
            types = new Object[typesArray.length()];
            for(int i=0;i<types.length;i++){
                Object type = typesArray.get(i);
                try {
                    float number = Float.parseFloat(type.toString());
                    if(number==0f){
                        types[i]=0;
                    }
                    else if(number==0.1f) {
                        types[i]=0.1;
                    }
                    else if(number==0.2f){
                        types[i]=0.2f;
                    }
                }
                catch (NumberFormatException e){
                    types[i] = type;
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        hints = object.hints;
        listenname = object.listenname;
        anzahlFelder = object.anzahlFelder;
        name = object.name;
        specials = object.specials;
        groupName = object.groupName;

        prefs = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
    }

    View makeBigCaptions(Context context, RelativeLayout mainLayout){
        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(context);
        if(!groupName.equals("")) {
            tv.setText(groupName);
        }
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        tv.setId(View.generateViewId());
        mainParams.setMargins(10,10,10,0);
        mainLayout.addView(tv,mainParams);

        mainParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView t = new TextView(context);
        t.setText(listenname );
        t.setTextSize(20);
        t.setGravity(Gravity.CENTER);
        t.setId(View.generateViewId());
        mainParams.setMargins(10,10,10,10);
        mainParams.addRule(RelativeLayout.BELOW,tv.getId());
        mainLayout.addView(t,mainParams);

        return t;
    }

    abstract void generateLayout(Context context, Activity activity, RelativeLayout mainLayout);

}
