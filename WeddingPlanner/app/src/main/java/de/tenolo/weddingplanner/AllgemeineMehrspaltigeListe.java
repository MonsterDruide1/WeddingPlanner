package de.tenolo.weddingplanner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

class AllgemeineMehrspaltigeListe extends AllgemeineSuperclass {

    //0.2f -> FLOAT == GELD-BETRAG -> Summe am Ende ;;;;;; 0.1 -> DOUBLE == NORMALE KOMMA-ZAHL -> keine Summe am Ende

    private String[] captions;
    private float[] weights;

    AllgemeineMehrspaltigeListe(AllgemeinesObject object, final Context context, final Activity activity, RelativeLayout mainLayout){
        super(object, context);

        captions = object.captions;
        weights = object.weights;

        generateLayout(context, activity, mainLayout);
    }

    void generateLayout(final Context context, final Activity activity, RelativeLayout mainLayout){
        System.out.println("generating Layout");

        String[][] loadedList = loadOrdered(name);
        String[][] listold = (loadedList!=null) ? (loadedList) : new String[0][anzahlFelder];

        final String[][] list = insertDummyAndHints(listold,hints);

        View prevRow = makeCaptions(captions,context,weights,mainLayout);

        for (int i=0;i<list.length;i++) {
            String[] strings = list[i];
            prevRow = generateNewRow(strings, context, activity, prevRow, weights, i,mainLayout);
        }

        mainLayout.removeViewAt(3); //REMOVING TEMP-ROW, NOT CAPTIONS
    }

    private View makeCaptions(final String[] captions, final Context context, float[] weights, RelativeLayout mainLayout){
        System.out.println("making captions");
        View t = super.makeBigCaptions(context, mainLayout);

        LinearLayout layout = new LinearLayout(context);

        for(int i2=0;i2<anzahlFelder;i2++){
            System.out.println("creating no "+i2+", caption: "+captions[i2]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weights[i2]);
            TextView currentVorstellungPreis = new TextView(context);
            currentVorstellungPreis.setText(captions[i2]);
            currentVorstellungPreis.setTextSize(16);
            currentVorstellungPreis.setTypeface(null, Typeface.BOLD);
            currentVorstellungPreis.setMaxLines(3);

            layout.addView(currentVorstellungPreis, params);
        }


        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mainParams.addRule(RelativeLayout.BELOW,t.getId());
        mainLayout.addView(layout,mainParams);

        layout.setId(View.generateViewId());
        System.out.println("done creating captions");
        return layout;
    }

