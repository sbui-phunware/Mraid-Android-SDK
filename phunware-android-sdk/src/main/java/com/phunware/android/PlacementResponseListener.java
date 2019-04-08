package com.phunware.android;

/**
 * Listener for the PlacementResponse to a placement request
 */
public interface PlacementResponseListener {
    void success(PlacementResponse response);
    void error(Throwable throwable);
}
