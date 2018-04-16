package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.timerPick;
import com.stylehair.nerdsolutions.stylehair.classes.CadastroFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.cadastrar_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.ver_funcionario;

import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ver_servico_salao extends AppCompatActivity {
    TextInputLayout servico;
    TextInputLayout tempo;
    TextInputLayout valor;
    Spinner sexo;
    ImageButton btPesquisaTempo;
    Button salvar;
    Loading loading;

    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;
    public int qtTentativaRealizadaEditar = 0;

    String id_Salao;
    String id_servico;
    Bundle bundle;

    AutoCompleteTextView EdtServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_servico_salao);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_ver_servicos_salao);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
         bundle = ver_servico_salao.this.getIntent().getExtras();



        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        id_Salao = getSharedPreferences.getString("idSalao", "");
        loading = new Loading(ver_servico_salao.this);
        servico = (TextInputLayout) findViewById(R.id.cadServico);
        tempo = (TextInputLayout) findViewById(R.id.txt_cadtempo);
        tempo.setEnabled(false);
        valor = (TextInputLayout) findViewById(R.id.cadValor);
        sexo = (Spinner) findViewById(R.id.spn_cadServSexo);
        btPesquisaTempo =(ImageButton) findViewById(R.id.pesquisa_tempo);
        salvar = (Button) findViewById(R.id.bt_salvarServico);

        btPesquisaTempo.requestFocus();

        List<String> servicosSal =
                Arrays.asList(getResources().getStringArray(R.array.servicos_salao));

        ArrayAdapter<String> adapters = new ArrayAdapter<String>(ver_servico_salao.this,
                android.R.layout.simple_dropdown_item_1line,servicosSal);
        EdtServico = (AutoCompleteTextView) findViewById(R.id.edt_servico);

        EdtServico.setAdapter(adapters);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.abrir("Salvando....");
                if(bundle!=null) {
                    editar();
                }
                else
                {
                    salvar();

                }
            }
        });

        if(bundle!=null)
        {
            if(!bundle.getString("servico").isEmpty()) {
                getSupportActionBar().setTitle("Serviço - " + bundle.getString("servico"));
               servico.getEditText().setText(bundle.getString("servico"));

                tempo.getEditText().setText(bundle.getString("tempo").substring(0,5));
                valor.getEditText().setText(bundle.getString("valor"));
                id_servico=bundle.getString("idServico");


                for(int i= 0; i < sexo.getAdapter().getCount(); i++)
                {
                    if(sexo.getAdapter().getItem(i).toString().contains(bundle.getString("sexo")))
                    {
                        sexo.setSelection(i);
                    }
                }

            }

        }
        else {
            getSupportActionBar().setTitle("Cadastro de Serviços");
        }

        btPesquisaTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_cadtempo);
               // tempo.getEditText().setError(null);
            }
        });
    }


    public void showTimePickerDialog(View v,int idCampo) {
        timerPick tim = new timerPick();
        tim.setId(idCampo);
        DialogFragment newFragment = tim;
        newFragment.show(getFragmentManager(), "timePicker");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(ver_servico_salao.this,servicos_salao.class);

               startActivity(intent);
               finish();
                break;
            default:break;
        }
        return true;
    }



    public void salvar(){
        RequestBody IdSalao = RequestBody.create(MediaType.parse("text/plain"), id_Salao);
        RequestBody Servico = RequestBody.create(MediaType.parse("text/plain"),EdtServico.getText().toString());
        RequestBody Sexo = RequestBody.create(MediaType.parse("text/plain"), sexo.getSelectedItem().toString());
        RequestBody Valor = RequestBody.create(MediaType.parse("text/plain"), valor.getEditText().getText().toString());
        RequestBody Tempo = RequestBody.create(MediaType.parse("text/plain"), tempo.getEditText().getText().toString());

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callSalvaServico = iApi.SalvarServicoSalao(IdSalao,Servico,Tempo,Sexo,Valor);
        callSalvaServico.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                qtTentativaRealizada = 0;
                callSalvaServico.cancel();

                switch (response.code())
                {
                    case 204:
                        Toast.makeText(ver_servico_salao.this,"Salvo com sucesso !!! ",Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(ver_servico_salao.this, ver_funcionario.class);
                       // startActivity(intent);
                        finish();
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "02":
                                Toast.makeText(ver_servico_salao.this,"Parametros incorretos !!! ",Toast.LENGTH_LONG).show();
                                break;

                            case "03":
                                Toast.makeText(ver_servico_salao.this,"erro ao salvar!!! ",Toast.LENGTH_LONG).show();
                                break;
                        }
                        break;
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    salvar();
                } else {
                    loading.fechar();
                }
            }
        });
    }

    public void editar(){
        RequestBody IdServicoSalao = RequestBody.create(MediaType.parse("text/plain"), id_servico);
        RequestBody Servico = RequestBody.create(MediaType.parse("text/plain"),EdtServico.getText().toString());
        RequestBody Sexo = RequestBody.create(MediaType.parse("text/plain"), sexo.getSelectedItem().toString());
        RequestBody Valor = RequestBody.create(MediaType.parse("text/plain"), valor.getEditText().getText().toString());
        RequestBody Tempo = RequestBody.create(MediaType.parse("text/plain"), tempo.getEditText().getText().toString());

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callSalvaServico = iApi.EditarServicoSalao(IdServicoSalao,Servico,Tempo,Sexo,Valor);
        callSalvaServico.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                qtTentativaRealizadaEditar = 0;
                callSalvaServico.cancel();

                switch (response.code())
                {
                    case 204:
                        Toast.makeText(ver_servico_salao.this,"Editado com sucesso !!! ",Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(ver_servico_salao.this, ver_funcionario.class);
                        // startActivity(intent);
                        finish();
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "02":
                                Toast.makeText(ver_servico_salao.this,"Parametros incorretos !!! ",Toast.LENGTH_LONG).show();
                                break;

                            case "04":
                                Toast.makeText(ver_servico_salao.this,"erro ao editar!!! ",Toast.LENGTH_LONG).show();
                                break;
                        }
                        break;
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if (qtTentativaRealizadaEditar < qtTentativas) {
                    qtTentativaRealizadaEditar++;
                    editar();
                } else {
                    loading.fechar();
                }
            }
        });
    }

}
