package id.egin.codechallenge2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import id.egin.codechallenge2.Views.Fragments.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction() .replace(android.R.id.content, new SettingFragment()) .commit();
    }
}