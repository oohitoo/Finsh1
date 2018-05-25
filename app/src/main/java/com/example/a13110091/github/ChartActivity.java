package com.example.a13110091.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    private ListView listView;
    private SenserListAdapter adapter;
    private List<Senser> senserList;
    private List<Senser> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

//        TextView userListTextView = (TextView)findViewById(R.id.userListTextView);
        Intent intent = getIntent();
//        userListTextView.setText(intent.getStringExtra("userList"));

        listView = (ListView)findViewById(R.id.listView);
        senserList = new ArrayList<Senser>();

        saveList = new ArrayList<Senser>(); //초기화 해준뒤

//         senserList.add(new Senser("11","11","1","11","11","11","11"));
//         senserList.add(new Senser("22","11","1","11","11","11","22"));
//         senserList.add(new Senser("33","11","1","11","11","11","33"));

        adapter = new SenserListAdapter(getApplicationContext(), senserList, saveList);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList")); //userList
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count= 0;
            String senser1, senser2, senser3, senser4, senser5, senser6, data_hora;
//             String senser1;
            while (count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                senser1 = object.getString("sensor1");
                senser2 = object.getString("sensor2");
                senser3 = object.getString("sensor3");
                senser4 = object.getString("sensor4");
                senser5 = object.getString("sensor5");
                senser6 = object.getString("sensor6");
                data_hora = object.getString("date_time");
                Senser senser = new Senser(senser1, senser2, senser3, senser4, senser5, senser6, data_hora);
//                 Senser senser = new Senser(senser1);
                senserList.add(senser);
                saveList.add(senser);
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        EditText search = (EditText)findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void searchUser(String search){
        senserList.clear();
        for (int i=0; i < saveList.size(); i++){
            if (saveList.get(i).getData_hora().contains(search)){ //찾아주는값
                senserList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}