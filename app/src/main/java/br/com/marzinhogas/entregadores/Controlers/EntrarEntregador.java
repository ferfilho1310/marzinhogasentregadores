package br.com.marzinhogas.entregadores.Controlers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import br.com.marzinhogas.entregadores.Helpers.AccessFirebase;
import br.com.marzinhogas.entregadores.Helpers.IntentsHelper;
import br.com.marzinhogas.entregadores.R;

public class EntrarEntregador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_entregador);

        FirebaseApp.initializeApp(EntrarEntregador.this);

        AccessFirebase.getInstance().PersistirEntregador(EntrarEntregador.this);

        init();
    }

    public void init(){

        Button entrar = findViewById(R.id.btn_entrar);
        final EditText email_entrar = findViewById(R.id.ed_email_entrar);
        final EditText senha_entrar = findViewById(R.id.ed_senha_entrar);
        TextView cadastrar_textview = findViewById(R.id.txt_cadastre_se);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AccessFirebase.getInstance().entrar_firebase(email_entrar.getText().toString(),
                        senha_entrar.getText().toString(),EntrarEntregador.this);
            }
        });

        cadastrar_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new IntentsHelper().intent(EntrarEntregador.this,CadastrarEntregador.class);


            }
        });

    }

}
