package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import com.stylehair.nerdsolutions.stylehair.R;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timerPick extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public int id;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme,this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextInputLayout imput = ((TextInputLayout) getActivity().findViewById(id));
        String h = hourOfDay + ":" + minute;
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        Date data = null;
        try {
            data = formatador.parse(h);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Time time = new Time(data.getTime());
        String[] hora = time.toString().split(":");
        imput.getEditText().setText(hora[0]+":"+hora[1]);
    }



    public void setId(int id)
    {
        this.id = id;
    }
}
