package com.stylehair.nerdsolutions.stylehair.api;


import com.stylehair.nerdsolutions.stylehair.classes.CadastroFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.Funcionario;
import com.stylehair.nerdsolutions.stylehair.classes.GetUsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.Logar;
import com.stylehair.nerdsolutions.stylehair.classes.Login;

import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.TipoUsuario;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Rodrigo on 18/01/2018.
 */

public interface IApi {

    //************ config API **********************************
    String chave = "R20p1g8S7m85ar92t";
    String WebService = "http://stylehair.xyz/";
    String versao = "v1";
    //**********************************************************


    //----------chamada para criar o login ---------------
    @Headers("apiKey:" + chave)
    @POST(versao + "/logins/salvar/")
    Call<ResponseBody> cadastraLogin(@Body Login login);
    //----------------------------------------------------

    //---------chamada para verificar login ---------------
    @Headers("apiKey:" + chave)
    @POST(versao + "/logins/")
    Call<Logar> Logar(@Body Login login);
    //-----------------------------------------------------

    //-------- chamada para editar login ---------------------------------------
    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/logins/editar/")
    Call<ResponseBody> EditarLogin(@Part("email") RequestBody email,
                                   @Part("senhaAtual") RequestBody senhaAtual,
                                   @Part("novaSenha") RequestBody novaSenha);
    //--------------------------------------------------------------------------

    //---------------chamada para criar usuario---------------------------------
    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/usuarios/salvar/")
    Call<ResponseBody> salvarUsuario(@Part("uploaded_file") RequestBody image64,
                                     @Part("mine_file") RequestBody tipoImg,
                                     @Part("idLogin") RequestBody idLogin,
                                     @Part("nome") RequestBody nome,
                                     @Part("apelido") RequestBody apelido,
                                     @Part("sexo") RequestBody sexo,
                                     @Part("dataNascimento") RequestBody dataNascimento,
                                     @Part("telefone") RequestBody telefone,
                                     @Part("cep") RequestBody cep,
                                     @Part("endereco") RequestBody endereco,
                                     @Part("numero") RequestBody numero,
                                     @Part("bairro") RequestBody bairro,
                                     @Part("estado") RequestBody estado,
                                     @Part("cidade") RequestBody cidade,
                                     @Part("obs") RequestBody obs);
    //----------------------------------------------------------------------------

    //---------------chamada para editar usuario---------------------------------
    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/usuarios/editar/")
    Call<ResponseBody> EditarUsuario(@Part("uploaded_file") RequestBody image64,
                                     @Part("mine_file") RequestBody tipoImg,
                                     @Part("idLogin") RequestBody idLogin,
                                     @Part("nome") RequestBody nome,
                                     @Part("apelido") RequestBody apelido,
                                     @Part("sexo") RequestBody sexo,
                                     @Part("dataNascimento") RequestBody dataNascimento,
                                     @Part("telefone") RequestBody telefone,
                                     @Part("cep") RequestBody cep,
                                     @Part("endereco") RequestBody endereco,
                                     @Part("numero") RequestBody numero,
                                     @Part("bairro") RequestBody bairro,
                                     @Part("estado") RequestBody estado,
                                     @Part("cidade") RequestBody cidade,
                                     @Part("obs") RequestBody obs,
                                     @Part("imagemAntiga") RequestBody imagemAntiga);
    //----------------------------------------------------------------------------



//-----------------------------------------------------------------------
    @Headers("apiKey:" + chave)
    @GET("v1/usuarios/{id}")
    Call <List<Usuario>> BuscaUsuario(@Path("id") int id);


    @Headers("apiKey:" + chave)
    @GET("v1/usuariosId/{id}")
    Call <List<Usuario>> BuscaUsuarioId(@Path("id") int id);

    //----------------------------------------------------------------------------



    @Headers("apiKey:" + chave)
    @GET("v1/saloes/{id}")
    Call <List<Salao>> BuscaSalao(@Path("id") int id);


    //---------------chamada para salvar salao---------------------------------
    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/saloes/salvar/")
    Call<ResponseBody> SalvarSalao(@Part("uploaded_file") RequestBody image64,
                                     @Part("mine_file") RequestBody tipoImg,
                                     @Part("idUsuario") RequestBody idUsuario,
                                     @Part("nome") RequestBody nome,
                                     @Part("telefone1") RequestBody telefone1,
                                     @Part("telefone2") RequestBody telefone2,
                                     @Part("endereco") RequestBody endereco,
                                     @Part("bairro") RequestBody bairro,
                                     @Part("cep") RequestBody cep,
                                     @Part("numero") RequestBody numero,
                                     @Part("estado") RequestBody estado,
                                     @Part("cidade") RequestBody cidade,
                                     @Part("email") RequestBody email,
                                     @Part("sobre") RequestBody sobre,
                                     @Part("cnpj") RequestBody cnpj,
                                     @Part("agendamento") RequestBody agendamento,
                                     @Part("complemento") RequestBody complemento);

