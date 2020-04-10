package br.com.marzinhogas.entregadores.Controlers.Fragments.TabelaDePreço;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import br.com.marzinhogas.entregadores.Helpers.AccessFirebase;
import br.com.marzinhogas.entregadores.Models.PrecoProdutos;
import br.com.marzinhogas.entregadores.R;


public class Tabeladepreco extends Fragment {

    private EditText preco_agua, preco_gas;
    private Button cadastrar_tabela;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.cadastro_tabela_preco, container, false);

        preco_agua = root.findViewById(R.id.ed_preco_da_agua);
        preco_gas = root.findViewById(R.id.ed_preco_da_gas);
        cadastrar_tabela = root.findViewById(R.id.btn_cadastra_tabela);

        try {
            FirebaseFirestore.getInstance().collection("Tabeladepreco").document("precos").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    if (documentSnapshot != null) {
                        PrecoProdutos precoProdutos = new PrecoProdutos();

                        precoProdutos.setPreco_agua(String.valueOf(documentSnapshot.get("precoagua")));
                        precoProdutos.setPreco_gas(String.valueOf(documentSnapshot.get("precogas")));

                        preco_agua.setText(precoProdutos.getPreco_agua());
                        preco_gas.setText(precoProdutos.getPreco_gas());
                    }
                }
            });
        } catch (Exception e) {

            Toast.makeText(getActivity(), "Nenhum preço cadastrado", Toast.LENGTH_LONG).show();
        }

        cadastrar_tabela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrecoProdutos precoProdutos = new PrecoProdutos();

                precoProdutos.setPreco_agua(preco_agua.getText().toString());
                precoProdutos.setPreco_gas(preco_gas.getText().toString());

                AccessFirebase.getInstance().cadastrar_tabela(precoProdutos, getActivity());

            }
        });

        return root;
    }
}