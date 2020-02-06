package br.com.marzinhogas.entregadores.Helpers;

import android.app.Activity;
import android.content.Intent;

public class IntentsHelper {

    public void intent(Activity activity,Class classe){

        Intent i_abstract = new Intent(activity,classe);
        activity.startActivity(i_abstract);

    }
}
