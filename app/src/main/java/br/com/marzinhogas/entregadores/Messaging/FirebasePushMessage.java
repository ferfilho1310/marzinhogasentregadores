package br.com.marzinhogas.entregadores.Messaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

public class FirebasePushMessage extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        final Map<String, String> map = remoteMessage.getData();

        final String id_entregador = map.get("id_entregador");

        if (map == null || id_entregador == null) return;

        final Intent i_entregadores = new Intent(this, MainActivity.class);

        FirebaseFirestore.getInstance().collection("notifications")
                .document(id_entregador)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        List<Pedido> pedido = Collections.singletonList(documentSnapshot.toObject(Pedido.class));

                        for(Pedido pedido1 : pedido){

                            pedido.add(pedido1);

                            i_entregadores.putExtra("pedido", pedido1);

                        }

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
                            notificationManager.createNotificationChannel(notificationChannel);

                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), notificacionid);

                        String pedidos = "Nome: " + map.get("nome_cliente") + "\nEndereço: " +
                                map.get("endereco_cliente") + "\nProduto: " + map.get("produto_cliente")
                                + "\nHorário: " + map.get("hora_pedido");

                        builder.setColor(Color.WHITE)
                                .setSmallIcon(R.drawable.logo_entrada)
                                .setContentTitle("Você tem uma entrega para fazer")
                                .setContentText(pedidos)
                                .setAutoCancel(true)
                                .setPriority(Notification.PRIORITY_MAX)
                                .setDefaults(Notification.DEFAULT_LIGHTS)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(pedidos))
                                .setContentIntent(pendingIntent);

                        notificationManager.notify(1, builder.build());

                    }
                });

    }
}
