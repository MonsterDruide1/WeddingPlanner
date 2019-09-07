package de.tenolo.weddingplanner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

public class AllgemeineListe extends AllgemeineSuperclass {

    //0.2f -> FLOAT == GELD-BETRAG -> Summe am Ende ;;;;;; 0.1 -> DOUBLE == NORMALE KOMMA-ZAHL -> keine Summe am Ende


    public AllgemeineListe(AllgemeinesObject object, Context context, Activity activity, RelativeLayout mainLayout){
        super(object, context);

        generateLayout(context,activity,mainLayout);
    }

    void generateLayout(final Context context, final Activity activity, RelativeLayout mainLayout){
        String[] list = (loadOrdered(name)!=null) ? (loadOrdered(name)) : new String[0];
        System.out.println(Arrays.deepToString(list));

        View prevRow = makeCaptions(context,mainLayout);

        for (int i=0;i<hints.length;i++) {
            try {
                String strings = list[i];
                prevRow = generateNewRow(strings,activity,context, prevRow, i,mainLayout);
            }
            catch(ArrayIndexOutOfBoundsException ignored){
                prevRow = generateNewRow("",activity,context,prevRow,i,mainLayout);
            }
        }
    }

    private View makeCaptions(final Context context, RelativeLayout mainLayout){
        return super.makeBigCaptions(context,mainLayout);
    }

    private View generateNewRow(final String entry, final Activity activity, final Context context, View prevRow, final int i, final RelativeLayout mainLayout){

        boolean bezahlt = false;
        if(specials.contains(" bezahlt=gruen ")){
            bezahlt = entry.equals("TRUE");
        }

        LinearLayout layout = new LinearLayout(context);

        for(int i2=0;i2<anzahlFelder;i2++){
            if(true) {
                if(i2==0){
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = 50;
                    TextView currentGrund = new TextView(context);
                    currentGrund.setText(hints[i]);
                    if (bezahlt) {
                        currentGrund.setTextColor(Color.parseColor("#53A653"));
                    }
                    currentGrund.setTextSize(20);

                    if (hints[i2].contains("<<CAPTION>>")) {
                        currentGrund.setText(hints[i2].split("<<CAPTION>>")[1]);
                        currentGrund.setTextSize(30);
                        currentGrund.setGravity(Gravity.CENTER);
                        layout.removeAllViews();
                        i2 = anzahlFelder;
                    }


                    layout.addView(currentGrund, params);
                }
                else {
                    System.out.println(Arrays.deepToString(types));
                    if (types[i2].getClass().toString().equals("class java.lang.Float")) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = 50;
                        TextView currentVorstellungPreis = new TextView(context);
                        currentVorstellungPreis.setText(entry + "€");
                        currentVorstellungPreis.setTextSize(20);
                        currentVorstellungPreis.setMaxLines(1);
                        if (bezahlt) {
                            currentVorstellungPreis.setTextColor(Color.parseColor("#53A653"));
                        }
                        currentVorstellungPreis.setGravity(Gravity.END);

                        layout.addView(currentVorstellungPreis, params);
                    } else if (types[i2].getClass().toString().equals("class java.lang.Double")) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = 50;
                        TextView currentVorstellungPreis = new TextView(context);
                        currentVorstellungPreis.setText(entry);
                        currentVorstellungPreis.setTextSize(20);
                        currentVorstellungPreis.setMaxLines(1);
                        if (bezahlt) {
                            currentVorstellungPreis.setTextColor(Color.parseColor("#53A653"));
                        }
                        currentVorstellungPreis.setGravity(Gravity.END);

                        layout.addView(currentVorstellungPreis, params);
                    } else if (types[i2].getClass().toString().equals("class java.lang.Integer")) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = 50;
                        TextView currentVorstellungPreis = new TextView(context);
                        currentVorstellungPreis.setText(entry);
                        currentVorstellungPreis.setTextSize(20);
                        currentVorstellungPreis.setMaxLines(1);
                        if (bezahlt) {
                            currentVorstellungPreis.setTextColor(Color.parseColor("#53A653"));
                        }
                        currentVorstellungPreis.setGravity(Gravity.END);

                        layout.addView(currentVorstellungPreis, params);
                    } else if (types[i2].getClass().toString().equals("class java.lang.String") || types[i2].getClass().toString().equals("class java.util.Date")) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0);
                        params.leftMargin = 50;
                        params.leftMargin = 10;
                        TextView currentGrund = new TextView(context);
                        currentGrund.setText(entry);
                        if (bezahlt) {
                            currentGrund.setTextColor(Color.parseColor("#53A653"));
                        }
                        currentGrund.setTextSize(20);

