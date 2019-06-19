package de.tenolo.weddingplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static de.tenolo.weddingplanner.TischordnungNames.loadOrdered;
import static de.tenolo.weddingplanner.TischordnungNames.prefs;
import static de.tenolo.weddingplanner.TischordnungNames.saveOrdered;

public class Gaesteliste extends AppCompatActivity {

    private RelativeLayout layout;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,"Gästeliste",R.layout.gaesteliste);

        layout = findViewById(R.id.gaesteliste_main);

        prefs = getSharedPreferences("Prefs",MODE_PRIVATE);

        generateLayout();
    }

    private void generateLayout(){
        String[] list = (loadOrdered("gaesteliste")!=null) ? (loadOrdered("gaesteliste")) : new String[0];

        String[] listtest = new String[list.length+1];
        listtest[0] = "REMOVE ME";
        System.arraycopy(list, 0, listtest, 1, list.length);

        ShowTischordnungAsList.generateList(listtest,layout,this);

        layout.removeViews(0,2);
    }

    public static void append(String name){
        String[] listOld = (loadOrdered("gaesteliste")!=null) ? (loadOrdered("gaesteliste")) : new String[0];
        String[] listNew = new String[listOld.length+1];

        System.arraycopy(listOld, 0, listNew, 0, listOld.length);

        listNew[listNew.length-1]=name;

        saveOrdered("gaesteliste",listNew);
    }

    public static void remove(String name){
        String[] listOld = loadOrdered("gaesteliste");
        String[] listNew = new String[listOld.length-1];

        int substract = 0;
        for(int i=0;i<listOld.length;i++){
            if(!listOld[i].equals(name)){
                listNew[i+substract] = listOld[i];
            }
            else {
                substract = -1;
            }
        }

        saveOrdered("gaesteliste",listNew);
    }

    public void listeAdd(View view){
        final Context context = this;

        final EditText input = new EditText(context);
        input.setSingleLine();
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);

        final AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle("Wer soll zur Gästeliste hinzugefügt werden?")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String editable = input.getText().toString();
                        Gaesteliste.append(editable);
                        layout.removeAllViews();
                        generateLayout();
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                }).create();
        input.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            builder.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
                            return true;
                        }
                        return false;
                    }
                });
        builder.show();
    }

}
