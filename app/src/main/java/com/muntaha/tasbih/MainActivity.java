package com.muntaha.tasbih;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String APP_URL = "https://muntaha-tasbih.vercel.app/";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(6, 78, 59));
        getWindow().setNavigationBarColor(Color.rgb(6, 27, 22));
        showApp(savedInstanceState);
    }

    private void showApp(Bundle savedInstanceState) {
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(6, 27, 22));
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setSaveEnabled(true);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("UTF-8");

        CookieManager cookies = CookieManager.getInstance();
        cookies.setAcceptCookie(true);
        cookies.setAcceptThirdPartyCookies(webView, true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                cookies.flush();
                hideVercelToolbar();
                setContentView(webView);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) showNetworkError();
            }
        });

        if (savedInstanceState == null || webView.restoreState(savedInstanceState) == null) {
            webView.loadUrl(APP_URL);
        } else {
            setContentView(webView);
        }
    }

    // Remove the Vercel Toolbar injected into the hosted page. This is limited
    // to the Android WebView and does not modify the live website or Firebase data.
    private void hideVercelToolbar() {
        String script = "(function(){"
            + "var selectors=['vercel-toolbar','#vercel-toolbar','[data-vercel-toolbar]',"
            + "'#vercel-toolbar-root','vercel-live-feedback','[data-vercel-live-feedback]',"
            + "'#vercel-toolbar-container','[data-testid=vercel-toolbar]'];"
            + "function clean(){selectors.forEach(function(s){"
            + "document.querySelectorAll(s).forEach(function(e){"
            + "e.style.setProperty('display','none','important');"
            + "e.style.setProperty('visibility','hidden','important');"
            + "e.style.setProperty('pointer-events','none','important');"
            + "});});"
            + "document.querySelectorAll('button,[role=button],a').forEach(function(e){"
            + "var label=((e.getAttribute('aria-label')||'')+' '+(e.getAttribute('title')||'')+' '+(e.innerText||'')).toLowerCase();"
            + "if(label.includes('vercel') && e.getBoundingClientRect().width<100){"
            + "e.style.setProperty('display','none','important');}});}"
            + "clean();new MutationObserver(clean).observe(document.documentElement,{childList:true,subtree:true});"
            + "})();";
        if (webView != null) webView.evaluateJavascript(script, null);
    }

    private void showNetworkError() {
        LinearLayout errorView = new LinearLayout(this);
        errorView.setOrientation(LinearLayout.VERTICAL);
        errorView.setGravity(android.view.Gravity.CENTER);
        errorView.setPadding(28, 28, 28, 28);
        errorView.setBackgroundColor(Color.rgb(6, 27, 22));

        TextView message = new TextView(this);
        message.setText("انٹرنیٹ کنکشن چیک کریں، پھر دوبارہ کوشش کریں۔");
        message.setTextColor(Color.WHITE);
        message.setTextSize(18);
        message.setGravity(android.view.Gravity.CENTER);
        errorView.addView(message);

        Button retry = new Button(this);
        retry.setText("دوبارہ کوشش کریں");
        retry.setOnClickListener(v -> webView.loadUrl(APP_URL));
        errorView.addView(retry);
        setContentView(errorView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (webView != null) webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (webView != null) webView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
