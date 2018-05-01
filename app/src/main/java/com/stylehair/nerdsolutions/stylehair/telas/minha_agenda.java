package com.stylehair.nerdsolutions.stylehair.telas;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import org.joda.time.DateTime;

public class minha_agenda extends AppCompatActivity implements DatePickerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_minha_agenda);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Meus Agendamentos");
        Drawable upArrow = ContextCompat.getDrawable(minha_agenda.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(minha_agenda.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        HorizontalPicker picker = (HorizontalPicker) findViewById(R.id.agendaH);

        // initialize it and attach a listener
        Resources resources = getBaseContext().getResources();
        picker
                .setListener(this)
                .setDays(20)
                .setOffset(10)
                .setDateSelectedColor(Color.GRAY)// dia selecionado
                .setDateSelectedTextColor(Color.WHITE)//nurmero dia selecionado
                .setMonthAndYearTextColor(Color.LTGRAY)//mes
                .setTodayDateBackgroundColor(Color.GRAY)//fundo dia atual
                .setTodayDateTextColor(Color.RED)//numero do dia atual
                .setUnselectedDayTextColor(Color.GRAY)
                .setDayOfWeekTextColor(Color.WHITE)

                //botao today----------
                .setTodayButtonTextColor(Color.RED)
                .showTodayButton(true)

                //----------------------

                .init();
        picker.setBackgroundColor(Color.DKGRAY);
        TextView btToday = (TextView)findViewById(com.github.jhonnyx2012.horizontalpicker.R.id.tvToday);
        btToday.setText("HOJE");
        picker.setDate(new DateTime());
    }

    @Override
    public void onDateSelected(@NonNull final DateTime dateSelected) {
        // log it for demo
        Log.i("HorizontalPicker", "Selected date is " + dateSelected.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
