package acadgild.com.sqlite_autocomplete_textview_product;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, serial_number;
    Button insert;
    AutoCompleteTextView textView;
    DatabaseInfo details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = (EditText) findViewById(R.id.editText);
        insert = (Button) findViewById(R.id.insert_button);
        serial_number = (EditText) findViewById(R.id.serial_editText);

        textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        details = new DatabaseInfo(getApplicationContext());

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = name.getText().toString();
                InsertData(productName);
            }
        });

         String required_data=search();

        String[] product_data = {required_data};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,product_data);
        textView.setThreshold(2);

        textView.setAdapter(adapter);

    }

    public void InsertData(String data) {
        String num = serial_number.getText().toString();
        long id = details.insertDetails(data, num);
        if (id <= 0) {
            Toast.makeText(getApplicationContext(), "Unable to insert Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Insert Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public String search(){
        String s=details.SearchDetails();
        return  s;
    }
}
