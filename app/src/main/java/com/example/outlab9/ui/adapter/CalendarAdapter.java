package com.example.outlab9.ui.adapter;

import static com.example.outlab9.ui.gallery.CalendarFragment.selectedDate;
import static com.example.outlab9.ui.gallery.CalendarFragment.setRedDotDate;
import static com.example.outlab9.ui.gallery.CalendarFragment.todayDate;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlab9.R;
import com.example.outlab9.ui.dataModel.DBHelper;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<LocalDate> localDates;
    private final OnItemListener onItemListener;
    DBHelper dbHelperCalAdapter;

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dayOfMonth;
        private final CalendarAdapter.OnItemListener onItemListener;
        public final View parentView;
        public final ImageButton redDot;
        private final ArrayList<LocalDate> localDates;

        public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> localDates) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            parentView = itemView.findViewById(R.id.calenderDate);
            redDot = itemView.findViewById(R.id.redDotImageButton);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
            this.localDates = localDates;
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), localDates.get(getAdapterPosition()));
        }
    }

    public CalendarAdapter(ArrayList<LocalDate> daysOfMonth, OnItemListener onItemListener) {
        this.localDates = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_cell, parent, false);
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.height = (int) (parent.getHeight() * 0.1);
        return new CalendarViewHolder(view, onItemListener, localDates);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

        final LocalDate date = localDates.get(position);
//        int array[] = dbHelperCalendar.dataForCalendar(date);
//        DBHelper myDBHelper = new DBHelper(this);

        if(date == null) {
            holder.dayOfMonth.setText("");
            holder.redDot.setVisibility(View.GONE);
        }else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            setRedDotDate(date, holder.redDot);
            if(date.equals(selectedDate)){
                holder.parentView.setBackgroundColor(Color.BLUE);
            }
            if(date.equals(todayDate)) {
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return localDates.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }

}