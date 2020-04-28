package br.com.marzinhogas.entregadores.Helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                deviceUniqueIdentifier = tm.getImei();
            } else {
                deviceUniqueIdentifier = tm.getDeviceId();
            }
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }

    @Override
    public boolean checkForPhoneStatePermissionImei(Activity activity) {
        final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int camera = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
            int storage = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE);
            int loc = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION);
            int loc2 = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION);

            List<String> listPermissionsNeeded = new ArrayList<>();
            if (camera != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (storage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
            }
            if (loc2 != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (loc != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray
                        (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
        }
        return true;
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
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
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
