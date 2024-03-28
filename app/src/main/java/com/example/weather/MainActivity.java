package com.example.weather;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.weather.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
//    f531b8ce606167de83d476f716aba9e4
private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fetchWeatherData("Jaipur");
        SearchCity();
        binding.lottieAnimationView3.setAnimation(R.raw.location_lottie);
        binding.lottieAnimationView3.playAnimation();

    }

    public void SearchCity() {
        SearchView searchView = binding.searchBar;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s != null) {
                    fetchWeatherData(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

    }

    public MainActivity() {
        super();
    }

    private void fetchWeatherData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<Weather> call = api_interface.getWeatherData(cityName, "f531b8ce606167de83d476f716aba9e4", "metric");
        call.enqueue(new Callback<Weather>(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather responseBody = response.body();
                if (response.isSuccessful() && responseBody != null) {

                    Log.d("Temperature", "Min: " + responseBody.getMain().getTempMin() + ", Max: " + responseBody.getMain().getTempMax() + ", Feels Like: " + responseBody.getMain().getFeelsLike());
                    double temp = responseBody.getMain().getTemp();
                    double maxtemp = responseBody.getMain().getTempMax();
                    double mintemp = responseBody.getMain().getTempMin();
//                    double feels = responseBody.getMain().getFeelsLike();
                    double wind_speed = responseBody.getWind().getSpeed();
                    long humidity = responseBody.getMain().getHumidity();
                    long pressure = responseBody.getMain().getPressure();
                    long SunRise = responseBody.getSys().getSunrise();
                    long SunSet = responseBody.getSys().getSunset();
                    String cond = responseBody.getWeather().isEmpty() ? "unknown" : responseBody.getWeather().get(0).getMain();

                    String Temp = Double.toString(temp);
                    String Min_Temp = Double.toString(mintemp);
                    String Max_Temp = Double.toString(maxtemp);
                    String Humidity = Long.toString(humidity);
                    String Pressure = Long.toString(pressure);
                    String Speed = Double.toString(wind_speed);
//                    String Feels_like = Double.toString(feels);


                    binding.temperature.setText(Temp + "°C");
                    binding.maxTemperature.setText(Max_Temp + "°C");
                    binding.minTemperature.setText(Min_Temp + "°C");
//                    binding.felt.setText(Feels_like + "°C");
                    binding.humidity.setText(Humidity + " %");
                    binding.pressure.setText(Pressure + " hPa");
                    binding.windSpeed.setText(Speed + " m/s");
                    binding.sunrise.setText(time(SunRise));
                    binding.sunset.setText(time(SunSet));
                    binding.Condition.setText(cond);
                    binding.day.setText(dayName(System.currentTimeMillis()));
                    binding.date.setText(dateName());
                    binding.cityName.setText(cityName);

                    imageChanges(cond);
                }
                else {
                    Log.d("Response Error", "Unsuccessful response: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

    }

    private void imageChanges(String Condition) {
        if(Condition.equals("partly clouds") || Condition.equals("Clouds") || Condition.equals("Overcast") || Condition.equals("Mist") || Condition.equals("Foggy")) {
            binding.lottieAnimationView.setImageDrawable(null);
            binding.background.setBackgroundResource(R.drawable.hazy_sky);
            binding.lottieAnimationView.playAnimation();
        } else if (Condition.equals("Clear Sky") || Condition.equals("sunny") || Condition.equals("Clear")) {
            binding.lottieAnimationView.setAnimation( R.raw.sunny_lottie);
            binding.background.setBackgroundResource(R.drawable.clear_sky);
            binding.lottieAnimationView.playAnimation();
        } else if (Condition.equals("Rain") || Condition.equals("Light Rain") || Condition.equals("Drizzle") || Condition.equals("Moderate Rain") || Condition.equals("Showers") || Condition.equals("Heavy Rain")) {
            binding.lottieAnimationView.setAnimation(R.raw.rainy_lottie);
            binding.background.setBackgroundResource(R.drawable.rainy_sky);
            binding.lottieAnimationView.playAnimation();
        }
        else if (Condition.equals("Light Snow") || Condition.equals("Blizzard") || Condition.equals("Moderate Snow") || Condition.equals("Heavy Snow")) {
            binding.lottieAnimationView.setAnimation(R.raw.snowy_lottie);
            binding.background.setBackgroundResource(R.drawable.snowy_sky);
            binding.lottieAnimationView.playAnimation();
        }
        else {
            binding.lottieAnimationView.setAnimation(R.raw.sun4_lottie);
            binding.background.setBackgroundResource(R.drawable.clear_sky);
            binding.lottieAnimationView.playAnimation();
        }
    }

    public String time(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date(timeStamp * 1000); // Convert from seconds to milliseconds
        return sdf.format(date);
    }

    public String dayName(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date date = new Date(timeStamp * 1000); // Convert from seconds to milliseconds
        return sdf.format(date);
    }

    public String dateName() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }


}