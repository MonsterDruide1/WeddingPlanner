package de.tenolo.weddingplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.Calendar;

public class Startseite extends AppCompatActivity {

    public static SharedPreferences prefs;

    final public static String[] TIPS_OF_THE_DAY = new String[]{"TestString1","TestString2","TestString3","TestString3","TestString4","TestString5","TestString6","LONGER\nMULTILINE\nTEXT"};

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Methoden methoden = new Methoden();
        methoden.onCreateFillIn(this,"Startseite",R.layout.startseite);

        prefs = getSharedPreferences("Prefs",MODE_PRIVATE);



        if(!prefs.contains("firstStart")) {
            String[][] zeitplan = erstelleZeitplan();
            saveOrderedListe(zeitplan, "zeitplan");
            prefs.edit().putBoolean("firstStart",false).apply();

            Intent alarmIntent = new Intent(this, NotificationZeitplan.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 7);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 5);

            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        ((TextView)findViewById(R.id.tipp_tages)).setText(TIPS_OF_THE_DAY[(int) (Math.random()*TIPS_OF_THE_DAY.length)]);

        prefs.edit().remove("menueplanung").apply();
    }

    private String[][] erstelleZeitplan(){
        String[][] zeitplan = new String[82][2];

        for(int i=0;i<zeitplan.length;i++){
            zeitplan[i][0]="FALSE";
        }

        zeitplan[0][1]="<<CAPTION>>Noch 12 Monate";
        zeitplan[1][1]="Welche Jahreszeit/welcher Monat?";
        zeitplan[2][1]="Welcher Rahmen (klein oder groß)?";
        zeitplan[3][1]="Kirchliche/freie Trauung?";
        zeitplan[4][1]="Welches Budget ist möglich?";

        zeitplan[5][1]="<<CAPTION>>Noch 11 Monate";
        zeitplan[6][1]="Vorläufige Gästeliste erstellen";
        zeitplan[7][1]="Geeignete Locations suchen";
        zeitplan[8][1]="Band oder DJ? Angebote einholen";
        zeitplan[9][1]="Bei kirchlicher Trauung Kirche besichtigen. Termin mit Pfarrer abstimmen";
        zeitplan[10][1]="Endgültigen Hochzeitstermin festlegen";

        zeitplan[11][1]="<<CAPTION>>Noch 10 Monate";
        zeitplan[12][1]="Endgültige Location buchen";
        zeitplan[13][1]="Suite für die Hochzeitsnacht reservieren";
        zeitplan[14][1]="Angebote Fotograf/Videofotograf einholen";
        zeitplan[15][1]="Ziel für die Hcohzeitsreise festlegen";
        zeitplan[16][1]="\"Save the Date Karten verschicken\"";

        zeitplan[17][1]="<<CAPTION>>Noch 8 Monate";
        zeitplan[18][1]="Buchung Fotograf/Videofotograf";
        zeitplan[19][1]="Angebote Hochzeitsfahrzeug einholen";
        zeitplan[20][1]="Hochzeitsreise planen";
        zeitplan[21][1]="Evtl. Tanzkurs anmelden";
        zeitplan[22][1]="Kostenplan und Budget nach Buchungen überprüfen";

        zeitplan[23][1]="<<CAPTION>>Noch 7 Monate";
        zeitplan[24][1]="Ablauf der Trauung mit Verantwortlichem besprechen";
        zeitplan[25][1]="Gültigkeit von Reisepass/Ausweis überprüfen";
        zeitplan[26][1]="Endgültige Buchung der Musik";

        zeitplan[27][1]="<<CAPTION>>Noch 6 Monate";
        zeitplan[28][1]="Brautkleid/Schuhe auswählen und bestellen";
        zeitplan[29][1]="Hochzeitsreise buchen";
        zeitplan[30][1]="Endgültige Gästeliste zusammenstellen";
        zeitplan[31][1]="Einladungskarten/Menükerten/Tischkarten auswählen";
        zeitplan[32][1]="Unterlagen für Trauung zusammenstellen";

        zeitplan[33][1]="<<CAPTION>>Noch 5 Monate";
        zeitplan[34][1]="Einladungen drucken";
        zeitplan[35][1]="Anzug/Schuhe für Bräutigam auswählen/kaufen";
        zeitplan[36][1]="Accessoires zum Brautkleid kaufen";
        zeitplan[37][1]="Geschenketisch/Hochzeitstisch vorbereiten";
        zeitplan[38][1]="Trauringe anfertigen lassen (Ringkissen besorgen)";

        zeitplan[39][1]="<<CAPTION>>Noch 4 Monate";
        zeitplan[40][1]="Einladungen verschicken";
        zeitplan[41][1]="Programm für die Hochzeit überlegen";
        zeitplan[42][1]="Ggf. Entertainer buchen";
        zeitplan[43][1]="Mit Trauzeugen Programm abstimmen";
        zeitplan[44][1]="Kostencheck";

        zeitplan[45][1]="<<CAPTION>>Noch 3 Monate";
        zeitplan[46][1]="Floristen/Dekorateure kontaktieren (Angebote einholen)";
        zeitplan[47][1]="Blumenkinder auswählen (einheitliche Kleidung?)";
        zeitplan[48][1]="Hotelzimmer reservieren (Gäste mit Übernachtung)";

        zeitplan[49][1]="<<CAPTION>>Noch 2 Monate";
        zeitplan[50][1]="Hochzeitsessen/Getränke besprechen und auswählen";
        zeitplan[51][1]="Menükarten/Tischkarten erstellen und drucken";
        zeitplan[52][1]="Absprache über Aufnahmen mit Foto-/Videograf";
        zeitplan[53][1]="Traugespräch mit Pfarrer";
        zeitplan[54][1]="Hochzeitstorte auswählen, probieren, bestellen";
        zeitplan[55][1]="Gastgeschenke planen/besorgen";

        zeitplan[56][1]="<<CAPTION>>Noch 1 Monat";
        zeitplan[57][1]="Gästeliste prüfen";
        zeitplan[58][1]="Kompletten Ablauf mit allen Personen durchgehen";
        zeitplan[59][1]="Brautstrauß, Blumen und Deko für Feier bestellen";
        zeitplan[60][1]="Friseurtermine/Probetermin";
        zeitplan[61][1]="Programm für kirchliche Trauung in Auftrag geben";

        zeitplan[62][1]="<<CAPTION>>Noch 3 Wochen";
        zeitplan[63][1]="Sitzordnung festlegen";
        zeitplan[64][1]="Tisch-/Menükarten fertig stellen";
        zeitplan[65][1]="Brautkleid/Anzug abholen (letzte Änderungen?)";
        zeitplan[66][1]="Trauringe abholen";

        zeitplan[67][1]="<<CAPTION>>Noch 2 Wochen";
        zeitplan[68][1]="Personenanzahl, Sitzordnung, Ablauf an Location melden";
        zeitplan[69][1]="Schlechtwetterplanung noch mal besprechen";
        zeitplan[70][1]="Braut-/Bräutigamsschuhe einlaufen";

        zeitplan[71][1]="<<CAPTION>>Noch wenige Tage";
        zeitplan[72][1]="Nägel der Braut machen lassen";
        zeitplan[73][1]="Probefrisur und Make-up testen";
        zeitplan[74][1]="Generalprobe mit Blumenkindern";

        zeitplan[75][1]="<<CAPTION>>Morgen ist es soweit";
        zeitplan[76][1]="Trauringe, Ringkissen, Unterlagen bereit legen";
        zeitplan[77][1]="Tasche für den Hochzeitstag einräumen";
        zeitplan[78][1]="Koffer für de Hochzeitsnacht packen";
        zeitplan[79][1]="Tischdeko aufbauen (lassen)";
        zeitplan[80][1]="Genaue Uhrzeiten noch mal mit allen Beteiligten durchgehen";

        zeitplan[81][1]="<<CAPTION>>Wedding Day";

        return zeitplan;
    }

    private void saveOrderedListe(String[][] array, String name){
        JSONArray jsonarray = new JSONArray();
        for(String[] partArray : array){
            jsonarray.put(new JSONArray(Arrays.asList(partArray)));
        }
        prefs.edit().putString(name,jsonarray.toString()).apply();
    }

}
