package br.com.marzinhogas.entregadores.Controlers.Fragments.PedidoPermanentes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.marzinhogas.entregadores.Adapters.AdapterPedidoRegistrados;
import br.com.marzinhogas.entregadores.Adapters.AdapterViewEmpty;
import br.com.marzinhogas.entregadores.Models.Pedido;
import br.com.marzinhogas.entregadores.R;

public class PedidosPermanentes extends Fragment {

    private Query query;
    private FirestoreRecyclerOptions<Pedido> fro_pedidos;
    private AdapterPedidoRegistrados adapterPedidosCliente;
    private RecyclerView rc_pedidos_feitos;

    private FirebaseFirestore firebaseAuth = FirebaseFirestore.getInstance();
    private CollectionReference cl_pedidos = firebaseAuth.collection("PedidoPerm");

    private TextView txt_registro_permanente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        rc_pedidos_feitos = root.findViewById(R.id.rc_fila_pedido_registro);
        txt_registro_permanente = root.findViewById(R.id.txt_registro_pedido_permanente);

        lerpedidosfeitos();

        AdapterViewEmpty adapterViewEmpty = new AdapterViewEmpty(txt_registro_permanente,rc_pedidos_feitos);
        adapterPedidosCliente.registerAdapterDataObserver(adapterViewEmpty);

        return root;
    }

    private void lerpedidosfeitos() {

        query = cl_pedidos
                .orderBy("data", Query.Direction.ASCENDING);

        fro_pedidos = new FirestoreRecyclerOptions.Builder<Pedido>()
                .setQuery(query, Pedido.class)
                .build();

        adapterPedidosCliente = new AdapterPedidoRegistrados(fro_pedidos);
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