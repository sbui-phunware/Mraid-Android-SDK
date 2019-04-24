package com.phunware.android;

import java.util.ArrayList;
import java.util.List;

class VASTCompanion {
    public String staticResource;
    public List<String> trackingEvents;
    public String clickThrough;

    public VASTCompanion(){
        trackingEvents = new ArrayList<>();
    }
}
