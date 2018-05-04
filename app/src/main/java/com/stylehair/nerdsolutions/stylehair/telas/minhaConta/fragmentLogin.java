package com.stylehair.nerdsolutions.stylehair.telas.minhaConta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.CaixaDialogo;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.telas.login.logar;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragmentLogin extends Fragment {

    TextView emailCadastrado;
    TextInputLayout senhaAtual;
    TextInputLayout novaSenha;
    Button BtalterarLogin;

    SharedPreferences getSharedPreferences;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    ProgressDialog dialog;

    Loading loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_login, container, false);
        loading = new Loading(getActivity());
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        emailCadastrado = (TextView) v.findViewById(R.id.txtEmailCadastrado);
        senhaAtual = (TextInputLayout)v.findViewById(R.id.editSenhaAtual);
        novaSenha = (TextInputLayout)v.findViewById(R.id.editSenhaNova);
        BtalterarLogin = (Button)v.findViewById(R.id.btAlterarLogin);

        emailCadastrado.setText(getSharedPreferences.getString("email", ""));


        BtalterarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.abrir("Aguarde...Enviando dados !!!");
                AtualizaLogin();
            }
        });
        return v;
    }


    public boolean verificaCampos()
    {
        Boolean status = false;
        String VSenhaAtual =senhaAtual.getEditText().getText().toString();
        String VSenhaNova = novaSenha.getEditText().getText().toString();

        if(!VSenhaAtual.equals("") && !VSenhaNova.equals("") )
        {
            status = true;
        }
        else
        {
            if(VSenhaAtual.equals(""))
                senhaAtual.getEditText().requestFocus();
            else
            if(VSenhaNova.equals(""))
                novaSenha.getEditText().requestFocus();
        }
        return  status;
    }



    public void AtualizaLogin()
    {
        if(!verificaCampos())
        {
           loading.fechar();
            Toast.makeText(getContext(), "Preencha os campos necessarios !!", Toast.LENGTH_LONG).show();
        }
        else
        {
            String  sAtual = senhaAtual.getEditText().getText().toString();
            String  sNova = novaSenha.getEditText().getText().toString();
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"),getSharedPreferences.getString("email", ""));
            final RequestBody senhaAtual = RequestBody.create(MediaType.parse("text/plain"), sAtual);
            RequestBody novaSenha = RequestBody.create(MediaType.parse("text/plain"),sNova);
            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callEditarLogin = iApi.EditarLogin(email,senhaAtual,novaSenha);
            callEditarLogin.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    loading.fechar();
                    qtTentativaRealizada = 0;
                    switch (response.code())
                    {
                        case 204:
                            Logout logout = new Logout();
                            Toast.makeText(getContext(),"Senha alterada com sucesso!!",Toast.LENGTH_LONG).show();
                            logout.deslogar(getActivity(),false);
                            break;

                        case 400:
                            switch (response.message())
                            {
                                case "02":
                                    Toast.makeText(getContext(),"Parametros incorretos!!",Toast.LENGTH_LONG).show();
                                    break;

                                case "04":
                                    Toast.makeText(getContext(),"Erro ao editar senha!!",Toast.LENGTH_LONG).show();
                                    break;

                                case "06":
                                    TextInputLayout senhaAtual = (TextInputLayout) getView().findViewById(R.id.editSenhaAtual);
                                    senhaAtual.getEditText().setText("");
                                    senhaAtual.getEditText().requestFocus();
                                    Toast.makeText(getContext(),"Senha atual incorreta!!",Toast.LENGTH_LONG).show();
                                    break;
                            }
                            break;
                    }

                    callEditarLogin.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                    if (qtTentativaRealizada < qtTentativas) {
                        qtTentativaRealizada++;
                        AtualizaLogin();
                    } else {
                        loading.fechar();

                        if (t instanceof IOException) {
                            Log.d("xex", "this is an actual network failure timeout:( inform the user and possibly retry");
                            Log.d("xex", String.valueOf(t.getCause()));
                        } else if (t instanceof IllegalStateException) {
                            Log.d("xex", "ConversionError");
                            Log.d("xex", String.valueOf(t.getCause()));
                        } else {
                            Log.d("xex", "erro");
                            Log.d("xex", String.valueOf(t.getCause()));
                            Log.d("xex", String.valueOf(t.getLocalizedMessage()));
                        }

                    }




                }
            });
        }
    }


}
