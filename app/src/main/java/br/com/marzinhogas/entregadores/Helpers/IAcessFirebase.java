package br.com.marzinhogas.entregadores.Helpers;

import android.app.Activity;

import java.util.List;

import br.com.marzinhogas.entregadores.Models.Entregador;

public interface IAcessFirebase {

    void CadastrarEntregador(Entregador entregador,final Activity activity);

    void PersistirEntregador(Activity activity);

    void sign_out_firebase(Activity activity);

    void entrar_firebase(final String email, String senha, final Activity activity);

    void reset_senha(final String email, final Activity context);

    void validar_usuario(String id_user_validacao, final Activity activity);

    boolean usuario_existe(List<String> ls_usuario, String user);

}
