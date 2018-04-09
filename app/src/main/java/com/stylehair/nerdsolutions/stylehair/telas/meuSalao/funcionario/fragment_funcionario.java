package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Mask;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_funcionario extends Fragment {


    private OnFragmentInteractionListener mListener;

    AlertDialog alerta;
    ProgressDialog dialog;


    int qtTentativas = 3;
    int qtTentativaRealizadaLoad = 0;




    CircleImageView ImagemUser;
    TextInputLayout verNomeUser;
    TextInputLayout verApelidoUser;
    TextInputLayout verTelefoneUser;
    TextInputLayout verCepUser;
    TextInputLayout verEnderecoUser;
    TextInputLayout verBairroUser;
    TextInputLayout verNumeroUser;
    TextInputLayout verCidadeUser;
    TextInputLayout verObsUser;
    TextInputLayout verNascimento;
    TextInputLayout verEstadoUser;
    TextInputLayout verSexoUser;


    String IdUsuario;

    View view;
    VerificaConexao verificaConexao;

    Loading loading;
    Config config;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_funcionario, container, false);
        loading = new Loading(getActivity());
        config = new Config();
        verificaConexao  = new VerificaConexao();
        Bundle bundle = getActivity().getIntent().getExtras();

        if(bundle!=null)
        {
            IdUsuario = bundle.getString("idUsuario");
        }


        verNomeUser = (TextInputLayout) view.findViewById(R.id.txt_funcNome);
        verApelidoUser = (TextInputLayout) view.findViewById(R.id.txt_funcApelido);
        verTelefoneUser = (TextInputLayout) view.findViewById(R.id.txt_funcTelefone);
        verEnderecoUser = (TextInputLayout) view.findViewById(R.id.txt_funcEndereco);
        verBairroUser = (TextInputLayout)view.findViewById(R.id.txt_funcBairro);
        verNumeroUser = (TextInputLayout)view.findViewById(R.id.txt_funcNumero);
        verEstadoUser = (TextInputLayout) view.findViewById(R.id.txt_funcEstado);
        verCidadeUser = (TextInputLayout) view.findViewById(R.id.txt_funcCidade);
        verObsUser = (TextInputLayout) view.findViewById(R.id.txt_funcObs);
        verCepUser = (TextInputLayout) view.findViewById(R.id.txt_funcCep);
        verNascimento = (TextInputLayout) view.findViewById(R.id.txt_funcNascimento);
        verSexoUser = (TextInputLayout) view.findViewById(R.id.txt_funcsexo);
        ImagemUser = (CircleImageView) view.findViewById(R.id.verimagemFunc);
        //--------------------------------------------------------------------

        if(verificaConexao.verifica(getContext())) {
            loading.abrir("Aguarde...Carregando dados!!!");
            pegarFuncionario(IdUsuario);
        }
        else {

            Toast.makeText(getActivity(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();
        }


        return view;
    }


    //---- função para pegar dados do usuario do servidor----
    public void pegarFuncionario(final String idUser){
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Usuario>> callBuscaUser = iApi.BuscaUsuario(Integer.valueOf(idUser));
        callBuscaUser.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                loading.fechar();
                callBuscaUser.cancel();
                switch (response.code()) {
                    case 200:
                        qtTentativaRealizadaLoad = 0;
                        List<Usuario> users = response.body();
                        Usuario user = users.get(0);
                        verNomeUser.getEditText().setText(user.getNome());
                        verApelidoUser.getEditText().setText(user.getApelido());
                        verTelefoneUser.getEditText().setText(user.getTelefone());
                        verEnderecoUser.getEditText().setText(user.getEndereco());
                        verBairroUser.getEditText().setText(user.getBairro());
                        verNumeroUser.getEditText().setText(String.valueOf(user.getNumero()));
                        verCidadeUser.getEditText().setText(user.getCidade());
                        verObsUser.getEditText().setText(user.getObs());
                        verCepUser.getEditText().setText(user.getCep());
                        verEstadoUser.getEditText().setText(user.getEstado());
                        verSexoUser.getEditText().setText(user.getSexo());

                        if (user.getLinkImagem() != "") {
                            Picasso.with(getContext()).load(config.getWebService() + user.getLinkImagem()).into(ImagemUser);
                        }

                        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat dataFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                        String outputNascimento = "";
                        try {
                            outputNascimento = dataFormat2.format(dataFormat.parse(user.getDataNascimento()));
                        } catch (ParseException p) {
                        }

                        verNascimento.getEditText().setText(outputNascimento);



                        break;


                    case 400:
                        if (response.message().equals("1")) {

                        }
                        if (response.message().equals("2")) {

                            //paramentros incorretos
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                if (qtTentativaRealizadaLoad < qtTentativas) {
                    qtTentativaRealizadaLoad++;
                    pegarFuncionario(IdUsuario);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }
    //-------------------------------------------------------



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
