package br.com.marzinhogas.entregadores.Controlers;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.marzinhogas.entregadores.Helpers.AccessFirebase;
import br.com.marzinhogas.entregadores.Helpers.AccessResourcesCellPhone;
import br.com.marzinhogas.entregadores.Helpers.IntentsHelper;
import br.com.marzinhogas.entregadores.R;

public class EntrarEntregador extends AppCompatActivity {

    TextView clique_aqui,imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_entregador);

        AccessResourcesCellPhone.getInstance().checkForPhoneStatePermissionImei(EntrarEntregador.this);
        FirebaseApp.initializeApp(EntrarEntregador.this);

        clique_aqui = findViewById(R.id.txt_clique_aqui);
        imei = findViewById(R.id.imei_celular);
        AccessFirebase.getInstance().PersistirEntregador(EntrarEntregador.this);

        imei.setText(AccessResourcesCellPhone.getInstance().getImei(EntrarEntregador.this));

        clique_aqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_clique_aqui = new Intent(EntrarEntregador.this, Resetsenha.class);
                startActivity(i_clique_aqui);
            }
        });

        init();
    }

    public void init() {

        Button entrar = findViewById(R.id.btn_entrar);
        final EditText email_entrar = findViewById(R.id.ed_email_entrar);
        final EditText senha_entrar = findViewById(R.id.ed_senha_entrar);
        TextView cadastrar_textview = findViewById(R.id.txt_cadastre_se);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessFirebase.getInstance().entrar_firebase(email_entrar.getText().toString(),
                        senha_entrar.getText().toString(), EntrarEntregador.this);
            }
        });

        cadastrar_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentsHelper().intent(EntrarEntregador.this, CadastrarEntregador.class);
            }
        });
    }
}
