package id.egin.codechallenge2.Views.Fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import id.egin.codechallenge2.MainActivity;
import id.egin.codechallenge2.Models.SavedNumber;
import id.egin.codechallenge2.R;

public class RandomFragment extends Fragment {
    private TextView numberOne,numberTwo,numberThree;
    private Button btnStart,btnStop;
    private Thread thread;
    private int valueOne,valueTwo,valueThree,count;
    private Random random;
    private boolean conditionOne,conditionTwo,conditionThree;
    private double getIntervalTime;
    private int intervalTime,winNumber;

    public RandomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        random = new Random();
        conditionOne = conditionTwo = conditionThree = true;
        count = 0;

        numberOne = getActivity().findViewById(R.id.number1);
        numberTwo = getActivity().findViewById(R.id.number2);
        numberThree = getActivity().findViewById(R.id.number3);
        btnStart = getActivity().findViewById(R.id.btn_start);
        btnStop = getActivity().findViewById(R.id.btn_stop);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        getIntervalTime = Double.parseDouble(sharedPreferences.getString("interval_time","0.5")) * 1000;
        intervalTime = (int) getIntervalTime;
        winNumber = Integer.parseInt(sharedPreferences.getString("win_number","7"));
        Toast.makeText(getContext(), "Interval Time : "+ Double.toString(getIntervalTime/1000) + " | Win Number : " + Integer.toString(winNumber), Toast.LENGTH_SHORT).show();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thread == null || thread.getState() == Thread.State.TERMINATED) {
                    conditionOne = conditionTwo = conditionThree = true;
                    setVisibilityButtonStart(false);

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // One
                                while (conditionOne) {
                                    valueOne = random.nextInt(10);
                                    valueTwo = random.nextInt(10);
                                    valueThree = random.nextInt(10);
                                    Thread.sleep(intervalTime);
                                    numberOne.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            numberOne.setText(String.valueOf(valueOne));
                                            numberTwo.setText(String.valueOf(valueTwo));
                                            numberThree.setText(String.valueOf(valueThree));
                                        }
                                    });
                                }
                                // Two
                                while (conditionTwo) {
                                    valueTwo = random.nextInt(10);
                                    valueThree = random.nextInt(10);
                                    Thread.sleep(intervalTime);
                                    numberTwo.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            numberTwo.setText(String.valueOf(valueTwo));
                                            numberThree.setText(String.valueOf(valueThree));
                                        }
                                    });
                                }
                                // Three
                                while (conditionThree) {
                                    valueThree = random.nextInt(10);
                                    Thread.sleep(intervalTime);
                                    numberThree.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            numberThree.setText(String.valueOf(valueThree));
                                        }
                                    });
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread = new Thread(runnable);
                    thread.start();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                switch (count) {
                    case 1:
                        conditionOne = false;
                        break;
                    case 2:
                        conditionTwo = false;
                        break;
                    case 3:
                        conditionThree = false;
                        thread.interrupt();
                        count = 0;
                        setVisibilityButtonStart(true);
                        ((MainActivity)getActivity()).addSaveNumber(valueOne, valueTwo, valueThree);

//                        valueOne = valueTwo = valueThree = winNumber;
                        if(valueOne == winNumber && valueTwo == winNumber && valueThree == winNumber) {
                            ((MainActivity)getActivity()).addNotification(Integer.toString(winNumber));
                            Toast.makeText(getActivity(), "WIN!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void setVisibilityButtonStart(boolean visible) {
        if(visible) {
            btnStart.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn));
            btnStart.setEnabled(visible);
        }else{
            btnStart.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_disabled));
            btnStart.setEnabled(visible);
        }
    }
}