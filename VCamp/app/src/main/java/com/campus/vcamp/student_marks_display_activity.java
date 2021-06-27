package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class student_marks_display_activity extends AppCompatActivity {
    private RecyclerView knowYourMarksStudentRecyclerView;
    FirebaseFirestore fStore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_marks_display);

        fStore = FirebaseFirestore.getInstance();
        knowYourMarksStudentRecyclerView = findViewById(R.id.knowYourMarksStudentRecyclerView);
        String admissionNumber = getIntent().getStringExtra("UserName");

        Query query = fStore.collection("studentAboutInfo").document(""+admissionNumber)
                .collection("Student_Marks")
                .orderBy("SemOrder", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<student_marks_display_modal_class> options = new FirestoreRecyclerOptions.Builder<student_marks_display_modal_class>()
                .setQuery(query, student_marks_display_modal_class.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<student_marks_display_modal_class, markViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public markViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_mark_display_recycler, parent, false);
                return new markViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull student_marks_display_activity.markViewHolder holder, int position, @NonNull @NotNull student_marks_display_modal_class model) {
                holder.mark1.setText(model.getMark1());
                holder.mark2.setText(model.getMark2());
                holder.mark3.setText(model.getMark3());
                holder.mark4.setText(model.getMark4());
                holder.mark5.setText(model.getMark5());
                holder.mark6.setText(model.getMark6());
                holder.sub1.setText(model.getSub1());
                holder.sub2.setText(model.getSub2());
                holder.sub3.setText(model.getSub3());
                holder.sub4.setText(model.getSub4());
                holder.sub5.setText(model.getSub5());
                holder.sub6.setText(model.getSub6());
                holder.total.setText("600/"+model.getTotal());
                holder.grade.setText(model.getGrade());
                holder.Semester.setText(model.getSemester());

            }
        };

        knowYourMarksStudentRecyclerView.setHasFixedSize(true);
        knowYourMarksStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        knowYourMarksStudentRecyclerView.setAdapter(adapter);

    }

    private class markViewHolder extends RecyclerView.ViewHolder {

        private TextView mark1, mark2, mark3, mark4, mark5, mark6, sub1, sub2, sub3, sub4, sub5, sub6, total, grade, Semester;
        public markViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mark1 = itemView.findViewById(R.id.mark1);
            mark2 = itemView.findViewById(R.id.mark2);
            mark3 = itemView.findViewById(R.id.mark3);
            mark4 = itemView.findViewById(R.id.mark4);
            mark5 = itemView.findViewById(R.id.mark5);
            mark6 = itemView.findViewById(R.id.mark6);
            sub1 = itemView.findViewById(R.id.sub1);
            sub2 = itemView.findViewById(R.id.sub2);
            sub3 = itemView.findViewById(R.id.sub3);
            sub4 = itemView.findViewById(R.id.sub4);
            sub5 = itemView.findViewById(R.id.sub5);
            sub6 = itemView.findViewById(R.id.sub6);
            total = itemView.findViewById(R.id.total);
            grade = itemView.findViewById(R.id.grade);
            Semester = itemView.findViewById(R.id.semName);

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