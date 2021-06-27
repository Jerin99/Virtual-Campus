 package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class class_chat_activity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ImageButton insertClassChatAttachButton, insertClassChatSendButton, insertClassChatEmojiButton, insertClassChatSendAudioButton;
    EmojiconEditText EmojisToSelect;
    EmojIconActions emojIconActions;
    StorageReference storageReference;
    View view;
    TextView specialTextView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private RecyclerView class_chat_send_RecyclerView, class_chat_recieve_RecyclerView;
    private FirestoreRecyclerAdapter sendAdapter, recieveAdapter;

    Chronometer timesForAudioRecording;

    @SuppressLint({"ClickableViewAccessibility", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_chat);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        insertClassChatAttachButton = findViewById(R.id.insertClassChatAttachButton);
        insertClassChatSendButton = findViewById(R.id.insertClassChatSendButton);
        insertClassChatEmojiButton = findViewById(R.id.insertClassChatEmojiButton);
        insertClassChatSendAudioButton = findViewById(R.id.insertClassChatSendAudioButton);
//        specialTextView = findViewById(R.id.specialTextView);
        timesForAudioRecording = findViewById(R.id.timesForAudioRecording);
        //Emoji
        EmojisToSelect = findViewById(R.id.EmojisToSelect);
        view = findViewById(R.id.classChatRelativeLayout);
        //Vibrator
        final Vibrator vibrator = (Vibrator) class_chat_activity.this.getSystemService(Context.VIBRATOR_SERVICE);
        class_chat_send_RecyclerView = findViewById(R.id.class_chat_RecyclerView);

        String TypeOfTeacher = getIntent().getStringExtra("TeacherType");//4BCA
        String FullName = getIntent().getStringExtra("FullName");
        String SemChecker = getIntent().getStringExtra("SemChecker");//4
        String Year = TypeOfTeacher.substring(0,1);
        String Dept = TypeOfTeacher.substring(1);

        Query query = fStore.collection("Internal_Chat")
                .document(""+Dept)
                .collection(""+TypeOfTeacher)
                .orderBy("rand", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<class_chat_activity_modal_class> sendOptions = new FirestoreRecyclerOptions.Builder<class_chat_activity_modal_class>()
                .setQuery(query, class_chat_activity_modal_class.class)
                .build();


        sendAdapter = new FirestoreRecyclerAdapter<class_chat_activity_modal_class, class_chat_send_view_holder>(sendOptions) {
            @NonNull
            @NotNull
            @Override
            public class_chat_send_view_holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_chat_modal_recycler, parent, false);
                return new class_chat_send_view_holder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull class_chat_activity.class_chat_send_view_holder holder, int position, @NonNull @NotNull class_chat_activity_modal_class model) {
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

        class_chat_send_RecyclerView.setHasFixedSize(true);
        class_chat_send_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        class_chat_send_RecyclerView.setAdapter(sendAdapter);

        emojIconActions = new EmojIconActions(this, view, EmojisToSelect, insertClassChatEmojiButton);
        emojIconActions.ShowEmojIcon();

        EmojisToSelect.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editText = s.toString();
                if(editText.isEmpty()){
                    insertClassChatSendButton.setImageResource(R.drawable.ic_baseline_mic_24);
                    insertClassChatSendAudioButton.setVisibility(View.VISIBLE);
                    insertClassChatSendButton.setVisibility(View.INVISIBLE);
                }else{
                    insertClassChatSendButton.setImageResource(R.drawable.ic_baseline_send_24);
                    insertClassChatSendAudioButton.setVisibility(View.INVISIBLE);
                    insertClassChatSendButton.setVisibility(View.VISIBLE);
                    insertClassChatSendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat currentTimeOnly = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy.MM.dd");
                            String CurrentDate = currentDate.format(new Date());
                            String CurrentTime = currentTimeOnly.format(calendar.getTime());
                            long randomNumber = Calendar.getInstance().getTimeInMillis();

                            DocumentReference insertDataRef = fStore.collection("Internal_Chat")
                                    .document(""+Dept)
                                    .collection(""+TypeOfTeacher)
                                    .document(""+CurrentDate);
                            Map<String, Object> insertData = new HashMap<>();
                            insertData.put("data", editText);
                            insertData.put("name", FullName);
                            insertData.put("time", CurrentTime);
                            insertData.put("date", CurrentDate);
                            insertData.put("rand", randomNumber);
                            insertData.put("dataType", "txt");
                            insertDataRef.set(insertData);

                            EmojisToSelect.setText("");

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//Audio Listener Button
        insertClassChatSendAudioButton.setOnTouchListener(new View.OnTouchListener() {
            long a, b;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    timesForAudioRecording.setBase(SystemClock.elapsedRealtime());
                    timesForAudioRecording.setVisibility(View.VISIBLE);
                    timesForAudioRecording.start();
                    vibrator.vibrate(50);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    timesForAudioRecording.stop();
                    vibrator.vibrate(50);
                    timesForAudioRecording.setBase(SystemClock.elapsedRealtime());
                    timesForAudioRecording.setVisibility(View.GONE);
                }
                return true;
            }
        });

        insertClassChatAttachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.containerForAttach);
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
                    fragmentTransaction.add(R.id.containerForAttach, fragClass);
                    fragmentTransaction.commit();
                }
            }
        });

    }

//    private class class_chat_recieve_view_holder extends RecyclerView.ViewHolder {
//        private TextView data, name, time;
//        ConstraintLayout class_chat_activity_reciever_ConstraintLayout;
//        public class_chat_recieve_view_holder(@NonNull @NotNull View recieveView) {
//            super(recieveView);
//
//            class_chat_activity_reciever_ConstraintLayout = recieveView.findViewById(R.id.class_chat_activity_reciever_ConstraintLayout);
//            name = recieveView.findViewById(R.id.recieveName);
//            data = recieveView.findViewById(R.id.recieveData);
//            time = recieveView.findViewById(R.id.recieveTime);
//        }
//    }

    private class class_chat_send_view_holder extends RecyclerView.ViewHolder {
        private TextView data, name, time, Data, Name, Time;
        ImageView rightArrow, leftArrow, image, Image;
        ConstraintLayout senderConstraintLayout, recieverConstraintLayout;

        public class_chat_send_view_holder(@NonNull @NotNull View itemView) {
            super(itemView);

//            classChatSendRelativeLayout = itemView.findViewById(R.id.classChatSendRelativeLayout);
//            classChatRecieveRelativeLayout = itemView.findViewById(R.id.classChatRecieveRelativeLayout);

            senderConstraintLayout = itemView.findViewById(R.id.chatImageRetrieverSenderConstraintlayout);
            recieverConstraintLayout = itemView.findViewById(R.id.chatImageRetrieverRecieverConstraintlayout);

            data = itemView.findViewById(R.id.sendData);
            time = itemView.findViewById(R.id.sendTime);
            image = itemView.findViewById(R.id.chatRetrivalImageImageView);

            Image = itemView.findViewById(R.id.chatRetrivalRecieverImageImageView);
            Name = itemView.findViewById(R.id.reciverName);
            Data = itemView.findViewById(R.id.RecieveData);
            Time = itemView.findViewById(R.id.RecieveTime);

//****************************************************************************** Width Measure
            ViewGroup.LayoutParams imageSizeSetter = image.getLayoutParams();

            int Measuredwidth = 0;
            int Measuredheight = 0;
            Point size = new Point();
            WindowManager w = getWindowManager();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
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
        class_chat_send_RecyclerView.smoothScrollToPosition(sendAdapter.getItemCount());
        sendAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sendAdapter.stopListening();
    }

}