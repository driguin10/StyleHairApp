package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Toast;


import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.SectionsPageAdapter;


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
        getSupportActionBar().setTitle("Funcionário");
        Drawable upArrow = ContextCompat.getDrawable(ver_funcionario.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(ver_funcionario.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.containerFuncionario);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsFuncionario);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.icone_funcionario_preto));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.icone_time));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.icone_servicos));
        tabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.icone_time));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch ( tab.getPosition())
                {
                    case 0:
                        getSupportActionBar().setTitle("Funcionário");
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Horários");
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Serviços");
                        break;
                    case 3:
                        getSupportActionBar().setTitle("Férias");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_funcionario(), "");
        adapter.addFragment(new fragment_horarios_funcionario(), "");
        adapter.addFragment(new fragment_servicos_funcionarios(), "");
        adapter.addFragment(new fragment_ferias_funcionario(), "");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                Intent intent = new Intent(ver_funcionario.this,funcionarios.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            default:break;
        }
        return true;
    }
}
