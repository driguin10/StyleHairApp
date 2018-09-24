package com.stylehair.nerdsolutions.stylehair.telas;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.VerSalao;
import com.stylehair.nerdsolutions.stylehair.telas.busca.verSalao_buscado;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.configuracaoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class configuracaoApp extends AppCompatActivity {
    Button btEncerrarConta;
    String typeUserApp;
    int qtTentativas = 3;
    int qtTentativaRealizadaComum = 0;
    int qtTentativaRealizadaUsuario = 0;
    int qtTentativaRealizadaFuncionario = 0;
    int qtTentativaRealizadaGerente = 0;

    boolean recebeNotify;
    Loading loading;
    ToggleButton btReceberNotify;
    SharedPreferences getSharedPreferences;
    SharedPreferences.Editor e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_configuracao);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Configurações");
        Drawable upArrow = ContextCompat.getDrawable(configuracaoApp.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(configuracaoApp.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
         getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

         e = getSharedPreferences.edit();

        typeUserApp =getSharedPreferences.getString("typeUserApp","");
        recebeNotify = getSharedPreferences.getBoolean("recebeNotify",true);
        loading = new Loading(this);
        btEncerrarConta=(Button) findViewById(R.id.bt_deletar_conta);
        btReceberNotify=(ToggleButton) findViewById(R.id.tgNotificações);



        btReceberNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                e.putBoolean("recebeNotify",isChecked);
                e.commit();
                e.apply();

                if(isChecked)
                    btReceberNotify.setBackground(getResources().getDrawable(R.drawable.bt_folga_on));
                else
                    btReceberNotify.setBackground(getResources().getDrawable(R.drawable.bt_folga_off));
            }
        });

        btReceberNotify.setChecked(recebeNotify);
        btEncerrarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(configuracaoApp.this)
                        .setTitle("Deseja excluir esta conta?")
                        .setMessage("Todas informações relacionadas a esta conta seram excluidas!!")
                        .setIcon(R.drawable.icone_delete)
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                loading.abrir("Aguarde...");
                                int idLogin = getSharedPreferences.getInt("idLogin", -1);
                                if(typeUserApp.equals("COMUM"))
                                {
                                    encerrarContaComum(String.valueOf(idLogin));
                                }
                                else
                                if(typeUserApp.equals("COMUMUSER"))
                                {
                                    String idUsuario = getSharedPreferences.getString("idUsuario", "-1");
                                    encerrarContaUsuario(String.valueOf(idLogin),idUsuario);
                                }
                                else
                                if(typeUserApp.equals("FUNCIONARIO"))
                                {
                                    String idUsuario = getSharedPreferences.getString("idUsuario", "-1");
                                    String idFuncionario = getSharedPreferences.getString("idFuncionario", "-1");
                                    encerrarContaFuncionario(String.valueOf(idLogin),idUsuario,idFuncionario);
                                }
                                else
                                if(typeUserApp.equals("GERENTE"))
                                {
                                    String idUsuario = getSharedPreferences.getString("idUsuario", "-1");
                                    String idFuncionario = getSharedPreferences.getString("idFuncionario", "-1");
                                    String idSalao = getSharedPreferences.getString("idSalao", "-1");
                                    encerrarContaGerente(String.valueOf(idLogin),idUsuario,idFuncionario,idSalao);
                                }
                            }
                        })
                        .setNegativeButton("não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
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

    public void encerrarContaComum(final String id_login){
        RequestBody IdLogin = RequestBody.create(MediaType.parse("text/plain"),id_login);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiConta = iApi.EncerrarUsuarioComum(IdLogin);
        callExcluiConta.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                qtTentativaRealizadaComum = 0;
                callExcluiConta.cancel();
                switch (response.code()) {
                    case 200:
                        Logout logout = new Logout();
                        logout.deslogar(configuracaoApp.this,false);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaComum < qtTentativas) {
                    qtTentativaRealizadaComum++;
                    encerrarContaComum(id_login);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }

    public void encerrarContaUsuario(final String id_login,final String id_usuario){
        RequestBody IdLogin = RequestBody.create(MediaType.parse("text/plain"),id_login);
        RequestBody IdUsuario = RequestBody.create(MediaType.parse("text/plain"),id_usuario);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiContaUsuario = iApi.EncerrarUsuario(IdLogin,IdUsuario);
        callExcluiContaUsuario.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                qtTentativaRealizadaUsuario= 0;
                callExcluiContaUsuario.cancel();
                switch (response.code()) {
                    case 200:
                        Logout logout = new Logout();
                        logout.deslogar(configuracaoApp.this,false);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaUsuario < qtTentativas) {
                    qtTentativaRealizadaUsuario++;
                    encerrarContaUsuario(id_login,id_usuario);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }

    public void encerrarContaFuncionario(final String id_login,final String id_usuario,final String id_funcionario){
        RequestBody IdLogin = RequestBody.create(MediaType.parse("text/plain"),id_login);
        RequestBody IdUsuario = RequestBody.create(MediaType.parse("text/plain"),id_usuario);
        RequestBody IdFuncionario = RequestBody.create(MediaType.parse("text/plain"),id_funcionario);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiContaFuncionario = iApi.EncerrarFuncionario(IdLogin,IdUsuario,IdFuncionario);
        callExcluiContaFuncionario.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                qtTentativaRealizadaFuncionario= 0;
                callExcluiContaFuncionario.cancel();
                switch (response.code()) {
                    case 200:
                        Logout logout = new Logout();
                        logout.deslogar(configuracaoApp.this,false);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaFuncionario < qtTentativas) {
                    qtTentativaRealizadaFuncionario++;
                    encerrarContaFuncionario(id_login,id_usuario,id_funcionario);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }

    public void encerrarContaGerente(final String id_login,final String id_usuario,final String id_funcionario,final String id_gerente){
        RequestBody IdLogin = RequestBody.create(MediaType.parse("text/plain"),id_login);
        RequestBody IdUsuario = RequestBody.create(MediaType.parse("text/plain"),id_usuario);
        RequestBody IdFuncionario = RequestBody.create(MediaType.parse("text/plain"),id_funcionario);
        RequestBody IdGerente = RequestBody.create(MediaType.parse("text/plain"),id_gerente);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiContaGerente = iApi.EncerrarGerente(IdLogin,IdUsuario,IdFuncionario,IdGerente);
        callExcluiContaGerente.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                qtTentativaRealizadaGerente= 0;
                callExcluiContaGerente.cancel();
                switch (response.code()) {
                    case 200:
                        Logout logout = new Logout();
                        logout.deslogar(configuracaoApp.this,false);
                        break;

                    default:
                        Toast.makeText(configuracaoApp.this,"Houve um erro...",Toast.LENGTH_LONG).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaGerente < qtTentativas) {
                    qtTentativaRealizadaGerente++;
                    encerrarContaGerente(id_login,id_usuario,id_funcionario,id_gerente);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }
}