    private View generateNewRow(final String[] entry, final Context context, final Activity activity, View prevRow, float[] weights, final int i, final RelativeLayout mainLayout){
        System.out.println("Generating row "+i);

        boolean bezahlt = false;
        if(specials.contains(" bezahlt=gruen ")){
            System.out.println("BEZAHLT = GRUEN");
            bezahlt = entry[entry.length-1].equals("TRUE");
        }

        LinearLayout layout = new LinearLayout(context);

        for(int i2=0;i2<anzahlFelder;i2++){
            System.out.print("creating no "+i2+" with type "+types[i2].getClass().toString()+", ");
            if (types[i2].getClass().toString().equals("class java.lang.Float")) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weights[i2]);
                TextView currentVorstellungPreis = new TextView(context);
                String text = entry[i2] + "€";
                currentVorstellungPreis.setText(text);
                currentVorstellungPreis.setTextSize(20);
                currentVorstellungPreis.setMaxLines(1);
                if (bezahlt) {
                    currentVorstellungPreis.setTextColor(Color.parseColor("#53A653"));
                }
                currentVorstellungPreis.setGravity(Gravity.END);

                try {
                    Float.parseFloat(entry[i2]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    currentVorstellungPreis.setText("-");
                    currentVorstellungPreis.setGravity(Gravity.CENTER);
                }

                System.out.println("text: "+currentVorstellungPreis.getText());
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

                System.out.println("text: "+currentGrund.getText());
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
                            AllgemeineMehrspaltigeListe.this.replace(entry,newEntry);

                            mainLayout.removeAllViews();
                            generateLayout(context,activity,mainLayout);
                        }
                    });
                }
                System.out.println("checked: "+bezahltBox.isChecked());
                layout.addView(bezahltBox);
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
                    showDialog(context, activity, entry, i-1, mainLayout);
                }
            });
        }
        else {
            System.out.println("disableEdit=true");
        }


        mainLayout.addView(layout,mainParams);

        layout.setId(View.generateViewId());
        if(i==0){
            System.out.println("created the temp row");
            return prevRow;
        }
        System.out.println("created row");
        return layout;
    }

    void append(String[] newRow){
        String[][] loadedList = loadOrdered(name);
        String[][] listOld = (loadedList!=null) ? (loadedList) : new String[0][0];
        String[][] listNew = new String[listOld.length+1][anzahlFelder];

        for(int ie=0;ie<listOld.length;ie++){
            System.arraycopy(listOld[ie], 0, listNew[ie], 0, anzahlFelder);
        }

        listNew[listNew.length-1]=newRow;

        System.out.println(Arrays.deepToString(listOld)+" appended to "+Arrays.deepToString(listNew));

        saveOrdered(listNew,name);
    }

    void replace(String[] row, String[] newRow){
        String[][] loadedList = loadOrdered(name);
        String[][] list = (loadedList!=null) ? (loadedList) : new String[0][0];

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

        System.out.println("replaced "+Arrays.deepToString(loadedList)+" to "+Arrays.deepToString(list)+", request was replace "+Arrays.deepToString(row)+" with "+Arrays.deepToString(newRow));

        saveOrdered(list,name);
    }

    void remove(String[] oldEntry){
        String[][] loadedList = loadOrdered(name);
        String[][] listOld = (loadedList!=null) ? (loadedList) : new String[0][0];
        listOld = insertDummyAndHints(listOld,hints);
        String[][] listNew = new String[listOld.length-2][anzahlFelder];

        int substract = 0;

        for(int ie=0;ie<listOld.length;ie++){
            int correctOnes = 0;
            for(int i=0;i<anzahlFelder;i++){
                if(listOld[ie][i].equals(oldEntry[i])){
                    correctOnes++;
                }
            }
            if(correctOnes!=anzahlFelder){
                listNew[ie+substract]=listOld[ie];
            }
            else {
                substract--;
            }
        }

        saveOrdered(listNew,name);
    }

    void showDialog(final Context context, final Activity activity, final String[] oldEntry, final int i, final RelativeLayout mainLayout){

        /*boolean overrideOld = false;
        for(int ie=1;ie<oldEntry.length;ie++){ //ignore the hint-entry
            if(oldEntry[ie]!=null){
                if(!oldEntry[ie].equals("")){
                    overrideOld=true;
                }
            }
        }*/ boolean overrideOld = true;

        LinearLayout dialogView = new LinearLayout(context);
        dialogView.setOrientation(LinearLayout.VERTICAL);

        final View[] editTexts = new View[anzahlFelder];
        for(int ie=1;ie<editTexts.length;ie++){
            if (types[i].getClass().toString().equals("class java.lang.Float")) {
                EditText editText = new EditText(context);
                editText.setSingleLine();
                editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.setHint(captions[ie]);

                if (overrideOld) {
                    editText.setText(oldEntry[i]);
                }

                editTexts[ie] = editText;
            } else if (types[i].getClass().toString().equals("class java.lang.String")) {
                if(((String)types[i]).contains("DatePickerFragment")){
                    TextView beschr = new TextView(context);
                    beschr.setText(captions[ie]);

                    final TextView aktuell = new TextView(context);
                    if(overrideOld) {
                        aktuell.setText(oldEntry[ie]);
                    }

                    Button button = new Button(context);
                    String text = "Ändern";
                    button.setText(text);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Object obj= "WAITING FOR DATEPICKER";
                            final DatePickerFragment newFragment = new DatePickerFragment(obj);
                            newFragment.show(((FragmentActivity)activity).getSupportFragmentManager(),"datePicker");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        synchronized (obj) {
                                            obj.wait();
                                        }
                                        final String date = newFragment.day+"."+ newFragment.month+"."+ newFragment.year;
                                        activity.runOnUiThread(new Runnable() {
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

                    editTexts[ie] = layout;
                }
                else {
                    EditText editText = new EditText(context);
                    editText.setSingleLine();
                    editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
                    editText.setHint(captions[ie]);

                    if (overrideOld) {
                        editText.setText(oldEntry[ie]);
                    }

                    editTexts[ie] = editText;
                }
            } else if (types[i].getClass().toString().equals("class java.lang.Boolean")) {

                final CheckBox bezahlt = new CheckBox(context);
                final TextView bezahltBeschreibung = new TextView(context);
                bezahltBeschreibung.setText(captions[ie]);

                if (overrideOld) {
                    if (oldEntry[ie].equals("TRUE")) {
                        bezahlt.setChecked(true);
                    } else {
                        bezahlt.setChecked(false);
                    }
                }

                LinearLayout bezahltLayout = new LinearLayout(context);
                bezahltLayout.addView(bezahltBeschreibung);
                bezahltLayout.addView(bezahlt);

                editTexts[ie] = bezahltLayout;
            } else if (types[i].getClass().toString().equals("class java.lang.Integer")) {
                EditText editText = new EditText(context);
                editText.setSingleLine();
                editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setHint(captions[ie]);

                if (overrideOld) {
                    editText.setText(oldEntry[ie]);
                }

                editTexts[ie] = editText;
            }
            else {
                System.err.println("NOT FOUND: "+types[i].getClass().toString());
            }

            dialogView.addView(editTexts[ie]);
        }


        final boolean finalOverrideOld = overrideOld;
        final AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle("Was soll zu \""+listenname+"\" hinzugefügt werden?")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String[] result = new String[anzahlFelder];
                        result[0]=hints[i];

                        NumberFormat df = NumberFormat.getNumberInstance(Locale.UK);
                        df.setMinimumFractionDigits(2);
                        df.setMaximumFractionDigits(2);
                        df.setGroupingUsed(false);

                        for(int ie=1;ie<editTexts.length;ie++) {
                            if (types[i].getClass().toString().equals("class java.lang.Float")) {
                                try {
                                    float f = Float.parseFloat(((EditText)editTexts[ie]).getText().toString());
                                    result[ie]=df.format(f);
                                }
                                catch(NumberFormatException e){
                                    result[ie]="-";
                                }
                            }
                            else if(types[i].getClass().toString().equals("class java.lang.String")){
                                if(((String)types[i]).contains("DatePickerFragment")){
                                    result[ie] = ((TextView)((LinearLayout)editTexts[ie]).getChildAt(1)).getText().toString();
                                }
                                else {
                                    String s = ((EditText) editTexts[ie]).getText().toString();
                                    result[ie] = s;
                                }
                            }
                            else if (types[i].getClass().toString().equals("class java.lang.Integer")) {
                                try {
                                    int f = Integer.parseInt(((EditText)editTexts[ie]).getText().toString());
                                    result[ie]=f+"";
                                }
                                catch(NumberFormatException e){
                                    result[ie]="-";
                                }
                            }
                            else if(types[i].getClass().toString().equals("class java.lang.Boolean")){
                                String bezahltString;
                                if(((CheckBox)((LinearLayout)editTexts[ie]).getChildAt(1)).isChecked()){
                                    bezahltString = "TRUE";
                                }
                                else {
                                    bezahltString = "FALSE";
                                }
                                result[ie]=bezahltString;
                            }
                            else {
                                System.err.println("NOT FOUND");
                            }
                        }


                        if(finalOverrideOld){
                            replace(oldEntry,result);
                        }
                        else {
                            append(result);
                        }

                        mainLayout.removeAllViews();
                        generateLayout(context, activity, mainLayout);
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                }).create();

        builder.show();
    }

    private String[][] loadOrdered(String name){
        try {
            int count = 0;
            JSONArray array = new JSONArray(prefs.getString(name,"[]"));
            for (int i = 0; i < array.length(); i++) {
                count = i+1;
            }
            //count = "Menge Einträge"
            if(count != hints.length){
                String[][] ret = new String[hints.length][anzahlFelder];
                for(int i=0;i<ret.length;i++){
                    Arrays.fill(ret[i],"");
                    ret[i][0]=hints[i];
                }
                return ret;
            }
            System.out.println(array.toString());
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

    /*private String[][] insertDummyAndHints(String[][] listold, String[] hints){
        final String[][] list = new String[Math.max(listold.length,hints.length)+1][anzahlFelder+1];

        list[0] = new String[anzahlFelder+1];
        for(int i=0;i<anzahlFelder+1;i++){  //DELETING THIS ROW LATER -> Nebeneinander stimmt wieder
            list[0][i] = "0";
        }

        int ie;
        for(ie=0;ie<listold.length;ie++){
            list[ie+1][0]=hints[ie];
            System.arraycopy(listold[ie], 0, list[ie+1], 1, anzahlFelder);
        }

        for(;ie<hints.length;ie++){
            list[ie+1][0]=hints[ie];
            for(int i=1;i<anzahlFelder+1;i++){
                list[ie+1][i]="";
            }
        }

        return list;
    }*/
    private String[][] insertDummyAndHints(String[][] listold, String[] hints){
        final String[][] list = new String[listold.length+1][anzahlFelder];

        list[0] = new String[anzahlFelder];
        for(int i=0;i<anzahlFelder;i++){  //DELETING THIS ROW LATER -> Nebeneinander stimmt wieder
            list[0][i] = "0";
        }

        for(int ie=0;ie<listold.length;ie++){
            System.arraycopy(listold[ie], 0, list[ie+1], 0, anzahlFelder);
        }

        return list;
    }

}
