package de.tenolo.weddingplanner;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class AllgemeineSeite extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.all_main);

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,getIntent().getStringExtra("navID"),R.layout.gaesteliste);

        findViewById(R.id.liste_add).setVisibility(View.INVISIBLE);

        String[] types = getIntent().getStringArrayExtra("listTypes");
        Parcelable[] parcelables = getIntent().getParcelableArrayExtra("parcelables");

        RelativeLayout layout = findViewById(R.id.gaesteliste_main);
        RelativeLayout.LayoutParams params;

        RelativeLayout prevRow = null;

        for(int i=0;i<types.length;i++){
            String type = types[i];
            AllgemeinesObject parcelable = (AllgemeinesObject) parcelables[i];

            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if(prevRow!=null){
                params.addRule(RelativeLayout.BELOW,prevRow.getId());
            }
            prevRow = new RelativeLayout(this);
            switch (type){
                case "AllgemeineListe":
                    new AllgemeineListe(parcelable,this,this,(findViewById(R.id.liste_add)),prevRow);
                    break;
                case "AllgemeineTabelle":
                    new AllgemeineTabelle(parcelable,this,this,(findViewById(R.id.liste_add)),prevRow);
                    break;
            }
            layout.addView(prevRow,params);
            prevRow.setId(View.generateViewId());
        }

    }

    public void listeAdd(View view){

    }

}
