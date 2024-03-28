package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        }, 4000);

        binding.lottieAnimationView1.setAnimation(R.raw.logo2);
        binding.lottieAnimationView1.playAnimation();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.lottieAnimationView2.setAnimation(R.raw.logo1);
                binding.lottieAnimationView2.playAnimation();
            }
        },1500);




        //        ImageView sun = findViewById(R.id.imageView4);
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_screen_sun);
//        sun.setAnimation(anim);
//
//        @SuppressLint("MissingInflatedId")
//
//        TextView name = findViewById(R.id.textView);
//        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.splash_screen_name);
//        name.setAnimation(anim2);


    }

}