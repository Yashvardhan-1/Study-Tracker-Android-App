package com.example.outlab9;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.outlab9.ui.dataModel.DBHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class DataInput extends AppCompatActivity {

    EditText name, course, description;
    TextView deadlineDate, deadlineTime;
    Button btnCreate, btnReturn, dateSetter, timeSetter;
    DBHelper DB;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);

        Spinner mSpinner = (Spinner) findViewById(R.id.spinnerType1);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DataInput.this,
            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_of_events));

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);

        name = (EditText) findViewById(R.id.inputName1);
        course = (EditText) findViewById(R.id.inputCourse1);
        description = (EditText) findViewById(R.id.description1);
        btnCreate = (Button) findViewById(R.id.addEvent1);
        btnReturn = (Button) findViewById(R.id.returnBtn1);

        deadlineDate = (TextView) findViewById(R.id.deadLineDate1);
        deadlineTime = (TextView) findViewById(R.id.deadlineTime1);

        LocalDate initialDate = LocalDate.now();
        deadlineDate.setText(initialDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currTime = sdf.format(Calendar.getInstance().getTime());
        deadlineTime.setText(currTime);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay,minute);
                deadlineTime.setText(time);
            }
        };

        int hours = 0;
        int minutes = 0;

        timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hours, minutes, true );

        deadlineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth+"/"+month+"/"+year;
                deadlineDate.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);

        deadlineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });



        DB = new DBHelper(this);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameTXT = name.getText().toString().trim();
                String courseTXT = course.getText().toString();
                String descriptionTXT = description.getText().toString();
                String datePickerTXT = deadlineDate.getText().toString();
                String timePickerTXT = deadlineTime.getText().toString();

                int position = mSpinner.getSelectedItemPosition();

                if(TextUtils.isEmpty(nameTXT)){
                    Toast.makeText(DataInput.this, "Please enter the name", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean checkInsertData = DB.insertData(position, nameTXT, courseTXT, timePickerTXT,
                        datePickerTXT,descriptionTXT);

                if(checkInsertData){
                    Toast.makeText(DataInput.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(DataInput.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}