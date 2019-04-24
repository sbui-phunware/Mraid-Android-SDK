package com.phunware.android;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class VASTVideo {

    private Context context;
    private int zoneID;
    private int accountID;
    private int publisherID;
    private String poster;
    private List<Source> sources;
    private static VASTListener listenerInstance;
    private VASTCompanion endCard;

    private void setListenerInstance(VASTListener listener){
        VASTVideo.listenerInstance = listener;
    }

    protected static VASTListener getListenerInstance(){
        return VASTVideo.listenerInstance;
    }

    private class Source {
        protected String source;
        protected String type;

        public Source(String source, String type){
            this.source = source;
            this.type = type;
        }
    }

    public VASTVideo(Context context, int accountID, int zoneID, int publisherID, VASTListener listener){
        this.context = context;
        this.zoneID = zoneID;
        this.accountID = accountID;
        this.publisherID = publisherID;
        setListenerInstance(listener);
    }



    public void addSoure(String source, String type){
        sources.add(new Source(source, type));
    }

    public void play(){
        Intent intent = new Intent(context, VideoPlayer.class);
        intent.putExtra("BODY", getVideoJSMarkup());
        intent.putExtra("vastURL", String.format("https://ssp-r.phunware.com/vast.spark?setID=%d&ID=%d&pid=%d", this.zoneID, this.accountID, this.publisherID));
        context.startActivity(intent);
    }

    private String getVideoJSMarkup(){
        StringBuilder str = new StringBuilder();
        str.append("<html>");
        str.append("<head>");
        str.append("<meta name=\"viewport\" content=\"initial-scale=1.0\" />");
        str.append("<link href=\"http://vjs.zencdn.net/4.12/video-js.css\" rel=\"stylesheet\">");
        str.append("<script src=\"http://vjs.zencdn.net/4.12/video.js\"></script>");
        str.append("<link href=\"http://ssp-r.phunware.com/videojs-vast-vpaid/bin/videojs.vast.vpaid.min.css\" rel=\"stylesheet\">");
        str.append("<script src=\"http://ssp-r.phunware.com/videojs-vast-vpaid/bin/videojs_4.vast.vpaid.min.js\"></script>");
        str.append("</head>");
        str.append("<body style=\"margin:0px; background-color:black\">");
        str.append("<video id=\"pw_video\" class=\"video-js vjs-default-skin\" playsinline=\"true\" autoplay muted ");
        str.append("controls preload=\"auto\" width=\"100%\" height=\"100%\"");
        if(this.poster != null){
            str.append(String.format("poster=\"%s\" ", this.poster));
        }
        str.append("data-setup='{ ");
        str.append("\"plugins\": { ");
        str.append("\"vastClient\": { ");
        str.append(String.format("\"adTagUrl\": \"https://ssp-r.phunware.com/vast.spark?setID=%d&ID=%d&pid=%d\", ", this.zoneID, this.accountID, this.publisherID));
        str.append("\"adCancelTimeout\": 5000, ");
        str.append("\"adsEnabled\": true ");
        str.append("} ");
        str.append("} ");
        str.append("}'> ");
        if(this.sources != null){
            for(Source s : this.sources) {
                str.append(String.format("<source src=\"%s\" type='%s'/>", s.source, s.type));
            }
        }else{
            str.append("<source src=\"http://ssp-r.phunware.com/assets/blank.mp4\" type='video/mp4'/>");
        }
        str.append("<p class=\"vjs-no-js\">");
        str.append("To view this video please enable JavaScript, and consider upgrading to a web browser that");
        str.append("<a href=\"http://videojs.com/html5-video-support/\" target=\"_blank\">supports HTML5 video</a>");
        str.append("</p>");
        str.append("</video>");
        str.append("</body>");
        str.append("</html>");

        return str.toString();
    }
}
