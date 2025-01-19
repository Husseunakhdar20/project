package com.example.myproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {
EditText Id,Name,Age,disease;
Button  enter,delete, selectAll, selectId,Edit;
SQLiteDatabase myDb;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main3);
    Id = findViewById(R.id.etID);
    Name = findViewById(R.id.etName);
    Age = findViewById(R.id.etAge);
    disease = findViewById(R.id.etDisease);
    enter = findViewById(R.id.bEnter);
    delete = findViewById(R.id.bDelete);
    selectAll = findViewById(R.id.bSelectAll);
    selectId = findViewById(R.id.bSelectId);
    Edit = findViewById(R.id.bEdit);

    myDb=openOrCreateDatabase("patientDB",MODE_PRIVATE,null);
    myDb.execSQL("CREATE TABLE IF NOT EXISTS patient(id VAECHAR,Name VAECHAR,Age VARCHAR,disease VARCAHR)");

    enter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Id.getText().toString().trim().length() == 0 ||
                    Name.getText().toString().trim().length() == 0 ||
                    Age.getText().toString().trim().length() == 0 ||
                    disease.getText().toString().trim().length() == 0) {
                Toast.makeText(MainActivity3.this, "All Fields are Required", Toast.LENGTH_LONG);
                return;
            }
            Cursor c = myDb.rawQuery("SELECT * FROM patient where Id='" + Id.getText() + "'", null);

            if (c.getCount() > 0) {
                Toast.makeText(MainActivity3.this, "deleted record ", Toast.LENGTH_LONG).show();
                return;
            }
            myDb.execSQL("INSERT Into patient VALUES('" + Id.getText() + "','" + Name.getText() + "','" + Age.getText() + "','" + disease.getText() + "');");
            Toast.makeText(MainActivity3.this, "ENTERED INFO ", Toast.LENGTH_LONG).show();

        }
    });
    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Id.getText().toString().trim().length() == 0){
                Toast.makeText(MainActivity3.this, "NO ID SELECTED ", Toast.LENGTH_LONG).show();
                return;
            }
            Cursor c=myDb.rawQuery("SELECT* FROM patient where id='"+Id.getText()+"'",null);
            if (c.getCount()>0){
                myDb.execSQL("DELETE FROM patient where id='"+Id.getText()+"';");
                Toast.makeText(MainActivity3.this,"deleted record ",Toast.LENGTH_LONG).show();
                return;
            }
        }
    });
    selectAll.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cursor c=myDb.rawQuery("SELECT * FROM patient",null);
            StringBuffer buffer=new StringBuffer();
            if (c.getCount()>0) {
                while (c.moveToNext()){
                    buffer.append("ID:"+c.getString(0));
                    buffer.append("\nName:"+c.getString(1));
                    buffer.append("\nAge:"+c.getString(2));
                    buffer.append("\nDisaese:" +
                            ""+c.getString(3));
                    buffer.append("\n***********************\n");
                }
                showMessage("Items",buffer.toString());
            }
        }
    });
Edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (Id.getText().toString().trim().length() == 0 ||
                Name.getText().toString().trim().length() == 0 ||
                Age.getText().toString().trim().length() == 0 ||
                disease.getText().toString().trim().length() == 0) {
            Toast.makeText(MainActivity3.this, "All Fields are Required", Toast.LENGTH_LONG);
            return;
        }
        Cursor c = myDb.rawQuery("SELECT * FROM patient where Id='" + Id.getText() + "'", null);
        if (c.getCount() > 0) {

            myDb.execSQL("Update patient set Name='" + Name.getText() + "',Age='" + Age.getText() + "',disease='" + disease.getText() +"' Where Id='" + Id.getText()+"'");
            Toast.makeText(MainActivity3.this, "Item Updated", Toast.LENGTH_LONG).show();
            return;
        }
        showMessage("Error", "Id Does Not Exist");
        Toast.makeText(MainActivity3.this, "Item does not Exist", Toast.LENGTH_LONG).show();
    }

});
selectId.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (Id.getText().toString().trim().length() == 0){
            Toast.makeText(MainActivity3.this, "NO ID SELECTED ", Toast.LENGTH_LONG).show();
            return;
        }
        Cursor c = myDb.rawQuery("SELECT * FROM patient where Id='" + Id.getText() + "'", null);
        if (c.moveToFirst()){
            Name.setText(c.getString(1));
            Age.setText(c.getString(2));
            disease.setText(c.getString(3));
        }
        else
            showMessage("select the patient","patient does not exist");

    }
});
}
public void showMessage(String title, String message){

    AlertDialog.Builder b = new AlertDialog.Builder(this);
    b.setCancelable(true);
    b.setIcon(R.drawable.img1);
    b.setTitle(title);
    b.setMessage(message);
    b.show();
}
}