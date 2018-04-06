package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.Notification.ver_notificacao;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.SectionsPageAdapter;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.fragmentLogin;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.fragmentUsuario;

public class ver_funcionario extends AppCompatActivity {
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_funcionario);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_verFuncionario);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Funcionario");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.containerFuncionario);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsFuncionario);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.icone_funcionario));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.icone_time));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.icone_servicos));


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            String id = bundle.getString("idUsuario");
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_funcionario(), "");
        adapter.addFragment(new fragment_horarios_funcionario(), "");
        adapter.addFragment(new fragment_servicos_funcionarios(), "");


        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                finish();
                break;
            default:break;
        }
        return true;
    }
}
