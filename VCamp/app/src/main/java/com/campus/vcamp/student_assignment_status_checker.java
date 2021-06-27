package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class student_assignment_status_checker extends AppCompatActivity {

    private RecyclerView studentAssignmentStatusRecyclerView;
    FirebaseFirestore fStore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assignment_status_checker);

        fStore = FirebaseFirestore.getInstance();
        studentAssignmentStatusRecyclerView = findViewById(R.id.studentAssignmentStatusRecyclerView);

        String Admission = getIntent().getStringExtra("Admission");

        Query query = fStore.collection("studentAboutInfo")
                .document("1234")
                .collection("Assignments")
                .orderBy("Time", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<student_assignment_status_checker_modal> options = new FirestoreRecyclerOptions.Builder<student_assignment_status_checker_modal>()
                .setQuery(query, student_assignment_status_checker_modal.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<student_assignment_status_checker_modal, student_assignment_status_checker_modal_view_holder>(options) {
            @NonNull
            @NotNull
            @Override
            public student_assignment_status_checker_modal_view_holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_assignment_status_checker_modal, parent, false);
                return new student_assignment_status_checker_modal_view_holder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull student_assignment_status_checker.student_assignment_status_checker_modal_view_holder holder, int position, @NonNull @NotNull student_assignment_status_checker_modal model) {
                holder.Date.setText(model.getDate());
                holder.Subject.setText(model.getSubject());
                holder.Teacher.setText(model.getTeacher());
                String Status = model.getStatus();
                if(Status.equals("0")){
                    holder.button.setText("Pending For Evaluation");
                    holder.button.setBackgroundColor(getResources().getColor(R.color.orange));
                }else if(Status.equals("1")){
                    holder.button.setText("Approved");
                    holder.button.setBackgroundColor(getResources().getColor(R.color.grant));
                }else if(Status.equals("2")){
                    holder.button.setText("Re-do");
                    holder.button.setBackgroundColor(getResources().getColor(R.color.deny));
                }
            }
        };

        studentAssignmentStatusRecyclerView.setHasFixedSize(true);
        studentAssignmentStatusRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAssignmentStatusRecyclerView.setAdapter(adapter);

    }

    private class student_assignment_status_checker_modal_view_holder extends RecyclerView.ViewHolder{
        private TextView Date, Semester, Subject, Teacher, Url;
        Button button;
        public student_assignment_status_checker_modal_view_holder(@NonNull @NotNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.dateForAssignmentUploaded);
            Teacher = itemView.findViewById(R.id.teacherForAssignmentUploaded);
            Subject = itemView.findViewById(R.id.subjectForAssignmentUploaded);
            button = itemView.findViewById(R.id.buttonForAssignmentUploaded);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}