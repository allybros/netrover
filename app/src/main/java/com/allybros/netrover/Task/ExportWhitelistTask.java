package com.allybros.netrover.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.allybros.netrover.R;
import com.allybros.netrover.Unit.BrowserUnit;
import com.allybros.netrover.Unit.PermissionsUnit;
import com.allybros.netrover.View.NinjaToast;

public class ExportWhitelistTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private String path;

    public ExportWhitelistTask(Context context) {
        this.context = context;
        this.dialog = null;
        this.path = null;
    }

    @Override
    protected void onPreExecute() {
        PermissionsUnit.permissionsCheck((Activity) context, PermissionsUnit.WRITE_EXTERNAL_STORAGE);
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage(context.getString(R.string.toast_wait_a_minute));
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        path = BrowserUnit.exportWhitelist(context);

        if (isCancelled()) {
            return false;
        }
        return path != null && !path.isEmpty();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        dialog.hide();
        dialog.dismiss();

        if (result) {
            NinjaToast.show(context, context.getString(R.string.toast_export_whitelist_successful) + path);
        } else {
            NinjaToast.show(context, R.string.toast_export_whitelist_failed);
        }
    }
}
