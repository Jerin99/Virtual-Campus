package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class teachers_primary_notification_activity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageButton primaryNotificationAttachButton, primaryNotificationSendButton, primaryNotificationEmojiButton, primaryNotificationSendAudioButton;
    EmojiconEditText primaryNotificationEmojisToSelect;
    EmojIconActions emojIconActions;
    StorageReference storageReference;
    View view;
    TextView specialTextView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private RecyclerView teacherPrimaryNotificationRecyclerView;
    private FirestoreRecyclerAdapter adapter;
    Chronometer primaryNotificationForAudioRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_primary_notification);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        primaryNotificationAttachButton = findViewById(R.id.primaryNotificationAttachButton);
        primaryNotificationSendButton = findViewById(R.id.primaryNotificationSendButton);
        primaryNotificationEmojiButton = findViewById(R.id.primaryNotificationEmojiButton);
        primaryNotificationSendAudioButton = findViewById(R.id.primaryNotificationSendAudioButton);
//        specialTextView = findViewById(R.id.specialTextView);
        primaryNotificationForAudioRecording = findViewById(R.id.primaryNotificationForAudioRecording);
        //Emoji
        primaryNotificationEmojisToSelect = findViewById(R.id.primaryNotificationEmojisToSelect);
        view = findViewById(R.id.primaryNotificationRelativeLayout);
        //Vibrator
        final Vibrator vibrator = (Vibrator) teachers_primary_notification_activity.this.getSystemService(Context.VIBRATOR_SERVICE);
        teacherPrimaryNotificationRecyclerView = findViewById(R.id.teacherPrimaryNotificationRecyclerView);

        String FullName = getIntent().getStringExtra("FullName");
        String SemChecker = "Primary";
        String TypeOfTeacher = "1Primary_Notification";

        Query query = fStore.collection("Internal_Chat")
                .document("Primary_Notification")
                .collection("1Primary_Notification")
                .orderBy("rand", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<teachers_primary_notification_modal_class> options = new FirestoreRecyclerOptions.Builder<teachers_primary_notification_modal_class>()
                .setQuery(query, teachers_primary_notification_modal_class.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<teachers_primary_notification_modal_class, teachers_primary_notification_view_holder>(options) {
            @NonNull
            @NotNull
            @Override
            public teachers_primary_notification_view_holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teachers_primary_notification_layout, parent, false);
                return new teachers_primary_notification_view_holder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull teachers_primary_notification_activity.teachers_primary_notification_view_holder holder, int position, @NonNull @NotNull teachers_primary_notification_modal_class model) {
                String CompareName = model.getName();
                String DataToBeGiven = model.getData();
                String dataType = model.getDataType();

                if(dataType.equalsIgnoreCase("jpg")){
                    if(CompareName.equals(FullName)){
                        //layout
                        holder.senderConstraintLayout.setVisibility(View.VISIBLE);
                        holder.recieverConstraintLayout.setVisibility(View.INVISIBLE);
                        //send Data
                        holder.data.setVisibility(View.GONE);
                        //send Image
                        holder.image.setVisibility(View.VISIBLE);
                        Picasso.get().load(model.getData()).into(holder.image);
                        holder.time.setText(model.getTime());
                    }else if(!CompareName.equals(FullName)){
                        holder.senderConstraintLayout.setVisibility(View.INVISIBLE);
                        holder.recieverConstraintLayout.setVisibility(View.VISIBLE);
                        //recieve data
                        holder.Data.setVisibility(View.GONE);
                        //send Image
                        holder.Image.setVisibility(View.VISIBLE);
                        //Name
                        holder.Name.setVisibility(View.VISIBLE);
                        holder.Name.setText(model.getName());
                        Picasso.get().load(model.getData()).into(holder.Image);
                        holder.time.setText(model.getTime());
                    }
                }else{
                    if(CompareName.equals(FullName)){
                        //layout
                        holder.senderConstraintLayout.setVisibility(View.VISIBLE);
                        holder.recieverConstraintLayout.setVisibility(View.INVISIBLE);
                        //send Data
                        holder.data.setVisibility(View.VISIBLE);
                        //send Image
                        holder.image.setVisibility(View.GONE);
                        holder.time.setText(model.getTime());
                        holder.data.setText(model.getData());
                    }else if(!CompareName.equals(FullName)){
                        //layout
                        holder.senderConstraintLayout.setVisibility(View.INVISIBLE);
                        holder.recieverConstraintLayout.setVisibility(View.VISIBLE);
                        //send Data
                        holder.Data.setVisibility(View.VISIBLE);
                        //send Image
                        holder.Image.setVisibility(View.GONE);
                        //Name
                        holder.Name.setVisibility(View.VISIBLE);
                        holder.Name.setText(model.getName());
                        holder.Time.setText(model.getTime());
                        holder.Data.setText(model.getData());
                    }
                }

            }
        };

        teacherPrimaryNotificationRecyclerView.setHasFixedSize(true);
        teacherPrimaryNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teacherPrimaryNotificationRecyclerView.setAdapter(adapter);

        emojIconActions = new EmojIconActions(this, view, primaryNotificationEmojisToSelect, primaryNotificationEmojiButton);
        emojIconActions.ShowEmojIcon();

        primaryNotificationEmojisToSelect.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editText = s.toString();
                if(editText.isEmpty()){
                    primaryNotificationSendButton.setImageResource(R.drawable.ic_baseline_mic_24);
                    primaryNotificationSendAudioButton.setVisibility(View.VISIBLE);
                    primaryNotificationSendButton.setVisibility(View.INVISIBLE);
                }else{
                    primaryNotificationSendButton.setImageResource(R.drawable.ic_baseline_send_24);
                    primaryNotificationSendAudioButton.setVisibility(View.INVISIBLE);
                    primaryNotificationSendButton.setVisibility(View.VISIBLE);
                    primaryNotificationSendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat currentTimeOnly = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy.MM.dd");
                            String CurrentDate = currentDate.format(new Date());
                            String CurrentTime = currentTimeOnly.format(calendar.getTime());
                            long randomNumber = Calendar.getInstance().getTimeInMillis();

                            DocumentReference insertDataRef = fStore.collection("Internal_Chat")
                                    .document("Primary_Notification")
                                    .collection("1Primary_Notification")
                                    .document(""+randomNumber);
                            Map<String, Object> insertData = new HashMap<>();
                            insertData.put("data", editText);
                            insertData.put("name", FullName);
                            insertData.put("time", CurrentTime);
                            insertData.put("date", CurrentDate);
                            insertData.put("rand", randomNumber);
                            insertData.put("dataType", "txt");
                            insertDataRef.set(insertData);

                            primaryNotificationEmojisToSelect.setText("");

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Audio Listener Button
        primaryNotificationSendAudioButton.setOnTouchListener(new View.OnTouchListener() {
            long a, b;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    primaryNotificationForAudioRecording.setBase(SystemClock.elapsedRealtime());
                    primaryNotificationForAudioRecording.setVisibility(View.VISIBLE);
                    primaryNotificationForAudioRecording.start();
                    vibrator.vibrate(50);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    primaryNotificationForAudioRecording.stop();
                    vibrator.vibrate(50);
                    primaryNotificationForAudioRecording.setBase(SystemClock.elapsedRealtime());
                    primaryNotificationForAudioRecording.setVisibility(View.GONE);
                }
                return true;
            }
        });

        primaryNotificationAttachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.primaryNotificationContainerForAttach);
                if(fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(fragment);
                    fragmentTransaction.commit();
                }else if(fragment==null){
                    Bundle bundle = new Bundle();
                    bundle.putString("SemChecker", SemChecker);
                    bundle.putString("TypeOfTeacher", TypeOfTeacher);
                    bundle.putString("FullName", FullName);
                    attach_extra_fragment fragClass = new attach_extra_fragment();
                    fragClass.setArguments(bundle);
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.primaryNotificationContainerForAttach, fragClass);
                    fragmentTransaction.commit();
                }
            }
        });


    }

    private class teachers_primary_notification_view_holder extends RecyclerView.ViewHolder{
        private TextView data, name, time, Data, Name, Time;
        ImageView image, Image;
        ConstraintLayout senderConstraintLayout, recieverConstraintLayout;
        public teachers_primary_notification_view_holder(@NonNull @NotNull View itemView) {
            super(itemView);

            senderConstraintLayout = itemView.findViewById(R.id.primaryNotificationSenderConstraintlayout);
            recieverConstraintLayout = itemView.findViewById(R.id.primaryNotificationRecieverConstraintlayout);

            data = itemView.findViewById(R.id.primaryNotificationSendData);
            time = itemView.findViewById(R.id.primaryNotificationSendTime);
            image = itemView.findViewById(R.id.primaryNotificationImageImageView);

            Image = itemView.findViewById(R.id.primaryNotificationRecieverImageImageView);
            Name = itemView.findViewById(R.id.primaryNotificationReciverName);
            Data = itemView.findViewById(R.id.primaryNotificationRecieveData);
            Time = itemView.findViewById(R.id.primaryNotificationRecieveTime);

            //****************************************************************************** Width Measure
            ViewGroup.LayoutParams imageSizeSetter = image.getLayoutParams();

            int Measuredwidth = 0;
            int Measuredheight = 0;
            Point size = new Point();
            WindowManager w = getWindowManager();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                w.getDefaultDisplay().getSize(size);
                Measuredwidth = size.x;
                Measuredheight = size.y;
            }else{
                Display d = w.getDefaultDisplay();
                Measuredwidth = ((Display) d).getWidth();
                Measuredheight = d.getHeight();
            }

            int TextMaxWidth = (Measuredwidth*70)/100;
            int ImageSize = (Measuredwidth*60)/100;

            data.setMaxWidth(TextMaxWidth);
            Data.setMaxWidth(TextMaxWidth);
            imageSizeSetter.width = ImageSize;
            imageSizeSetter.height = ImageSize;
            image.setLayoutParams(imageSizeSetter);
            Image.setLayoutParams(imageSizeSetter);

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