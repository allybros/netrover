package com.allybros.netrover.View;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import com.allybros.netrover.R;

public class NinjaContextWrapper extends ContextWrapper {
    private Context context;

    public NinjaContextWrapper(Context context) {
        super(context);
        this.context = context;
        this.context.setTheme(R.style.BrowserActivityTheme);
    }

    @Override
    public Resources.Theme getTheme() {
        return context.getTheme();
    }
}
