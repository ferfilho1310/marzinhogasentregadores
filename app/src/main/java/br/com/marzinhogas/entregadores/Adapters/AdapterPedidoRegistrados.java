package br.com.marzinhogas.entregadores.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.com.marzinhogas.entregadores.Models.Pedido;
import br.com.marzinhogas.entregadores.R;
import br.com.marzinhogas.entregadores.ViewHolders.ViewHolderPedidosTemporarios;

public class AdapterPedidoRegistrados extends FirestoreRecyclerAdapter<Pedido, ViewHolderPedidosTemporarios> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public AdapterPedidoRegistrados(@NonNull FirestoreRecyclerOptions<Pedido> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolderPedidosTemporarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderPedidosTemporarios(LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidos, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderPedidosTemporarios holder, int position, @NonNull Pedido model) {

        String endereco = model.getEndereco() + ", " + model.getNumero() + ", \n" + model.getBairro() + "\n" + model.getComplemento();

        holder.nome.setText(model.getNome());
        holder.produtos.setText(model.getProduto());
        holder.qtd_gas.setText(String.valueOf(model.getQuantidade_gas()));
        holder.qtd_agua.setText(String.valueOf(model.getQuantidade_agua()));
        holder.data.setText(model.getData());
        holder.endereco.setText(endereco);
        holder.horario_pedido.setText(model.getHorario());
    }
}
