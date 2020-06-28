package id.egin.codechallenge2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import id.egin.codechallenge2.Views.Fragments.RandomFragment;
import id.egin.codechallenge2.Views.Fragments.SavedNumberFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Button btnPlay,btnListData;

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
}