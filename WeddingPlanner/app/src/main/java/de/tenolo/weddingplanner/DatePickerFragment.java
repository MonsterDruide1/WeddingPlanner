package de.tenolo.weddingplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public int year,month,day;
    private Object notifyer;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public DatePickerFragment(Object obj){
        notifyer=obj;
    }

    public DatePickerFragment(){
        //Useless
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.year=year;
        this.month=month+1;
        this.day=day;
        synchronized (notifyer) {
            notifyer.notifyAll();
        }
    }
}