package com.stylehair.nerdsolutions.stylehair.auxiliar;
import android.util.Log;

import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnviarReceber {
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    Salao salao;
    Usuario usuario;



    public List<Usuario> Lusuario;
    public String Status;

    public List<Usuario> getRetornoUsuario()
    {
        return Lusuario;
    }

    public void setUsuario(List<Usuario> listaUser)
    {
        this.Lusuario = listaUser;
    }

    public void setStatus(String valor)
    {
        this.Status = valor;
        Log.d("xex","setou -" + valor);
    }

    public String getStatus(){
        return Status;
    }


    public Salao pegarSalaoIdUsuario(final String AidUsuario){

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Salao>> callBuscaSalao = iApi.BuscaSalao(Integer.valueOf(AidUsuario));
        callBuscaSalao.enqueue(new Callback<List<Salao>>() {
            @Override
            public void onResponse(Call<List<Salao>> call, Response<List<Salao>> response) {
                qtTentativaRealizada = 0;
                callBuscaSalao.cancel();
                switch (response.code()) {
                    case 200:
                        List<Salao> saloes = response.body();
                        salao  = saloes.get(0);
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "04": salao = null;  break;
                            case "02": salao = null;  break;
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Salao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    pegarSalaoIdUsuario(AidUsuario);
                }
            }
        });
        return salao;
    }
    public  void pegarUsuarioIdUsuario(final String BidUsuario){
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Usuario>> callBuscaUser = iApi.BuscaUsuarioId(Integer.valueOf(BidUsuario));
        callBuscaUser.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                callBuscaUser.cancel();
                qtTentativaRealizada =0;
                switch (response.code()) {
                    case 200:
                        //List<Usuario> users = response.body();
                        setUsuario(response.body());
                        setStatus("200");
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "01": setStatus("40001");  break;
                            case "02": setStatus("40002");  break;
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    pegarUsuarioIdUsuario(BidUsuario);
                }
                else
                {
                    setStatus("erro");
                }
            }
        });

    }



    public Usuario pegarUsuarioIdLogin(final String CidLogin){
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Usuario>> callBuscaUser = iApi.BuscaUsuario(Integer.valueOf(CidLogin));
        callBuscaUser.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                callBuscaUser.cancel();
                qtTentativaRealizada =0;
                switch (response.code()) {
                    case 200:
                        List<Usuario> users = response.body();
                        Usuario user = users.get(0);
                        usuario = user;

                        Log.d("xex",user.getApelido());
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "01": salao = null;  break;
                            case "02": salao = null;  break;
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    pegarUsuarioIdLogin(CidLogin);
                }
            }
        });
        return usuario  ;
    }


}
