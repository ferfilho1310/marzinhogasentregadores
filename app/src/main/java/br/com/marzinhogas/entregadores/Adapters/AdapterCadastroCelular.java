package br.com.marzinhogas.entregadores.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import br.com.marzinhogas.entregadores.Models.Imei;
import br.com.marzinhogas.entregadores.R;

public class AdapterCadastroCelular extends FirestoreRecyclerAdapter<Imei, AdapterCadastroCelular.ViewHolderCadastroCelular> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterCadastroCelular(@NonNull FirestoreRecyclerOptions<Imei> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolderCadastroCelular onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolderCadastroCelular(LayoutInflater.from(parent.getContext()).inflate(R.layout.mostra_dispositivos_cadastros, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolderCadastroCelular holder, int position, @NonNull Imei model) {

        holder.nome_celular.setText(model.getUsuariodocelular());
        holder.imei_celular_cadsatrado.setText(model.getImei());
        holder.delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteuser(holder.getAdapterPosition());

            }
        });
    }

    public void deleteuser(int i) {

        DocumentReference documentReference = getSnapshots().getSnapshot(i).getReference();
        documentReference.delete();

    }

    public class ViewHolderCadastroCelular extends RecyclerView.ViewHolder {

        public TextView imei_celular_cadsatrado, nome_celular;
        public ImageButton delete_user;

        public ViewHolderCadastroCelular(@NonNull View itemView) {
            super(itemView);

            imei_celular_cadsatrado = itemView.findViewById(R.id.imei_celular);
            nome_celular = itemView.findViewById(R.id.txt_nome_usuario);
            delete_user = itemView.findViewById(R.id.img_delete);

        }
    }
}