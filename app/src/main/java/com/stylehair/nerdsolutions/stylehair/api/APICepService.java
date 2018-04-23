package com.stylehair.nerdsolutions.stylehair.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stylehair.nerdsolutions.stylehair.classes.cep.CEP;
import com.stylehair.nerdsolutions.stylehair.classes.cep.CEPDeserializer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface APICepService {

    String BASE_URL = "https://viacep.com.br/ws/";

    /* Retorna uma lista de objetos CEP */
    @GET("{estado}/{cidade}/{endereco}/json/")
    Call<List<CEP>> getCEPByCidadeEstadoEndereco(@Path("estado") String estado,
                                                 @Path("cidade") String cidade,
                                                 @Path("endereco") String endereco);

    /* Retorna apenas um objeto CEP */
    @GET("{CEP}/json/")
    Call<CEP> getEnderecoByCEP(@Path("CEP") String CEP);


    Gson g = new GsonBuilder().registerTypeAdapter(CEP.class, new CEPDeserializer()).create();
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APICepService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(g))
            .build();

}