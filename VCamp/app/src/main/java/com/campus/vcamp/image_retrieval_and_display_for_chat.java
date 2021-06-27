package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class image_retrieval_and_display_for_chat extends AppCompatActivity {

    DatabaseReference fDatabase;
    private RecyclerView chatImageDisplayRecyclerView;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_retrieval_and_display_for_chat);

        fDatabase = FirebaseDatabase.getInstance().getReference();
        chatImageDisplayRecyclerView = findViewById(R.id.chatImageDisplayRecyclerView);

        Query query = FirebaseDatabase.getInstance().getReference().child("Images").child("BCA").child("1BCA");
        FirebaseRecyclerOptions<chatImageRetrivalModalClass> options = new FirebaseRecyclerOptions.Builder<chatImageRetrivalModalClass>()
                .setQuery(query, chatImageRetrivalModalClass.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<chatImageRetrivalModalClass, imageViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public imageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_image_retieval_modal_class_recycler_view, parent, false);
                return new imageViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull image_retrieval_and_display_for_chat.imageViewHolder holder, int position, @NonNull @NotNull chatImageRetrivalModalClass model) {
                String url = model.getImageUrl();
                holder.chatImageetrievalConstraintlayout.setVisibility(View.VISIBLE);
                Picasso.get().load(url).into(holder.chatRetrivalImageImageView);
                holder.chatImageTime.setText(model.getTime());
                holder.chatImageName.setText(model.getName());

            }
        };

        chatImageDisplayRecyclerView.setHasFixedSize(true);
        chatImageDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatImageDisplayRecyclerView.setAdapter(adapter);

   }

    private class imageViewHolder extends RecyclerView.ViewHolder {
        ImageView chatRetrivalImageImageView;
        private TextView chatImageTime, chatImageName;
        ConstraintLayout chatImageetrievalConstraintlayout;
        LinearLayout chatImagelinearLayout;
        public imageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            chatRetrivalImageImageView = itemView.findViewById(R.id.chatRetrivalImageImageView);
            chatImageTime = itemView.findViewById(R.id.chatImageTime);
            chatImageName = itemView.findViewById(R.id.chatImageName);
            chatImageetrievalConstraintlayout = itemView.findViewById(R.id.chatImageRetrieverSenderConstraintlayout);
            chatImagelinearLayout = itemView.findViewById(R.id.chatImagelinearLayout);


            ViewGroup.LayoutParams layoutConstParams = chatImageetrievalConstraintlayout.getLayoutParams();
            ViewGroup.LayoutParams layoutLinearParams = chatImagelinearLayout.getLayoutParams();
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

            int ConstraintPercent = (Measuredwidth*70)/100;
            int LinearPercent = (ConstraintPercent*90)/100;

            layoutConstParams.width = ConstraintPercent;
            layoutConstParams.height = ConstraintPercent;
            layoutLinearParams.height = LinearPercent;
            chatImageetrievalConstraintlayout.setLayoutParams(layoutConstParams);
            chatImagelinearLayout.setLayoutParams(layoutLinearParams);

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