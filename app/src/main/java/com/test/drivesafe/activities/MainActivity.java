package com.test.drivesafe.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.test.drivesafe.R;
import com.test.drivesafe.api.ApiInvoker;
import com.test.drivesafe.models.TokenReq;
import com.test.drivesafe.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG";
    private BroadcastReceiver mReceiver;
    private LottieAnimationView mWarning;
    private LottieAnimationView mCamera;
    private MediaPlayer mWarningPlayer = new MediaPlayer();
    private Button mAwake;

    private void playSiren() {
        try {
            if (mWarningPlayer.isPlaying()) {
                mWarningPlayer.stop();
            }
            mWarningPlayer.release();
            mWarningPlayer = new MediaPlayer();
            AssetFileDescriptor descriptor = getAssets().openFd("siren.mp3");
            mWarningPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mWarningPlayer.prepare();
            mWarningPlayer.setVolume(1f, 1f);
            mWarningPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWarning = findViewById(R.id.av_warning);
        mCamera = findViewById(R.id.av_camera);
        mAwake = findViewById(R.id.btn_awake);
        mAwake.setOnClickListener((View v) -> {
            mWarning.setVisibility(View.INVISIBLE);
            mAwake.setVisibility(View.INVISIBLE);
            mCamera.setVisibility(View.VISIBLE);
            mWarningPlayer.stop();
        });

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String firebaseToken = preferences.getString(Constants.FIREBASE_TOKEN_KEY, "");
        if (!firebaseToken.isEmpty()) {
            String userToken = preferences.getString(Constants.USER_TOKEN_KEY, "");
            TokenReq req = new TokenReq(firebaseToken);
            ApiInvoker.getInstance().UpdateTokenCall(req, userToken).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        Log.d(TAG, "Updated token with success: " + firebaseToken);
                        preferences.edit().remove(Constants.FIREBASE_TOKEN_KEY).apply();
                    } else {
                        Log.d(TAG, "Failed to update token: " + firebaseToken);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG, "Failed to update token: " + firebaseToken);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                playSiren();
                mWarning.setVisibility(View.VISIBLE);
                mAwake.setVisibility(View.VISIBLE);
                mCamera.setVisibility(View.INVISIBLE);
            }
        };

        this.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mReceiver);
    }
}
