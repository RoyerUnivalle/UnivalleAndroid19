package com.example.usuario.univalle19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn1,btn2,btn3;
    EditText passw, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"hola create",Toast.LENGTH_LONG).show();
        //enlace
        //btn1 = findViewById(R.id.btn1);
        //btn1.setOnClickListener(this);
        //enlace
        //btn3 = findViewById(R.id.btn3);
        /*btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ir = new Intent(getApplicationContext(), Main2Activity.class);
                ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ir);
            }
        });*/
        passw = findViewById(R.id.edPasswd);
        userName = findViewById(R.id.edUser);
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
        /*switch (view.getId()){
            case R.id.btn1:
                this.navegar();
                break;
            default: return;

        }*/
    }
}
