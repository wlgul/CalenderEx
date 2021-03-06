package com.example.stell.calenderex;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalDinner extends Fragment {

    Button btn;

    public CalDinner() {
        // Required empty public constructor
    }

    public static CalDinner newInstance(){
        Bundle args = new Bundle();

        CalDinner fragment = new CalDinner();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(), "catDB", null, 1);
        // Inflate the layout for this fragment
        Intent intent = getActivity().getIntent();
        final String data = intent.getStringExtra("month");
        final String data2 = intent.getStringExtra("dayOfMonth");

        final String fpagename = "저녁";
        final String fulldate = data + data2;

        View view = inflater.inflate(R.layout.fragment_cal_dinner,null);

        int count = dbHelper.getFoodCount(fulldate, fpagename);
        System.out.println("저녁탭입니다");
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_cal_dinner, container, false);


        int nDatCnt = 0;
        ArrayList<ItemData> oData = new ArrayList<>();
        ArrayList<String> list_food = new ArrayList<>();
        ArrayList<Integer> list_cal = new ArrayList<>();

        for(int i=0; i<count; i++)
        {

            list_food.add (dbHelper.getFoodData(i, fulldate, fpagename));
            list_cal.add (dbHelper.getCalData(i, fulldate, fpagename));
            int from = list_cal.get(i);
            String to = Integer.toString(from);
            ItemData oItem = new ItemData();
            oItem.strTitle = list_food.get(i);
            oItem.strCal = to;
            oData.add(oItem);
        }

        ListView listview = (ListView) layout.findViewById(R.id.list_food);

        //ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list_food);
        ListAdapter listViewAdapter = new ListAdapter(oData);

        listview.setAdapter(listViewAdapter);

        // Button btn_eatinput = (Button)findViewById(R.id.btn_fplus);
        ImageButton btn_eatinput = (ImageButton)layout.findViewById(R.id.btn_fplus);

        btn_eatinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EatInput.class);
                intent.putExtra("month", data);
                intent.putExtra("dayOfMonth", data2);
                intent.putExtra("fpagename", fpagename);
                startActivity(intent);
            }
        });

        return layout;
    }

}
