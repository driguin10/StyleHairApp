package com.stylehair.nerdsolutions.stylehair.telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Permissoes;
import com.stylehair.nerdsolutions.stylehair.auxiliar.TopicoNotificacao;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.CriaBancoNotificacao;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.classes.TipoUsuario;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.meuSalao;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.minhaConta;

import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    NavigationView navigationView;
    int idLogin = -1;
    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;


    public int qtTentativaRealizadaUser = 0;

    String typeUser="";
    int ResultCode = 0;
    TextView NomeDrawer;
    TextView EmailDrawerr;
    VerificaConexao verificaConexao;
    String nomeUsuario = "";
    String linkImagem ="";
    String qtNotificacoes;
    TextView notificacoes;

     CircleImageView imgUser;
     Permissoes permissoes;

     Logout logout;
    AlertDialog alerta;
    Loading loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loading = new Loading(principal.this);
        verificaConexao = new VerificaConexao();
        logout = new Logout();

        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        idLogin = getSharedPreferences.getInt("idLogin", -1);
        typeUser = getSharedPreferences.getString("typeUserApp","COMUM");
        nomeUsuario = getSharedPreferences.getString("nomeUser","");
        linkImagem = getSharedPreferences.getString("linkImagem","");





/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date();

                String tituloNotify = "Promoção 10% desconto";
                String nomeSalaoMen = "LuizCabeleireiro";
                String tituloMen = "Amanhã promoção de 10% de desconto Codigo-311447" ;
                String horaMen = dateFormat.format(date);

                String saidaMen = nomeSalaoMen+"§"+tituloMen+"§"+horaMen;
                Notification notification = new Notification(saidaMen,tituloNotify);
                Sender sender = new Sender(notification, "/topics/AllNotifications");
                INotification iNotification = INotification.retrofit.create(INotification.class);
                iNotification.enviarNotificacao(sender).enqueue(new Callback<ReturnMessage>() {
                    @Override
                    public void onResponse(Call<ReturnMessage> call, Response<ReturnMessage> response) {
                            Toast.makeText(principal.this,"enviado",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ReturnMessage> call, Throwable t) {
                        Toast.makeText(principal.this,"erro",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);

        Menu men = navigationView.getMenu();
        men.findItem(R.id.nav_meuSalao).setVisible(false);
        men.findItem(R.id.nav_agendamento).setVisible(false);
        men.findItem(R.id.nav_meu_agendamento).setVisible(false);


        qtNotificacoes = atualizaNotificacoes();

        notificacoes=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
              findItem(R.id.nav_notificacoes));
        initializeCountDrawer(qtNotificacoes);


        EmailDrawerr = (TextView) headerView.findViewById(R.id.txtemaildrawer);
        EmailDrawerr.setText(getSharedPreferences.getString("email","..."));


        NomeDrawer = (TextView) headerView.findViewById(R.id.nomeuser);
        if(nomeUsuario!="")
            NomeDrawer.setText(nomeUsuario);
        else
            NomeDrawer.setText("...");

       imgUser = (CircleImageView) headerView.findViewById((R.id.imagemUser));


        if(linkImagem!="") {

            Picasso.with(getBaseContext()).load("http://stylehair.xyz/" + linkImagem).into(imgUser);
        }else
        {
            imgUser.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
        }

        if(verificaConexao.verifica(principal.this)) {
            loading.abrir("Aguarde...Carregando!!!");
            atualizatipo();
            pegarUsuario(idLogin);
        }
        else
        {
            Toast.makeText(getBaseContext(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();
           // atualizaTela(typeUser);
            logout.deslogar(this,true);

        }



    }


    public String atualizaNotificacoes(){
        BancoNotifyController crud = new BancoNotifyController(getBaseContext());
        Cursor cursor = crud.carregaQuantidade(String.valueOf(idLogin));
        final List<menssagem> conteudo_menssagem = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.ID));
                String titulo = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.TITULO));
                String texto = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.TEXTO));
                String hora = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.HORA));
                String visualizacao = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.VISUALIZACAO));
                String nome_salao = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.NOME_SALAO));
                conteudo_menssagem.add(new menssagem(id, titulo, texto, visualizacao,hora,nome_salao));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  String.valueOf(conteudo_menssagem.size());
    }

    public void initializeCountDrawer(String qt){
        //Gravity property aligns the text

        notificacoes.setGravity(Gravity.CENTER);
        notificacoes.setTypeface(null, Typeface.BOLD);
        notificacoes.setTextColor(getResources().getColor(R.color.corToobar));
        if(qt.equals("0"))
            notificacoes.setText("");
        else
            notificacoes.setText(qt);
        notificacoes.setTextSize(15);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pesquisa, menu);

        //Pega o Componente.
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setQueryHint("Oque está procurando ?");
        //mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(true);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent =new Intent(principal.this,busca_salao.class);
                intent.putExtra("query",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        permissoes = new Permissoes();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_minhaConta) {
            if(permissoes.habilitarIMagem(principal.this))
            {
                Intent intent=new Intent(principal.this,minhaConta.class);
                startActivityForResult(intent,1);
            }
        } else if (id == R.id.nav_salaoFavorito) {
           Intent intent=new Intent(principal.this,saloesFavoritos.class);
            startActivityForResult(intent,2);
        } else if (id == R.id.nav_agendamento) {


        } else if (id == R.id.nav_notificacoes) {
            Intent intent=new Intent(principal.this,notificacao.class);
            startActivityForResult(intent,4);

        } else if (id == R.id.nav_criarSalao) {

            if(permissoes.habilitarLocalizacao(principal.this)) {
                Intent intent = new Intent(principal.this, cadastroSalao.class);
                startActivityForResult(intent, 5);
            }

        } else if (id == R.id.nav_meuSalao) {
            if(permissoes.habilitarLocalizacao(principal.this)) {
                Intent intent = new Intent(principal.this, meuSalao.class);
                startActivityForResult(intent, 6);
            }

        } else if (id == R.id.nav_meu_agendamento) {
            Intent intent=new Intent(principal.this,minha_agenda.class);
            startActivityForResult(intent,7);

        } else if (id == R.id.nav_configuracao) {


                //Intent intent=new Intent(principal.this,testeee.class);
                //startActivity(intent);


        } else if (id == R.id.nav_logout) {
            logout.deslogar(this,true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void atualizaTela(String user){
        Menu men = navigationView.getMenu();
        if( user == "gerente")//gerente
        {
            men.findItem(R.id.nav_criarSalao).setVisible(false);
            men.findItem(R.id.nav_meuSalao).setVisible(true);
            men.findItem(R.id.nav_meu_agendamento).setVisible(true);

            Fragment fragment = null;
            fragment = new fragment_principal_gerente();
            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame_principal, fragment);
                ft.commit();
            }
        }
        else
        if(user == "funcionario")//funcionario
        {
            men.findItem(R.id.nav_criarSalao).setVisible(false);
            men.findItem(R.id.nav_meuSalao).setVisible(false);
            men.findItem(R.id.nav_agendamento).setVisible(false);
            men.findItem(R.id.nav_meu_agendamento).setVisible(true);
        }
        else
        if(user == "usuario")
        {
            men.findItem(R.id.nav_criarSalao).setVisible(true);
            men.findItem(R.id.nav_meuSalao).setVisible(false);
        }
        else
        {
            men.findItem(R.id.nav_criarSalao).setVisible(true);
            men.findItem(R.id.nav_meuSalao).setVisible(false);
            men.findItem(R.id.nav_agendamento).setVisible(false);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent intent){
        if(requestCode == 1){
            atualizatipo();
            String qt = atualizaNotificacoes();
            initializeCountDrawer(qt);
        }else
        if(requestCode == 2){
            atualizatipo();
            String qt = atualizaNotificacoes();
            initializeCountDrawer(qt);
        }else
        if(requestCode == 3){
            atualizatipo();
            String qt = atualizaNotificacoes();
            initializeCountDrawer(qt);
        }else
        if(requestCode == 4)
        {
           String qt = atualizaNotificacoes();
           initializeCountDrawer(qt);
        }
        else
        if(requestCode == 5)
        {
            atualizatipo();
            String qt = atualizaNotificacoes();
            initializeCountDrawer(qt);
        }
        else
        if(requestCode == 6)
        {
            atualizatipo();
            String qt = atualizaNotificacoes();
            initializeCountDrawer(qt);
        }
        else
        if(requestCode == 8)
        {
            atualizatipo();
            String qt = atualizaNotificacoes();
            initializeCountDrawer(qt);
        }
}


    public void atualizatipo(){
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<TipoUsuario> callTipos = iApi.tipoUsuario(idLogin);
        callTipos.enqueue(new Callback<TipoUsuario>() {
            @Override
            public void onResponse(Call<TipoUsuario> call, Response<TipoUsuario> response) {



                callTipos.cancel();
                qtTentativaRealizada = 0;
                loading.fechar();
                if(response.isSuccessful()) {
                    TipoUsuario tipo = response.body();

                    int id_suario = -1;
                    int id_funcionario = -1;
                    int id_salao = -1;

                    if(tipo.getIdSalao()>=0) {
                        id_salao = tipo.getIdSalao();
                    }

                    if(tipo.getIdFuncionario()>=0) {
                        id_funcionario = tipo.getIdFuncionario();
                    }

                    if(tipo.getIdUsuario()>=0) {
                        id_suario = tipo.getIdUsuario();
                    }



                    SharedPreferences getSharedPreferencesL = PreferenceManager
                            .getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor e = getSharedPreferencesL.edit();


                    if( id_salao > -1)//gerente
                    {
                        e.putString("typeUserApp","GERENTE");
                        e.putString("idSalao",String.valueOf(id_salao));
                        atualizaTela("gerente");
                    }
                    else
                    if(id_funcionario > -1)//funcionario
                    {
                        e.putString("typeUserApp","FUNCIONARIO");
                        e.putString("idFuncionario",String.valueOf(id_funcionario));
                        atualizaTela("funcionario");
                    }
                    else
                    if(id_suario > -1)
                    {
                        e.putString("typeUserApp","COMUM");
                        atualizaTela("usuario");
                    }
                    else
                    {
                        e.putString("typeUserApp","COMUM");
                        atualizaTela("comum");
                    }
                    e.putString("idUsuario",String.valueOf(tipo.getIdUsuario()));
                    e.apply();
                    e.commit();
                }

            }

            @Override
            public void onFailure(Call<TipoUsuario> call, Throwable t) {
                if(qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    atualizatipo();
                }
                else
                {
                    loading.fechar();
                }
            }
        });
    }

    //---- função para pegar dados do usuario do servidor----
    public void pegarUsuario(final int id_Usuario){
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Usuario>> callBuscaUser = iApi.BuscaUsuario(id_Usuario);
        callBuscaUser.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                loading.fechar();
                callBuscaUser.cancel();
                switch (response.code()) {
                    case 200:
                        qtTentativaRealizadaUser = 0;
                        List<Usuario> users = response.body();
                        Usuario user = users.get(0);
                        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
                        topicoNotificacao.addTopico(user.getTopicoNotificacao());
                        Log.d("xex", "add ao topico -" +user.getTopicoNotificacao() );
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
                if (qtTentativaRealizadaUser < qtTentativas) {
                    qtTentativaRealizadaUser++;
                    pegarUsuario(idLogin);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }
    //-------------------------------------------------------

}
