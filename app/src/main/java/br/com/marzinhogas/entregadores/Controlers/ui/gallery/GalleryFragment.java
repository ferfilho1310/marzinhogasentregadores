package br.com.marzinhogas.entregadores.Controlers.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.marzinhogas.entregadores.Adapters.AdapterPedidoRegistrados;
import br.com.marzinhogas.entregadores.Adapters.AdaptersPedidosTemporarios;
import br.com.marzinhogas.entregadores.Models.Pedido;
import br.com.marzinhogas.entregadores.R;

public class GalleryFragment extends Fragment {

    private Query query;
    private FirestoreRecyclerOptions<Pedido> fro_pedidos;
    private AdapterPedidoRegistrados adapterPedidosCliente;
    private RecyclerView rc_pedidos_feitos;

    private FirebaseFirestore firebaseAuth = FirebaseFirestore.getInstance();
    private CollectionReference cl_pedidos = firebaseAuth.collection("PedidoReg");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        rc_pedidos_feitos = root.findViewById(R.id.rc_fila_pedido_registro);

        lerpedidosfeitos();

        return root;
    }

    private void lerpedidosfeitos() {

        query = cl_pedidos
                .orderBy("data", Query.Direction.DESCENDING)
                .orderBy("horario", Query.Direction.DESCENDING);

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