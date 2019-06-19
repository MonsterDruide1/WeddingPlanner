package de.tenolo.weddingplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Template extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,"Template",R.layout.startseite);
    }

}
