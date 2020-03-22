package br.com.marzinhogas.entregadores.Controlers;

import android.Manifest;
import android.content.DialogInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_entregador);

        checkForPhoneStatePermission();

        FirebaseApp.initializeApp(EntrarEntregador.this);

        AccessFirebase.getInstance().PersistirEntregador(EntrarEntregador.this);

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

    private void checkForPhoneStatePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(EntrarEntregador.this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(EntrarEntregador.this,
                        Manifest.permission.READ_PHONE_STATE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    showPermissionMessage();

                } else {

                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(EntrarEntregador.this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            1);
                }
            }
        } else {
            //... Permission has already been granted, obtain the UUID
            AccessResourcesCellPhone.getInstance().getImei(EntrarEntregador.this);
        }

    }

    private void showPermissionMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Read phone state")
                .setMessage("This app requires the permission to read phone state to continue")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(EntrarEntregador.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},
                                1);
                    }
                }).create().show();
    }


}
