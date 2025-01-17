package de.tenolo.weddingplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Methoden {

    private DrawerLayout drawerLayout;
    private String currentNav;
    private int currentGroupPos=0;
    private Map<String,List<String>> expandableListData;

    void onCreateFillIn(AppCompatActivity currentActivity, String currentNav, int layout){
        currentActivity.setContentView(R.layout.all_main);

        Toolbar toolbar = currentActivity.findViewById(R.id.toolbar);
        currentActivity.setSupportActionBar(toolbar);

        drawerLayout = currentActivity.findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                currentActivity, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ViewStub stub = currentActivity.findViewById(R.id.all_content);
        stub.setLayoutResource(layout);
        stub.inflate();



        this.currentNav=currentNav;

        final ExpandableListView expandableListView = currentActivity.findViewById(R.id.navList);

        expandableListData = getNavigationMap();
        ArrayList expandableListTitle = new ArrayList(expandableListData.keySet());

        addDrawerItems(expandableListView,currentActivity,expandableListTitle,expandableListData);
        setupDrawer(currentActivity);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            int lastPosition = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastPosition != -1
                        && groupPosition != lastPosition) {
                    expandableListView.collapseGroup(lastPosition);
                }
                lastPosition = groupPosition;
            }
        });
    }

    private void onNavigationItemSelectedFillIn(String selectedItem, int groupPosition,Activity currentActivity){
        final int PosVIP=0;
        final int PosZeitplan=1;
        final int PosLocation=2;
        final int PosTagesablauf=3;
        final int PosGaeste=4;
        final int PosEssen=5;
        final int PosErinnerungen=6;
        final int PosDressUp=7;
        final int PosDrucke=8;
        final int PosFlowerpower=9;
        final int PosSonstiges=10;
        final int PosUeberblick=11;
        final int PosEinstellungen=12;

        boolean found = true;
        switch (groupPosition){
            case PosVIP:
                switch (selectedItem){
                    case "VIPS":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[7];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", new DatePickerFragment(), new DatePickerFragment(), "", "",0,0};
                                String[] hints = new String[]{"Namen", "Zusammen seit", "Wunsch Hochzeitsdatum", "Eure größten Träume", "Was ihr euch für die Zukunft wünscht","Ringgröße Braut", "Ringgröße Bräutigam"};
                                String listenname = "Braut & Bräutigam";
                                int anzahlFelder = 2;
                                String name = "braut_braeutigam";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }
                            {
                                Object[] types = new Object[]{"", new DatePickerFragment(), ""};
                                String[] hints = new String[]{"Name", "Geboren am", "Telefonnummer"};
                                String listenname = "Trauzeugin";
                                int anzahlFelder = 2;
                                String name = "trauzeugin";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[1]=object;
                            }
                            {
                                Object[] types = new Object[]{"", new DatePickerFragment(), ""};
                                String[] hints = new String[]{"Name", "Geboren am", "Telefonnummer"};
                                String listenname = "Brautjungfern";
                                int anzahlFelder = 2;
                                String name = "brautjungfern";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[2]=object;
                            }
                            {
                                Object[] types = new Object[]{"", new DatePickerFragment(), ""};
                                String[] hints = new String[]{"Name", "Geboren am", "Telefonnummer"};
                                String listenname = "Trauzeuge";
                                int anzahlFelder = 2;
                                String name = "trauzeuge";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[3]=object;
                            }
                            {
                                Object[] types = new Object[]{"", new DatePickerFragment(), ""};
                                String[] hints = new String[]{"Name", "Geboren am", "Telefonnummer"};
                                String listenname = "Groosmen";
                                int anzahlFelder = 2;
                                String name = "groosmen";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[4]=object;
                            }
                            {
                                Object[] types = new Object[]{"", new DatePickerFragment(), ""};
                                String[] hints = new String[]{"Name", "Geboren am", "Telefonnummer"};
                                String listenname = "Blumenkind/-er";
                                int anzahlFelder = 2;
                                String name = "blumenkinder";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[5]=object;
                            }
                            {
                                Object[] types = new Object[]{"", new DatePickerFragment(), ""};
                                String[] hints = new String[]{"Name", "Geboren am", "Telefonnummer"};
                                String listenname = "Ringbringer/-in";
                                int anzahlFelder = 2;
                                String name = "ringbringer";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[6]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe","AllgemeineListe","AllgemeineListe","AllgemeineListe","AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","VIPS");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                }
                break;
            case PosZeitplan:
                switch (selectedItem){
                    case "Zeitplan":
                        if (!selectedItem.equals(currentNav)) {
                            Object[] types = new Object[]{false,""};
                            String[] hints = new String[]{"",""};
                            boolean[] editable = new boolean[]{true,false};
                            boolean[] shown = new boolean[]{true,true};
                            String listenname = "Zeitplan";
                            int anzahlFelder = 2;
                            String name = "zeitplan";
                            String specials = " disableNew=true disableEdit=true saveOnCheckbox=true ";
                            float[] weights = new float[]{0.2f,1f};
                            String groupName ="";

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Zeitplan");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "To do":
                        if ((!selectedItem.equals(currentNav)) || (!(groupPosition==currentGroupPos))) {
                            Object[] types = new Object[]{"",new DatePickerFragment(),false};
                            String[] hints = new String[]{"To do","Bis wann?","Erledigt?"};
                            boolean[] editable = new boolean[]{true,true,true};
                            boolean[] shown = new boolean[]{true,true,false};
                            String listenname = "ToDo-Liste";
                            int anzahlFelder = 3;
                            String name = "todoliste";
                            String specials = " bezahlt=gruen ";
                            float[] weights = new float[]{1f,0.5f};
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","To do");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Wer hilft mit?":
                        if (!selectedItem.equals(currentNav)) {
                            Object[] types = new Object[]{"",""};
                            String[] hints = new String[]{"Wer","Aufgabe"};
                            boolean[] editable = new boolean[]{true,true};
                            boolean[] shown = new boolean[]{true,true};
                            String listenname = "Wer hilft mit?";
                            int anzahlFelder = 2;
                            String name = "wer_hilft_mit";
                            String specials = "";
                            float[] weights = new float[]{1f,1f};
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Wer hilft mit?");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                }
                break;
            case PosLocation:
                switch (selectedItem){
                    case "Location Angebote":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[3];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            for(int i=1;i<=parcels.length;i++){
                                Object[] types = new Object[]{"", "", "", 0.2f, true, true, true, true, 0, true, "", "", ""};
                                String[] hints = new String[]{"Ansprechpartner", "Telefonnummer", "Adresse", "Preis", "Service/Personal inklusive?", "Catering inklusive?", "Platz für einen DJ/Band?",
                                        "Platz für eine Tanzfläche?","Sitzplätze drinnen/draußen", "Parkmöglichkeit ausreichend?", "Regeln & Verbote", "Vorteile", "Nachteile"};
                                String listenname = "Location Angebot "+i;
                                int anzahlFelder = 2;
                                String name = "location_angebot_"+i;
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[i-1]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Location Angebote");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Raumplanung":
                        if (!selectedItem.equals(currentNav)) {
                            currentActivity.startActivity(new Intent(currentActivity, TischordnungSelector.class));
                        }
                        break;
                    case "Dekowünsche":
                        if (!selectedItem.equals(currentNav)) {
                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            String[] strings = new String[]{"Trauung","Party"};
                            Parcelable[] parcels = new Parcelable[strings.length+1];
                            int i=0;
                            for(String string : strings){
                                Object[] types = new Object[]{"", "", "", "", "", ""};
                                String[] hints = new String[]{"Blumen", "Schleifen", "Hussen", "Kerzen", "Lichter", "Sonstiges"};
                                String listenname = "Dekowünsche"+string;
                                int anzahlFelder = 2;
                                String name = "dekowuensche"+string;
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[i]=object;
                                i++;
                            }

                            {
                                Object[] types = new Object[]{""};
                                String[] hints = new String[]{"Notizen/Sonstiges"};
                                String listenname = "Notizen/Sonstiges";
                                int anzahlFelder = 2;
                                String name = "dekowuensche"+"_notizen";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[i]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Dekowünsche");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    //BESORGUNGEN
                    case "Unterhaltungsprogramm":
                        if (!selectedItem.equals(currentNav)) {
                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            Parcelable[] parcels = new Parcelable[1];

                            {
                                Object[] types = new Object[]{""};
                                String[] hints = new String[]{"Wünsche & Ideen"};
                                String listenname = "Unterhaltungsprogramm";
                                int anzahlFelder = 2;
                                String name = "unterhaltungsprogramm";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Unterhaltungsprogramm");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                    //BudgetÜbersicht
                }
                break;
            case PosTagesablauf:
                switch (selectedItem){
                    case "Startklar machen":
                        if (!selectedItem.equals(currentNav)) {
                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            Parcelable[] parcels = new Parcelable[2];

                            {
                                Object[] types = new Object[]{"","",""};
                                String[] hints = new String[]{"Wann & Wo","Uhrzeit","Abfahrt zur Trauung"};
                                String listenname = "Startklar machen";
                                int anzahlFelder = 2;
                                String name = "startklar_machen";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }
                            {
                                Object[] types = new Object[]{"",""};
                                String[] hints = new String[]{"Name","Sonstiges"};
                                boolean[] editable = new boolean[]{true,true};
                                boolean[] shown = new boolean[]{true,true};
                                String listenname = "Wer ist dabei?";
                                int anzahlFelder = 2;
                                String name = "startklar_wer_dabei";
                                String specials = "";
                                float[] weights = new float[]{1f,1f};
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                                parcels[1]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineTabelle"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Startklar machen");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Trauung":
                        if (!selectedItem.equals(currentNav)) {
                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            Parcelable[] parcels = new Parcelable[1];

                            {
                                Object[] types = new Object[]{new DatePickerFragment(),"","",0,""};
                                String[] hints = new String[]{"Trautermin","Uhrzeit","Adresse","Wie viele Personen?","Parkmöglichkeiten"};
                                String[] captions = new String[]{"Standesamt","Trauzeremonie"};
                                String listenname = "Trauung";
                                int anzahlFelder = 2;
                                String name = "trauung";
                                String specials = " disableNew=true ";
                                float[] weights = new float[]{0.5f,1f,1f};
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),false,hints,captions,listenname,anzahlFelder,name,specials,weights,groupName);

                                parcels[0]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineMehrspaltigeListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Trauung");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    //BESORGUNGEN
                    case "Hochzeitsfahrzeug Angebote":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[2];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            for(int i=1;i<=parcels.length;i++){
                                Object[] types = new Object[]{"", "", 0.2f, ""};
                                String[] hints = new String[]{"Art des Fahrzeugs", "Anbieter", "Preis", "Fahrer inklusive?", "Wartezeiten inklusive?", "Allgemeine Infos/Notizen"};
                                String listenname = "Hochzeitsfahrzeug Angebot "+i;
                                int anzahlFelder = 2;
                                String name = "hochzeitsfahrzeug_angebot_"+i;
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[i-1]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Location Angebote");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                    //BudgetÜbersicht
                }
                break;
            case PosGaeste:
                switch (selectedItem){
                    case "Gästeliste":
                        if (!selectedItem.equals(currentNav)) {
                            currentActivity.startActivity(new Intent(currentActivity, Gaesteliste.class));
                        }
                        break;
                    case "Gastgeschenke":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[3];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", "", new DatePickerFragment(), new DatePickerFragment()};
                                String[] hints = new String[]{"Was?", "Wo zu kaufen?", "Bestellt am?", "Lieferung am?"};
                                String listenname = "Gastgeschenke";
                                int anzahlFelder = 2;
                                String name = "gastgeschenke";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }
                            {
                                Object[] types = new Object[]{""};
                                String[] hints = new String[]{"Notizen"};
                                String listenname = "Notizen";
                                int anzahlFelder = 2;
                                String name = "notizen";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[1]=object;
                            }
                            {
                                Object[] types = new Object[]{0.2f, 0.2f};
                                String[] hints = new String[]{"Preis pro Gast","Preis Gesamt"};
                                String listenname = "Gastgeschenke - Preis";
                                int anzahlFelder = 2;
                                String name = "gastgeschenke_preis";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[2]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Gastgeschenke");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Übernachtungsgäste":
                        if (!selectedItem.equals(currentNav)) {
                            Object[] types = new Object[]{"","","",0.2f};
                            String[] hints = new String[]{"Wer?","Wo?","Aufenthaltsdauer?","Kosten"};
                            boolean[] editable = new boolean[]{true,true,true,true};
                            boolean[] shown = new boolean[]{true,true,true,true};
                            float[] weights = new float[]{1f,1f,0.8f,0.5f};
                            String listenname = "Übernachtungsgäste";
                            int anzahlFelder = 4;
                            String name = "uebernachtungsgaeste";
                            String specials = "";
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Übernachtungsgäste");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Was wir uns wünschen":
                        if (!selectedItem.equals(currentNav)) {
                            Object[] types = new Object[]{"","",0.2f};
                            String[] hints = new String[]{"Was?","Wo zu kaufen?","€"};
                            boolean[] editable = new boolean[]{true,true,true};
                            boolean[] shown = new boolean[]{true,true,true};
                            float[] weights = new float[]{1f,1f,0.5f};
                            String listenname = "Was wir uns wünschen";
                            int anzahlFelder = 3;
                            String name = "was_wir_uns_wuenschen";
                            String specials = "";
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Was wir uns wünschen");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                    //Budgetübersicht
                }
                break;
            case PosEssen:
                switch (selectedItem){
                    case "Sektempfang / Candybar":
                        if ((!selectedItem.equals(currentNav)) || (!(groupPosition==currentGroupPos))) {
                            Object[] types = new Object[]{"","",0,0.2f,false};
                            String[] hints = new String[]{"Was?","Wer?","Anzahl","€","erledigt?"};
                            boolean[] editable = new boolean[]{true,true,true,true,true};
                            boolean[] shown = new boolean[]{true,true,true,true,false};
                            String listenname = "Sektempfang / Candybar";
                            int anzahlFelder = 5;
                            String name = "sektempfang";
                            System.out.println(name);
                            String specials = " bezahlt=gruen ";
                            float[] weights = new float[]{0.5f,1f,0.5f};
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Sektempfang / Candybar");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Wer bringt was mit?":
                        if ((!selectedItem.equals(currentNav))) {
                            Object[] types = new Object[]{"","",""};
                            String[] hints = new String[]{"Was?","Wer?","Kontakt"};
                            boolean[] editable = new boolean[]{true,true,true};
                            boolean[] shown = new boolean[]{true,true,true};
                            String listenname = "Wer bringt was mit?";
                            int anzahlFelder = 3;
                            String name = "wer_bringt_was_mit";
                            String specials = " bezahlt=gruen ";
                            float[] weights = new float[]{1f,1f,1f};
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Besorgungen");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Menüplanung":
                        if (!selectedItem.equals(currentNav)) {

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);
                            Parcelable[] parcels = new Parcelable[7];

                            {
                                Object[] types = new Object[]{"",0,0,0,""};
                                String[] hints = new String[]{"Wünsche & Ideen","Anzahl der Gäste","davon Kinder","Vegetarier, Veganer","Unverträglichkeiten"};
                                String listenname = "Menüplanung";
                                int anzahlFelder = 2;
                                String name = "menueplanung";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }
                            {
                                Object[] types = new Object[]{"","","",0.2f,"","","",""};
                                String[] hints = new String[]{"Ansprechpartner","Telefonnummer","Adresse","Preis","Angebot","Verpflegung für DJ etc. bedacht?","Ansprechpartner vor Ort","Preis & Leistung"};
                                String listenname = "Caterer";
                                int anzahlFelder = 2;
                                String name = "caterer";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[1]=object;
                            }
                            {
                                Object[] types = new Object[]{"","",""};
                                String[] hints = new String[]{"Variation 1","Variation 2","Variation 3"};
                                boolean[] editable = new boolean[]{true,true,true};
                                boolean[] shown = new boolean[]{true,true,true};
                                float[] weights = new float[]{1f,1f,1f};
                                String listenname = "Aperitifs";
                                int anzahlFelder = 3;
                                String name = "aperitifs";
                                String specials = "";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                                parcels[2]=object;
                            }
                            {
                                Object[] types = new Object[]{"","",""};
                                String[] hints = new String[]{"Variation 1","Variation 2","Variation 3"};
                                boolean[] editable = new boolean[]{true,true,true};
                                boolean[] shown = new boolean[]{true,true,true};
                                float[] weights = new float[]{1f,1f,1f};
                                String listenname = "Hauptspeisen";
                                int anzahlFelder = 3;
                                String name = "hauptspeisen";
                                String specials = "";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                                parcels[3]=object;
                            }
                            {
                                Object[] types = new Object[]{"","",""};
                                String[] hints = new String[]{"Variation 1","Variation 2","Variation 3"};
                                boolean[] editable = new boolean[]{true,true,true};
                                boolean[] shown = new boolean[]{true,true,true};
                                float[] weights = new float[]{1f,1f,1f};
                                String listenname = "Desserts";
                                int anzahlFelder = 3;
                                String name = "desserts";
                                String specials = "";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                                parcels[4]=object;
                            }
                            {
                                Object[] types = new Object[]{""};
                                String[] hints = new String[]{"Mitternachtssnack"};
                                String listenname = "Mitternachtssnack";
                                int anzahlFelder = 2;
                                String name = "mitternachtssnack";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[5]=object;
                            }
                            {
                                Object[] types = new Object[]{"","",""};
                                String[] hints = new String[]{"Alkoholfreie Getränke","Alkoholische Getränke","Heißgetränke"};
                                boolean[] editable = new boolean[]{true,true,true};
                                boolean[] shown = new boolean[]{true,true,true};
                                float[] weights = new float[]{1f,1f,1f};
                                String listenname = "Getränke";
                                int anzahlFelder = 3;
                                String name = "getraenke";
                                String specials = "";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                                parcels[4]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe","AllgemeineTabelle","AllgemeineTabelle","AllgemeineTabelle"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Menüplanung");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Hochzeitstorte":
                        if (!selectedItem.equals(currentNav)) {
                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);
                            Parcelable[] parcels = new Parcelable[2];

                            {
                                Object[] types = new Object[]{"", 0, 0, 0, ""};
                                String[] hints = new String[]{"Wünsche & Ideen", "Anzahl der Gäste", "davon Kinder", "Vegetarier, Veganer", "Unverträglichkeiten"};
                                String listenname = "Hochzeitstorte";
                                int anzahlFelder = 2;
                                String name = "hochzeitstorte";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(), hints, listenname, anzahlFelder, name, specials, groupName);

                                parcels[0]=object;
                            }
                            {
                                Object[] types = new Object[]{"", "", "",0.2f, true, "","",""};
                                String[] hints = new String[]{"Ansprechpartner", "Telefonnummer", "Adresse", "Preis", "Kühlung nötig?","Ansprechpartner vor Ort","Preis & Leistung","Lieferung oder Abholung?"};
                                String listenname = "Hochzeitstorte";
                                int anzahlFelder = 2;
                                String name = "hochzeitstorte";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(), hints, listenname, anzahlFelder, name, specials, groupName);

                                parcels[1]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Hochzeitstorte");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                }
                break;
            case PosErinnerungen:
                switch (selectedItem){
                    case "Die wichtigsten Infos":
                        if (!selectedItem.equals(currentNav)) {
                            Object[] types = new Object[]{"","","","",""};
                            String[] hints = new String[]{"Lied für den Hochzeitstanz","Uhrzeit Hochzeitstanz","Lied für Vater/Tochtertanz","Generelle Musikrichtung","Notizen"};
                            String listenname = "Die wichtigsten Infos";
                            int anzahlFelder = 2;
                            String name = "die_wichtigsten_infos";
                            String specials = " disableNew=true ";
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Die wichtigsten Infos");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Location Angebote":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[3];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            for(int i=1;i<=parcels.length;i++){
                                Object[] types = new Object[]{"", "", 0.2f, "", true};
                                String[] hints = new String[]{"Band oder DJ?", "Adresse/Kontakt", "Preis", "Spielzeiten", "Service inklusive?", "Allgemeine Infos/Notizen"};
                                String listenname = "Musik Angebot "+i;
                                int anzahlFelder = 2;
                                String name = "musik_angebot_"+i;
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[i-1]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Musik Angebote");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Musikwünsche":
                        if ((!selectedItem.equals(currentNav)) || (!(groupPosition==currentGroupPos))) {
                            Object[] types = new Object[]{"",""};
                            String[] hints = new String[]{"Titel","Interpret"};
                            boolean[] editable = new boolean[]{true,true};
                            boolean[] shown = new boolean[]{true,true};
                            String listenname = "Musikwünsche";
                            int anzahlFelder = 2;
                            String name = "musikwuensche";
                            System.out.println(name);
                            String specials = "";
                            float[] weights = new float[]{1f,1f};
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Musikwünsche");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "No-Go Lieder":
                        if ((!selectedItem.equals(currentNav)) || (!(groupPosition==currentGroupPos))) {
                            Object[] types = new Object[]{"",""};
                            String[] hints = new String[]{"Titel","Interpret"};
                            boolean[] editable = new boolean[]{true,true};
                            boolean[] shown = new boolean[]{true,true};
                            String listenname = "No-Go Lieder";
                            int anzahlFelder = 2;
                            String name = "no-go_lieder";
                            String specials = "";
                            float[] weights = new float[]{1f,1f};
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","No-Go Lieder");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Fotos":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", "", new DatePickerFragment(), "",""};
                                String[] hints = new String[]{"Fotograf", "Kontakt", "Termin", "Preis & Leistung", "Mögliche Fragen"};
                                String listenname = "Fotos";
                                int anzahlFelder = 2;
                                String name = "fotos";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Fotos");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Videos":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", "", new DatePickerFragment(), "",""};
                                String[] hints = new String[]{"Videograf", "Kontakt", "Termin", "Preis & Leistung", "Mögliche Fragen"};
                                String listenname = "Video";
                                int anzahlFelder = 2;
                                String name = "video";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Videos");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                    //BudgetÜbersicht
                }
                break;
            case PosDressUp:
                switch (selectedItem){
                    case "Brauthäuser":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[3];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            for(int i=1;i<=parcels.length;i++){
                                Object[] types = new Object[]{"", "", "", new DatePickerFragment()};
                                String[] hints = new String[]{"Name der Botique", "Adresse", "Kontakt", "Termin"};
                                String listenname = "Brauthaus "+i;
                                int anzahlFelder = 2;
                                String name = "brauthaus_"+i;
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[i-1]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Brauthäuser");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Brautmessen":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[3];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            for(int i=1;i<=parcels.length;i++){
                                Object[] types = new Object[]{"", "", "", new DatePickerFragment()};
                                String[] hints = new String[]{"Name der Messe", "Adresse", "Designer", "Termin"};
                                String listenname = "Brautmesse "+i;
                                int anzahlFelder = 2;
                                String name = "brautmesse_"+i;
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[i-1]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Brautmessen");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Haare & Make-up":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[2];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", "", "", 0.2f, new DatePickerFragment(), "", new DatePickerFragment(), ""};
                                String[] hints = new String[]{"Friseur", "Adresse", "Kontakt", "Kosten", "Probetermin", "Uhrzeit", "Hochzeitstermin", "Uhrzeit"};
                                String listenname = "Haare";
                                int anzahlFelder = 2;
                                String name = "haare";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }
                            {
                                Object[] types = new Object[]{"", "", "", 0.2f, new DatePickerFragment(), "", new DatePickerFragment(), ""};
                                String[] hints = new String[]{"Stylist", "Adresse", "Kontakt", "Kosten", "Probetermin", "Uhrzeit", "Hochzeitstermin", "Uhrzeit"};
                                String listenname = "Make-up";
                                int anzahlFelder = 2;
                                String name = "makeup";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[1]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe","AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Haare & Make-up");

                            currentActivity.startActivity(intent);
                        }
                        break;

                        //TODO Alles beisammen?

                    case "Traditionen & Bräuche":
                        if ((!selectedItem.equals(currentNav)) || (!(groupPosition==currentGroupPos))) {
                            Object[] types = new Object[]{"",true};
                            String[] hints = new String[]{"Brauch","Berücksichtigt?"};
                            boolean[] editable = new boolean[]{true,true};
                            boolean[] shown = new boolean[]{true,true};
                            String listenname = "Traditionen & Bräuche";
                            int anzahlFelder = 2;
                            String name = "traditionen_braeuche";
                            String specials = " saveOnCheckbox=true ";
                            float[] weights = new float[]{1f,0.3f};
                            String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            JSONArray typesArray = new JSONArray();
                            for(Object o : types){
                                typesArray.put(o);
                            }

                            AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",new Parcelable[]{object});

                            intent.putExtra("navID","Traditionen & Bräuche");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Outfits für Trauzeugen, Brautjungfern, Groomsmen":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", "", "",""};
                                String[] hints = new String[]{"Farbvorstellungen", "Art der Kleider", "Sonstiges", "Notizen"};
                                String listenname = "Outfits für Trauzeugen, Brautjungfern, Groomsmen";
                                int anzahlFelder = 2;
                                String name = "outfits";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Outfits für Trauzeugen, Brautjungfern, Groomsmen");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                        //BudgetÜbersicht
                }
                break;
            case PosDrucke:
                switch (selectedItem){

                    case "Übersicht":
                        if (!selectedItem.equals(currentNav)) {
                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            Parcelable[] parcels = new Parcelable[1];

                            {
                                Object[] types = new Object[]{"","",0,0.2f};
                                String[] hints = new String[]{"Save the Date","Einladung","Programm Trauung","Tischkarten","Menükarten","Danksagungskarten"};
                                String[] captions = new String[]{"Selbermachen?","Druckerei?","Stückzahl?","€"};
                                String listenname = "Übersicht";
                                int anzahlFelder = 4;
                                String name = "uebersicht_druck";
                                String specials = " disableNew=true ";
                                float[] weights = new float[]{2f,1f,1f,1f,1f};
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),true,hints,captions,listenname,anzahlFelder,name,specials,weights,groupName);

                                parcels[0]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineMehrspaltigeListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Übersicht");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Druckerei":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", "", "", ""};
                                String[] hints = new String[]{"Name", "Telefonnummer", "Adresse", "Sonstiges"};
                                String listenname = "Druckerei";
                                int anzahlFelder = 2;
                                String name = "druckerei";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Druckerei");

                            currentActivity.startActivity(intent);
                        }
                    case "Save the Date":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", new DatePickerFragment()};
                                String[] hints = new String[]{"Mustertext & Infos", "Versanddatum"};
                                String listenname = "Save the Date";
                                int anzahlFelder = 2;
                                String name = "save_the_date";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Save the Date");

                            currentActivity.startActivity(intent);
                        }
                    case "Einladungskarten":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", new DatePickerFragment()};
                                String[] hints = new String[]{"Mustertext & Infos", "Versanddatum"};
                                String listenname = "Einladungskarten";
                                int anzahlFelder = 2;
                                String name = "einladungskarten";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Einladungskarten");

                            currentActivity.startActivity(intent);
                        }
                    case "Trauspruch":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", new DatePickerFragment()};
                                String[] hints = new String[]{"Mustertext & Infos", "Versanddatum"};
                                String listenname = "Trauspruch";
                                int anzahlFelder = 2;
                                String name = "trauspruch";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Trauspruch");

                            currentActivity.startActivity(intent);
                        }
                    case "Kirchenprogramm":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", new DatePickerFragment()};
                                String[] hints = new String[]{"Mustertext & Infos", "Versanddatum"};
                                String listenname = "Kirchenprogramm";
                                int anzahlFelder = 2;
                                String name = "kirchenprogramm";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Kirchenprogramm");

                            currentActivity.startActivity(intent);
                        }
                    case "Menükarten":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", "", "", "", new DatePickerFragment()};
                                String[] hints = new String[]{"Vorspeisen", "Hauptspeisen", "Desserts", "Getränke", "Erledigungsdatum"};
                                String listenname = "Menükarten";
                                int anzahlFelder = 2;
                                String name = "menuekarten";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Menükarten");

                            currentActivity.startActivity(intent);
                        }
                    case "Danksagungen":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", new DatePickerFragment()};
                                String[] hints = new String[]{"Mustertext & Infos", "Versanddatum"};
                                String listenname = "Danksagungen";
                                int anzahlFelder = 2;
                                String name = "danksagungen";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Danksagungen");

                            currentActivity.startActivity(intent);
                        }
                    case "Sonstige":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", new DatePickerFragment()};
                                String[] hints = new String[]{"Mustertext & Infos", "Versanddatum"};
                                String listenname = "Sonstige";
                                int anzahlFelder = 2;
                                String name = "sonstige";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Sonstige");

                            currentActivity.startActivity(intent);
                        }
                    default:
                            found=false;
                }
                break;
            case PosFlowerpower:
                switch (selectedItem){

                    case "Übersicht":
                        if (!selectedItem.equals(currentNav)) {
                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            Parcelable[] parcels = new Parcelable[1];

                            {
                                Object[] types = new Object[]{"",0,0.2f};
                                String[] hints = new String[]{"Brautstrauß","Blumen für Trauung","Blumen für Party","Tischbouquets","Sonstiges"};
                                String[] captions = new String[]{"Art","Stückzahl","€"};
                                String listenname = "Übersicht";
                                int anzahlFelder = 3;
                                String name = "uebersicht_flower";
                                String specials = " disableNew=true ";
                                float[] weights = new float[]{1.5f,2f,1f,1f};
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),true,hints,captions,listenname,anzahlFelder,name,specials,weights,groupName);

                                parcels[0]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineMehrspaltigeListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Übersicht");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Florist":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{"", "", "", ""};
                                String[] hints = new String[]{"Name", "Telefonnummer", "Adresse", "Sonstiges"};
                                String listenname = "Florist";
                                int anzahlFelder = 2;
                                String name = "florist";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Florist");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    default:
                        found=false;
                }
                break;

            case PosSonstiges:
                switch (selectedItem) {

                    case "Geliehenes zurückgeben":
                        if (!selectedItem.equals(currentNav)) {
                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            Parcelable[] parcels = new Parcelable[1];

                            {
                                Object[] types = new Object[]{"",""};
                                String[] hints = new String[]{"Was?","Von wem?"};
                                boolean[] editable = new boolean[]{true,true};
                                boolean[] shown = new boolean[]{true,true};
                                String listenname = "Geliehenes zurückgeben";
                                int anzahlFelder = 2;
                                String name = "geliehenes_zurueckgeben";
                                String specials = "";
                                float[] weights = new float[]{1f,1f};
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for(Object o : types){
                                    typesArray.put(o);
                                }

                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                                parcels[0]=object;
                            }

                            intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Geliehenes zurückgeben");

                            currentActivity.startActivity(intent);
                        }
                        break;
                    case "Notizen/Sonstiges":
                        if (!selectedItem.equals(currentNav)) {
                            Parcelable[] parcels = new Parcelable[1];

                            Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                            {
                                Object[] types = new Object[]{""};
                                String[] hints = new String[]{"Notizen/Sonstiges"};
                                String listenname = "Notizen/Sonstiges";
                                int anzahlFelder = 2;
                                String name = "notizen_sonstiges";
                                String specials = " disableNew=true ";
                                String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                                JSONArray typesArray = new JSONArray();
                                for (Object o : types) {
                                    typesArray.put(o);
                                }
                                AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,groupName);

                                parcels[0]=object;
                            }


                            intent.putExtra("listTypes",new String[]{"AllgemeineListe"});
                            intent.putExtra("parcelables",parcels);

                            intent.putExtra("navID","Notizen/Sonstiges");

                            currentActivity.startActivity(intent);
                        }
                        break;
                }
                break;
            default:
                found=false;
            //FIXME Rest
        }
        switch (selectedItem) {
            case "Startseite":
                if (!selectedItem.equals(currentNav)) {
                    Intent intent = new Intent(currentActivity, Startseite.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    currentActivity.startActivity(intent);
                }
                break;
            case "Besorgungen":
                if ((!selectedItem.equals(currentNav))) {
                    Object[] types = new Object[]{"","",0,0.2f,false};
                    String[] hints = new String[]{"Was?","Wer?","Anzahl","€","erledigt?"};
                    boolean[] editable = new boolean[]{true,true,true,true,true};
                    boolean[] shown = new boolean[]{true,true,true,true,false};
                    String listenname = "Besorgungen";
                    int anzahlFelder = 5;
                    String name = "besorgungen";
                    String specials = " bezahlt=gruen ";
                    float[] weights = new float[]{1f,1f,0.5f,0.5f};
                    String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                    Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                    JSONArray typesArray = new JSONArray();
                    for(Object o : types){
                        typesArray.put(o);
                    }

                    AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                    intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                    intent.putExtra("parcelables",new Parcelable[]{object});

                    intent.putExtra("navID","Besorgungen");

                    currentActivity.startActivity(intent);
                }
                break;
            case "Budgetübersicht":
                if ((!selectedItem.equals(currentNav)) || (!(groupPosition==currentGroupPos))) {
                    Object[] types = new Object[]{0.2f,"",0.2f,false};
                    String[] hints = new String[]{"Preisvorstellung","Was wurde gekauft?","Tatsächlicher Preis","Bezahlt?"};
                    boolean[] editable = new boolean[]{true,true,true,true};
                    boolean[] shown = new boolean[]{true,true,true,false};
                    String listenname = "Budgetübersicht";
                    int anzahlFelder = 4;
                    String name = "budgetliste"+groupPosition;
                    System.out.println(name);
                    String specials = " bezahlt=gruen ";
                    float[] weights = new float[]{0.5f,1f,0.5f};
                    String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                    Intent intent = new Intent(currentActivity, AllgemeineSeite.class);

                    JSONArray typesArray = new JSONArray();
                    for(Object o : types){
                        typesArray.put(o);
                    }

                    AllgemeinesObject object = new AllgemeinesObject(typesArray.toString(),hints,listenname,anzahlFelder,name,specials,weights,editable,shown,groupName);

                    intent.putExtra("listTypes",new String[]{"AllgemeineTabelle"});
                    intent.putExtra("parcelables",new Parcelable[]{object});

                    intent.putExtra("navID","Budgetübersicht");

                    currentActivity.startActivity(intent);
                }
                break;
            case "Einstellungen":
                if (!selectedItem.equals(currentNav)) {
                    currentActivity.startActivity(new Intent(currentActivity, Einstellungen.class));
                }
                break;
            default:
                if(!found){
                    Toast.makeText(currentActivity,"Dieser Bereich existiert noch nicht!",Toast.LENGTH_SHORT).show();
                }

        }

        DrawerLayout drawer = currentActivity.findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        currentGroupPos=groupPosition;
    }

    private Map<String,List<String>> getNavigationMap(){
        Map<String,List<String>> map = new LinkedHashMap<>();

        List<String> vips = Collections.singletonList("VIPS");
        List<String> zeitplan = Arrays.asList("Zeitplan","To do","Wer hilft mit?");
        List<String> location = Arrays.asList("Location Angebote","Raumplanung","Dekowünsche","Besorgungen","Unterhaltungsprogramm","Budgetübersicht");
        List<String> tagesablauf = Arrays.asList("Startklar machen","Trauung","Besorgungen","Hochzeitsfahrzeug Angebote","Budgetübersicht");
        List<String> gaeste = Arrays.asList("Gästeliste","Gastgeschenke","Übernachtungsgäste","Was wir uns wünschen","Budgetübersicht");
        List<String> essenTrinken = Arrays.asList("Sektempfang / Candybar","Wer bringt was mit?","Menüplanung","Hochzeitstorte","Budgetübersicht");
        List<String> erinnerungen = Arrays.asList("Die wichtigsten Infos","Musik Angebote","Musikwünsche","No-Go Lieder","Fotos","Videos","Budgetübersicht");
        List<String> dressUp = Arrays.asList("Brauthäuser","Brautmessen","Haare & Make-up","Alles beisammen?","Traditionen & Bräuche","Outfits für Trauzeugen, Brautjungfern, Groosmen","Budgetübersicht");
        List<String> drucke = Arrays.asList("Übersicht","Save the Date","Einladungskarten","Trauspruch","Kirchenprogramm","Menükarten","Danksagungen","Sonsige","Besorgungen","Budgetübersicht");
        List<String> flowerpower = Arrays.asList("Übersicht","Florist","Budgetübersicht");
        List<String> sonstiges = Arrays.asList("Geliehenes zurückgeben","Notizen/Sonstiges","Budgetübersicht");
        List<String> ueberblick = Collections.singletonList("Budgetübersicht");
        List<String> einstellungen = Collections.singletonList("Einstellungen");

        map.put("VIPS",vips);
        map.put("Zeitplan",zeitplan);
        map.put("Location",location);
        map.put("Tagesablauf",tagesablauf);
        map.put("Gäste",gaeste);
        map.put("Essen & Trinken",essenTrinken);
        map.put("Erinnerungen",erinnerungen);
        map.put("Dress Up",dressUp);
        map.put("Drucke",drucke);
        map.put("Flowerpower",flowerpower);
        map.put("Sonstiges",sonstiges);
        map.put("Überblick",ueberblick);
        map.put("Einstellungen",einstellungen);

        return map;
    }

    private void addDrawerItems(ExpandableListView mExpandableListView, final AppCompatActivity activity, final ArrayList mExpandableListTitle, final Map<String,List<String>> mExpandableListData) {
        ExpandableListAdapter mExpandableListAdapter = new CustomExpandableListAdapter(activity, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) { //Show Fragment on click -> Content change
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                        .get(childPosition).toString();

                onNavigationItemSelectedFillIn(selectedItem,groupPosition,activity);

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setupDrawer(final AppCompatActivity activity) {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.closed) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }
}
