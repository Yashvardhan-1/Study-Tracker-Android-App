package com.example.outlab9.ui.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.outlab9.R;
import com.example.outlab9.ui.adapter.DataAdapter;
import com.example.outlab9.ui.dataModel.DBHelper;
import com.example.outlab9.ui.dataModel.DataModel;

import java.util.ArrayList;

public class assignments extends Fragment {


    RecyclerView recyclerView;
    View view;
    DBHelper dbHelper;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<DataModel> arrayList;

    public assignments() {
        // Required empty public constructor
    }

    public static assignments newInstance(String param1, String param2) {
        assignments fragment = new assignments();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_assignments, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.assignment_recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.assignment_swipeRefresh);

        dbHelper = new DBHelper(getContext());
        arrayList = dbHelper.getData(1);
        DataAdapter d = new DataAdapter(arrayList,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(d);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dbHelper = new DBHelper(getContext());
                arrayList = dbHelper.getData(1);
                DataAdapter d = new DataAdapter(arrayList,1);
                recyclerView.setAdapter(d);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

}