package br.com.marzinhogas.entregadores.Helpers;

import android.app.Activity;

public interface IAccessResourcesCellPhone {

    String getImei(Activity activity);

    boolean checkForPhoneStatePermissionImei(Activity activity);

    String criptografiadesenha(String user,String senha);
}
