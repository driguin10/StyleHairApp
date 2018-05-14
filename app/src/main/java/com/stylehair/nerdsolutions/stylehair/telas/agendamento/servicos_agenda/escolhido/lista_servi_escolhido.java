package com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolhido;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolhido.Adaptador_servico_salaoE;

import java.util.ArrayList;
import java.util.List;

public class lista_servi_escolhido extends AppCompatActivity {
    RecyclerView listaServicos;
    ArrayList<String> listRecebida = new ArrayList<>();
    List<ServicoSalao> ListaServicos;
    ServicoSalao servicoSalao;
    Float vlTotal = 0f;
    Button btInfo;
    ImageButton sair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servi_escolhido);
        btInfo = (Button) findViewById(R.id.btValorTotal);
        sair = (ImageButton) findViewById(R.id.btFecharLista);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            listRecebida = bundle.getStringArrayList("ListaServ");
        }

        LayoutInflater li = getLayoutInflater();
        listaServicos = (RecyclerView) findViewById(R.id.listaServEscolhidos);
        listaServicos.setHasFixedSize(true);
        ListaServicos = new ArrayList<>();

        for (int x=0 ; x < listRecebida.size();x++)
        {
            String[] obj = listRecebida.get(x).split("#");
            int idServico = Integer.valueOf(obj[0]);
            String Servico = obj[1];
            Float Valor = Float.valueOf(obj[2]);
            String Tempo =obj[3];
            servicoSalao = new ServicoSalao();
            servicoSalao.setIdServicoSalao(idServico);
            servicoSalao.setServico(Servico);
            servicoSalao.setValor(Valor);
            servicoSalao.setTempo(Tempo);
            ListaServicos.add(servicoSalao);
            vlTotal = vlTotal + Valor;

        }
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        listaServicos.setAdapter(new Adaptador_servico_salaoE(ListaServicos));
        listaServicos.setLayoutManager(layout);
        listaServicos.setClickable(true);
        btInfo.setText("Volor Total R$ " + String.valueOf(vlTotal));
    }
}
