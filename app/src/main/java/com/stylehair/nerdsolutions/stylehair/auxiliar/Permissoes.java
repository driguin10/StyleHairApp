package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * Created by Rodrigo on 07/02/2018.
 */

public class Permissoes extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_CODE = 128;
    private MaterialDialog mMaterialDialog;
    private Boolean habilitado = false;
    private static String menssagem = "É preciso habilitar a permissão para continuar!!!";




    public boolean habilitarIMagem(Activity activity)
    {
        //**************** Qual permissão ira solicitar **********************
        String permissao = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        //********************************************************************

        if( ContextCompat.checkSelfPermission( activity, permissao ) != PackageManager.PERMISSION_GRANTED )// verifica se tem a permessao
        {
            if( ActivityCompat.shouldShowRequestPermissionRationale( activity,permissao)) {//verifica se o usuario já ignorou a permissao
                callDialog(activity, menssagem, new String[]{permissao});
            }
            else {
                ActivityCompat.requestPermissions(activity, new String[]{permissao}, REQUEST_PERMISSIONS_CODE);// requisita a permissão novamente
            }
        }
        else
        {
            habilitado = true;
        }
        return  habilitado;
    }


    //-----------------------------------------------------------------------------------------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch( requestCode ){
            case REQUEST_PERMISSIONS_CODE:
                for( int i = 0; i < permissions.length; i++ ){
                    if( permissions[i].equalsIgnoreCase( android.Manifest.permission.WRITE_EXTERNAL_STORAGE )
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED )
                    {
                        habilitado = true;
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void callDialog(final Activity activity, String message, final String[] permissions ){
        mMaterialDialog = new MaterialDialog(activity)
                .setTitle("Permission")
                .setMessage( message )
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSIONS_CODE);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }
    //-------------------------------------------------------------------------------------------------------


}
