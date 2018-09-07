package com.stylehair.nerdsolutions.stylehair.telas.minhaConta;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

public class minhaConta extends AppCompatActivity {
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    public  boolean status = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_minha_conta);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Minha Conta");
        Drawable upArrow = ContextCompat.getDrawable(minhaConta.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(minhaConta.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public  void setStatus(boolean st)
    {
        status = st;
    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragmentUsuario(), "Usuario");
        adapter.addFragment(new fragmentLogin(), "Login");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            Intent data = new Intent();
            if(status)
                data.setData(Uri.parse("userCad"));
            else
                data.setData(Uri.parse(""));

            setResult(RESULT_OK, data);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent data = new Intent();
        if(status)
            data.setData(Uri.parse("userCad"));
        else
            data.setData(Uri.parse(""));
        setResult(RESULT_OK, data);
        finish();
    }
}
