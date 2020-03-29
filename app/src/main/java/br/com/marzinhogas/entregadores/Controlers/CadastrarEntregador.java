package br.com.marzinhogas.entregadores.Controlers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.marzinhogas.entregadores.Helpers.AccessFirebase;
import br.com.marzinhogas.entregadores.Helpers.AccessResourcesCellPhone;
import br.com.marzinhogas.entregadores.Models.Entregador;
import br.com.marzinhogas.entregadores.R;

public class CadastrarEntregador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_entregador);

        AccessFirebase.getInstance().PersistirEntregador(CadastrarEntregador.this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Cadastre-se");

        init();
    }

    public void init() {

        Button cadastrar = findViewById(R.id.btn_cadastrar_user);
        final EditText nome = findViewById(R.id.ed_nome);
        final EditText email_cadastrar = findViewById(R.id.ed_email);
        final EditText senha = findViewById(R.id.ed_senha);
        final EditText senha_confirmar = findViewById(R.id.ed_confirmar_senha);
        final String token = FirebaseInstanceId.getInstance().getToken();
        final EditText cpf = findViewById(R.id.ed_cpf);
        final EditText placa = findViewById(R.id.ed_placa);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Entregador entregador = new Entregador();

                entregador.setNome(nome.getText().toString());
                entregador.setEmail(email_cadastrar.getText().toString());
                entregador.setSenha(senha.getText().toString());
                entregador.setConfirmarsenha(senha_confirmar.getText().toString());
                entregador.setToken(token);
                entregador.setCpf(cpf.getText().toString());
                entregador.setPlaca(placa.getText().toString());

                AccessFirebase.getInstance().CadastrarEntregador(entregador, CadastrarEntregador.this);
                validartelefone(CadastrarEntregador.this);

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void validartelefone(final Activity activity){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {

                AccessFirebase.getInstance().validar_cadastro(AccessResourcesCellPhone.getInstance().getImei(activity),activity);
                return null;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){

            Intent i_voltar_tela_inicial = new Intent(CadastrarEntregador.this,EntrarEntregador.class);
            startActivity(i_voltar_tela_inicial);
            finish();

        }
        return false;
    }
}
