package de.tenolo.weddingplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TischordnungNames extends AppCompatActivity {
    private final Context context = this;
    static SharedPreferences prefs;

    private static final int[][] uKoords = new int[][]{new int[]{125,90},new int[]{159,90},new int[]{198,90},new int[]{232,90},/*5->*/new int[]{265,48},new int[]{232,7},new int[]{198,7},new int[]{159,7},new int[]{125,7},
            /*10->*/new int[]{89,6},new int[]{50,37},new int[]{50,71},new int[]{50,110},new int[]{50,143},/*15->*/new int[]{50,179},new int[]{50,223},new int[]{90,265},new int[]{125,265},new int[]{160,265},
            /*20->*/new int[]{198,265},new int[]{233,265},new int[]{265,223},new int[]{233,181},new int[]{198,181},/*25->*/new int[]{160,181},new int[]{126,181}};

    private static int[][] tKoords = new int[][]{new int[]{48,40},new int[]{48,75},new int[]{48,115},new int[]{48,150},/*5->*/new int[]{48,185},new int[]{48,230},new int[]{88,255},new int[]{125,230},new int[]{125,175},
            /*10->*/new int[]{160,175},new int[]{200,175},new int[]{233,175},new int[]{268,130},new int[]{233,90},/*15->*/new int[]{200,90},new int[]{160,90},new int[]{125,90},new int[]{125,40},new int[]{86,10}};

    static int[][] koords;

    private final int[] brautpaarU = new int[]{13,14};
    private final int[] brautpaarT = new int[]{3,4};

    private int[] brautpaar;

    private static final int size = 18;

    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String name = getIntent().getStringExtra("name");
        prefs = getSharedPreferences("Prefs",MODE_PRIVATE);
        setContentView(R.layout.tischordnung_names);
        final RelativeLayout layout = findViewById(R.id.tische_u_layout);

        if(loadOrdered(name)!=null){
            names = (loadOrdered(name));
        } else {
            if(name.equals("u")){
                names = new String[uKoords.length];

                for(int i=0;i<names.length;i++){
                    names[i] = " ";
                }
            }
            else if(name.equals("t")){
                names = new String[tKoords.length];

                for(int i=0;i<names.length;i++){
                    names[i] = " ";
                }
            }
            else {
                names = null;
            }
        }
        int i=0;

        if(name.equals("u")){
            koords = uKoords;
            ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.tische_u);
            brautpaar = brautpaarU;
        }
        else if(name.equals("t")){
            koords = tKoords;
            ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.tische_t);
            brautpaar = brautpaarT;
        }

        for(int[] koord : koords){
            final int finali = i;
            i++;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( (int)(size * getResources().getDisplayMetrics().density),(int)(size * getResources().getDisplayMetrics().density));
            params.setMargins((int)((koord[1]) * getResources().getDisplayMetrics().density),(int)((koord[0]-1) * getResources().getDisplayMetrics().density),0,0);
            params.addRule(RelativeLayout.ALIGN_TOP,R.id.imageView);
            params.addRule(RelativeLayout.ALIGN_LEFT,R.id.imageView);
            final Button button = new Button(this);
            if((brautpaar[0]==i)||(brautpaar[1]==i)){
                if((names[finali] == null) || (names[finali].equals(" ")) || (names[finali].equals(""))){
                    button.setBackground(getResources().getDrawable(R.drawable.heirat_stuhl_frei));
                }
                else {
                    button.setBackground(getResources().getDrawable(R.drawable.heirat_stuhl_besetzt));
                }
            }
            else {
                if((names[finali] == null) || (names[finali].equals(" ")) || (names[finali].equals(""))){
                    button.setBackground(getResources().getDrawable(R.drawable.heirat_stuhl_normal_frei));
                }
                else {
                    button.setBackground(getResources().getDrawable(R.drawable.heirat_stuhl_normal_besetzt));
                }
            }
            button.setLayoutParams(params);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final boolean newName;
                    final String text;
                    if((names[finali] == null) || (names[finali].equals(" ")) || (names[finali].equals(""))){
                        text = "Nicht vergeben";
                        newName=true;
                    }
                    else {
                        text = names[finali];
                        newName=false;
                    }
                    Snackbar bar = Snackbar.make(layout,(text),Snackbar.LENGTH_INDEFINITE);
                    bar.setAction("Bearbeiten", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final AutoCompleteTextView input = new AutoCompleteTextView(context);
                            if(!text.equals("Nicht vergeben")){
                                input.setText(text);
                            }
                            input.setSingleLine();
                            input.setImeOptions(EditorInfo.IME_ACTION_DONE);

                            String[] array = getAutocompletes();

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_dropdown_item_1line,array);
                            input.setAdapter(adapter);

                            final AlertDialog builder = new AlertDialog.Builder(context)
                                    .setTitle("Wer soll hier sitzen?")
                                    .setView(input)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            String editable = input.getText().toString();
                                            names[finali] = editable;
                                            saveOrdered(name,names);
                                            Gaesteliste.append(editable);
                                            if(!newName){
                                                Gaesteliste.remove(text);
                                            }
                                            if((brautpaar[0]==finali)||(brautpaar[1]==finali)){
                                                if((names[finali] == null) || (names[finali].equals(" ")) || (names[finali].equals(""))){
                                                    button.setBackground(getResources().getDrawable(R.drawable.heirat_stuhl_frei));
                                                }
                                                else {
                                                    button.setBackground(getResources().getDrawable(R.drawable.heirat_stuhl_besetzt));
                                                }
                                            }
                                            else {
                                                if((names[finali] == null) || (names[finali].equals(" ")) || (names[finali].equals(""))){
                                                    button.setBackground(getResources().getDrawable(R.drawable.heirat_stuhl_normal_frei));
                                                }
                                                else {
                                                    button.setBackground(getResources().getDrawable(R.drawable.heirat_stuhl_normal_besetzt));
                                                }
                                            }
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
                    });
                    bar.show();
                }
            });
            layout.addView(button,params);

            TextView number = new TextView(this);
            number.setText(i+"");
            params = new RelativeLayout.LayoutParams( (int)(25 * getResources().getDisplayMetrics().density),(int)(25 * getResources().getDisplayMetrics().density));
            params.setMargins((int)((koord[1]+4) * getResources().getDisplayMetrics().density),(int)((koord[0]-2) * getResources().getDisplayMetrics().density),0,0);
            params.addRule(RelativeLayout.ALIGN_TOP,R.id.imageView);
            params.addRule(RelativeLayout.ALIGN_LEFT,R.id.imageView);
            layout.addView(number,params);
        }

        FloatingActionButton button = findViewById(R.id.toggle_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowTischordnungAsList.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }

    private String[] getAutocompletes(){
        String[] gaesteliste = (loadOrdered("gaesteliste")!=null) ? (loadOrdered("gaesteliste")) : new String[0];
        List<String> returns = new ArrayList<>();

        for (String gast : gaesteliste) {
            boolean found = false;
            for (String name : names) {
                if (gast.equals(name)) {
                    found = true;
                }
            }
            if (!found) {
                returns.add(gast);
            }
        }

        return returns.toArray(new String[0]);
    }

    static void saveOrdered(String name, String[] array){
        JSONArray jsonarray = new JSONArray(Arrays.asList(array));
        prefs.edit().putString(name,jsonarray.toString()).apply();
    }

    static  String[] loadOrdered(String name){
        try {
            int count = 0;
            JSONArray array = new JSONArray(prefs.getString(name,null));
            for (int i = 0; i < array.length(); i++) {
                count = i;
            }
            String[] ret = new String[count+1];
            for (int i = 0; i < array.length(); i++) {
                ret[i] = ((String)array.get(i));
            }
            return ret;
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
