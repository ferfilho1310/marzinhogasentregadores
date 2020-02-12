package br.com.marzinhogas.entregadores.Controlers.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.marzinhogas.entregadores.Adapters.Adapters;
import br.com.marzinhogas.entregadores.Models.Pedido;
import br.com.marzinhogas.entregadores.R;


public class HomeFragment extends Fragment {

    Query query;
    FirestoreRecyclerOptions<Pedido> fro_pedidos;
    Adapters adapterPedidosCliente;
    RecyclerView rc_pedidos_feitos;

    FirebaseFirestore firebaseAuth = FirebaseFirestore.getInstance();
    CollectionReference cl_pedidos = firebaseAuth.collection("Pedidos");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rc_pedidos_feitos = root.findViewById(R.id.rc_fila_pedido);

        lerpedidosfeitos();

        return root;
    }

    public void lerpedidosfeitos() {

        query = cl_pedidos
                .orderBy("data", Query.Direction.DESCENDING)
                .orderBy("horario", Query.Direction.DESCENDING);

        fro_pedidos = new FirestoreRecyclerOptions.Builder<Pedido>()
                .setQuery(query, Pedido.class)
                .build();

        adapterPedidosCliente = new Adapters(fro_pedidos);
        rc_pedidos_feitos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rc_pedidos_feitos.setAdapter(adapterPedidosCliente);
        rc_pedidos_feitos.setHasFixedSize(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapterPedidosCliente.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterPedidosCliente.stopListening();
    }
}