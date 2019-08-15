package com.example.usuario.univalle19;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.univalle19.Servicios.CurrentDate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Servicio.OnFragmentInteractionListener{

    Button btn1,btn2,btn3, btn4, btn5, btn6;
    EditText passw, userName;
    TextView tvContador;
    Pintar obj;
    public int contador = 0;


    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"hola create",Toast.LENGTH_LONG).show();
        //enlace
        //btn1 = findViewById(R.id.btn1);
        //btn1.setOnClickListener(this);
        //enlace
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarContrasena();
            }
        });
        //enlace
        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        //enlace
        tvContador = findViewById(R.id.tvContador);
        //enlace
        btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        //enlace
        btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        passw = findViewById(R.id.edPasswd);
        userName = findViewById(R.id.edUser);
    }

    public void recuperarContrasena(){
        Servicio fragment = new Servicio();
        fragmentTransaction.add(R.id.fragmentRecuperar, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"hola destroy",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"hola start",Toast.LENGTH_LONG).show();
    }

    public  void irHome(View v){
        this.navegar();
    }
    public  void navegar(){
        String passwd = passw.getText().toString();
        String user = userName.getText().toString();
        if(passwd.length() > 0 && user.length() > 0){
            Bundle data = new Bundle();
            data.putString("username",user);
            data.putString("passwd",passwd);
            Intent ir = new Intent(this, Main2Activity.class);
            ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
            ir.putExtras(data);
            startActivity(ir);
        } else {
            Toast.makeText(this,"Debe ngresar usernaem y passwd",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn4:
                this.colorear();
                break;
            case R.id.btn5:
                this.contar();
                break;
            case R.id.btn6:
                this.iniciarServicio();
                break;
            default: return;

        }
    }

    public void iniciarServicio(){
        Intent servicio = new Intent(this, CurrentDate.class);
        startService(servicio);
    }

    public void contar(){
        contador++;
        tvContador.setText("contador: "+contador);
    }

    public void colorear(){
        /*for (int i=0; i<= 10; i++){
            try {
                btn4.setBackgroundColor(Color.rgb(this.aleatorio(),this.aleatorio(),this.aleatorio()));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        obj = new Pintar();
        obj.execute(1);
    }

    public int aleatorio(){
        return (int) Math.ceil(Math.random()* 255 + 1);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class Pintar extends AsyncTask<Integer,Integer,Void>{

        @Override
        protected Void doInBackground(Integer... voids) {
            for (int i=0; i<= 100; i++){
                try {
                    publishProgress(i);
                    // btn4.setBackgroundColor(Color.rgb(this.aleatorio(),this.aleatorio(),this.aleatorio()));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btn4.setBackgroundColor(Color.rgb(this.aleatorio(),this.aleatorio(),this.aleatorio()));
        }

        public int aleatorio(){
            return (int) Math.ceil(Math.random()* 255 + 1);
        }
    }
}
