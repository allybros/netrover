package com.allybros.netrover.Unit;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.allybros.netrover.R;

/**
 * Created by Umut Can Alaçam on 27.05.2018.
 **/

public class PermissionsUnit {

    public static String[] permissions = {  Manifest.permission.WRITE_EXTERNAL_STORAGE, // id: 0
                                            Manifest.permission.READ_EXTERNAL_STORAGE,  // id: 1
                                            Manifest.permission.ACCESS_COARSE_LOCATION, // id: 2
                                            Manifest.permission.ACCESS_FINE_LOCATION    }; // id: 3

    public static final int WRITE_EXTERNAL_STORAGE = 0, //Permission idP's
            READ_EXTERNAL_STORAGE = 1,
            ACCESS_COARSE_LOCATION = 2,
            ACCESS_FINE_LOCATION = 3;

    public static void firstTimePermissions(final Activity activity){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            AlertDialog.Builder alertDialog;
            alertDialog = new AlertDialog.Builder(activity);
            alertDialog.setTitle(R.string.permissions_needed_title);
            alertDialog.setMessage(R.string.permissions_description);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(R.string.main_relayout_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    permissionsCheck(activity, WRITE_EXTERNAL_STORAGE, //Request permissions
                            READ_EXTERNAL_STORAGE,
                            ACCESS_COARSE_LOCATION,
                            ACCESS_FINE_LOCATION);
                }
            });
            alertDialog.show();         //Explain to user why we need these permissions.
        }

    }

    public static boolean permissionsCheck(Activity activity, final int idP) {
        final String[] currentPermission = {permissions[idP]};
        int check = ActivityCompat.checkSelfPermission(activity, currentPermission[0]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && check != PackageManager.PERMISSION_GRANTED) {
            //If new android and permission not granted
            Log.d("Request permission",permissions[idP]);
            ActivityCompat.requestPermissions(activity, currentPermission, idP);
            if (ActivityCompat.checkSelfPermission(activity, currentPermission[0]) == PackageManager.PERMISSION_GRANTED) {
                Log.d("Granted permission:",permissions[idP]);
                return true;
            } else {
                Log.d("Deny Permission:",permissions[idP]);
                return false;
            }
        } else {
            Log.d("Already granted:",permissions[idP]);
            return true;
        }
    }

    public static boolean[] permissionsCheck(Activity activity, final int... idPs){ //overload for multiple idP
        final boolean[] permissionResults = new boolean[idPs.length];
        final String[] permissionsList = new String[idPs.length];
        for (int i=0; i<idPs.length; i++){
            permissionsList[i] = permissions[idPs[i]];
        }
        ActivityCompat.requestPermissions(activity,permissionsList,1000);
        for (int i=0; i<idPs.length; i++){
            if (ActivityCompat.checkSelfPermission(activity, permissionsList[i]) == PackageManager.PERMISSION_GRANTED)
                permissionResults[i] = true;
            else
                permissionResults[i] = false;
        }
        return permissionResults;
    }


}
