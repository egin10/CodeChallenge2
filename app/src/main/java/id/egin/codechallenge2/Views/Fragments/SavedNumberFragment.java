package id.egin.codechallenge2.Views.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.egin.codechallenge2.Models.SavedNumber;
import id.egin.codechallenge2.R;
import id.egin.codechallenge2.Views.Adapters.SavedNumberAdapter;

public class SavedNumberFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<SavedNumber> savedNumbers = new ArrayList<>();
    private SavedNumberAdapter savedNumberAdapter;

    public SavedNumberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_number, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();

        recyclerView = getActivity().findViewById(R.id.recyclerViewSavedNumber);
        savedNumberAdapter = new SavedNumberAdapter(getContext(), savedNumbers);

        recyclerView.setAdapter(savedNumberAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        savedNumberAdapter.notifyDataSetChanged();
    }

    public void loadData() {
        savedNumbers.add(new SavedNumber(1,3,5));
        savedNumbers.add(new SavedNumber(1,3,5));
        savedNumbers.add(new SavedNumber(1,3,5));
        savedNumbers.add(new SavedNumber(1,3,5));
    }
}