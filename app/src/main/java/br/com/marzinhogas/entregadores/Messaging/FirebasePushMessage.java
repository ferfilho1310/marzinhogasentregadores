package br.com.marzinhogas.entregadores.Messaging;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.stats.WakeLock;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.com.marzinhogas.entregadores.Controlers.MainActivity;
import br.com.marzinhogas.entregadores.Models.Pedido;
import br.com.marzinhogas.entregadores.R;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class FirebasePushMessage extends FirebaseMessagingService {

    PowerManager.WakeLock wl;
    PowerManager.WakeLock wl_cpu;
    PowerManager pm;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        AudioManager audioManager = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);

        final Map<String, String> map = remoteMessage.getData();

        String id_entregador = map.get("id_entregador");

        if (map == null || id_entregador == null) return;

        final Intent i_entregadores = new Intent(this, MainActivity.class);

        FirebaseFirestore.getInstance().collection("notifications")
                .document(id_entregador)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if (documentSnapshot != null) {

                            Pedido pedido = documentSnapshot.toObject(Pedido.class);

                            i_entregadores.putExtra("pedido", String.valueOf(pedido));

                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                    0, i_entregadores, 0);

                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            String notificacionid = "channel_id";

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                NotificationChannel notificationChannel =
                                        new NotificationChannel(notificacionid, "notificacion",
                                                NotificationManager.IMPORTANCE_HIGH);

                                notificationChannel.setDescription("Channel Description");
                                notificationChannel.enableLights(true);
                                notificationChannel.setLightColor(Color.GREEN);
                                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                                notificationChannel.getSound();
                                notificationChannel.shouldShowLights();
                                notificationManager.createNotificationChannel(notificationChannel);
                            }

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), notificacionid);

                            String pedidos = map.get("body");

                            builder.setColor(Color.WHITE)
                                    .setSmallIcon(R.drawable.logo_margzinho_entregadores)
                                    .setContentTitle("Você tem entrega para fazer")
                                    .setContentText(pedidos)
                                    .setAutoCancel(true)
                                    .setLights(0xff00ff00, 300, 100)
                                    .setPriority(Notification.PRIORITY_MAX)
                                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                                    .setStyle(new NotificationCompat.BigTextStyle().bigText(pedidos))
                                    .setContentIntent(pendingIntent)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE);

                            notificationManager.notify(1, builder.build());
                        }
                    }
                });

        pm = (PowerManager) getApplication().getSystemService(Context.POWER_SERVICE);
        boolean displayon = pm.isScreenOn();

        if (displayon != true) {

            wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
            wl.acquire(10000);
            wl_cpu = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyCpuLock");
            wl_cpu.acquire(10000);
        }
    }
}