    //----------------------------------------------------------------------------



    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/saloes/editar/")
    Call<ResponseBody> EditarSalao(@Part("uploaded_file") RequestBody image64,
                                   @Part("mine_file") RequestBody tipoImg,
                                   @Part("idSalao") RequestBody idSalao,
                                   @Part("nome") RequestBody nome,
                                   @Part("telefone1") RequestBody telefone1,
                                   @Part("telefone2") RequestBody telefone2,
                                   @Part("endereco") RequestBody endereco,
                                   @Part("bairro") RequestBody bairro,
                                   @Part("cep") RequestBody cep,
                                   @Part("numero") RequestBody numero,
                                   @Part("estado") RequestBody estado,
                                   @Part("cidade") RequestBody cidade,
                                   @Part("email") RequestBody email,
                                   @Part("sobre") RequestBody sobre,
                                   @Part("cnpj") RequestBody cnpj,
                                   @Part("complemento") RequestBody complemento,
                                   @Part("agendamento") RequestBody agendamento,
                                   @Part("imagemAntiga") RequestBody imagemAntiga);



    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/saloes/configuracoes/")
    Call<ResponseBody> EditarConfiguracoesSalao(@Part("idSalao") RequestBody idSalao,
                                                @Part("tempoReserva") RequestBody tempoReserva,
                                                @Part("tempoMinReserva") RequestBody tempoMinReserva,
                                                @Part("segE") RequestBody segE,
                                                @Part("segS") RequestBody segS,
                                                @Part("terE") RequestBody terE,
                                                @Part("terS") RequestBody terS,
                                                @Part("quaE") RequestBody quaE,
                                                @Part("quaS") RequestBody quaS,
                                                @Part("quiE") RequestBody quiE,
                                                @Part("quiS") RequestBody quiS,
                                                @Part("sexE") RequestBody sexE,
                                                @Part("sexS") RequestBody sexS,
                                                @Part("sabE") RequestBody sabE,
                                                @Part("sabS") RequestBody sabS,
                                                @Part("domE") RequestBody domE,
                                                @Part("domS") RequestBody domS);





//----------------------- funcionario--------------------------------------------
    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/saloes/funcionario/")
    Call<ResponseBody> SalvarFuncionario(@Part("idSalao") RequestBody idSalao,
                                         @Part("idUsuario") RequestBody idUsuario);


    @Headers("apiKey:" + chave)
    @GET("v1/funcionarios/funcionario/{id}")
    Call<List<Funcionario>> BuscaFuncionario(@Path("id") String id);

    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/funcionarios/editar/")
    Call<ResponseBody> EditarHoraFuncionario(@Part("idFuncionario") RequestBody idFuncionario,
                                                @Part("segE") RequestBody segE,
                                                @Part("segS") RequestBody segS,
                                                @Part("terE") RequestBody terE,
                                                @Part("terS") RequestBody terS,
                                                @Part("quaE") RequestBody quaE,
                                                @Part("quaS") RequestBody quaS,
                                                @Part("quiE") RequestBody quiE,
                                                @Part("quiS") RequestBody quiS,
                                                @Part("sexE") RequestBody sexE,
                                                @Part("sexS") RequestBody sexS,
                                                @Part("sabE") RequestBody sabE,
                                                @Part("sabS") RequestBody sabS,
                                                @Part("domE") RequestBody domE,
                                                @Part("domS") RequestBody domS);


    @Headers("apiKey:" + chave)
    @GET("v1/funcionarios/{id}")
    Call <GetUsuarioFuncionario> buscaFuncionarios(@Path("id") int id);



    //criar funcionario -- já tem usuario e login

    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/funcionarios/salvar/")
    Call<CadastroFuncionario> CriarFuncionario1(@Part("TipoSalvar") RequestBody TipoSalvar,
                                                      @Part("idSalao") RequestBody idSalao,
                                                      @Part("idUsuario") RequestBody idUsuario);


    //criar funcionario -- não tem usuario - nem login
    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/funcionarios/salvar/")
    Call<CadastroFuncionario> CriarFuncionario2(@Part("TipoSalvar") RequestBody TipoSalvar,
                                                      @Part("idSalao") RequestBody idSalao,
                                                      @Part("uploaded_file") RequestBody image64,
                                                      @Part("mine_file") RequestBody tipoImg,
                                                      @Part("idLogin") RequestBody idLogin,
                                                      @Part("nome") RequestBody nome,
                                                      @Part("apelido") RequestBody apelido,
                                                      @Part("sexo") RequestBody sexo,
                                                      @Part("dataNascimento") RequestBody dataNascimento,
                                                      @Part("telefone") RequestBody telefone,
                                                      @Part("cep") RequestBody cep,
                                                      @Part("endereco") RequestBody endereco,
                                                      @Part("numero") RequestBody numero,
                                                      @Part("bairro") RequestBody bairro,
                                                      @Part("estado") RequestBody estado,
                                                      @Part("cidade") RequestBody cidade,
                                                      @Part("obs") RequestBody obs);


