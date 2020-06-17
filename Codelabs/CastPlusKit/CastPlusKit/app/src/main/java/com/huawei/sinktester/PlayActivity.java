package com.huawei.sinktester;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.huawei.castpluskit.Constant;
import com.huawei.castpluskit.HiSightSurfaceView;

public class PlayActivity extends Activity {
    private static final String TAG = "SinkTesterPlayActivity";
    private static final int INVALID_NETWORK_QUALITY = -1000;

    private Drawable mDrawableNetworkWorst;
    private Drawable mDrawableNetworkWorse;
    private Drawable mDrawableNetworkBad;
    private Drawable mDrawableNetworkGeneral;
    private Drawable mDrawableNetworkGood;

    private boolean mIsFinishSelfBehavior = true;
    private ImageView mWlanImageView;

    public static HiSightSurfaceView mHiView;
    public static volatile boolean isSurfaceReady = false;

    private SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated() called.");
            isSurfaceReady = true;
            sendBroadcast(SinkTesterService.BROADCAST_ACTION_PLAY);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged() called.");
            isSurfaceReady = true;
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed() called.");
            isSurfaceReady = false;
            sendBroadcast(SinkTesterService.BROADCAST_ACTION_PAUSE);
        }
    };

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "Broadcast received, action: " + action);
            if (SinkTesterService.BROADCAST_ACTION_FINISH_PLAY_ACTIVITY.equals(action)) {
                mIsFinishSelfBehavior = false;
                finish();
            } else if (SinkTesterService.BROADCAST_ACTION_NETWORK_QUALITY.equals(action)) {
                int networkQuality = intent.getIntExtra("networkquality", INVALID_NETWORK_QUALITY);
                switch (networkQuality) {
                    case INVALID_NETWORK_QUALITY:
                        Log.e(TAG, "invalid network quality.");
                        break;
                    case Constant.NETWORK_QUALITY_EXCEPTION:
                        Log.d(TAG, "network exception.");
                        mWlanImageView.setVisibility(View.GONE);
                        Toast.makeText(PlayActivity.this, "网络异常，投屏中断", Toast.LENGTH_LONG).show();
                        break;
                    case Constant.NETWORK_QUALITY_WORST:
                        Log.d(TAG, "network worst.");
                        mWlanImageView.setVisibility(View.VISIBLE);
                        mWlanImageView.setImageDrawable(mDrawableNetworkWorst);
                        break;
                    case Constant.NETWORK_QUALITY_WORSE:
                        Log.d(TAG, "network worse.");
                        mWlanImageView.setVisibility(View.VISIBLE);
                        mWlanImageView.setImageDrawable(mDrawableNetworkWorse);
                        break;
                    case Constant.NETWORK_QUALITY_BAD:
                        Log.d(TAG, "network bad.");
                        mWlanImageView.setVisibility(View.VISIBLE);
                        mWlanImageView.setImageDrawable(mDrawableNetworkBad);
                        break;
                    case Constant.NETWORK_QUALITY_GENERAL:
                        Log.d(TAG, "network general.");
                        mWlanImageView.setVisibility(View.VISIBLE);
                        mWlanImageView.setImageDrawable(mDrawableNetworkGeneral);
                        break;
                    case Constant.NETWORK_QUALITY_GOOD:
                        Log.d(TAG, "network good.");
                        mWlanImageView.setVisibility(View.VISIBLE);
                        mWlanImageView.setImageDrawable(mDrawableNetworkGood);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called.");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉信息栏，
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_play);

        IntentFilter broadcastFilter = new IntentFilter();
        broadcastFilter.addAction(SinkTesterService.BROADCAST_ACTION_FINISH_PLAY_ACTIVITY);
        broadcastFilter.addAction(SinkTesterService.BROADCAST_ACTION_NETWORK_QUALITY);
        registerReceiver(mBroadcastReceiver, broadcastFilter);

        mWlanImageView = findViewById(R.id.wlan_imageview);
        mDrawableNetworkWorst = getDrawable(R.drawable.ic_wlan_worst);
        mDrawableNetworkWorse = getDrawable(R.drawable.ic_wlan_worse);
        mDrawableNetworkBad = getDrawable(R.drawable.ic_wlan_bad);
        mDrawableNetworkGeneral = getDrawable(R.drawable.ic_wlan_general);
        mDrawableNetworkGood = getDrawable(R.drawable.ic_wlan_good);

        mHiView = (HiSightSurfaceView) findViewById(R.id.HiSightSurfaceView);
        if (mHiView != null) {
            mHiView.setSecure(true);
            SurfaceHolder surfaceHolder = mHiView.getHolder();
            if (surfaceHolder != null) {
                surfaceHolder.addCallback(mSurfaceHolderCallback);
            } else {
                Log.e(TAG, "surfaceHolder is null.");
            }
        } else {
            Log.e(TAG, "mHiView is null.");
        }
        Log.d(TAG, "onCreate() end.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called.");
        if (mIsFinishSelfBehavior) {
            Log.d(TAG, "Finish is self behavior, send broadcast to service.");
            Intent disconnectIntent = new Intent();
            disconnectIntent.setAction(SinkTesterService.BROADCAST_ACTION_DISCONNECT);
            sendBroadcast(disconnectIntent);
        }
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called.");
    }

    private void sendBroadcast(String broadcastAction) {
        Intent intent = new Intent();
        intent.setAction(broadcastAction);
        sendBroadcast(intent);
    }
}
