ackage com.example.simpletodo;

import static android.os.FileUtils.*;

import static org.apache.commons.io.FileUtils.readLines;

import android.os.Bundle;
import android.os.FileUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    ArrayList items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdaptor itemsAdaptor;

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();
        //items.add("Buy milk");
        //items.add("Go to the gym");
        //items.add("Have fun!");

        ItemsAdaptor.OnLongClickListener onLongClickListener = new ItemsAdaptor.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from model
                items.remove(position);
                //Notify the adapter
                itemsAdaptor.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

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
                saveItems();
            }
        });

    }
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //this function will load items by reading every line of the data file
    private void loadItems() {
        try {
            items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()))
        } catch (IOException e) {
            Log.e ("MainActivity", "Error reading items",e);
            items= new ArrayList<>();

        }
    }

    //this function saves items by writing them into the data file
    private void saveItems() {
        try {
            FileUtils.writelines(getDataFile(), items);
        }
        catch(IDException e) {
            Log.e ("MainActivity", "Error reading items", e);
        }

    }
}
