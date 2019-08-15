package com.example.usuario.univalle19;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
//import android.app.FragmentTransaction;

public class Main2Activity extends AppCompatActivity implements EjemploHttp.OnFragmentInteractionListener, Servicio.OnFragmentInteractionListener {

    EjemploHttp fragmento1;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle delivery = getIntent().getExtras();
        String userName = delivery.getString("username");
        //Toast.makeText(this,"Hola "+userName,Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hola "+userName)
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent ir = new Intent(getApplicationContext(), MainActivity.class);
                ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ir);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public  void irLogin(View v){
        Intent ir = new Intent(this, MainActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }
    public  void viewHttpExample(View v){
        httpExample();
    }

    public void httpExample(){
        fragmento1 = new EjemploHttp();
        fragmentTransaction.add(R.id.fragmentA, fragmento1);
        fragmentTransaction.commit();
    }

    public void recuperarContrasena(View v){
        Servicio fragment = new Servicio();
        fragmentTransaction.add(R.id.fragmentA, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
