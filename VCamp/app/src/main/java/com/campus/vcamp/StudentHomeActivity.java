package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class StudentHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener/*, studentAboutInfoFragment.onSubmitButtonIsSelected*/{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    StorageReference profilePic;
    FragmentTransaction fragmentTransaction;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView text;
    ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        profilePic = FirebaseStorage.getInstance().getReference().child("Profile/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");

        toolbar = findViewById(R.id.studentToolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.student_drawer);
        navigationView = findViewById(R.id.student_navigationView);

        //For printing the name of the user
        View headerView = navigationView.getHeaderView(0);
        text = headerView.findViewById(R.id.studentName);
        userPic = headerView.findViewById(R.id.userImage);

        //Retrieval and displaying of DP
        profilePic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(userPic);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opened, R.string.closed);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //Default Fragment calling
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.student_container_fragment, new MainStudentFragment());
        fragmentTransaction.commit();

        DocumentReference df = fStore.collection("Users").document(Objects.requireNonNull(fAuth.getUid()));
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name = documentSnapshot.getString("FullName");
                text.setText(name);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentHomeActivity.this, "Bad request", Toast.LENGTH_SHORT).show();
            }
        });

       /* //Sending data to fragment
        Bundle bundle = new Bundle();
        bundle.putString("key", "hai");

        FragmentTransaction frag  = getSupportFragmentManager().beginTransaction();
        studentAboutInfoFragment fragClass = new studentAboutInfoFragment();
        fragClass.setArguments(bundle);
        fragmentTransaction.replace(R.id.student_container_fragment, fragClass);
        frag.commit();*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    //Recieving from fragments
    public void recieveStudentAboutInfo(String department, String address, String guardianName, String guardianNumber){
       String dept = department;
       String addr = address;
       String guardName = guardianName;
       String guardNum = guardianNumber;
    }

//Side Fragment onClick
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.homepage){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.student_container_fragment, new MainStudentFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.studentClassChat){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String Admission = documentSnapshot.getString("Admission");
                DocumentReference studentInfo = fStore.collection("studentAboutInfo").document(""+Admission);
                studentInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String Department = documentSnapshot.getString("Department");
                        String Year = documentSnapshot.getString("Year");
                        String StudentYear = "";
                        if(Year.equals("1")){
                            StudentYear = "1"+Department;
                        }else if(Year.equals("2")){
                            StudentYear = "2"+Department;
                        }else if(Year.equals("3")){
                            StudentYear = "3"+Department;
                        }
                        Intent intent = new Intent(StudentHomeActivity.this, student_class_notification_activity.class);
                        intent.putExtra("StudentType", StudentYear);
                        intent.putExtra("Department", Department);
                        startActivity(intent);
                    }
                });
                }
            });
        }

        if(item.getItemId() == R.id.studentDeptChat){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String Admission = documentSnapshot.getString("Admission");
                    DocumentReference studentInfo = fStore.collection("studentAboutInfo").document(""+Admission);
                    studentInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String Department = documentSnapshot.getString("Department");
                            String studentType = "4"+Department;
                            Intent intent = new Intent(StudentHomeActivity.this, student_dept_notification_activity.class);
                            intent.putExtra("Department", Department);
                            intent.putExtra("StudentType", studentType);
                            startActivity(intent);
                        }
                    });
                }
            });
        }

        if(item.getItemId() == R.id.knowStudentLeave){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String uName = documentSnapshot.getString("Admission");
                    Intent intent = new Intent(StudentHomeActivity.this, student_leave_status.class);
                    intent.putExtra("UserName", uName);
                    startActivity(intent);
                }
            });
        }

        if(item.getItemId() == R.id.assignmentStatusChecker){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String Admission = documentSnapshot.getString("Admission");
                    Intent intent = new Intent(StudentHomeActivity.this, student_assignment_status_checker.class);
                    intent.putExtra("Admission", Admission);
                    startActivity(intent);
                }
            });
        }

        if(item.getItemId() == R.id.student_apply_for_leave){
            DocumentReference getAdmissionId = fStore.collection("Users").document(fAuth.getUid());
            getAdmissionId.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String admissionID = documentSnapshot.getString("Admission");

                    DocumentReference documentReference = fStore.collection("studentAboutInfo").document(""+admissionID);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String abouInfoDept = documentSnapshot.getString("Department");
                            String abouInfoYear = documentSnapshot.getString("Year");

                            try{
                                if(!abouInfoDept.isEmpty() && !abouInfoYear.isEmpty()){
                                    fragmentManager = getSupportFragmentManager();
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.student_container_fragment, new student_attendance());
                                    fragmentTransaction.commit();
                                }
                            }catch (Exception e){
                                Toast.makeText(StudentHomeActivity.this, "Please fill your information", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
        }

        if(item.getItemId() == R.id.studentAssignmentUploadMenuTab){
            DocumentReference getAdmissionID = fStore.collection("Users").document(fAuth.getUid());
            getAdmissionID.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String admission = documentSnapshot.getString("Admission");
                    String name = documentSnapshot.getString("FullName");
                    DocumentReference getStudentInfo = fStore.collection("studentAboutInfo").document(""+admission);
                    getStudentInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String year = documentSnapshot.getString("Year");
                            String dept = documentSnapshot.getString("Department");
                            Bundle bundle = new Bundle();
                            bundle.putString("admission", admission);
                            bundle.putString("name", name);
                            bundle.putString("year", year);
                            bundle.putString("dept", dept);
                            FragmentTransaction frag  = getSupportFragmentManager().beginTransaction();
                            student_assignment_upload_fragment fragClass = new student_assignment_upload_fragment();
                            fragClass.setArguments(bundle);
                            frag.replace(R.id.student_container_fragment, fragClass);
                            frag.commit();
                        }
                    });
                }
            });
        }

        if(item.getItemId() == R.id.studentYourAttendance){
            DocumentReference getAdmissionID = fStore.collection("Users").document(fAuth.getUid());
            getAdmissionID.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String admission = documentSnapshot.getString("Admission");
                    String name = documentSnapshot.getString("FullName");
                    DocumentReference getStudentInfo = fStore.collection("studentAboutInfo").document(""+admission);
                    getStudentInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String year = documentSnapshot.getString("Year");
                            String dept = documentSnapshot.getString("Department");
                            Bundle bundle = new Bundle();
                            bundle.putString("admission", admission);
                            bundle.putString("name", name);
                            bundle.putString("year", year);
                            bundle.putString("dept", dept);
                            FragmentTransaction frag  = getSupportFragmentManager().beginTransaction();
                            student_your_attendance_display_fragment fragClass = new student_your_attendance_display_fragment();
                            fragClass.setArguments(bundle);
                            frag.replace(R.id.student_container_fragment, fragClass);
                            frag.commit();
                        }
                    });
                }
            });
        }


        if(item.getItemId() == R.id.student_attendance){
            DocumentReference getAdmissionID = fStore.collection("Users").document(fAuth.getUid());
            getAdmissionID.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String admission = documentSnapshot.getString("Admission");
                    String name = documentSnapshot.getString("FullName");
                    DocumentReference getStudentInfo = fStore.collection("studentAboutInfo").document(""+admission);
                    getStudentInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String year = documentSnapshot.getString("Year");
                            String dept = documentSnapshot.getString("Department");
                            Bundle bundle = new Bundle();
                            bundle.putString("admission", admission);
                            bundle.putString("name", name);
                            bundle.putString("year", year);
                            bundle.putString("dept", dept);
                            FragmentTransaction frag  = getSupportFragmentManager().beginTransaction();
                            insert_student_attendance_fragment fragClass = new insert_student_attendance_fragment();
                            fragClass.setArguments(bundle);
                            frag.replace(R.id.student_container_fragment, fragClass);
                            frag.commit();
                        }
                    });
                }
            });
        }


        if(item.getItemId() == R.id.knowYourMarks){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String uName = documentSnapshot.getString("Admission");
                    Intent intent = new Intent(StudentHomeActivity.this, student_marks_display_activity.class);
                    intent.putExtra("UserName", uName);
                    startActivity(intent);
                }
            });
        }
        if(item.getItemId() == R.id.studentYourInfo){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.student_container_fragment, new student_your_information());
            fragmentTransaction.commit();
        }
        if(item.getItemId() == R.id.about){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.student_container_fragment, new studentAboutInfoFragment());
            fragmentTransaction.commit();
        }
        if(item.getItemId() == R.id.changePwd){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.student_container_fragment, new student_Change_Password());
            fragmentTransaction.commit();
        }
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return true;
    }
/*
    @Override
    public void onStudentInfoSubmitButton() {
        department.setText("hai");
    }*/
}