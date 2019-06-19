package de.tenolo.weddingplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import static de.tenolo.weddingplanner.Startseite.prefs;

public class AllgemeineTabelle extends AppCompatActivity {

    private RelativeLayout mainLayout;
    //0.2f -> FLOAT == GELD-BETRAG -> Summe am Ende ;;;;;; 0.1 -> DOUBLE == NORMALE KOMMA-ZAHL -> keine Summe am Ende

    private Object[] types;
    private String[] hints;
    private String listenname;
    private int anzahlFelder;
    private String name;
    private String specials;
    private float[] weights;
    private boolean[] shown;
    private boolean[] editable;
    private String groupName;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.all_main);

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,getIntent().getStringExtra("navID"),R.layout.gaesteliste);

        Intent intent = getIntent();
        try {
            String typesExtra = intent.getStringExtra("types");
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
                        types[i]=(float)0.2;
                    }
                }
                catch (NumberFormatException e){
                    types[i] = type;
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
            return;
        }

        hints = intent.getStringArrayExtra("hints");
        listenname = intent.getStringExtra("listenname");
        anzahlFelder = intent.getIntExtra("anzahlFelder",0);
        name = intent.getStringExtra("name");
        specials = intent.getStringExtra("specials");
        weights = intent.getFloatArrayExtra("weights");
        editable = intent.getBooleanArrayExtra("editable");
        shown = intent.getBooleanArrayExtra("shown");
        groupName = intent.getStringExtra("groupName");

        mainLayout = findViewById(R.id.gaesteliste_main);

        prefs = getSharedPreferences("Prefs",MODE_PRIVATE);

        if(specials.contains(" disableNew=true ")){
            (findViewById(R.id.liste_add)).setVisibility(View.INVISIBLE);
        }

        generateLayout(this);
    }

    private void generateLayout(final Context context){
        String[][] listold = (loadOrdered(name)!=null) ? (loadOrdered(name)) : new String[0][anzahlFelder];

        final String[][] list = new String[listold.length+1][anzahlFelder];

        list[0] = new String[anzahlFelder];
        for(int i=0;i<anzahlFelder;i++){  //DELETING THIS ROW LATER -> Nebeneinander stimmt wieder
            list[0][i] = "0";
        }

        for(int ie=0;ie<listold.length;ie++){
            System.arraycopy(listold[ie], 0, list[ie+1], 0, anzahlFelder);
        }

        View prevRow = null;
        float[] sums = new float[anzahlFelder];

        prevRow = makeCaptions(hints,context,weights);

        for (int i=0;i<list.length;i++) {
            String[] strings = list[i];
            prevRow = generateNewRow(strings, context, prevRow, weights, false,i);


            sums = calcSums(list);
        }

        mainLayout.removeViewAt(3); //REMOVING TEMP-ROW, NOT CAPTIONS

        boolean needsSumRow = false;

        for(Object type : types){
            if(type.getClass().toString().equals("class java.lang.Float")){
                needsSumRow=true;
            }
        }

        if(needsSumRow && listold.length>0) {
            View divisor = new View(context);
            divisor.setBackgroundColor(Color.parseColor("#CCCCCC"));

            RelativeLayout.LayoutParams paramsRel = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4);
            paramsRel.addRule(RelativeLayout.BELOW, prevRow.getId());

            mainLayout.addView(divisor, paramsRel);

            divisor.setId(View.generateViewId());
            prevRow = divisor;


            NumberFormat df = NumberFormat.getNumberInstance(Locale.UK);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            df.setGroupingUsed(false);

            String[] row = new String[anzahlFelder];

            for(int i=0;i<row.length;i++){
                if(types[i].getClass().toString().equals("class java.lang.Float")){
                    row[i]=df.format(sums[i]);
                }
                else {
                    row[i]="";
                }
            }

            generateNewRow(row,context,prevRow,weights,true,1);
        }
    }

    private View makeCaptions(final String[] hints, final Context context, float[] weights){
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


        LinearLayout layout = new LinearLayout(context);

        for(int i2=0;i2<anzahlFelder;i2++){
            if(shown[i2]) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weights[i2]);
                TextView currentVorstellungPreis = new TextView(context);
                currentVorstellungPreis.setText(hints[i2]);
                currentVorstellungPreis.setTextSize(16);
                currentVorstellungPreis.setTypeface(null, Typeface.BOLD);
                currentVorstellungPreis.setMaxLines(3);

                layout.addView(currentVorstellungPreis, params);
            }
        }


        mainParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mainParams.addRule(RelativeLayout.BELOW,t.getId());
        mainLayout.addView(layout,mainParams);

        layout.setId(View.generateViewId());
        return layout;
    }

    private float[] calcSums(String[][] list){
        float[] sums = new float[anzahlFelder];
        for(int i=0;i<anzahlFelder;i++) {
            sums[i] = 0f;
            for (String[] strings : list) {
                try {
                    sums[i] += Float.parseFloat(strings[i]);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return sums;
    }

    private View generateNewRow(final String[] entry, final Context context, View prevRow, float[] weights, boolean sumRow, int i){

        boolean bezahlt = false;
        if(specials.contains(" bezahlt=gruen ")){
            bezahlt = entry[entry.length-1].equals("TRUE");
        }

        LinearLayout layout = new LinearLayout(context);

        for(int i2=0;i2<anzahlFelder;i2++){
            if(shown[i2]) {
                if (types[i2].getClass().toString().equals("class java.lang.Float")) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weights[i2]);
                    TextView currentVorstellungPreis = new TextView(context);
                    currentVorstellungPreis.setText(entry[i2] + "€");
                    currentVorstellungPreis.setTextSize(20);
                    currentVorstellungPreis.setMaxLines(1);
                    if (bezahlt) {
                        currentVorstellungPreis.setTextColor(Color.parseColor("#53A653"));
                    }
                    currentVorstellungPreis.setGravity(Gravity.END);

                    if (!sumRow) {
                        try {
                            Float.parseFloat(entry[i2]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            currentVorstellungPreis.setText("-");
                            currentVorstellungPreis.setGravity(Gravity.CENTER);
                        }
                    }

                    layout.addView(currentVorstellungPreis, params);
                } else if (types[i2].getClass().toString().equals("class java.lang.String")||types[i2].getClass().toString().equals("class java.util.Date")) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weights[i2]);
                    params.leftMargin = 10;
                    TextView currentGrund = new TextView(context);
                    currentGrund.setText(entry[i2]);
                    if (bezahlt) {
                        currentGrund.setTextColor(Color.parseColor("#53A653"));
                    }
                    currentGrund.setTextSize(20);

                    if(entry[i2].contains("<<CAPTION>>")){
                        currentGrund.setText(entry[i2].split("<<CAPTION>>")[1]);
                        currentGrund.setTextSize(30);
                        currentGrund.setGravity(Gravity.CENTER);
                        layout.removeAllViews();
                        i2=anzahlFelder;
                    }

                    layout.addView(currentGrund, params);
                } else if (types[i2].getClass().toString().equals("class java.lang.Boolean")) {

                    final CheckBox bezahltBox = new CheckBox(context);

                    bezahltBox.setChecked(entry[i2].equals("TRUE"));

                    final int finali2 = i2;

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weights[i2]);
                    params.leftMargin = 10;
                    if(specials.contains(" saveOnCheckbox=true ")){
                        bezahltBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                String[] newEntry = Arrays.copyOf(entry,entry.length);

                                if(b){
                                    newEntry[finali2] = "TRUE";
                                }
                                else {
                                    newEntry[finali2] = "FALSE";
                                }
                                replace(entry,newEntry);

                                mainLayout.removeAllViews();
                                generateLayout(context);
                            }
                        });
                    }
                    layout.addView(bezahltBox);
                }
            }
        }


        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        if(prevRow!=null) {
            mainParams.addRule(RelativeLayout.BELOW, prevRow.getId());
        }


        if(!specials.contains(" disableEdit=true ")) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(context, true, entry);
                }
            });
        }


        mainLayout.addView(layout,mainParams);

        layout.setId(View.generateViewId());
        if(i==0){
            return prevRow;
        }
        return layout;
    }

    private void append(String[] newRow){
        String[][] listOld = (loadOrdered(name)!=null) ? (loadOrdered(name)) : new String[0][0];
        String[][] listNew = new String[listOld.length+1][anzahlFelder];

        for(int ie=0;ie<listOld.length;ie++){
            System.arraycopy(listOld[ie], 0, listNew[ie], 0, anzahlFelder);
        }

        listNew[listNew.length-1]=newRow;

        saveOrdered(listNew,name);
    }

    private void replace(String[] row, String[] newRow){
        String[][] list = (loadOrdered(name)!=null) ? (loadOrdered(name)) : new String[0][0];

        for(int ie=0;ie<list.length;ie++){
            int correctOnes = 0;
            for(int i=0;i<anzahlFelder;i++){
                if(list[ie][i].equals(row[i])){
                    correctOnes++;
                }
            }
            if(correctOnes==anzahlFelder){
                list[ie]=newRow;
            }
        }

        saveOrdered(list,name);
    }

    private void remove(String[] oldEntry){
        String[][] listOld = (loadOrdered(name)!=null) ? (loadOrdered(name)) : new String[0][0];
        String[][] listNew = new String[listOld.length-1][anzahlFelder];

        int substract = 0;

        for(int ie=0;ie<listOld.length;ie++){
            int correctOnes = 0;
            for(int i=0;i<anzahlFelder;i++){
                if(listOld[ie][i].equals(oldEntry[i])){
                    correctOnes++;
                }
            }
            if(!(correctOnes==anzahlFelder)){
                listNew[ie+substract]=listOld[ie];
            }
            else {
                substract--;
            }
        }

        saveOrdered(listNew,name);
    }

    private void showDialog(final Context context, final boolean overrideOld, final String[] oldEntry){

        LinearLayout dialogView = new LinearLayout(context);
        dialogView.setOrientation(LinearLayout.VERTICAL);

        final View[] editTexts = new View[types.length];
        for(int i=0;i<editTexts.length;i++){
            if(editable[i]) {
                if (types[i].getClass().toString().equals("class java.lang.Float")) {
                    EditText editText = new EditText(context);
                    editText.setSingleLine();
                    editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    editText.setHint(hints[i]);

                    if (overrideOld) {
                        editText.setText(oldEntry[i]);
                    }

                    editTexts[i] = editText;
                } else if (types[i].getClass().toString().equals("class java.lang.String")) {
                    if(((String)types[i]).contains("DatePickerFragment")){
                        TextView beschr = new TextView(context);
                        beschr.setText(hints[i]);

                        final TextView aktuell = new TextView(context);
                        if(overrideOld) {
                            aktuell.setText(oldEntry[i]);
                        }

                        Button button = new Button(context);
                        button.setText("Ändern");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
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
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    aktuell.setText(date);
                                                }
                                            });

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        });

                        LinearLayout layout = new LinearLayout(context);
                        LinearLayout.LayoutParams linpams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        layout.addView(beschr,linpams);

                        linpams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layout.addView(aktuell,linpams);
                        layout.addView(button,linpams);

                        editTexts[i] = layout;
                    }
                    else {
                        EditText editText = new EditText(context);
                        editText.setSingleLine();
                        editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                        editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
                        editText.setHint(hints[i]);

                        if (overrideOld) {
                            editText.setText(oldEntry[i]);
                        }

                        editTexts[i] = editText;
                    }
                } else if (types[i].getClass().toString().equals("class java.lang.Boolean")) {

                    final CheckBox bezahlt = new CheckBox(context);
                    final TextView bezahltBeschreibung = new TextView(context);
                    bezahltBeschreibung.setText(hints[i]);

                    if (overrideOld) {
                        if (oldEntry[i].equals("TRUE")) {
                            bezahlt.setChecked(true);
                        } else {
                            bezahlt.setChecked(false);
                        }
                    }

                    LinearLayout bezahltLayout = new LinearLayout(context);
                    bezahltLayout.addView(bezahltBeschreibung);
                    bezahltLayout.addView(bezahlt);

                    editTexts[i] = bezahltLayout;
                }

                dialogView.addView(editTexts[i]);
            }
        }


        final AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle("Was soll zu \""+listenname+"\" hinzugefügt werden?")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(overrideOld){
                            remove(oldEntry);
                        }

                        String[] result = new String[anzahlFelder];

                        NumberFormat df = NumberFormat.getNumberInstance(Locale.UK);
                        df.setMinimumFractionDigits(2);
                        df.setMaximumFractionDigits(2);
                        df.setGroupingUsed(false);

                        for(int i=0;i<editTexts.length;i++) {
                            if (types[i].getClass().toString().equals("class java.lang.Float")) {
                                try {
                                    float f = Float.parseFloat(((EditText)editTexts[i]).getText().toString());
                                    result[i]=df.format(f);
                                }
                                catch(NumberFormatException e){
                                    result[i]="-";
                                }
                            }
                            else if(types[i].getClass().toString().equals("class java.lang.String")){
                                if(((String)types[i]).contains("DatePickerFragment")){
                                    result[i] = ((TextView)((LinearLayout)editTexts[i]).getChildAt(1)).getText().toString();
                                }
                                else {
                                    String s = ((EditText) editTexts[i]).getText().toString();
                                    result[i] = s;
                                }
                            }
                            else if(types[i].getClass().toString().equals("class java.lang.Boolean")){
                                String bezahltString;
                                if(((CheckBox)((LinearLayout)editTexts[i]).getChildAt(1)).isChecked()){
                                    bezahltString = "TRUE";
                                }
                                else {
                                    bezahltString = "FALSE";
                                }
                                result[i]=bezahltString;
                            }
                        }





                        append(result);

                        mainLayout.removeAllViews();
                        generateLayout(context);
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                }).create();

        builder.show();
    }

    public void listeAdd(View view){
        showDialog(this,false,null);
    }

    private String[][] loadOrdered(String name){
        try {
            int count = 0;
            JSONArray array = new JSONArray(prefs.getString(name,"[]"));
            for (int i = 0; i < array.length(); i++) {
                count = i+1;
            }
            //count = "Menge Einträge"
            String[][] ret = new String[count][anzahlFelder];
            for (int i = 0; i < array.length(); i++) {
                for(int ie=0;ie<anzahlFelder;ie++){
                    ret[i][ie] = (String)((JSONArray)array.get(i)).get(ie);
                }
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

    private void saveOrdered(String[][] array, String name){
        JSONArray jsonarray = new JSONArray();
        for(String[] partArray : array){
            jsonarray.put(new JSONArray(Arrays.asList(partArray)));
        }
        prefs.edit().putString(name,jsonarray.toString()).apply();
    }

}