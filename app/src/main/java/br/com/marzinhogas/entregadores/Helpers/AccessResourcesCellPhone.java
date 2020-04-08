package br.com.marzinhogas.entregadores.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
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
    public void checkForPhoneStatePermission(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.READ_PHONE_STATE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    showPermissionMessage(activity);

                } else {

                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            1);
                }
            }
        } else {
            //... Permission has already been granted, obtain the UUID
            getImei(activity);
        }
    }

    @Override
    public void showPermissionMessage(final Activity activity) {

        new AlertDialog.Builder(activity)
                .setTitle("Read phone state")
                .setMessage("This app requires the permission to read phone state to continue")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.READ_PHONE_STATE},
                                1);
                    }
                }).create().show();

    }

    @Override
    public String criptografiadesenha(String user, String senha) {

        String sign = user + senha;

        try {
            java.security.MessageDigest md =
                    java.security.MessageDigest.getInstance("MD5");
            md.update(sign.getBytes());
            byte[] hash = md.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10)
                    hexString.append(
                            "0" + Integer.toHexString((0xFF & hash[i])));
                else
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
            sign = hexString.toString();
        } catch (Exception nsae) {
            nsae.printStackTrace();
        }
        return sign;

    }
}
