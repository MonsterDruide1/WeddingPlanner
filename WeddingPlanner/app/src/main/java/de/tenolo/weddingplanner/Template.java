package de.tenolo.weddingplanner;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Template extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,"Template",R.layout.startseite);
    }

}
