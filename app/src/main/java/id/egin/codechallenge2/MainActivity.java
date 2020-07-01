package id.egin.codechallenge2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.egin.codechallenge2.Models.SavedNumber;
import id.egin.codechallenge2.Views.Adapters.SavedNumberAdapter;
import id.egin.codechallenge2.Views.Fragments.RandomFragment;
import id.egin.codechallenge2.Views.Fragments.SavedNumberFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Button btnPlay,btnListData;
    private SharedPreferences sharedPreferences,sharedPreferencesSavedNumber;
    private List<SavedNumber> savedNumberArrayList = new ArrayList<>();
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btn_play);
        btnListData = findViewById(R.id.btn_listSaved);

        btnPlay.setOnClickListener(this);
        btnListData.setOnClickListener(this);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RandomFragment()).commit();
        }

        fragmentManager = getSupportFragmentManager();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sharedPreferencesSavedNumber = getSharedPreferences("savedNumberList", Context.MODE_PRIVATE);

        savedNumberArrayList = getSharedPrefSavedNumbers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                btnPlay.setBackground(getResources().getDrawable(R.drawable.btn_menu));
                btnListData.setBackground(getResources().getDrawable(R.drawable.btn_menu_idle));
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new RandomFragment()).commit();
                break;
            case R.id.btn_listSaved:
                btnPlay.setBackground(getResources().getDrawable(R.drawable.btn_menu_idle));
                btnListData.setBackground(getResources().getDrawable(R.drawable.btn_menu));
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new SavedNumberFragment()).commit();
                break;
            default:
                break;
        }
    }

    public void addNotification(String winnerNumber) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "0")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("You're winner!")
                .setContentText("The win number is " + winnerNumber)
                .setPriority(2);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as Notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }

    public List<SavedNumber> getSharedPrefSavedNumbers() {
        gson = new Gson();
        String json = sharedPreferencesSavedNumber.getString("listNumbers", null);
        Type type = new TypeToken<List<SavedNumber>>() {}.getType();
        List<SavedNumber> savedNumbers = (List<SavedNumber>) gson.fromJson(json, type);
        return savedNumbers;
    }

    public void addSaveNumber(int numberOneSaved, int numberTwoSaved, int numberThreeSaved, boolean overSize) {
        // Checking length of array
        if(overSize) {
            savedNumberArrayList.remove(0);
            savedNumberArrayList.add(new SavedNumber(numberOneSaved, numberTwoSaved, numberThreeSaved));
            SharedPreferences.Editor saveNumberPrefEdit = sharedPreferencesSavedNumber.edit();
            String json = gson.toJson(savedNumberArrayList);
            saveNumberPrefEdit.putString("listNumbers", json);
            saveNumberPrefEdit.apply();
        }else{
            savedNumberArrayList.add(new SavedNumber(numberOneSaved, numberTwoSaved, numberThreeSaved));
            SharedPreferences.Editor saveNumberPrefEdit = sharedPreferencesSavedNumber.edit();
            String json = gson.toJson(savedNumberArrayList);
            saveNumberPrefEdit.putString("listNumbers", json);
            saveNumberPrefEdit.apply();
        }
    }
}