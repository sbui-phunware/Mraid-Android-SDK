package com.phunware.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

class VideoPlayer extends AppCompatActivity implements HTTPGetListener {
    VideoEnabledWebView webView;
    VideoEnabledWebChromeClient webChromeClient;
    VASTListener listener;
    String vastURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        String url = getIntent().getStringExtra("URL");
        String body = getIntent().getStringExtra("BODY");
        vastURL = getIntent().getStringExtra("vastURL");
        listener = VASTVideo.getListenerInstance();
        // async get vast content to find companion end cards
        getVastContent();
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
        if(url != null){
            webView.loadUrl(url);
        }
        else if(body != null){
            webView.loadDataWithBaseURL("http://ssp-r.phunware.com", body, "text/html; charset=utf-8", "UTF-8", "");
        }

    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        // Phunware,  get vast events from js library
        public WebResourceResponse shouldInterceptRequest (WebView view, String url){
            if(url.contains("vast://")){
                handleEvent(url);
                return null;
            }
            return super.shouldInterceptRequest(view, url);
        }
    }

    private void handleEvent(String url){
        String event = url.replaceFirst("vast://", "");
        switch(event){
            case "mute":
                listener.onMute();
                break;
            case "unmute":
                listener.onUnmute();
                break;
            case "pause":
                listener.onPause();
                break;
            case "resume":
                listener.onResume();
                break;
            case "rewind":
                listener.onRewind();
                break;
            case "skip":
                listener.onSkip();
                break;
            case "playerExpand":
                listener.onPlayerExpand();
                break;
            case "playerCollapse":
                listener.onPlayerCollapse();
                break;
            case "notUsed":
                listener.onNotUsed();
                break;
            case "loaded":
                listener.onLoaded();
                break;
            case "start":
                listener.onStart();
                break;
            case "firstQuartile":
                listener.onFirstQuartile();
                break;
            case "midpoint":
                listener.onMidpoint();
                break;
            case "thirdQuartile":
                listener.onThirdQuartile();
                break;
//            case "otherAdInteraction":
//                listener.onOtherAdInteraction();
//                break;
            case "complete":
                listener.onComplete();
                break;
            case "closeLinear":
                listener.onCloseLinear();
                break;
//                                future additions
//            case "creativeView":
//                listener.onCreativeView();
//                break;
//            case "acceptInvitation":
//                listener.onAcceptInvitation();
//                break;
//            case "adExpand":
//                listener.onAdExpand();
//                break;
//            case "adCollapse":
//                listener.onAdCollapse();
//                break;
//            case "minimize":
//                listener.onMinimize();
//                break;
//            case "close":
//                listener.onClose();
//                break;
//            case "overlayViewDuration":
//                listener.onOverlayViewDuration();
//                break;
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

    private void getVastContent(){
        new HTTPGet(this).execute(vastURL);
    }

    public void HTTPGetCallback(String str){
        final String body = str;
        Node bestFit = null;
        // deserialize
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(str)));
            NodeList nodes = doc.getElementsByTagName("Companion");
            if(nodes.getLength() > 0){
                bestFit = findBestCompanion(nodes);
            }
        }catch(Exception ex){
            System.out.println("PW - Problem finding end card companion.");
        }

        if(bestFit != null) {
            constructEndCard(bestFit);
        }
    }

    private void constructEndCard(Node node){
        VASTCompanion companion = new VASTCompanion();
        NodeList children = node.getChildNodes();
        for(int i = 0; i < children.getLength(); i++){
            Node n = children.item(i);
            switch(n.getNodeName()){
                case "StaticResource":
                    companion.staticResource = n.getFirstChild().getNodeValue();
                    break;
                case "TrackingEvents":
                    NodeList events = n.getChildNodes();
                    for(int c = 0; c < events.getLength(); c++){
                        companion.trackingEvents.add(events.item(c).getFirstChild().getNodeValue());
                    }
                    break;
                case "CompanionClickThrough":
                    companion.clickThrough = n.getFirstChild().getNodeValue();
                    break;
                default:break;
            }
        }
    }

    private Node findBestCompanion(NodeList list){
        Rect rect = MRAIDUtilities.getFullScreenRectDP(this);
        Node curBest = null;
        float aspectRatio = (float)rect.width / (float)rect.height;
        float closestRatio = 0f;
        for(int i=0; i < list.getLength(); i++){
            NamedNodeMap map = list.item(i).getAttributes();
            int w = Integer.parseInt(map.getNamedItem("width").getNodeValue());
            int h = Integer.parseInt(map.getNamedItem("height").getNodeValue());
            float r = (float)w / (float)h;
            if(i == 0 || aspectRatio - r < closestRatio){
                closestRatio = r;
                curBest = list.item(i);
            }
        }
        return curBest;
    }
}

