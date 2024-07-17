package com.kumar.iitadmin.students;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.kumar.iitadmin.R;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class studentAdapter extends RecyclerView.Adapter<studentAdapter.studentViewHolder> {

    Context context;
    ArrayList<studentHelper> studentHelpers;

    public studentAdapter(Context context, ArrayList<studentHelper> studentHelpers) {
        this.context = context;
        this.studentHelpers = studentHelpers;
    }

    @NonNull
    @Override
    public studentAdapter.studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.student_records, parent, false);

        return new studentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull studentAdapter.studentViewHolder holder, int position) {

        studentHelper studentHelper = studentHelpers.get(position);

        holder.Name.setText(studentHelper.Name);
        holder.StudentID.setText(studentHelper.StudentID);
        holder.Gender.setText(studentHelper.Gender);
        holder.Email.setText(studentHelper.Email);
        holder.Contact.setText(studentHelper.Contact);
        holder.Course.setText(studentHelper.Course);

    }

    @Override
    public int getItemCount() {
        return studentHelpers.size();
    }

    public static class studentViewHolder extends RecyclerView.ViewHolder{

        TextView Name, StudentID, Gender, Contact, Email, Course;

        public studentViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.sName);
            StudentID = itemView.findViewById(R.id.sID);
            Gender = itemView.findViewById(R.id.sGender);
            Email = itemView.findViewById(R.id.sEmail);
            Contact = itemView.findViewById(R.id.sContact);
            Course = itemView.findViewById(R.id.sCourse);


        }
    }
}
