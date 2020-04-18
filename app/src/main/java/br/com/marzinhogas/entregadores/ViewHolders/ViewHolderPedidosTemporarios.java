package br.com.marzinhogas.entregadores.ViewHolders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.marzinhogas.entregadores.R;

public class ViewHolderPedidosTemporarios extends RecyclerView.ViewHolder {

    public TextView nome, produtos, data, qtd_gas, qtd_agua, endereco, horario_pedido;

    public ViewHolderPedidosTemporarios(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.txt_nome_do_pedido);
        produtos = itemView.findViewById(R.id.txt_produto_pedido);
        data = itemView.findViewById(R.id.txt_data_pedido);
        qtd_agua = itemView.findViewById(R.id.txt_qtd_agua);
        qtd_gas = itemView.findViewById(R.id.txt_qtd_gas);
        endereco = itemView.findViewById(R.id.txt_endereco_cliente);
        horario_pedido = itemView.findViewById(R.id.txt_horario_pedido);
    }
}
