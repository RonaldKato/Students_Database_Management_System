package com.example.studentdatabasemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class ViewAllStudentsActivity extends AppCompatActivity implements StudentAdapter.IDeleteStudnets{
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private  DBHelper dbHelper;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_students);

        recyclerView = findViewById(R.id.recyv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dbHelper = new DBHelper(this);

        students = dbHelper.getStudents();

        studentAdapter = new StudentAdapter(dbHelper.getStudents(), this);

        recyclerView.setAdapter(studentAdapter);
    }

    @Override
    public void delete(int position, int studentId) {
        if(dbHelper.deleteStudent(studentId)==1){
            Toast.makeText(this, "Deletion is successful", Toast.LENGTH_SHORT).show();
            students.remove(position);
            studentAdapter.notifyItemRemoved(position);
        }
    }
}