package br.com.marzinhogas.entregadores.Controlers;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import br.com.marzinhogas.entregadores.Helpers.AccessFirebase;
import br.com.marzinhogas.entregadores.Helpers.AccessResourcesCellPhone;
import br.com.marzinhogas.entregadores.R;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String getUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.pedidos_temp, R.id.pedidos_permanentes, R.id.cadastro_tabela, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        AccessFirebase.getInstance().atualiza_token(FirebaseAuth.getInstance().getUid(), FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.sair) {
            AlertDialog.Builder alert_exit = new AlertDialog.Builder(MainActivity.this);
            alert_exit.setMessage("Você deseja realmente sair ?");

            alert_exit.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AccessFirebase.getInstance().sign_out_firebase(MainActivity.this);
                }
            }).setNegativeButton("Cancelar", null);
            alert_exit.show();
        } else if (id == android.R.id.home) {
            drawer.openDrawer(Gravity.LEFT);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null) {
            getUid = firebaseUser.getUid();
        }
        AccessFirebase.getInstance().validar_usuario(getUid, MainActivity.this);
        AccessFirebase.getInstance().validar_cadastro(AccessResourcesCellPhone.getInstance().getImei(MainActivity.this), MainActivity.this);
    }
}
