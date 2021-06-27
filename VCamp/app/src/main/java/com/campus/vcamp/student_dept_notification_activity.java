package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class student_dept_notification_activity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    View view;
    private RecyclerView studentDeptNotificationsRecycler;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dept_notification);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        studentDeptNotificationsRecycler = findViewById(R.id.studentDeptNotificationsRecycler);

        String StudentType = getIntent().getStringExtra("StudentType");
        String Department = getIntent().getStringExtra("Department");

        Query query = fStore.collection("Internal_Chat")
                .document(""+Department)
                .collection(""+StudentType)
                .orderBy("rand", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<student_dept_notification_modal_class> options = new FirestoreRecyclerOptions.Builder<student_dept_notification_modal_class>()
                .setQuery(query, student_dept_notification_modal_class.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<student_dept_notification_modal_class, student_dept_notification_view_holder>(options) {
            @NonNull
            @NotNull
            @Override
            public student_dept_notification_view_holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_dept_notification_modal_class, parent, false);
                return new student_dept_notification_view_holder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull student_dept_notification_activity.student_dept_notification_view_holder holder, int position, @NonNull @NotNull student_dept_notification_modal_class model) {
                String dataType = model.getDataType();

                if(dataType.equalsIgnoreCase("jpg")){
                    holder.data.setVisibility(View.GONE);
                    holder.image.setVisibility(View.VISIBLE);
                    Picasso.get().load(model.getData()).into(holder.image);
                    holder.time.setText(model.getTime());
                    holder.name.setText(model.getName());
                }else if(dataType.equalsIgnoreCase("txt")){
                    holder.data.setVisibility(View.VISIBLE);
                    holder.image.setVisibility(View.GONE);
                    holder.time.setText(model.getTime());
                    holder.data.setText(model.getData());
                    holder.name.setText(model.getName());
                }
            }
        };

        studentDeptNotificationsRecycler.setHasFixedSize(true);
        studentDeptNotificationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentDeptNotificationsRecycler.setAdapter(adapter);

    }

    private class student_dept_notification_view_holder extends RecyclerView.ViewHolder{
        private TextView data, name, time;
        ImageView image;
        public student_dept_notification_view_holder(@NonNull @NotNull View itemView) {
            super(itemView);

            data = itemView.findViewById(R.id.studentDeptData);
            name = itemView.findViewById(R.id.studentDeptNotificationName);
            time = itemView.findViewById(R.id.studentDeptNotificationTime);
            image = itemView.findViewById(R.id.studentDeptNotificationImageImageView);
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