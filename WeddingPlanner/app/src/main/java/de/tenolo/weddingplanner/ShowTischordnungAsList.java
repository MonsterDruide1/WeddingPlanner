package de.tenolo.weddingplanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static de.tenolo.weddingplanner.TischordnungNames.loadOrdered;

public class ShowTischordnungAsList extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        String name = getIntent().getStringExtra("name");
        final String[] list;
        if(loadOrdered(name)!=null){
            list = loadOrdered(name);
        }
        else {
            list = new String[TischordnungNames.koords.length];
            for(int i=0;i<list.length;i++) {
                list[i]="";
            }
        }

        ScrollView main = new ScrollView(this);
        RelativeLayout layout = new RelativeLayout(this);


        generateList(list,layout,this);


        main.addView(layout);
        setContentView(main);
    }

    public static void generateList(String[] list,RelativeLayout layout, Context context){
        TextView prevNr = null;
        TextView prevName = null;

        int substract = 0;
        for(int i=0;i<list.length;i++){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i!=0){
                params.removeRule(RelativeLayout.BELOW);
                params.addRule(RelativeLayout.BELOW,prevNr.getId());
            }
            TextView currentNr = new TextView(context);
            currentNr.setText(i+1+substract+".");
            currentNr.setTextSize(25);
            layout.addView(currentNr,params);
            currentNr.setId(View.generateViewId());
            prevNr = currentNr;

            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i!=0){
                params.removeRule(RelativeLayout.BELOW);
                params.removeRule(RelativeLayout.ALIGN_LEFT);
                params.addRule(RelativeLayout.BELOW,prevName.getId());
                params.addRule(RelativeLayout.RIGHT_OF,currentNr.getId());
            }
            TextView currentName = new TextView(context);
            currentName.setText(list[i]);
            layout.addView(currentName,params);
            currentName.setId(View.generateViewId());
            currentName.setTextSize(25);
            prevName = currentName;

            if(list[i].equals("REMOVE ME")){
                substract--;
            }
        }
    }

}
