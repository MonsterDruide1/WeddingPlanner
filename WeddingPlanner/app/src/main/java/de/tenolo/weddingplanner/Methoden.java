package de.tenolo.weddingplanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Methoden {

    DrawerLayout drawerLayout;
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
        switch (selectedItem) {
            case "Startseite":
                if (!selectedItem.equals(currentNav)) {
                    Intent intent = new Intent(currentActivity, Startseite.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    currentActivity.startActivity(intent);
                }
                break;
            case "Tischordnung":
                if (!selectedItem.equals(currentNav)) {
                    currentActivity.startActivity(new Intent(currentActivity, TischordnungSelector.class));
                }
                break;
            case "Gästeliste":
                if (!selectedItem.equals(currentNav)) {
                    currentActivity.startActivity(new Intent(currentActivity, Gaesteliste.class));
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

                    Intent intent = new Intent(currentActivity, AllgemeineTabelle.class);

                    JSONArray typesArray = new JSONArray();
                    for(Object o : types){
                        typesArray.put(o);
                    }

                    intent.putExtra("types",typesArray.toString());
                    intent.putExtra("hints",hints);
                    intent.putExtra("listenname",listenname);
                    intent.putExtra("anzahlFelder",anzahlFelder);
                    intent.putExtra("name",name);
                    intent.putExtra("specials",specials);
                    intent.putExtra("weights",weights);
                    intent.putExtra("editable",editable);
                    intent.putExtra("shown",shown);
                    intent.putExtra("groupName",groupName);

                    intent.putExtra("navID","Budgetübersicht");

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

                    Intent intent = new Intent(currentActivity, AllgemeineTabelle.class);

                    JSONArray typesArray = new JSONArray();
                    for(Object o : types){
                        typesArray.put(o);
                    }

                    intent.putExtra("types",typesArray.toString());
                    intent.putExtra("hints",hints);
                    intent.putExtra("listenname",listenname);
                    intent.putExtra("anzahlFelder",anzahlFelder);
                    intent.putExtra("name",name);
                    intent.putExtra("specials",specials);
                    intent.putExtra("weights",weights);
                    intent.putExtra("editable",editable);
                    intent.putExtra("shown",shown);
                    intent.putExtra("groupName",groupName);

                    intent.putExtra("navID","To do");

                    currentActivity.startActivity(intent);
                }
                break;
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

                    Intent intent = new Intent(currentActivity, AllgemeineTabelle.class);

                    JSONArray typesArray = new JSONArray();
                    for(Object o : types){
                        typesArray.put(o);
                    }

                    intent.putExtra("types",typesArray.toString());
                    intent.putExtra("hints",hints);
                    intent.putExtra("listenname",listenname);
                    intent.putExtra("anzahlFelder",anzahlFelder);
                    intent.putExtra("name",name);
                    intent.putExtra("specials",specials);
                    intent.putExtra("weights",weights);
                    intent.putExtra("editable",editable);
                    intent.putExtra("shown",shown);
                    intent.putExtra("groupName",groupName);

                    intent.putExtra("navID","Zeitplan");

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

                    Intent intent = new Intent(currentActivity, AllgemeineTabelle.class);

                    JSONArray typesArray = new JSONArray();
                    for(Object o : types){
                        typesArray.put(o);
                    }

                    intent.putExtra("types",typesArray.toString());
                    intent.putExtra("hints",hints);
                    intent.putExtra("listenname",listenname);
                    intent.putExtra("anzahlFelder",anzahlFelder);
                    intent.putExtra("name",name);
                    intent.putExtra("specials",specials);
                    intent.putExtra("weights",weights);
                    intent.putExtra("editable",editable);
                    intent.putExtra("shown",shown);
                    intent.putExtra("groupName",groupName);

                    intent.putExtra("navID","Zeitplan");

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

                    Intent intent = new Intent(currentActivity, AllgemeineTabelle.class);

                    JSONArray typesArray = new JSONArray();
                    for(Object o : types){
                        typesArray.put(o);
                    }

                    intent.putExtra("types",typesArray.toString());
                    intent.putExtra("hints",hints);
                    intent.putExtra("listenname",listenname);
                    intent.putExtra("anzahlFelder",anzahlFelder);
                    intent.putExtra("name",name);
                    intent.putExtra("specials",specials);
                    intent.putExtra("editable",editable);
                    intent.putExtra("shown",shown);
                    intent.putExtra("groupName",groupName);
                    intent.putExtra("weights",weights);

                    intent.putExtra("navID","Zeitplan");

                    currentActivity.startActivity(intent);
                }
                break;
            case "Menüplanung":
                if (!selectedItem.equals(currentNav)) {
                    Object[] types = new Object[]{"",0,0,0,""};
                    String[] hints = new String[]{"Wünsche & Ideen","Anzahl der Gäste","davon Kinder","Vegetarier, Veganer","Unverträglichkeiten"};
                    String listenname = "Menüplanung";
                    int anzahlFelder = 2;
                    String name = "menueplanung";
                    String specials = " disableNew=true ";
                    String groupName = expandableListData.keySet().toArray(new String[0])[groupPosition];

                    Intent intent = new Intent(currentActivity, AllgemeineListe.class);

                    JSONArray typesArray = new JSONArray();
                    for(Object o : types){
                        typesArray.put(o);
                    }

                    intent.putExtra("types",typesArray.toString());
                    intent.putExtra("hints",hints);
                    intent.putExtra("listenname",listenname);
                    intent.putExtra("anzahlFelder",anzahlFelder);
                    intent.putExtra("name",name);
                    intent.putExtra("specials",specials);
                    intent.putExtra("groupName",groupName);

                    intent.putExtra("navID","Zeitplan");

                    currentActivity.startActivity(intent);
                }
                break;
            case "Einstellungen":
                if (!selectedItem.equals(currentNav)) {
                    currentActivity.startActivity(new Intent(currentActivity, Einstellungen.class));
                }
                break;
            default:
                Toast.makeText(currentActivity,"Dieser Bereich existiert noch nicht!",Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = currentActivity.findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        currentGroupPos=groupPosition;
    }

    private Map<String,List<String>> getNavigationMap(){
        Map<String,List<String>> map = new LinkedHashMap<>();

        /*List<String> kap1 = Arrays.asList("Zeitplan","ToDo-Liste");
        List<String> kap2 = Arrays.asList("Tischordnung","Budget-Liste");
        List<String> kap3 = Arrays.asList("Gästeliste","Einstellungen");

        map.put(items[0],kap1);
        map.put(items[1],kap2);
        map.put(items[2],kap3);*/

        List<String> vips = Collections.emptyList();
        List<String> zeitplan = Arrays.asList("Zeitplan","To do","Wer hilft mit?"); //TODO Wer hilft mit?
        List<String> location = Arrays.asList("Location Angebote","Raumplanung","Dekowünsche","Besorgungen","Unterhaltungsprogramm","Budgetübersicht"); //TODO Location Angebote, Dekowünsche, Besorgungen, Unterhaltungsprogramm FIXME Name Budgetübersicht,Raumplanung
        List<String> tagesablauf = Arrays.asList("Startklar machen","Trauung","Besorgungen","Hochzeitsfahrzeug Angebote","Budgetübersicht");
        List<String> gaeste = Arrays.asList("Gästeliste","Gastgeschenke","Übernachtungsgäste","Was wir uns wünschen","Budgetübersicht");
        List<String> essenTrinken = Arrays.asList("Sektempfang / Candybar","Wer bringt was mit?","Menüplanung","Hochzeitstorte","Budgetübersicht");
        List<String> erinnerungen = Arrays.asList("Die wichtigsten Infos","Musik Angebote","Musikwünsche","No-Go Lieder","Fotos","Videos","Budgetübersicht");
        List<String> dressUp = Arrays.asList("Brauthäuser","Brautmessen","Haare & Make-up","Alles beisammen?","Traditionen & Bräuche","Outfits für Trauzeugen, Brautjungfern, Groosmen","Budgetübersicht");
        List<String> drucke = Arrays.asList("Übersicht","Save the Date","Einladungskarten","Ideen Trausprüche","Trauspruch","Kirchenprogramm","Menükarten","Danksagungen","Sonsige","Das muss besorgt werden...","Budgetübersicht");
        List<String> flowerpower = Arrays.asList("Übersicht","Budgetübersicht");
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
