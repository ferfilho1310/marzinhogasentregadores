package br.com.marzinhogas.entregadores.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;

public class AccessResourcesCellPhone implements IAccessResourcesCellPhone {

    private static AccessResourcesCellPhone accessResourcesCellPhone;

    public AccessResourcesCellPhone() {
    }

    public synchronized static AccessResourcesCellPhone getInstance() {
        if (accessResourcesCellPhone == null) {
            accessResourcesCellPhone = new AccessResourcesCellPhone();
        }
        return accessResourcesCellPhone;
    }

    @Override
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public String getImei(Activity activity) {

        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }

    @Override
    public void permissoes(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

        }else {
            getImei(activity);
        }

    }
}
