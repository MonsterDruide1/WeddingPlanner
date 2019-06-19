package de.tenolo.weddingplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Einstellungen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,"Einstellungen",R.layout.einstellungen);

        ((TextView)findViewById(R.id.datumShow)).setText(Startseite.prefs.getString("dateHochzeit",""));
    }

    public void changeDate(View view){
        final Object obj= "WAITING FOR DATEPICKER";
        final DatePickerFragment newFragment = new DatePickerFragment(obj);
        newFragment.show(getSupportFragmentManager(),"datePicker");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (obj) {
                        obj.wait();
                    }
                    final String date = newFragment.day+"."+ newFragment.month+"."+ newFragment.year;
                    Startseite.prefs.edit().putString("dateHochzeit",date).apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)findViewById(R.id.datumShow)).setText(date);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
