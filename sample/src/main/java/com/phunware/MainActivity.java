/*
 * Copyright (C) 2018 Phunware, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phunware;

import android.app.FragmentManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.phunware.android.AdRequest;
import com.phunware.android.BannerView;
import com.phunware.android.Positions;
import com.phunware.android.InterstitialView;
import com.phunware.android.AdListener;
import com.phunware.android.ErrorCode;
import com.phunware.android.Phunware;
import com.phunware.android.VASTListener;
import com.phunware.android.VASTVideo;

import java.util.Date;
import java.util.Random;

import android.view.View;

/**
 * A simple {@link android.app.Activity} that displays adds using the sample adapter and sample
 * custom event.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private BannerView bannerView;
    private InterstitialView interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Phunware (It's okay to call this multiple times, but make sure it's called at least once)
        Phunware.initialize(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    public void onGetInterstitialClick(View view){
        interstitial = new InterstitialView();
        AdRequest request = new AdRequest(174812, 335348);
        request.setCoppa(0);
        request.setAge(30);
        request.setGender(getUserGender());
        request.setLocation(getUserLocation());
        request.setBirthday(new Date());
        interstitial.initialize(request, this, new AdListener() {
            @Override
            public void onAdFetchSucceeded() {
                super.onAdFetchSucceeded();
            }

            @Override
            public void onInterstitialReady(){
                super.onInterstitialReady();
                if(interstitial.isReady){
                    interstitial.show();
                }
            }

            @Override
            public void onAdFetchFailed(ErrorCode code) {
                super.onAdFetchFailed(code);
            }

            @Override
            public void onInterstitialDisplayed() {
                super.onInterstitialDisplayed();
            }

            @Override
            public void onAdExpanded(){
                super.onAdExpanded();
            }

            @Override
            public void onAdResized(){
                super.onAdResized();
            }

            @Override
            public void onAdLeavingApplication(){
                super.onAdLeavingApplication();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }
        });
    }


    public void onGetBannerClick(View view){
        FragmentManager fm = getFragmentManager();
        bannerView = (BannerView)fm.findFragmentById(R.id.phunware_fragment);
        AdRequest request = new AdRequest(172084, 357451);
        request.setCoppa(0);
        request.setAge(30);
        request.setGender(getUserGender());
        request.setLocation(getUserLocation());
        request.setBirthday(new Date());
        bannerView.initialize(request, Positions.BOTTOM_CENTER, this, new AdListener() {
            @Override
            public void onAdFetchSucceeded() {
                super.onAdFetchSucceeded();
            }

            @Override
            public void onAdFetchFailed(ErrorCode code) {
                super.onAdFetchFailed(code);
            }

            @Override
            public void onInterstitialDisplayed() {
                super.onInterstitialDisplayed();
            }

            @Override
            public void onAdExpanded(){
                super.onAdExpanded();
            }

            @Override
            public void onAdResized(){
                super.onAdResized();
            }

            @Override
            public void onAdLeavingApplication(){
                super.onAdLeavingApplication();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }
        });
    }

    public void onGetVASTClick(View view){
        VASTVideo vast = new VASTVideo(this, 172084, 6249, 51080, new VASTListener() {
            @Override
            public void onMute() {
                System.out.println("mute");
                super.onMute();
            }

            @Override
            public void onUnmute() {
                System.out.println("unmute");
                super.onUnmute();
            }

            @Override
            public void onPause() {
                System.out.println("pause");
                super.onPause();
            }

            @Override
            public void onResume() {
                System.out.println("resume");
                super.onResume();
            }

            @Override
            public void onRewind() {
                System.out.println("rewind");
                super.onRewind();
            }

            @Override
            public void onSkip() {
                System.out.println("skip");
                super.onSkip();
            }

            @Override
            public void onPlayerExpand() {
                System.out.println("playerExpand");
                super.onPlayerExpand();
            }

            @Override
            public void onPlayerCollapse() {
                System.out.println("playerCollapse");
                super.onPlayerCollapse();
            }

            @Override
            public void onNotUsed() {
                System.out.println("notUsed");
                super.onNotUsed();
            }

            @Override
            public void onLoaded() {
                System.out.println("loaded");
                super.onLoaded();
            }

            @Override
            public void onStart() {
                System.out.println("start");
                super.onStart();
            }

            @Override
            public void onFirstQuartile() {
                System.out.println("firstQuartile");
                super.onFirstQuartile();
            }

            @Override
            public void onMidpoint() {
                System.out.println("midpoint");
                super.onMidpoint();
            }

            @Override
            public void onThirdQuartile() {
                System.out.println("thirdQuartile");
                super.onThirdQuartile();
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
                super.onComplete();
            }

            @Override
            public void onCloseLinear() {
                System.out.println("closeLinear");
                super.onCloseLinear();
            }
        });
        vast.play();
    }

    // dummy gender
    public int getUserGender() {
        Random rand = new Random();
        int i = rand.nextInt(3);
        int[] genders = {AdRequest.GENDER_UNKNOWN, AdRequest.GENDER_MALE, AdRequest.GENDER_FEMALE};
        return genders[i];
    }

    public Location getUserLocation() {
        Location loc = new Location("Dummy");
        loc.setLatitude(37.4220);
        loc.setLongitude(122.0841);
        return loc;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        bannerView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
