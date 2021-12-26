package com.example.outlab9.ui.addevent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.outlab9.R;
import com.example.outlab9.databinding.FragmentAddeventBinding;
import com.example.outlab9.ui.dataModel.DBHelper;

public class AddEventFragment extends Fragment {

    private AddEventViewModel slideshowViewModel;
    private FragmentAddeventBinding binding;

    EditText name, course, deadlineTime, deadlineDate, description;
    Button btnCreate;
    Spinner spinner;
    DBHelper DB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(AddEventViewModel.class);

        binding = FragmentAddeventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spinner = (Spinner) root.findViewById(R.id.spinnerType);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_of_events));

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        name = (EditText) root.findViewById(R.id.inputName);
        course = (EditText) root.findViewById(R.id.inputCourse);
        deadlineDate = (EditText) root.findViewById(R.id.deadlineDateInput);
        deadlineTime = (EditText) root.findViewById(R.id.deadlineTimeInput);
        description = (EditText) root.findViewById(R.id.description);
        btnCreate = (Button) root.findViewById(R.id.addEvent);

        DB = new DBHelper(getContext());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameTXT = name.getText().toString();
                String courseTXT = course.getText().toString();
                String descriptionTXT = description.getText().toString();
                String dateTXT = deadlineDate.getText().toString();
                String timeTXT = deadlineTime.getText().toString();

                int position = spinner.getSelectedItemPosition();
                boolean checkInsertData = DB.insertData(position, nameTXT, courseTXT, timeTXT,
                        dateTXT,descriptionTXT);

                if(checkInsertData){
                    Toast.makeText(getContext(), "New Entry Inserted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                }

//                finish();
            }
        });

        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}