package br.com.marzinhogas.entregadores.Controlers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.marzinhogas.entregadores.Helpers.AccessFirebase;
import br.com.marzinhogas.entregadores.Models.Entregador;
import br.com.marzinhogas.entregadores.R;

public class CadastrarEntregador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_entregador);

        AccessFirebase.getInstance().PersistirEntregador(CadastrarEntregador.this);

        init();

    }

    public void init() {

        Button cadastrar = findViewById(R.id.btn_cadastrar_user);
        final EditText nome = findViewById(R.id.ed_nome);
        final EditText email_cadastrar = findViewById(R.id.ed_email);
        final EditText senha = findViewById(R.id.ed_senha);
        final EditText senha_confirmar = findViewById(R.id.ed_confirmar_senha);
        final String token = FirebaseInstanceId.getInstance().getToken();

        FirebaseFirestore.getInstance().collection("Entregadores")
                .document()
                .update("token",token);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Entregador entregador = new Entregador();

                entregador.setNome(nome.getText().toString());
                entregador.setEmail(email_cadastrar.getText().toString());
                entregador.setSenha(senha.getText().toString());
                entregador.setConfirmarsenha(senha_confirmar.getText().toString());
                entregador.setToken(token);

                AccessFirebase.getInstance().CadastrarEntregador(entregador, CadastrarEntregador.this);

            }
        });

    }


}
