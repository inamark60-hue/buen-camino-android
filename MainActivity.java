package com.buencamino.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private static final String ONLINE_URL = "https://inamark60-hue.github.io/camino-frances/";
    private static final String OFFLINE_URL = "file:///android_asset/www/index.html";
    private static final int LOCATION_REQUEST = 1001;

    private WebView webView;
    private GeolocationPermissions.Callback pendingGeoCallback;
    private String pendingGeoOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setLoadWithOverviewMode(false);
        settings.setUseWideViewPort(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        settings.setUserAgentString(settings.getUserAgentString() + " BuenCaminoAndroid/1.0");

        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    callback.invoke(origin, true, false);
                } else {
                    pendingGeoOrigin = origin;
                    pendingGeoCallback = callback;
                    requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    }, LOCATION_REQUEST);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            private boolean mainPageFailed = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (ONLINE_URL.equals(url)) mainPageFailed = false;
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, android.webkit.WebResourceError error) {
                if (request.isForMainFrame() && request.getUrl().toString().startsWith("https://inamark60-hue.github.io/")) {
                    mainPageFailed = true;
                    view.loadUrl(OFFLINE_URL);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                String host = uri.getHost() == null ? "" : uri.getHost();
                if (host.equals("inamark60-hue.github.io")) {
                    return false;
                }
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } catch (Exception ignored) {
                    // If no external handler exists, keep the app running.
                }
                return true;
            }
        });

        if (savedInstanceState == null) {
            webView.loadUrl(ONLINE_URL);
        } else {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST && pendingGeoCallback != null) {
            boolean granted = false;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    granted = true;
                    break;
                }
            }
            pendingGeoCallback.invoke(pendingGeoOrigin, granted, false);
            pendingGeoCallback = null;
            pendingGeoOrigin = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
