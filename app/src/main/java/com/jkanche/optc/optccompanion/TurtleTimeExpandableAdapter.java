package com.jkanche.optc.optccompanion;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by jayar on 10/13/2015.
 */
public class TurtleTimeExpandableAdapter extends ExpandableRecyclerAdapter<TurtleTimeParentViewHolder, TurtleTimeChildViewHolder> {
    private LayoutInflater mInflater;
    public static final String PREFS_NAME_Turtle = "turtleTimes";

    /**
     * Public primary constructor.
     *
     * @param parentList the list of parent items to be displayed in the RecyclerView
     */
    public TurtleTimeExpandableAdapter(Context context, List<ParentListItem> parentList) {
        super(parentList);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TurtleTimeParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.parent_turtletime, viewGroup, false);
        return new TurtleTimeParentViewHolder(view);
    }

    @Override
    public TurtleTimeChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.child_turtletime, viewGroup, false);
        return new TurtleTimeChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(TurtleTimeParentViewHolder turtleTimeParentViewHolder, int i, ParentListItem parentListItem) {
        TurtleLocations tloc = (TurtleLocations) parentListItem;
        turtleTimeParentViewHolder.turtleLocation.setText(tloc.getTurtleLocation());
    }

    private Date getDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
        //formatter.setTimeZone(TimeZone.getTimeZone("EDT"));
        Date value = null;
        try {
            value = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(value);

        try {
            value = dateFormatter.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value;
    }

    @Override
    public void onBindChildViewHolder(TurtleTimeChildViewHolder turtleTimeChildViewHolder, int i, Object childObject) {
        final TurtleLocationTimes tlocTime = (TurtleLocationTimes) childObject;
        turtleTimeChildViewHolder.turtleDigit.setText(tlocTime.getDigitID());

        String[] ttimes = tlocTime.getTurtleTime().split(",");

        String dateFinal = "";

        int di = 0 ;

        for (String dateTT : ttimes) {

            if (di != 0) {
                dateFinal += ",\n";
            }

            Date dd = getDate(dateTT);

            String dateOut;
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            dateOut = formatter.format(dd);

            dateFinal += dateOut;

            di++;
        }


        turtleTimeChildViewHolder.turtleTime.setText(dateFinal);
        turtleTimeChildViewHolder.turtleNotify.setChecked(tlocTime.isNotifySwitch());

        turtleTimeChildViewHolder.turtleNotify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<TurtleLocationTimes>>() {
                }.getType();

                SharedPreferences optcTT = mInflater.getContext().getSharedPreferences(PREFS_NAME_Turtle, 0);

                if (tlocTime.getLocation().toLowerCase().equals("global")) {
                    String dataTTGlobal = optcTT.getString("globalTT", null);
                    ArrayList<TurtleLocationTimes> globalChildList = new ArrayList<TurtleLocationTimes>();

                    globalChildList = gson.fromJson(dataTTGlobal, collectionType);

                    for (int gi = 0; gi < globalChildList.size(); gi++) {
                        TurtleLocationTimes temp = globalChildList.get(gi);

                        if (temp.getDigitID().equals(tlocTime.getDigitID())) {
                            ((TurtleLocationTimes) globalChildList.get(gi)).toggleNotifySwitch();
                            scheduleNotification(getNotification("TURTLE TIME - GLOBAL - " + tlocTime.getDigitID()), tlocTime.getDigitID(), tlocTime.getTurtleTime(), !tlocTime.isNotifySwitch(), tlocTime.getLocation());
                        }
                    }

                    String gcldata = gson.toJson(globalChildList);
                    SharedPreferences.Editor editor = optcTT.edit();
                    editor.putString("globalTT", gcldata);
                    editor.commit();

                } else if (tlocTime.getLocation().toLowerCase().equals("japan")) {
                    String dataTTJapan = optcTT.getString("japanTT", null);
                    ArrayList<TurtleLocationTimes> japanChildList = new ArrayList<TurtleLocationTimes>();
                    japanChildList = gson.fromJson(dataTTJapan, collectionType);

                    for (int gi = 0; gi < japanChildList.size(); gi++) {
                        TurtleLocationTimes temp = japanChildList.get(gi);

                        if (temp.getDigitID().equals(tlocTime.getDigitID())) {
                            ((TurtleLocationTimes) japanChildList.get(gi)).toggleNotifySwitch();
                            scheduleNotification(getNotification("TURTLE TIME - JAPAN - " + tlocTime.getDigitID()), tlocTime.getDigitID(), tlocTime.getTurtleTime(), !tlocTime.isNotifySwitch(), tlocTime.getLocation());
                        }
                    }


                    SharedPreferences.Editor editor = optcTT.edit();
                    String jcldata = gson.toJson(japanChildList);
                    editor.putString("japanTT", jcldata);
                    editor.commit();
                }
            }

            private void scheduleNotification(Notification notification, String digitTT, String tDates, boolean notifyTT, String locTT) {

                Context act = mInflater.getContext();

                int idTT = 0;

                if (locTT.toLowerCase().equals("japan")) {
                    idTT = 100;
                }

                if (digitTT.equals("0,5")) {
                    idTT += 0;
                } else if (digitTT.equals("1,6")) {
                    idTT += 10;
                } else if (digitTT.equals("2,7")) {
                    idTT += 20;
                } else if (digitTT.equals("3,8")) {
                    idTT += 30;
                } else if (digitTT.equals("4,9")) {
                    idTT += 40;
                }

                String[] ttimes = tDates.split(",");

                int nTimeNotifier = 0;

                for (String dateTT : ttimes) {

                    Date ddTT = getDate(dateTT);

                    Calendar calTemp = Calendar.getInstance();

                    calTemp.setTime(ddTT);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    //calendar.set(ddTT);
                    calendar.set(Calendar.MONTH, calTemp.get(Calendar.MONTH));
                    calendar.set(Calendar.YEAR, calTemp.get(Calendar.YEAR));
                    calendar.set(Calendar.DATE, calTemp.get(Calendar.DATE));
                    calendar.set(Calendar.HOUR_OF_DAY, calTemp.get(Calendar.HOUR_OF_DAY));
                    calendar.set(Calendar.MINUTE, calTemp.get(Calendar.MINUTE));

                    int nidTT = idTT + nTimeNotifier;

                    Intent notificationIntent = new Intent(act, NotificationPublisher.class);
                    notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, nidTT);
                    notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
                    notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_TAG, ddTT.toString());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(act, nidTT, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) act.getSystemService(Context.ALARM_SERVICE);


                    //long futureInMillis = dd.getTimeInMillis();

                    if (notifyTT) {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    } else {
                        alarmManager.cancel(pendingIntent);
                    }


                    nTimeNotifier++;
                }
            }

            private Date getDate(String dateString) {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm z");
                //formatter.setTimeZone(TimeZone.getTimeZone("EDT"));
                Date value = null;
                try {
                    value = formatter.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                dateFormatter.setTimeZone(TimeZone.getDefault());
                String dt = dateFormatter.format(value);

                try {
                    value = dateFormatter.parse(dt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return value;
            }

            private Notification getNotification(String content) {

                Context act = mInflater.getContext();

                Notification.Builder builder = new Notification.Builder(act);
                builder.setContentTitle("Scheduled Notification");
                builder.setContentText(content);
                builder.setSmallIcon(R.drawable.ic_action_info);
                return builder.build();
            }
        });

    }
}
