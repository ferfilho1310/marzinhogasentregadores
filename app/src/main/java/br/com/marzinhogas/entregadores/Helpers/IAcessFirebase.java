package br.com.marzinhogas.entregadores.Helpers;

import android.app.Activity;
import android.widget.EditText;
import java.util.List;
import br.com.marzinhogas.entregadores.Models.Entregador;
import br.com.marzinhogas.entregadores.Models.Imei;
import br.com.marzinhogas.entregadores.Models.PrecoProdutos;

public interface IAcessFirebase {

    void CadastrarEntregador(Entregador entregador,final Activity activity);

    void PersistirEntregador(Activity activity);

    void sign_out_firebase(Activity activity);

    void entrar_firebase(final String email, String senha, final Activity activity);

    void reset_senha(final String email, final Activity context);

    void validar_usuario(String id_user_validacao, final Activity activity);

    boolean usuario_existe(List<String> ls_usuario, String user);

    void atualiza_token(String uid,String token);

    void validar_cadastro(String imei, Activity activity);

    void cadastro_celular(Imei imei);

    void cadastrar_tabela(PrecoProdutos precoProdutos,Activity activity);

    void retornatabeladepreco(EditText ed_preco_agua,EditText ed_preco_gas);

}
