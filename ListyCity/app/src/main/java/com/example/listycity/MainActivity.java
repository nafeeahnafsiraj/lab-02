package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // UI components
    ListView cityListView;
    EditText editTextCity;
    Button buttonAddCity;
    Button buttonDeleteCity;
    Button buttonConfirmAdd;
    LinearLayout input_confirm_layout;

    // Data
    ArrayList<String> cityList;
    ArrayAdapter<String> cityAdapter;
    int selectedCityPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views by id
        cityListView = findViewById(R.id.listViewCities);
        editTextCity = findViewById(R.id.editTextCity);
        buttonAddCity = findViewById(R.id.buttonAddCity);
        buttonDeleteCity = findViewById(R.id.buttonDeleteCity);
        buttonConfirmAdd = findViewById(R.id.buttonConfirmAdd);
        input_confirm_layout = findViewById(R.id.input_confirm_layout);

        // set up list + adapter
        cityList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_city,        // row layout
                R.id.textViewCityNameItem,      // TextView inside row
                cityList
        );
        cityListView.setAdapter(cityAdapter);

        // when an item is tapped in the list
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCityPosition = position;
                Toast.makeText(MainActivity.this,
                        cityList.get(position) + " selected",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // when "ADD CITY" button is clicked
        buttonAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_confirm_layout.setVisibility(View.VISIBLE);
                editTextCity.requestFocus();
            }
        });

        // when "CONFIRM" button is clicked
        buttonConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = editTextCity.getText().toString().trim();
                if (!newCity.isEmpty()) {
                    cityList.add(newCity);
                    cityAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,
                            newCity + " added",
                            Toast.LENGTH_SHORT).show();
                }
                editTextCity.setText("");
                input_confirm_layout.setVisibility(View.GONE);
            }
        });

        // when "DELETE CITY" button is clicked
        buttonDeleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCityPosition != -1) {
                    String removed = cityList.remove(selectedCityPosition);
                    cityAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,
                            removed + " deleted",
                            Toast.LENGTH_SHORT).show();
                    selectedCityPosition = -1;
                } else {
                    Toast.makeText(MainActivity.this,
                            "Select a city first",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
