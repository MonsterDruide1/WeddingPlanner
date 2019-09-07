package de.tenolo.weddingplanner;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NotificationZeitplan extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = context.getSharedPreferences("Prefs", MODE_PRIVATE);

                if(prefs.getString("dateHochzeit",null)!=null){

                    //FIXME Find out/write TEXT

                    String[][] listAll = loadOrdered("zeitplan",2,prefs);

                    String[][] listAllImportant = null;
                    listAllImportant = getImportantPart(listAll,prefs.getString("dateHochzeit",""));

                    if(listAllImportant==null){
                        return;
                    }

                    String[][] list = onlyUnfinished(listAllImportant);

                    System.out.println(Arrays.deepToString(list));

                    String text;
                    text="Du hast für diesen Zeitraum noch "+list.length+" Aufgaben, um deine Hochzeit entspannt zu planen!";





                    Object[] types = new Object[]{false,""};
                    String[] hints = new String[]{"",""};
                    boolean[] editable = new boolean[]{true,false};
                    boolean[] shown = new boolean[]{true,true};
                    String listenname = "Zeitplan";
                    int anzahlFelder = 2;
                    String name = "zeitplan";
                    String specials = " disableNew=true disableEdit=true saveOnCheckbox=true ";
                    float[] weights = new float[]{0.2f,1f};

                    Intent intent = new Intent(context, AllgemeineTabelle.class);

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

                    intent.putExtra("navNr",5);
                    intent.putExtra("navID","Zeitplan");


                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent onOpen = PendingIntent.getActivity(context,0,intent,0);
                    if(Build.VERSION.SDK_INT>=26){
                        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                        NotificationChannel mChannel = new NotificationChannel("zeitplan_notification_weddingplanner", "NOTIFICATION-CHANNEL-TITLE", NotificationManager.IMPORTANCE_DEFAULT);

                        mChannel.setDescription("NOTIFICATION-CHANNEL-DESCRIPTION");

                        mChannel.enableLights(true);
                        mChannel.setLightColor(Color.RED);

                        mChannel.enableVibration(true);
                        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                        mNotificationManager.createNotificationChannel(mChannel);
                    }

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,NotificationCompat.CATEGORY_REMINDER).setSmallIcon(R.mipmap.logo_quadratisch);

                    mBuilder.setContentTitle("NOTIFICATION-TEST");
                    mBuilder.setContentText(text);
                    mBuilder.setChannelId("zeitplan_notification_weddingplanner");

                    mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT).setStyle(new NotificationCompat.BigTextStyle().bigText(text));
                    mBuilder.setContentIntent(onOpen).setAutoCancel(true).setCategory(NotificationCompat.CATEGORY_REMINDER).setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(0,mBuilder.build());
                }
            }
        }).start();


    }

    private String[][] onlyUnfinished(String[][] list){
        List<String[]> arrayList = new ArrayList<>();
        for(String[] listEntry : list){
            if(listEntry[0].equals("FALSE")){
                arrayList.add(listEntry);
            }
        }
        return arrayList.toArray(new String[0][0]);
    }

    private String[][] getImportantPart(String[][] listAll, String dateHochzeit) {

        List<Integer> dateIndexes = new ArrayList<>();
        for (int i=0;i<listAll.length;i++) {
            String text = listAll[i][1];
            if(text.contains("<<CAPTION>>")){
                dateIndexes.add(i);
            }
        }

        String[] array = dateHochzeit.split("\\.");

        DateTime current = new DateTime().withTime(0,0,0,0);
        DateTime dateHochzeitObj = new DateTime().withDate(Integer.parseInt(array[2]),Integer.parseInt(array[1]),Integer.parseInt(array[0])).withTime(0,0,0,0);

        int monthsDiff = Months.monthsBetween(current,dateHochzeitObj).getMonths();
        int weeksDiff = Weeks.weeksBetween(current,dateHochzeitObj).getWeeks();
        int daysDiff = Days.daysBetween(current,dateHochzeitObj).getDays();

        System.out.println("MONTHS: "+monthsDiff+", WEEKS: "+weeksDiff+", DAYS: "+daysDiff);
        if(monthsDiff<=0) {
            if (weeksDiff <= 0 && daysDiff < 0) {
                return null;
            }
        }

        while(true) {
            for (int i = 0; i < dateIndexes.size(); i++) {
                int dateIndex = dateIndexes.get(i);
                String title = listAll[dateIndex][1];
                String nochZeit = "";
                try {
                    nochZeit = title.split("<<CAPTION>>Noch ")[1];
                } catch (ArrayIndexOutOfBoundsException ignored) {}

                if ((nochZeit.equals(monthsDiff + " Monate") || nochZeit.equals(monthsDiff + " Monat")) || (nochZeit.equals(weeksDiff + " Wochen")) ||
                        (nochZeit.equals("wenige Tage") && daysDiff < 7 && daysDiff > 1) ||
                        (title.equals("<<CAPTION>>Morgen ist es soweit") && daysDiff == 1) || (title.equals("<<CAPTION>>Wedding Day") && daysDiff == 0)) {
                    System.out.println("FOUND: " + monthsDiff);
                    try {
                        return Arrays.copyOf(listAll, dateIndexes.get(i + 1));
                    }
                    catch (IndexOutOfBoundsException e){
                        return Arrays.copyOf(listAll, dateIndexes.get(i)+1);
                    }
                }
            }
            if(monthsDiff<=0){
                if(weeksDiff<=0 && daysDiff<=0){
                    return null;
                }
                weeksDiff++;
                daysDiff++;
            }
            else {
                monthsDiff++;
            }
        }
    }

    private String[][] loadOrdered(String name, int anzahlFelder, SharedPreferences prefs){
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
}