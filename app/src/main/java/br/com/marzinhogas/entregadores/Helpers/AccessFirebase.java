package br.com.marzinhogas.entregadores.Helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.marzinhogas.entregadores.Controlers.EntrarEntregador;
import br.com.marzinhogas.entregadores.Controlers.MainActivity;
import br.com.marzinhogas.entregadores.Models.Entregador;

public class AccessFirebase implements IAcessFirebase {

    private static AccessFirebase accessFirebase;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    CollectionReference db_entregadores = FirebaseFirestore.getInstance().collection("Entregadores");

    private AccessFirebase() {
    }

    public static synchronized AccessFirebase getInstance() {

        if (accessFirebase == null) {
            accessFirebase = new AccessFirebase();
        }
        return accessFirebase;
    }

    @Override
    public void CadastrarEntregador(final Entregador entregador, final Activity activity) {

        FirebaseApp.initializeApp(activity);

        if (TextUtils.isEmpty(entregador.getNome())) {
            Toast.makeText(activity, "Digite seu nome", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(entregador.getEmail())) {
            Toast.makeText(activity, "Informe um e-mail.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(entregador.getSenha())) {
            Toast.makeText(activity, "Informe uma senha.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(entregador.getConfirmarsenha())) {

            Toast.makeText(activity, "Confirme a senha", Toast.LENGTH_LONG).show();
            return;
        }

        if (entregador.getSenha().equals(entregador.getConfirmarsenha())) {

            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Cadastrando...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(entregador.getEmail(), entregador.getSenha()).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        Map<String, Object> map = new HashMap<>();

                        map.put("id", "E");
                        map.put("online", entregador.isOnline());
                        map.put("id_user", firebaseAuth.getUid());
                        map.put("nome", entregador.getNome());
                        map.put("email", entregador.getEmail());
                        map.put("senha", entregador.getSenha());
                        map.put("confirmarsenha", entregador.getConfirmarsenha());
                        map.put("sexo", entregador.getSexo());
                        map.put("token", entregador.getToken());

                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);

                        db_entregadores.add(map);

                        Toast.makeText(activity, "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG).show();

                    } else if (!task.isSuccessful()) {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {

                            Toast.makeText(activity, "Senha inferior a 6 caracteres", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } catch (FirebaseAuthInvalidCredentialsException e) {

                            Toast.makeText(activity, "E-mail inválido", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } catch (FirebaseAuthUserCollisionException e) {

                            Toast.makeText(activity, "Usuário já cadastrado", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } catch (Exception e) {

                            Toast.makeText(activity, "Ops!Erro a cadastrar o usuário", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }

            });
        } else {

            Toast.makeText(activity, "As senhas estão diferentes.", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void PersistirEntregador(Activity activity) {

        FirebaseApp.initializeApp(activity);

        if (firebaseAuth.getCurrentUser() != null) {

            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    @Override
    public void sign_out_firebase(Activity activity) {

        FirebaseApp.initializeApp(activity);

        Intent intent = new Intent(activity, EntrarEntregador.class);
        activity.startActivity(intent);
        activity.finish();

        firebaseAuth.signOut();
    }

    @Override
    public void entrar_firebase(String email, String senha, final Activity activity) {

        FirebaseApp.initializeApp(activity);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(activity, "Digite seu e-mail", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(activity, "Informe uma senha.", Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(activity);

        progressDialog.setMessage("Entrando...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()) {

                        Intent i_entrar_prof = new Intent(activity, MainActivity.class);
                        i_entrar_prof.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(i_entrar_prof);
                        activity.finish();

                        Toast.makeText(activity, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(activity, "Erro ao efetuar o login. Verifique os dados digitados", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {

                    Toast.makeText(activity, "Ops! Ocorreu um erro inesperado.", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void reset_senha(final String email, final Activity context) {

        FirebaseApp.initializeApp(context);

        if (TextUtils.isEmpty(email)) {

            Toast.makeText(context, "Informe um e-mail.", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                try {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(context, EntrarEntregador.class);
                        context.startActivity(intent);
                        context.finish();

                        Toast.makeText(context, "Enviado e-mail para reset de senha para " + email, Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(context, "E-mail inválido", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    Toast.makeText(context, "Erro ao enviar e-mail de recuperação:" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void validar_usuario(final String id_user_validacao, final Activity activity) {

        db_entregadores
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        List<String> id_user = new ArrayList<>();

                        QuerySnapshot queryDocumentSnapshots = task.getResult();

                        for (Entregador entregador1 : queryDocumentSnapshots.toObjects(Entregador.class)) {

                            Log.i("Dados do entregador", "Entregador: " + entregador1.getId_user());

                            id_user.add(entregador1.getId_user());
                        }

                        if (usuario_existe(id_user, id_user_validacao)) {

                        }else {
                            sign_out_firebase(activity);
                            Toast.makeText(activity,"Acesso negado",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    public boolean usuario_existe(List<String> ls_usuario, String user) {
        boolean usario_validar = false;

        for (int i = 0; i < ls_usuario.size(); i++) {
            if (user.equals(ls_usuario.get(i))) {
                usario_validar = true;
            }
        }
        return usario_validar;
    }
}

