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
import com.phunware.android.Interstitial;
import com.phunware.android.AdListener;
import com.phunware.android.ErrorCode;
import com.phunware.android.Phunware;

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
    private Interstitial interstitial;

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
        interstitial = new Interstitial();
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
        AdRequest request = new AdRequest(174812, 335341);
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
