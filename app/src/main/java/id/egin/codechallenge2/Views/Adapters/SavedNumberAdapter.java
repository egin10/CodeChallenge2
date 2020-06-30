package id.egin.codechallenge2.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.egin.codechallenge2.Models.SavedNumber;
import id.egin.codechallenge2.R;

public class SavedNumberAdapter extends RecyclerView.Adapter<SavedNumberAdapter.SavedNumberViewHolder> {
    private ArrayList<SavedNumber> savedNumbers;
    private Context context;
    private LayoutInflater layoutInflater;

    public SavedNumberAdapter(Context context, ArrayList<SavedNumber> savedNumbers) {
        this.savedNumbers = savedNumbers;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SavedNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(R.layout.saved_number_item,parent,false);
        return new SavedNumberAdapter.SavedNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNumberAdapter.SavedNumberViewHolder holder, int position) {
        holder.numberOne.setText(Integer.toString(savedNumbers.get(position).getNumberOne()));
        holder.numberTwo.setText(Integer.toString(savedNumbers.get(position).getNumberTwo()));
        holder.numberThree.setText(Integer.toString(savedNumbers.get(position).getNumberThree()));
    }

    @Override
    public int getItemCount() { return savedNumbers.size(); }

    public class SavedNumberViewHolder extends RecyclerView.ViewHolder {
        private TextView numberOne,numberTwo,numberThree;

        public SavedNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            numberOne = itemView.findViewById(R.id.number1);
            numberTwo = itemView.findViewById(R.id.number2);
            numberThree = itemView.findViewById(R.id.number3);
        }
    }
}
