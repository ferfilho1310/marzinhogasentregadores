package br.com.marzinhogas.entregadores.Controlers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.marzinhogas.entregadores.Helpers.AccessFirebase;
import br.com.marzinhogas.entregadores.R;

public class Resetsenha extends AppCompatActivity {

    Button resetar_senha;
    EditText email_para_envio_de_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetsenha);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Altere sua senha");

        resetar_senha = findViewById(R.id.btn_alterar_senha);
        email_para_envio_de_senha = findViewById(R.id.ed_recuperar_senha);

        resetar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessFirebase.getInstance().reset_senha(email_para_envio_de_senha.getText().toString(), Resetsenha.this);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i_back = new Intent(Resetsenha.this, EntrarEntregador.class);
                startActivity(i_back);
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
