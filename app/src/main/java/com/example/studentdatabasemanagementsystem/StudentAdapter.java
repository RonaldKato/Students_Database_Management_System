package com.example.studentdatabasemanagementsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> students;
    private Context context;
    private IDeleteStudnets deleteInterface;

    public StudentAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
        deleteInterface = (IDeleteStudnets) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Student student = students.get(position);

        holder.stdName.setText(student.getName());
        holder.stdRoll.setText(student.getRoll());
        holder.stdAddress.setText(student.getAddress());
        holder.stdPhone.setText(student.getPhone());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stdName, stdRoll, stdPhone, stdAddress;
        Button btnEdit, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            stdName = itemView.findViewById(R.id.txt_student_name);
            stdRoll = itemView.findViewById(R.id.txt_student_roll);
            stdPhone = itemView.findViewById(R.id.txt_student_phone);
            stdAddress = itemView.findViewById(R.id.txt_student_address);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnEdit = itemView.findViewById(R.id.btn_edt);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("isEditData", true);
                    intent.putExtra("stdId", students.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to delete this data ?")
                            .setTitle("Warning !")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteInterface.delete(getAdapterPosition(), students.get(getAdapterPosition()).getId());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();

                }
            });
        }
    }

    public interface IDeleteStudnets {
        void delete(int position, int studentId);
    }
}