                        if (entry.contains("<<CAPTION>>")) {
                            currentGrund.setText(entry.split("<<CAPTION>>")[1]);
                            currentGrund.setTextSize(30);
                            currentGrund.setGravity(Gravity.CENTER);
                            layout.removeAllViews();
                            i2 = anzahlFelder;
                        }

                        layout.addView(currentGrund, params);
                    } else if (types[i2].getClass().toString().equals("class java.lang.Boolean")) {

                        final CheckBox bezahltBox = new CheckBox(context);

                        bezahltBox.setChecked(entry.equals("TRUE"));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = 50;
                        if (specials.contains(" saveOnCheckbox=true ")) {
                            bezahltBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if (b) {
                                        replace(entry, "TRUE");
                                    } else {
                                        replace(entry, "FALSE");
                                    }

                                    mainLayout.removeAllViews();
                                    generateLayout(context,activity,mainLayout);
                                }
                            });
                        }
                        layout.addView(bezahltBox);
                    }
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
                    showDialog(context, activity, entry,i,mainLayout);
                }
            });
        }


        mainLayout.addView(layout,mainParams);

        layout.setId(View.generateViewId());
        return layout;
    }

    private void append(String newRow){
        String[] listOld = (loadOrdered(name)!=null) ? (loadOrdered(name)) : new String[0];
        String[] listNew = new String[listOld.length+1];

        for(int ie=0;ie<listOld.length;ie++){
            System.arraycopy(listOld, 0, listNew, 0, listOld.length);
        }

        listNew[listNew.length-1]=newRow;

        saveOrdered(listNew,name);
    }

    private void replace(String row, String newRow){
        String[] list = (loadOrdered(name)!=null) ? (loadOrdered(name)) : new String[0];

        for(int ie=0;ie<list.length;ie++){
            if(list[ie].equals(row)){
                list[ie]=newRow;
            }
        }

        saveOrdered(list,name);
    }

    private void showDialog(final Context context, final Activity activity, final String oldEntry, final int ie, final RelativeLayout mainLayout){
        boolean overrideOld=true;
        if(oldEntry.equals("")){
            overrideOld=false;
        }

        LinearLayout dialogView = new LinearLayout(context);
        dialogView.setOrientation(LinearLayout.VERTICAL);

        final View[] editTexts = new View[anzahlFelder];
        final int i=1;
        if (types[ie].getClass().toString().equals("class java.lang.Float")) {
            EditText editText = new EditText(context);
            editText.setSingleLine();
            editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setHint(hints[ie]);

            if (overrideOld) {
                editText.setText(oldEntry);
            }

            editTexts[i] = editText;
        } else if (types[ie].getClass().toString().equals("class java.lang.Double")) {
            EditText editText = new EditText(context);
            editText.setSingleLine();
            editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setHint(hints[ie]);

            if (overrideOld) {
                editText.setText(oldEntry);
            }

            editTexts[i] = editText;
        } else if (types[ie].getClass().toString().equals("class java.lang.Integer")) {
            EditText editText = new EditText(context);
            editText.setSingleLine();
            editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setHint(hints[ie]);

            if (overrideOld) {
                editText.setText(oldEntry);
            }

            editTexts[i] = editText;
        } else if (types[ie].getClass().toString().equals("class java.lang.String")) {
            if(((String)types[ie]).contains("DatePickerFragment")){
                TextView beschr = new TextView(context);
                beschr.setText(hints[ie]);

                final TextView aktuell = new TextView(context);
                if(overrideOld) {
                    aktuell.setText(oldEntry);
                }

                Button button = new Button(context);
                button.setText("Ändern");
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

                editTexts[i] = layout;
            }
            else {
                EditText editText = new EditText(context);
                editText.setSingleLine();
                editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
                editText.setHint(hints[ie]);

                if (overrideOld) {
                    editText.setText(oldEntry);
                }

                editTexts[i] = editText;
            }
        } else if (types[ie].getClass().toString().equals("class java.lang.Boolean")) {

            final CheckBox bezahlt = new CheckBox(context);
            final TextView bezahltBeschreibung = new TextView(context);
            bezahltBeschreibung.setText(hints[ie]);

            if (overrideOld) {
                if (oldEntry.equals("TRUE")) {
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

        System.out.println("TYPE: "+types[ie].getClass().toString());

        dialogView.addView(editTexts[i]);


        final boolean finalOverrideOld = overrideOld;
        final AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle("Was soll zu \""+listenname+"\" hinzugefügt werden?")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String result = "";

                        NumberFormat df = NumberFormat.getNumberInstance(Locale.UK);
                        df.setMinimumFractionDigits(2);
                        df.setMaximumFractionDigits(2);
                        df.setGroupingUsed(false);

                        if (types[ie].getClass().toString().equals("class java.lang.Float")) {
                            try {
                                float f = Float.parseFloat(((EditText)editTexts[i]).getText().toString());
                                result=df.format(f);
                            }
                            catch(NumberFormatException e){
                                result="-";
                            }
                        }
                        else if (types[ie].getClass().toString().equals("class java.lang.Double")) {
                            try {
                                float f = Float.parseFloat(((EditText)editTexts[i]).getText().toString());
                                result=df.format(f);
                            }
                            catch(NumberFormatException e){
                                result="-";
                            }
                        }
                        else if (types[ie].getClass().toString().equals("class java.lang.Integer")) {
                            try {
                                int f = Integer.parseInt(((EditText)editTexts[i]).getText().toString());
                                result=f+"";
                            }
                            catch(NumberFormatException e){
                                result="-";
                            }
                        }
                        else if(types[ie].getClass().toString().equals("class java.lang.String")){
                            if(((String)types[ie]).contains("DatePickerFragment")){
                                result = ((TextView)((LinearLayout)editTexts[i]).getChildAt(1)).getText().toString();
                            }
                            else {
                                String s = ((EditText) editTexts[i]).getText().toString();
                                result = s;
                            }
                        }
                        else if(types[ie].getClass().toString().equals("class java.lang.Boolean")){
                            String bezahltString;
                            if(((CheckBox)((LinearLayout)editTexts[i]).getChildAt(1)).isChecked()){
                                bezahltString = "TRUE";
                            }
                            else {
                                bezahltString = "FALSE";
                            }
                            result=bezahltString;
                        }

                        if(finalOverrideOld){
                            replace(oldEntry,result);
                        }
                        else {
                            append(result);
                        }

                        mainLayout.removeAllViews();
                        generateLayout(context,activity,mainLayout);
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                }).create();

        builder.show();
    }

    private String[] loadOrdered(String name){
        try {
            int count = 0;
            JSONArray array = new JSONArray(prefs.getString(name,null));
            for (int i = 0; i < array.length(); i++) {
                count = i;
            }
            String[] ret = new String[count+1];
            for (int i = 0; i < array.length(); i++) {
                System.out.println("OBJECT: "+array.get(i)+", ARRAY: "+array);
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

    private void saveOrdered(String[] array, String name){
        JSONArray jsonarray = new JSONArray();
        for(String s : array){
            jsonarray.put(s);
        }
        prefs.edit().putString(name,jsonarray.toString()).apply();
    }

}
