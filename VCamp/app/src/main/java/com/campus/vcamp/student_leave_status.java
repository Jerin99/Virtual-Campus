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

public class student_leave_status extends AppCompatActivity {

    private RecyclerView student_leave_status_recycler_view;
    FirebaseFirestore fStore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_leave_status);

        fStore = FirebaseFirestore.getInstance();
        student_leave_status_recycler_view = findViewById(R.id.student_leave_status_recycler_view);

        String uName = getIntent().getStringExtra("UserName");

        Query query = fStore.collection("studentAboutInfo").document(""+uName).collection("Leave").orderBy("LeaveGrantedTime", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<student_leave_status_modal_class> options = new FirestoreRecyclerOptions.Builder<student_leave_status_modal_class>()
                .setQuery(query, student_leave_status_modal_class.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<student_leave_status_modal_class, attendanceViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public attendanceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single_leave_status, parent, false);
                return new attendanceViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull attendanceViewHolder holder, int position, @NonNull @NotNull student_leave_status_modal_class model) {
                holder.LeaveGrantedFor.setText(model.getLeaveGrantedFor());
                String Leave_status = model.getGrantedLeave();
                holder.LeaveGrantedTime.setText(model.getLeaveGrantedTime());
                if(Leave_status.equalsIgnoreCase("1")){
                    holder.GrantedLeave.setText("Granted");
                    holder.GrantedLeave.setBackgroundColor(getResources().getColor(R.color.grant));
                }else if(Leave_status.equalsIgnoreCase("0")){
                    holder.GrantedLeave.setText("Denied");
                    holder.GrantedLeave.setBackgroundColor(getResources().getColor(R.color.deny));
                }
            }
        };

        student_leave_status_recycler_view.setHasFixedSize(true);
        student_leave_status_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        student_leave_status_recycler_view.setAdapter(adapter);

    }

    private class attendanceViewHolder extends RecyclerView.ViewHolder {

        private TextView GrantedLeave, LeaveGrantedFor, LeaveGrantedTime;

        public attendanceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            GrantedLeave = itemView.findViewById(R.id.leaveStatus);
            LeaveGrantedFor = itemView.findViewById(R.id.leaveGrantedFor);
            LeaveGrantedTime = itemView.findViewById(R.id.leaveGrantedOn);

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