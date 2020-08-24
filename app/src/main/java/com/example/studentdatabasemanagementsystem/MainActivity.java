package com.example.studentdatabasemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtRoll, edtPhone, edtAddress;
    private Button btnInsert, btnGetAllStudents;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        //dbHelper.getStudents();
        final boolean isEditData = getIntent().getBooleanExtra("isEditData", false);


        edtName = findViewById(R.id.edt_name);
        edtRoll = findViewById(R.id.edt_roll);
        edtPhone = findViewById(R.id.edt_phone);
        edtAddress = findViewById(R.id.edt_address);
        btnInsert = findViewById(R.id.btn_insert);
        btnGetAllStudents = findViewById(R.id.btn_get_all_students);

        if (isEditData) {
            int stdId = getIntent().getIntExtra("stdId", 0);

            Student student = dbHelper.getStudent(stdId + "");
            edtName.setText(student.getName());
            edtRoll.setText(student.getRoll());
            edtPhone.setText(student.getPhone());
            edtAddress.setText(student.getAddress());
            btnInsert.setText("Update");
        }

        btnGetAllStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewAllStudentsActivity.class));
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String roll = edtRoll.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAddress.getText().toString();

                if (name.length() >= 2) {
                    if (roll.length() >= 1) {
                        if (phone.length() >= 4) {
                            if (address.length() >= 3) {
                                Student student = new Student(name, roll, address, phone);
                                if (isEditData) {
                                    //Update Data
                                    student.setId(getIntent().getIntExtra("stdId",0));
                                    if(dbHelper.updateStudent(student)==1){
                                        clearData();
                                        Toast.makeText(MainActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //Insert the Data
                                    if (dbHelper.insertStudent(student) != -1) {
                                        clearData();
                                        Toast.makeText(MainActivity.this, "Insertion Successfull", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Invalid Address", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Phone number", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Enter a valid roll number", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Enter a valid name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void clearData() {
        edtName.setText("");
        edtAddress.setText("");
        edtPhone.setText("");
        edtRoll.setText("");

    }
}