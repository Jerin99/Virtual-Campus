
package com.campus.vcamp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class studentAboutInfoFragment extends Fragment {
/*    private onSubmitButtonIsSelected listener;*/
    EditText department, year, address, guardianName, guardianNumber;
    FirebaseAuth fAuth;
    Button submitAboutInfo;
    FirebaseFirestore fStore;
    StorageReference storageReference;

    //recived data from database holding variables

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_about_info_fragment, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //Storage reference for profile image
        storageReference = FirebaseStorage.getInstance().getReference();

        /*message = this.getArguments().getString("key");*/
        //Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
        submitAboutInfo = view.findViewById(R.id.studentAboutInfoButton);
        Button setProfilePic = view.findViewById(R.id.setProfilePic);
        department = view.findViewById(R.id.department);
        year = view.findViewById(R.id.year);
        address = view.findViewById(R.id.address);
        guardianName = view.findViewById(R.id.guardiansName);
        guardianNumber = view.findViewById(R.id.guardiansNumber);

        //Getting information back from firebase

        DocumentReference getAdmissionNumber = fStore.collection("Users").document(fAuth.getUid());
        getAdmissionNumber.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String AdmissionNumber = documentSnapshot.getString("Admission");

                DocumentReference docRef = fStore.collection("studentAboutInfo").document(""+AdmissionNumber);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String recievedDept = documentSnapshot.getString("Department");
                        String deptYear = documentSnapshot.getString("Year");
                        String recieveAddr = documentSnapshot.getString("Address");
                        String recieveGuardName = documentSnapshot.getString("Guardian's Name");
                        String recieveGuardNumb = documentSnapshot.getString("Guardian's Number");
                        department.setText(recievedDept);
                        year.setText(deptYear);
                        address.setText(recieveAddr);
                        guardianName.setText(recieveGuardName);
                        guardianNumber.setText(recieveGuardNumb);


                        submitAboutInfo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //*listener.onStudentInfoSubmitButton();*//*
                                boolean checkDept, checkNumber, matchDepartments, checkYear;
                                checkDept = true;
                                checkNumber = true;
                                checkYear = true;
                                matchDepartments = true;

                                String dept = department.getText().toString().toUpperCase();
                                String yearOfDepartment = year.getText().toString();
                                String addr = address.getText().toString();
                                String guardName = guardianName.getText().toString();
                                String guardNum = guardianNumber.getText().toString();

                                if(!dept.isEmpty()){
                                    String[] Departments = new String[]{"ba", "Bcom", "bca", "BBA"};
                                    for(int i=0; i<Departments.length; i++){
                                        if(dept.equalsIgnoreCase(Departments[i])){
                                            matchDepartments=false;
                                            break;
                                        }
                                    }
                                    if(matchDepartments==true){
                                        department.setError("You can only select from BCA, BCOM, BA, BBA");
                                        department.requestFocus();
                                        checkDept = false;
                                    }
                                }
                                if(!guardNum.isEmpty()){
                                    if(!guardNum.matches("[0-9]{10}")){
                                        guardianNumber.setError("Please enter a valid number");
                                        guardianNumber.requestFocus();
                                        checkNumber = false;
                                    }
                                }

                                if(!yearOfDepartment.isEmpty()){
                                    if(yearOfDepartment.equals("1") || yearOfDepartment.equals("2") || yearOfDepartment.equals("3")){
                                        checkYear = true;
                                    }else{
                                        year.setError("Please enter a valid year");
                                        year.requestFocus();
                                        checkYear = false;
                                    }
                                }
                                if(checkDept==true && checkNumber==true && checkYear==true){
                                    FirebaseUser user = fAuth.getCurrentUser();
                                    DocumentReference df = fStore.collection("studentAboutInfo").document(""+AdmissionNumber);
                                    Map<String, Object> userAboutInfo = new HashMap<>();
                                    userAboutInfo.put("Department", dept);
                                    userAboutInfo.put("Year", yearOfDepartment);
                                    userAboutInfo.put("Address", addr);
                                    userAboutInfo.put("Guardian's Name", guardName);
                                    userAboutInfo.put("Guardian's Number", guardNum);
                                    userAboutInfo.put("AdmissionID", AdmissionNumber);

                                    df.set(userAboutInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getContext(), "Successfully Entered", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Failed to enter", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                StudentHomeActivity SHA = (StudentHomeActivity) getActivity();
                                SHA.recieveStudentAboutInfo(dept, addr, guardName, guardNum);
                            }
                        });



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Not able to connect with database", Toast.LENGTH_SHORT).show();
                    }
                });

                DocumentReference docRefabout = fStore.collection("studentAboutInfo").document(""+AdmissionNumber);
                docRefabout.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String recievedDept = documentSnapshot.getString("Department");
                        String deptYear = documentSnapshot.getString("Year");
                        String recieveAddr = documentSnapshot.getString("Address");
                        String recieveGuardName = documentSnapshot.getString("Guardian's Name");
                        String recieveGuardNumb = documentSnapshot.getString("Guardian's Number");
                        department.setText(recievedDept);
                        year.setText(deptYear);
                        address.setText(recieveAddr);
                        guardianName.setText(recieveGuardName);
                        guardianNumber.setText(recieveGuardNumb);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Not able to connect with database", Toast.LENGTH_SHORT).show();
                    }
                });
                //checking variables are empty or not with firebase recieved variables

                //Sending datas from fragment to activity