    //criar funcionario -- não tem nada
    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/funcionarios/salvar/")
    Call<CadastroFuncionario> CriarFuncionario3(@Part("TipoSalvar") RequestBody TipoSalvar,
                                                      @Part("idSalao") RequestBody idSalao,
                                                      @Part("email") RequestBody email,
                                                      @Part("senha") RequestBody senha,
                                                      @Part("uploaded_file") RequestBody image64,
                                                      @Part("mine_file") RequestBody tipoImg,
                                                      @Part("idLogin") RequestBody idLogin,
                                                      @Part("nome") RequestBody nome,
                                                      @Part("apelido") RequestBody apelido,
                                                      @Part("sexo") RequestBody sexo,
                                                      @Part("dataNascimento") RequestBody dataNascimento,
                                                      @Part("telefone") RequestBody telefone,
                                                      @Part("cep") RequestBody cep,
                                                      @Part("endereco") RequestBody endereco,
                                                      @Part("numero") RequestBody numero,
                                                      @Part("bairro") RequestBody bairro,
                                                      @Part("estado") RequestBody estado,
                                                      @Part("cidade") RequestBody cidade,
                                                      @Part("obs") RequestBody obs);

//--------------------------------------------------------------------------------

    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/saloes/editarStatus/")
    Call<ResponseBody> EditarStatusSalao(@Part("idSalao") RequestBody idSalao,
                                          @Part("status") RequestBody status);


    //---------------chamada para resetar login---------------------------------
    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/logins/reset/")
    Call<ResponseBody> esqueciSenha(@Part("email") RequestBody email);
    //----------------------------------------------------------------------------

    //-----------------------------------------------------------------------------
    @Headers("apiKey:" + chave)
    @GET("v1/usuarios/tipos/{id}")
    Call<TipoUsuario> tipoUsuario(@Path("id") int id);
    //-----------------------------------------------------------------------------

    @Headers("apiKey:" + chave)
    @GET("v1/servi_saloes/servicos/{id}")
    Call<List<ServicoSalao>> BuscaServicosSalao(@Path("id") String id);


    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/servi_saloes/salvar/")
    Call<ResponseBody> SalvarServicoSalao(@Part("idSalao") RequestBody idSalao,
                                          @Part("servico") RequestBody servico,
                                          @Part("tempo") RequestBody tempo,
                                          @Part("sexo") RequestBody sexo,
                                          @Part("valor") RequestBody valor);

    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/servi_saloes/editar/")
    Call<ResponseBody> EditarServicoSalao(@Part("idServicoSalao") RequestBody idServicoSalao,
                                          @Part("servico") RequestBody servico,
                                          @Part("tempo") RequestBody tempo,
                                          @Part("sexo") RequestBody sexo,
                                          @Part("valor") RequestBody valor);

    @Headers("apiKey:" + chave)
    @GET("v1/servi_saloes/deletar/{id}")
    Call<ResponseBody> ExcluirServicosSalao(@Path("id") String id);


    //-------------------------servicos funcionario-----------------------

    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/servi_funcionarios/salvar/")
    Call<ResponseBody> SalvarServicoFuncionario(@Part("idFuncionario") RequestBody idFuncionario,
                                          @Part("idServicoSalao") RequestBody idServicoSalao);


    @Headers("apiKey:" + chave)
    @Multipart
    @POST("v1/servi_funcionarios/salvar/")
    Call<ResponseBody> EditarServicoFuncionario(@Part("idFuncionario") RequestBody idFuncionario,
                                                @Part("idServicoSalao") RequestBody idServicoSalao);

    @Headers("apiKey:" + chave)
    @GET("v1/servi_funcionarios/deletar/{id}")
    Call<ResponseBody> ExcluirServicosFuncionario(@Path("id") String id);

    //----------------------------------------------------


    //******************* SERVICE RETROFIT ******************************
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
          .connectTimeout(15, TimeUnit.SECONDS)
          .readTimeout(30, TimeUnit.SECONDS)
          .writeTimeout(15, TimeUnit.SECONDS)
          .build();

    public static final Retrofit retrofit = new Retrofit.Builder()

            .baseUrl(WebService)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //*******************************************************************


}
