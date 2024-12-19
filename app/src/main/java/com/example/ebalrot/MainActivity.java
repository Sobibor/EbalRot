package com.example.ebalrot;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView listViewData;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewData = findViewById(R.id.listViewData);
        Button btnPersons = findViewById(R.id.btnPersons);
        Button btnProjects = findViewById(R.id.btnProjects);
        Button btnTechnologies = findViewById(R.id.btnTechnologies);

        dbHelper = new DBHelper(this);

        btnPersons.setOnClickListener(view -> loadTable("persons"));
        btnProjects.setOnClickListener(view -> loadTable("projects"));
        btnTechnologies.setOnClickListener(view -> loadTable("technologies"));
    }

    private void loadTable(String tableName) {
        SQLiteDatabase db = dbHelper.openDatabase();
        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

        String query = "SELECT * FROM " + tableName;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                dataList.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Адаптер для отображения данных
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                dataList,
                android.R.layout.simple_list_item_2,
                new String[]{cursor.getColumnName(1), cursor.getColumnName(2)},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        listViewData.setAdapter(adapter);
    }
}