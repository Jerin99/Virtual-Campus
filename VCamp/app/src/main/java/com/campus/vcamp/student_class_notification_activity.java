package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
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

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class student_class_notification_activity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    View view;
    private RecyclerView studentClassNotificationsRecycler;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_notification);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        view = findViewById(R.id.classChatRelativeLayout);
        studentClassNotificationsRecycler = findViewById(R.id.studentClassNotificationsRecycler);

        String StudentType = getIntent().getStringExtra("StudentType");
        String Department = getIntent().getStringExtra("Department");


        Query query = fStore.collection("Internal_Chat")
                .document(""+Department)
                .collection(""+StudentType)
                .orderBy("rand", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<student_class_notifications_modal_class> options = new FirestoreRecyclerOptions.Builder<student_class_notifications_modal_class>()
                .setQuery(query, student_class_notifications_modal_class.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<student_class_notifications_modal_class, student_class_notification_view_holder>(options) {
            @NonNull
            @NotNull
            @Override
            public student_class_notification_view_holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_class_notification_modal_class, parent, false);
                return new student_class_notification_view_holder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull student_class_notification_activity.student_class_notification_view_holder holder, int position, @NonNull @NotNull student_class_notifications_modal_class model) {
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

        studentClassNotificationsRecycler.setHasFixedSize(true);
        studentClassNotificationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentClassNotificationsRecycler.setAdapter(adapter);

    }

    private class student_class_notification_view_holder extends RecyclerView.ViewHolder {
        private TextView data, name, time;
        ImageView image;
        public student_class_notification_view_holder(@NonNull @NotNull View itemView) {
            super(itemView);

            data = itemView.findViewById(R.id.studentData);
            name = itemView.findViewById(R.id.studentClassNotificationName);
            time = itemView.findViewById(R.id.studentClassNotificationTime);
            image = itemView.findViewById(R.id.studentClassNotificationImageImageView);

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