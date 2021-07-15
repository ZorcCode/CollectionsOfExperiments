package com.example.easylife;

import android.content.Context;
import android.webkit.JavascriptInterface;

import static android.util.Log.e;

public class WebAppInterface {
    Context context;
    public WebAppInterface(Context context)
    {
        this.context = context;
    }
    @JavascriptInterface
    public void online()
    {
        e("13", "WebAppInterface -> callBack : online");
    }
    @JavascriptInterface
    public void offline()
    {
        e("22", "WebAppInterface -> callBack : offline");
    }
}
