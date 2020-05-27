package com.example.todolistingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    DbHelper dbHelper;
    EditText editText;
    Button buttonAdd,buttonDel;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        buttonAdd = findViewById(R.id.btnAdd);


        dbHelper = new DbHelper(this);

        loadTaskList();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String task = String.valueOf(editText.getText());
                dbHelper.insertData(task);
                loadTaskList();


            }
        });

        registerForContextMenu(listView);




    }

    private void loadTaskList() {
        ArrayList<String>taskList = dbHelper.getAllData();
        if (arrayAdapter == null){
            arrayAdapter = new ArrayAdapter<>(this,R.layout.row_layout,taskList);
            listView.setAdapter(arrayAdapter);
        }else{
           arrayAdapter.clear();
           arrayAdapter.addAll(taskList);
           arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                dbHelper.deleteData(listView.getSelectedItem().toString());
            default:
                return super.onContextItemSelected(item);
        }

    }
}