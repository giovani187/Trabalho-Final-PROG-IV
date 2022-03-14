package com.example.trabprogiv_giovanichagas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SunsetActivity extends AppCompatActivity implements LocationListener {
    Button botao_localizacao;
    LocationManager gerenciarLocalizacao;
    TextView sunrise;
    TextView sunset;
    TextView cabecalho;
    SunriseService sunriseservice;
    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunset);
        sunrise=(TextView)findViewById(R.id.hour_sunrise);
        sunset=(TextView)findViewById(R.id.hour_sunset);
        cabecalho=(TextView)findViewById(R.id.cabeçalho);


        //PERMISSAO LOCALIZACAO
        if (ContextCompat.checkSelfPermission(SunsetActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SunsetActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.sunrise-sunset.org/")
                //.client(new OkHttpClient.Builder().build())
                //.addConverterFactory(JacksonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                sunriseservice = retrofit.create(SunriseService.class);


    }

    @Override
    public void onResume(){
        super.onResume();
        getLocalizacao();
    }

    public void getLocalizacao() {
        gerenciarLocalizacao = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gerenciarLocalizacao.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, SunsetActivity.this);
    }


    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        System.out.println(location.getLatitude() + " " + location.getLongitude());
       try {
           Geocoder geocoder = new Geocoder(SunsetActivity.this, Locale.getDefault());
           List<Address> endereco = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
           cabecalho.setText(endereco.get(0).getSubAdminArea() + " - " + endereco.get(0).getAdminArea() + ", " + endereco.get(0).getCountryName());

           double lat = location.getLatitude();
           double lng = location.getLongitude();

           Call<SunriseSunset> result = sunriseservice.carregar(lat, lng);
           result.enqueue(new Callback<SunriseSunset>() {
               @Override
               public void onResponse(Call<SunriseSunset> call, Response<SunriseSunset> response) {
                   System.out.println(response.body());
                   Results r = response.body().results;
                   try {
                       Date datesunrise = formatter.parse(r.sunrise);
                       Date datesunset = formatter.parse(r.sunset);
                       Calendar cal = Calendar.getInstance();
                       cal.setTime(datesunrise);
                       cal.add(Calendar.HOUR, -3);
                       sunrise.setText(formatter.format(cal.getTime()).toString());
                       cal.setTime(datesunset);
                       cal.add(Calendar.HOUR, -3);
                       sunset.setText(formatter.format(cal.getTime()).toString());
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
               }

               @Override
               public void onFailure(Call<SunriseSunset> call, Throwable t) {
                   System.out.println(t.fillInStackTrace());
               }
           });
       }catch (Exception e) {
           e.printStackTrace();
       }



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}