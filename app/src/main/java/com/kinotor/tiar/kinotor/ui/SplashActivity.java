package com.kinotor.tiar.kinotor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.kinotor.tiar.kinotor.R;
import com.kinotor.tiar.kinotor.items.ItemMain;

public class SplashActivity extends AppCompatActivity {
    private boolean l = true;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        if (l) onPlay();
    }

    private void onPlay() {
        l = false;
        TextView splash_title = findViewById(R.id.splash_title);
        TextView splash_subtitle = findViewById(R.id.splash_subtitle);
        Animation splash = AnimationUtils.loadAnimation(this, R.anim.splash);

        splash_title.startAnimation(splash);
        splash_subtitle.startAnimation(splash);

        Thread logoTimer = new Thread()
        {
            public void run()
            {
                try {
                    int logoTimer = 0;
                    while(logoTimer < 4000)
                    {
                        sleep(100);
                        logoTimer += 100;
                    }
                    Intent intent = new Intent(SplashActivity.this, MainCatalogActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    l = true;
                }
            }
        };
        logoTimer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ItemMain.cur_url.equals("q")) {
            ItemMain.cur_url = "";
            finish();
        } else if (l) onPlay();
    }
}
