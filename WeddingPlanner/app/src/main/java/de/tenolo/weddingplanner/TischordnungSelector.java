package de.tenolo.weddingplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class TischordnungSelector extends AppCompatActivity { //TODO Rest

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,"Tischordnung",R.layout.tischordnung_selector);

    }

    public void startNames(View view) {
        String name;
        switch(view.getId()) {
            case R.id.tischform_u:
                name="u";
                break;
            case R.id.tischform_t:
                name="t";
                break;
            default:
                Toast.makeText(this,"Dieser Bereich ist noch nicht freigeschaltet!",Toast.LENGTH_LONG).show();
                return;
        }
        Intent intent = new Intent(this,TischordnungNames.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }

}
