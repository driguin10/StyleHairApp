package com.stylehair.nerdsolutions.stylehair.telas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
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
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Permissoes;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.CriaBancoNotificacao;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.auxiliar.AtualizaInfos;
import com.stylehair.nerdsolutions.stylehair.telas.favorito.saloesFavoritos;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.meuSalao;
import com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.minha_agenda;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.minhaConta;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;


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
    Loading loading;
    AtualizaInfos atualizaInfos ;
    SharedPreferences getSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bem vindo");
        loading = new Loading(principal.this);
        verificaConexao = new VerificaConexao();
        logout = new Logout();
         atualizaInfos = new AtualizaInfos(principal.this);
         getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        idLogin = getSharedPreferences.getInt("idLogin", -1);
        typeUser = getSharedPreferences.getString("typeUserApp","COMUM");
        nomeUsuario = getSharedPreferences.getString("nomeUser","");
        linkImagem = getSharedPreferences.getString("linkImagem","");
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
            loading.abrir("Aguarde...");
            atualizaInfos.atualizatipo(true);
        }
        else
        {
            Toast.makeText(getBaseContext(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
            Intent intent=new Intent(principal.this,minha_agenda.class);
            intent.putExtra("tipo","0");
            startActivityForResult(intent,7);

        } else if (id == R.id.nav_notificacoes) {
            Intent intent=new Intent(principal.this,notificacao.class);
            startActivityForResult(intent,4);

        } else if (id == R.id.nav_criarSalao) {
            String user = getSharedPreferences.getString("typeUserApp","COMUM");
            if(!user.equals("COMUM"))
            {
                if(permissoes.habilitarLocalizacao(principal.this)) {
                    Intent intent = new Intent(principal.this, cadastroSalao.class);
                    startActivityForResult(intent, 5);
                }
            }
            else
            {
                new AlertDialog.Builder(this)
                        .setTitle("Deseja concluir seu cadastro?")
                        .setMessage("Para cadastrar um Salão é necessario concluir seu cadastro.")
                        .setIcon(R.drawable.icone_funcionario_preto)
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(principal.this, minhaConta.class);
                               startActivityForResult(intent, 0);
                            }
                        })
                        .setNegativeButton("não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }

        } else if (id == R.id.nav_meuSalao) {
            if(permissoes.habilitarLocalizacao(principal.this)) {
                Intent intent = new Intent(principal.this, meuSalao.class);
                startActivityForResult(intent, 6);
            }

        } else if (id == R.id.nav_meu_agendamento) {
            Intent intent=new Intent(principal.this,minha_agenda.class);
            intent.putExtra("tipo","1");
            startActivityForResult(intent,7);

        } else if (id == R.id.nav_configuracao) {
               Intent intent=new Intent(principal.this,configuracaoApp.class);
                startActivity(intent);
        } else if (id == R.id.nav_logout) {
            logout.deslogar(this,true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    //é chamado apenas pela classe Atualizainfos
    public void atualizaTela(){

        Menu men = navigationView.getMenu();
        String user = getSharedPreferences.getString("typeUserApp","COMUM");
        nomeUsuario = getSharedPreferences.getString("nomeUser","");
        linkImagem = getSharedPreferences.getString("linkImagem","");

        NomeDrawer.setText(nomeUsuario);
        if(linkImagem!="")
            Picasso.with(getBaseContext()).load("http://stylehair.xyz/" + linkImagem).into(imgUser);
        else
            imgUser.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));

        if( user == "GERENTE")//gerente
        {
            men.findItem(R.id.nav_criarSalao).setVisible(false);
            men.findItem(R.id.nav_meuSalao).setVisible(true);
            men.findItem(R.id.nav_meu_agendamento).setVisible(true);
            men.findItem(R.id.nav_agendamento).setVisible(true);
            Fragment fragment = null;
            fragment = new fragment_principal_gerente();
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame_principal, fragment);
                ft.commitAllowingStateLoss();
            }
        }
        else
        if(user == "FUNCIONARIO")//funcionario
        {
            men.findItem(R.id.nav_criarSalao).setVisible(false);
            men.findItem(R.id.nav_meuSalao).setVisible(false);
            men.findItem(R.id.nav_agendamento).setVisible(true);
            men.findItem(R.id.nav_meu_agendamento).setVisible(true);
            Fragment fragment = null;
            fragment = new fragment_principal_gerente();
            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame_principal, fragment);
                ft.commitAllowingStateLoss();
            }
        }
        else {
            if (user == "COMUMUSER") {
                men.findItem(R.id.nav_criarSalao).setVisible(true);
                men.findItem(R.id.nav_meuSalao).setVisible(false);
                men.findItem(R.id.nav_agendamento).setVisible(true);
                men.findItem(R.id.nav_meu_agendamento).setVisible(false);
            } else {
                men.findItem(R.id.nav_criarSalao).setVisible(true);
                men.findItem(R.id.nav_meuSalao).setVisible(false);
                men.findItem(R.id.nav_agendamento).setVisible(false);
            }

            Fragment fragment = null;
            fragment = new fragment_principal_usuario();
            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame_principal, fragment);
                ft.commitAllowingStateLoss();
            }
        }
        loading.fechar();
    }

    @Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent intent)
    {
        initializeCountDrawer(atualizaNotificacoes());
        atualizaInfos.atualizatipo(true);
    }

    @Override
    public void onResume(){
        super.onResume();
      //  Appodeal.onResume(principal.this, Appodeal.BANNER_BOTTOM);
    }

}
