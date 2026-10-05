package com.mustafa.antrenman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

/* Uygulamanın tek ekranı: assets/index.html dosyasını tam ekran bir WebView içinde açar. */
public class MainActivity extends Activity {
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);      // uygulamanın kodu JavaScript
        s.setDomStorageEnabled(true);      // kayıtlar telefonda localStorage'da tutulur
        s.setAllowFileAccess(true);
        s.setTextZoom(100);                // sistem yazı boyutu düzeni bozmasın
        web.addJavascriptInterface(new Bridge(), "Android");
        setContentView(web);
        if (savedInstanceState != null) web.restoreState(savedInstanceState);
        else web.loadUrl("file:///android_asset/index.html");
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }

    /* Sayfadaki "Yedeği dışa aktar" düğmesi bunu çağırır: Android paylaş menüsü açılır. */
    class Bridge {
        @JavascriptInterface
        public void share(String text) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Antrenman Defteri yedeği");
            i.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(i, "Yedeği kaydet"));
        }
    }
}
