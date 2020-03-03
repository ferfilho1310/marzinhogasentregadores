package br.com.marzinhogas.entregadores.Controlers.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.marzinhogas.entregadores.Adapters.AdapterViewEmpty;
import br.com.marzinhogas.entregadores.Adapters.AdaptersPedidosTemporarios;
import br.com.marzinhogas.entregadores.Models.Pedido;
import br.com.marzinhogas.entregadores.R;


public class HomeFragment extends Fragment {

    Query query;
    FirestoreRecyclerOptions<Pedido> fro_pedidos;
    AdaptersPedidosTemporarios adapterPedidosCliente;
    RecyclerView rc_pedidos_feitos;
    TextView txt_registro_temp;

    FirebaseFirestore firebaseAuth = FirebaseFirestore.getInstance();
    CollectionReference cl_pedidos = firebaseAuth.collection("PedidoTemp");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        rc_pedidos_feitos = root.findViewById(R.id.rc_fila_pedido);
        txt_registro_temp = root.findViewById(R.id.txt_registro_pedido_temporario);

        lerpedidosfeitos();

        AdapterViewEmpty adapterViewEmpty = new AdapterViewEmpty(txt_registro_temp,rc_pedidos_feitos);
        adapterPedidosCliente.registerAdapterDataObserver(adapterViewEmpty);

        return root;
    }

    private void lerpedidosfeitos() {

        query = cl_pedidos
                .orderBy("horario", Query.Direction.ASCENDING);

        fro_pedidos = new FirestoreRecyclerOptions.Builder<Pedido>()
                .setQuery(query, Pedido.class)
                .build();

        adapterPedidosCliente = new AdaptersPedidosTemporarios(fro_pedidos);
        rc_pedidos_feitos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rc_pedidos_feitos.setAdapter(adapterPedidosCliente);
        rc_pedidos_feitos.setHasFixedSize(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapterPedidosCliente.deletepedido(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(rc_pedidos_feitos);

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