//Button for setting profile pic
                setProfilePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(openGalleryIntent, 1000);
                    }
                });
            }
        });

//Button for submitting the about information

        return view;
    }

//Overiding on activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
                Uri profileUri = data.getData();
                uploadImageToFirebase(profileUri);
        }
    }

    private void uploadImageToFirebase(Uri profileUri) {
        //upload image to firebase
        StorageReference fileRef = storageReference.child("Profile/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "Profile picture uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

/*    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onSubmitButtonIsSelected) {
            listener = (onSubmitButtonIsSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");
        }

    }

    public interface onSubmitButtonIsSelected{
        public void onStudentInfoSubmitButton();
    }*/

}


//    DocumentReference docRef = fStore.collection("studentAboutInfo").document(""+AdmissionID);
//                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//@Override
//public void onSuccess(DocumentSnapshot documentSnapshot) {
//        String recievedDept = documentSnapshot.getString("Department");
//        String deptYear = documentSnapshot.getString("Year");
//        String recieveAddr = documentSnapshot.getString("Address");
//        String recieveGuardName = documentSnapshot.getString("Guardian's Name");
//        String recieveGuardNumb = documentSnapshot.getString("Guardian's Number");
//        department.setText(recievedDept);
//        year.setText(deptYear);
//        address.setText(recieveAddr);
//        guardianName.setText(recieveGuardName);
//        guardianNumber.setText(recieveGuardNumb);
//
//
//        submitAboutInfo.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        //*listener.onStudentInfoSubmitButton();*//*
//        boolean checkDept, checkNumber, matchDepartments, checkYear;
//        checkDept = true;
//        checkNumber = true;
//        checkYear = true;
//        matchDepartments = true;
//
//        String dept = department.getText().toString().toUpperCase();
//        String yearOfDepartment = year.getText().toString();
//        String addr = address.getText().toString();
//        String guardName = guardianName.getText().toString();
//        String guardNum = guardianNumber.getText().toString();
//
//        if(!dept.isEmpty()){
//        String[] Departments = new String[]{"ba", "Bcom", "bca", "BBA"};
//        for(int i=0; i<Departments.length; i++){
//        if(dept.equalsIgnoreCase(Departments[i])){
//        matchDepartments=false;
//        break;
//        }
//        }
//        if(matchDepartments==true){
//        department.setError("You can only select from BCA, BCOM, BA, BBA");
//        department.requestFocus();
//        checkDept = false;
//        }
//        }
//        if(!guardNum.isEmpty()){
//        if(!guardNum.matches("[0-9]{10}")){
//        guardianNumber.setError("Please enter a valid number");
//        guardianNumber.requestFocus();
//        checkNumber = false;
//        }
//        }
//
//        if(!yearOfDepartment.isEmpty()){
//        if(yearOfDepartment.equals("1") || yearOfDepartment.equals("2") || yearOfDepartment.equals("3")){
//        checkYear = true;
//        }else{
//        year.setError("Please enter a valid year");
//        year.requestFocus();
//        checkYear = false;
//        }
//        }
//        if(checkDept==true && checkNumber==true && checkYear==true){
//        FirebaseUser user = fAuth.getCurrentUser();
//        DocumentReference df = fStore.collection("studentAboutInfo").document(""+AdmissionID);
//        Map<String, Object> userAboutInfo = new HashMap<>();
//        userAboutInfo.put("Department", dept);
//        userAboutInfo.put("Year", yearOfDepartment);
//        userAboutInfo.put("Address", addr);
//        userAboutInfo.put("Guardian's Name", guardName);
//        userAboutInfo.put("Guardian's Number", guardNum);
//        userAboutInfo.put("AdmissionID", AdmissionID);
//
//        df.set(userAboutInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
//@Override
//public void onSuccess(Void unused) {
//        Toast.makeText(getContext(), "Successfully Entered", Toast.LENGTH_SHORT).show();
//        }
//        }).addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Toast.makeText(getContext(), "Failed to enter", Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
//
//        StudentHomeActivity SHA = (StudentHomeActivity) getActivity();
//        SHA.recieveStudentAboutInfo(dept, addr, guardName, guardNum);
//        }
//        });
//
//
//
//        }
//        }).addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Toast.makeText(getContext(), "Not able to connect with database", Toast.LENGTH_SHORT).show();
//        }
//        });