# phunware-android-mraid-sdk v0.9 - end user

Requirements:
- minSdkVersion: 14
- compileSdkVersion: 27
- targetSdkVersion: 26

## Installation

TODO

### Banners

Banners require the use of a Fragment.  Add a placeholder in your activity xml.  **(The activity that will contain the ad)*

    <FrameLayout android:id="@+id/phunware_frame"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <fragment
        android:name="com.phunware.android.BannerView"
        android:id="@+id/phunware_fragment"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        />
    </FrameLayout>

Banners will be displayed immediately upon being received from the Phunware server.

#### Banner Code Example

    // get a reference to the fragment  (bannerView should be defined previously)
    FragmentManager fm = getFragmentManager();
    bannerView = (BannerView)fm.findFragmentById(R.id.phunware_fragment);

    // create an ad request
    AdRequest request = new AdRequest(ACCOUNT_ID, ZONE_ID); // your account and zone id
    // if you want to set any extra data, add it to the request object.
    request.setCoppa(0);
    request.setAge(30);
    request.setGender(AdRequest.GENDER_MALE);
    request.setBirthday(new Date());

    // initialize will retrieve the banner, and place it in the fragment.
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

### Interstitials

Interstitials do not require a fragment.  This SDK supports MRAID interstitials, as well as basic images.

#### Interstitial Code Example

    interstitial = new Interstitial();
    // create an ad request
    AdRequest request = new AdRequest(0, 0); // your account and zone id
    // if you want to set any extra data, add it to the request object.
    request.setCoppa(0);
    request.setAge(30);
    request.setGender(AdRequest.GENDER_MALE);
    request.setBirthday(new Date());

    // initialize will fetch the ad, but not display it.  
    // in this example we show the ad as soon as it is ready
    interstitial.initialize(request, this, new AdListener() {
    @Override
    public void onAdFetchSucceeded() {
        super.onAdFetchSucceeded();
    }

    @Override
    public void onInterstitialReady(){
        super.onInterstitialReady();
        // for demo purposes we show the ad immediately once it’s ready.  You don’t have to do this.
        if(interstitial.isReady){  // you can check if it’s ready any time via this property
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

### VAST Video

A VAST Video ad requires a VASTListener to listen for VAST events.

Create one like this:

    new VASTListener() {
        @Override
        public void onMute() {
            super.onMute();
        }

        @Override
        public void onUnmute() {
            super.onUnmute();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onRewind() {
            super.onRewind();
        }

        @Override
        public void onSkip() {
            super.onSkip();
        }

        @Override
        public void onPlayerExpand() {
            super.onPlayerExpand();
        }

        @Override
        public void onPlayerCollapse() {
            super.onPlayerCollapse();
        }

        @Override
        public void onNotUsed() {
            super.onNotUsed();
        }

        @Override
        public void onLoaded() {
            super.onLoaded();
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onFirstQuartile() {
            super.onFirstQuartile();
        }

        @Override
        public void onMidpoint() {
            super.onMidpoint();
        }

        @Override
        public void onThirdQuartile() {
            super.onThirdQuartile();
        }

        @Override
        public void onComplete() {
            super.onComplete();
        }

        @Override
        public void onCloseLinear() {
            super.onCloseLinear();
        }

        @Override
        public void onClose(){
            // if you pass an orientation to VAST, then reset it here.
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }

        @Override
        public void onReady(){
        }

        @Override
        public void onError(){
        }
    }
