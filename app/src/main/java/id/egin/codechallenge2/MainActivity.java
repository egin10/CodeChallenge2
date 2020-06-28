package id.egin.codechallenge2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import id.egin.codechallenge2.Views.Fragments.RandomFragment;
import id.egin.codechallenge2.Views.Fragments.SavedNumberFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RandomFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedNumberFragment()).commit();
        }
    }
}