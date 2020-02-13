package br.com.marzinhogas.entregadores.Messaging;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PedidoAplication extends Application implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

        setOnLine(true);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

        setOnLine(false);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    private void setOnLine(boolean enabled){

        String uid = FirebaseAuth.getInstance().getUid();

        if(uid != null){

            FirebaseFirestore.getInstance().collection("Entregadores")
                    .document(uid)
                    .update("online",enabled);

        }

    }
}
