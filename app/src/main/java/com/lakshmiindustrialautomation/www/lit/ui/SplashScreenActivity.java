package com.lakshmiindustrialautomation.www.lit.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.felipecsl.gifimageview.library.GifImageView;
import com.lakshmiindustrialautomation.www.lit.Database.DatabaseHelper;
import com.lakshmiindustrialautomation.www.lit.LoginActivity;
import com.lakshmiindustrialautomation.www.lit.MainActivity;
import com.lakshmiindustrialautomation.www.lit.R;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreenActivity extends AppCompatActivity {
    private GifImageView gifImageView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        databaseHelper = new DatabaseHelper(this);
        gifImageView=(GifImageView)findViewById(R.id.gifimage);

        try{
            InputStream inputStream=getAssets().open("splash.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }
        catch (IOException ex){
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(databaseHelper.isAuthenticated()){
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    intent.putExtra("silent", "true");
                    startActivity(intent);
                }
            }
        }, 2000);

    }
}
