package br.com.marzinhogas.entregadores.Controlers.Fragments.CadastrarDispositivo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.com.marzinhogas.entregadores.Adapters.AdapterCadastroCelular;
import br.com.marzinhogas.entregadores.Adapters.AdapterPedidoRegistrados;
import br.com.marzinhogas.entregadores.Adapters.AdapterViewEmpty;
import br.com.marzinhogas.entregadores.Helpers.AccessFirebase;
import br.com.marzinhogas.entregadores.Models.Imei;
import br.com.marzinhogas.entregadores.Models.Pedido;
import br.com.marzinhogas.entregadores.R;


public class Cadastrodispositivo extends Fragment {

    private RecyclerView rc_cadastro_dispositivo;
    private TextView txt_no_cellphone_cadastro;
    private AdapterCadastroCelular adapterCadastroCelular;
    private FloatingActionButton add_celular;

    private Query query;
    private FirestoreRecyclerOptions<Imei> fro_cadastro_celular;

    private FirebaseFirestore firebaseAuth = FirebaseFirestore.getInstance();
    private CollectionReference cl_imei = firebaseAuth.collection("Imei");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.cadastro_dispositivo, container, false);

        rc_cadastro_dispositivo = root.findViewById(R.id.rc_cadastro_dispositivo);
        txt_no_cellphone_cadastro = root.findViewById(R.id.sem_dispositivo);
        add_celular = root.findViewById(R.id.fab_cadastrar_dispositivo);

        lerdadofirebase(getActivity());

        add_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adicionardispositivos();

            }
        });

        AdapterViewEmpty adapterViewEmpty = new AdapterViewEmpty(txt_no_cellphone_cadastro, rc_cadastro_dispositivo);
        adapterCadastroCelular.registerAdapterDataObserver(adapterViewEmpty);

        return root;
    }

    private void adicionardispositivos() {

        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_cadastro_dispositivo);

        final EditText nome_dono_celular = dialog.findViewById(R.id.ed_nome_dono_celular);
        final EditText imei_celular = dialog.findViewById(R.id.imei_do_celular);
        Button cadastro_celular = dialog.findViewById(R.id.btn_cadastrar_telefone);

        cadastro_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Imei imei = new Imei();

                imei.setUsuariodocelular(nome_dono_celular.getText().toString());
                imei.setImei(imei_celular.getText().toString());

                AccessFirebase.getInstance().cadastro_celular(imei);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void lerdadofirebase(Context context) {

        query = cl_imei;

        fro_cadastro_celular = new FirestoreRecyclerOptions.Builder<Imei>()
                .setQuery(query, Imei.class)
                .build();

        adapterCadastroCelular = new AdapterCadastroCelular(fro_cadastro_celular);
        rc_cadastro_dispositivo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rc_cadastro_dispositivo.setAdapter(adapterCadastroCelular);
        rc_cadastro_dispositivo.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterCadastroCelular.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterCadastroCelular.stopListening();
    }
}