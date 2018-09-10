package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;

public class Filtros extends AppCompatActivity {
    TextView textoDistancia;
    TextView cidade;
    SeekBar seekDistancia;
    Button salvar;
    ImageButton fechar;
    int progresso = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);
        textoDistancia = (TextView) findViewById(R.id.txt_distancia);
        cidade = (TextView) findViewById(R.id.txtCidadeFiltro);
        seekDistancia = (SeekBar) findViewById(R.id.SeekDistancia);
        salvar = (Button) findViewById(R.id.btSalvarFiltro);
        fechar = (ImageButton) findViewById(R.id.btFecharFiltro);
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent data = new Intent();
                data.putExtra("cidade","");
                data.putExtra("kilometro","5");
                setResult(RESULT_OK, data);*/
                finish();
            }
        });
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("cidade",cidade.getText().toString());
                data.putExtra("kilometro",String.valueOf(progresso));
                setResult(RESULT_OK, data);
                finish();
            }
        });
        cidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0)
                {
                    seekDistancia.setEnabled(false);
                    progresso = 0;
                }
                else
                {
                   progresso = seekDistancia.getProgress();
                   seekDistancia.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        seekDistancia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textoDistancia.setText(String.valueOf(progress)+"km");
                progresso = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            String cid = bundle.getString("cidade","");
            int distancia = bundle.getInt("kilometro",5);
            cidade.setText(cid);
            seekDistancia.setProgress(distancia);
        }
    }
}
