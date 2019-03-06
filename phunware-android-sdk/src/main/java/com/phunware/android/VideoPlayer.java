package com.phunware.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VideoPlayer extends AppCompatActivity {
    VideoEnabledWebView webView;
    VideoEnabledWebChromeClient webChromeClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        String url = getIntent().getStringExtra("URL");

// Save the web view
        webView = (VideoEnabledWebView)findViewById(R.id.webView);

        // Initialize the VideoEnabledWebChromeClient and set event handlers
        View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
        //noinspection all
        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress)
            {
                // Your code...
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback()
        {
            @Override
            public void toggledFullscreen(boolean fullscreen)
            {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen)
                {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14)
                    {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                }
                else
                {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14)
                    {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });
        webView.setWebChromeClient(webChromeClient);
        // Call private class InsideWebViewClient
        webView.setWebViewClient(new InsideWebViewClient());

        // Navigate anywhere you want, but consider that this classes have only been tested on YouTube's mobile site
        webView.loadUrl(url);

    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed()
    {
        // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
        if (!webChromeClient.onBackPressed())
        {
            if (webView.canGoBack())
            {
                webView.goBack();
            }
            else
            {
                // Standard back button implementation (for example this could close the app)
                super.onBackPressed();
            }
        }
    }


//    public void load(String url){
//        FrameLayout container = findViewById(R.id.video_container);
//        WebView webView = new WebView(this);
//        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
//            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
//        }
//
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        params.gravity = Gravity.CENTER;
//        webView.setLayoutParams(params);
//        container.addView(webView);
//        webView.loadUrl(url);
//        addCloseButton();
//    }
//
//    public void addCloseButton(){
//        FrameLayout closeButton = new FrameLayout(this);
//        int width = MRAIDUtilities.convertDpToPixel(50, this);
//        int height = MRAIDUtilities.convertDpToPixel(50, this);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
//        params.gravity = Gravity.TOP | Gravity.RIGHT;
//        addContentView(closeButton, params);
//
//        closeButton.setBackgroundColor(0xaa000000);
//        TextView tv = new TextView(this);
//        FrameLayout.LayoutParams tparams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        tparams.gravity = Gravity.CENTER;
//        Typeface font = Typeface.create("Droid Sans Mono", Typeface.NORMAL);
//        tv.setTextColor(Color.WHITE);
//        tv.setTypeface(font);
//        tv.setText("X");
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f);
//        closeButton.addView(tv);
//        tv.setLayoutParams(tparams);
//        tv.bringToFront();
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

}
