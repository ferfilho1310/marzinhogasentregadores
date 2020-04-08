package br.com.marzinhogas.entregadores.Helpers;

import android.app.Activity;

public interface IAccessResourcesCellPhone {

    String getImei(Activity activity);

    void checkForPhoneStatePermission(Activity activity);

    void showPermissionMessage(Activity activity);

    String criptografiadesenha(String user,String senha);

}
