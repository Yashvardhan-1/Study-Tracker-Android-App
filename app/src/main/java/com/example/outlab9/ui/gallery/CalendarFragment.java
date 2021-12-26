package com.example.outlab9.ui.gallery;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlab9.R;
import com.example.outlab9.databinding.FragmentCalendarBinding;
import com.example.outlab9.ui.adapter.CalendarAdapter;
import com.example.outlab9.ui.dataModel.DBHelper;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private CalendarViewModel galleryViewModel;
    private FragmentCalendarBinding binding;
    private TextView monthYearText, currentDate;
    private RecyclerView calendarRecyclerView;
    public static LocalDate selectedDate;
    public static LocalDate todayDate;
    public static DBHelper dbHelperCalendar;
    Button nextMonthBtn, previousMonthBtn;

    private TextView studyPlan_number, assignment_number, exam_number, lecture_number;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calendarRecyclerView = root.findViewById(R.id.calendarRecyclerView);
        monthYearText = root.findViewById(R.id.monthYearTV);
        selectedDate = LocalDate.now();
        todayDate = LocalDate.now();
        currentDate = (TextView) root.findViewById(R.id.selectedDate);
        currentDate.setText(selectedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));

        nextMonthBtn = (Button) root.findViewById(R.id.calander_next_btn);
        previousMonthBtn = (Button) root.findViewById(R.id.calender_previous_btn);

        setMonthView();

        studyPlan_number = (TextView) root.findViewById(R.id.calender_studyPlan_number);
        assignment_number = (TextView) root.findViewById(R.id.calender_assignment_number);
        exam_number = (TextView) root.findViewById(R.id.calender_exam_number);
        lecture_number = (TextView) root.findViewById(R.id.calender_lecture_number);

        dbHelperCalendar = new DBHelper(getContext());
        setTodayDeadline(selectedDate);

        return root;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTodayDeadline(LocalDate currentDate) {
        int array[] = dbHelperCalendar.dataForCalendar(currentDate);

        studyPlan_number.setText(Integer.toString(array[0]));
        assignment_number.setText(Integer.toString(array[1]));
        exam_number.setText(Integer.toString(array[2]));
        lecture_number.setText(Integer.toString(array[3]));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {

        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        ArrayList<LocalDate> datesInMonth = datesInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(datesInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null)
        {
            selectedDate = date;
            currentDate.setText(selectedDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            setMonthView();
            setTodayDeadline(selectedDate);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        previousMonthBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<LocalDate> datesInMonthArray(LocalDate date)
    {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
                daysInMonthArray.add(null);
            else
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i - dayOfWeek));
        }
        return  daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setRedDotDate(LocalDate date, ImageButton redDot){
        int array[] = dbHelperCalendar.dataForCalendar(date);

        if( (array[0]+array[1]+array[2]+array[3]) == 0){
            redDot.setVisibility(View.GONE);
        }
    }
}