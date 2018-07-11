package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.DatePicker;

import com.stylehair.nerdsolutions.stylehair.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class datePick extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
    public int id;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme,this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String DataNasc = String.valueOf(day)+"-"+String.valueOf(month +1)+"-" +String.valueOf(year);
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");
        String output =  "";

        try {
            output = dataFormat.format(dataFormat.parse(DataNasc));
        }catch (ParseException p) { }

            TextInputLayout campo = (TextInputLayout) getActivity().findViewById(id);
            campo.getEditText().setText(output);
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
