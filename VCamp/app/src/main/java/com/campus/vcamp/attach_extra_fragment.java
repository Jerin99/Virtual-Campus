package com.campus.vcamp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class attach_extra_fragment extends Fragment {
    ImageButton attachWord, attachPdf, attachImage;
    FirebaseFirestore fStore;
    DatabaseReference attachImageRef;
    StorageReference fStorage;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    Uri imageUri;
    Button attachImageSeeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_attach_extra_fragment, container, false);
        attachWord = view.findViewById(R.id.attachWord);
        attachPdf = view.findViewById(R.id.attachPdf);
        attachImage = view.findViewById(R.id.attachImage);
        progressBar = view.findViewById(R.id.progressBar);


        fStorage = FirebaseStorage.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

///*------------------------------------------------------------General-----------------------------------------------------------------*/

        String TeacherType;
        Bundle bundle = getArguments();
        TeacherType = bundle.getString("TypeOfTeacher");

        String dept = TeacherType.substring(1);
        // Sem Identifier Coming from teacher Home activity
        String semChecker = null, YearDetailsForImageDirectory = null;
        if(bundle != null){
            semChecker = bundle.getString("SemChecker");
        }
        if(semChecker.equals("0")){
            YearDetailsForImageDirectory = TeacherType;
        }else if(semChecker.equals("1")){
            YearDetailsForImageDirectory = "1BCA";
        }else if(semChecker.equals("2")){
            YearDetailsForImageDirectory = "2BCA";
        }else if(semChecker.equals("3")){
            YearDetailsForImageDirectory = "3BCA";
        }else if(semChecker.equals("Primary")){
            YearDetailsForImageDirectory = "College";
        }

        attachImageRef = FirebaseDatabase.getInstance().getReference().child("Images").child(""+dept).child(""+YearDetailsForImageDirectory);

///*------------------------------------------------------------------------------------------------------------------------------------*/

        attachWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openWord = new Intent();
                openWord.setType("application/doc");
                openWord.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(openWord, "Select DOCX"), 10);
            }
        });

        attachPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent openPDF = new Intent();
//                openPDF.setType("application/pdf");
//                openPDF.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(openPDF, "Select PDF"), 12);
            }
        });

        attachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent();
                openGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && data !=null){

            imageUri = data.getData();

            uploadImageToDatabase(imageUri);

        }
    }

    private void uploadImageToDatabase(Uri imageUri) {
        String TeacherType, FullName;
        Bundle bundle = getArguments();
        TeacherType = bundle.getString("TypeOfTeacher");
        FullName = bundle.getString("FullName");
        String dept = TeacherType.substring(1);
    // Sem Identifier Coming from teacher Home activity
        String semChecker = null, YearDetailsForImageDirectory = null;
        if(bundle != null){
            semChecker = bundle.getString("SemChecker");
        }
        if(semChecker.equals("0")){
            YearDetailsForImageDirectory = TeacherType;
        }else if(semChecker.equals("1")){
            YearDetailsForImageDirectory = "1BCA";
        }else if(semChecker.equals("2")){
            YearDetailsForImageDirectory = "2BCA";
        }else if(semChecker.equals("3")){
            YearDetailsForImageDirectory = "3BCA";
        }else if(semChecker.equals("Primary")){
            YearDetailsForImageDirectory = "College";
        }

        StorageReference imageRef = fStorage.child("Internal_Chat/"+dept+"/"+"Images/"+YearDetailsForImageDirectory+"/"+System.currentTimeMillis()+"."+getFileExtension(imageUri));
        imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressBar.setVisibility(View.INVISIBLE);
                        String url = uri.toString();
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat currentTimeOnly = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy.MM.dd");
                        String CurrentDate = currentDate.format(new Date());
                            String CurrentTime = currentTimeOnly.format(calendar.getTime());
                            long randomNumber = Calendar.getInstance().getTimeInMillis();

                        DocumentReference insertDataRef = fStore.collection("Internal_Chat")
                                .document(""+dept)
                                .collection(""+TeacherType)
                                .document(""+randomNumber);
                        Map<String, Object> insertData = new HashMap<>();
                        insertData.put("data", url);
                        insertData.put("name", FullName);
                        insertData.put("time", CurrentTime);
                        insertData.put("rand", randomNumber);
                        insertData.put("dataType", "jpg");
                        insertData.put("date", CurrentDate);
                        insertDataRef.set(insertData);

                        Toast.makeText(getContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressBar.setVisibility(View.VISIBLE);
                int currentProgress = (int)progress;
                progressBar.setProgress(currentProgress);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), "Uploading failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }
}