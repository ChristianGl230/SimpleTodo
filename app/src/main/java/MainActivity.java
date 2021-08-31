package com.example.simpletodo;

import android.os.Bundle;
import android.os.FileUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    ArrayList items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdaptor itemsAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        items = new ArrayList<>();
        items.add("Buy milk");
        items.add("Go to the gym");
        items.add("Have fun!");

        ItemsAdaptor.OnLongClickListener onLongClickListener = new ItemsAdaptor.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from model
                items.remove(position);
                //Notify the adapter
                itemsAdaptor.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();


            }
        };
        itemsAdaptor = new ItemsAdaptor(items, onLongClickListener);
        rvItems.setAdapter(itemsAdaptor);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                //add item to the model
                items.add(todoItem);
                //Notify adapter that an item is inserted
                itemsAdaptor.notifyItemInserted(items.size() -1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //this function will load items by reading every line of the data file
    private void loadItems() {
           //items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
    }

    //this function saves items by writing them into the data file
    private void saveItems() {
        //FileUtils.writelines(getDataFile(), items);
    }
}