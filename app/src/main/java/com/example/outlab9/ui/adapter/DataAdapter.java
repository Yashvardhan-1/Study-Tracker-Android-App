package com.example.outlab9.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlab9.R;
import com.example.outlab9.ui.dataModel.DBHelper;
import com.example.outlab9.ui.dataModel.DataModel;

import java.util.ArrayList;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    ArrayList<DataModel> localDataSet;
    int type;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,course,description,deadlineDate, deadlineTime, dateWord;
        Button deleteItemBtn;
        ConstraintLayout expandableLayout;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = (TextView) view.findViewById(R.id.name);
            course = (TextView) view.findViewById(R.id.course);
            deadlineDate = (TextView) view.findViewById(R.id.deadline);
            description = (TextView) view.findViewById(R.id.description);
            deadlineTime = (TextView) view.findViewById(R.id.deadlineTimeInLayout);
            expandableLayout = (ConstraintLayout) view.findViewById(R.id.expandableLayout);
            deleteItemBtn = (Button) view.findViewById(R.id.deleteItemBtn);
            dateWord = (TextView) view.findViewById(R.id.dateWordInLayout);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataModel data = localDataSet.get(getAdapterPosition());
                    data.setExpanded(!data.getExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            deleteItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    DataModel data = localDataSet.get(position);
                    localDataSet.remove(position);

                    DBHelper db = new DBHelper(name.getContext());
                    db.deleteData(data);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public DataAdapter(ArrayList<DataModel> dataSet, int type) {
        localDataSet = dataSet;
        this.type = type;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_recycle, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        DataModel data = localDataSet.get(position);

        viewHolder.name.setText(data.getName());
        viewHolder.course.setText(data.getCourse());
        viewHolder.deadlineDate.setText(data.getDeadlineDate());
        viewHolder.description.setText(data.getDescription());
        viewHolder.deadlineTime.setText(data.getDeadlineTime());

        switch (this.type){
            case 0:
                viewHolder.dateWord.setText("Target");
                break;
            case 3:
                viewHolder.dateWord.setText("Target");
                break;
            case 2:
                viewHolder.dateWord.setText("Date");
                break;
        }

        boolean isExpanded = data.getExpanded();
        viewHolder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}