package com.campus.vcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.TypeOrBuilder;

public class TeacherHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    TextView teacherName;
    FragmentTransaction fragmentTransaction;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        View header = navigationView.getHeaderView(0);
        teacherName = header.findViewById(R.id.teacher_name);

        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        // load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new MainFragmentTeacher());
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.teacherHome){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new MainFragmentTeacher());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.primaryNotification){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String FullName = documentSnapshot.getString("FullName");
                    Intent intent = new Intent(TeacherHomeActivity.this, teachers_primary_notification_activity.class);
                    intent.putExtra("FullName", FullName);
                    startActivity(intent);
                }
            });
        }

        if(item.getItemId() == R.id.firstYear){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String oldType = documentSnapshot.getString("TypeOfTeacher");
                    String Year = oldType.substring(0,1);
                    String hod = oldType.substring(0, 3);
                    String TeacherType = "";
                    if(Year.equals("0")){
                        TeacherType = oldType.replaceFirst("0", "1");
                    }else if(Year.equals("1")){
                        TeacherType = oldType.replaceFirst("1", "1");
                    }else if(Year.equals("2")){
                        TeacherType = oldType.replaceFirst("2", "1");
                    }else if(Year.equals("3")){
                        TeacherType = oldType.replaceFirst("3", "1");
                    }else if(Year.equals("4")){
                        TeacherType = oldType.replaceFirst("4", "1");
                    }

                    String SemChecker = "1";
                    String FullName = documentSnapshot.getString("FullName");
                    Intent intent = new Intent(TeacherHomeActivity.this, class_chat_activity.class);
                    intent.putExtra("TeacherType", TeacherType);
                    intent.putExtra("FullName", FullName);
                    intent.putExtra("SemChecker", SemChecker);
                    startActivity(intent);
                }
            });
        }

        if(item.getItemId() == R.id.secondYear){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String oldType = documentSnapshot.getString("TypeOfTeacher");
                    String Year = oldType.substring(0,1);
                    String TeacherType = "";
                    if(Year.equals("0")){
                        TeacherType = oldType.replaceFirst("0", "2");
                    }else if(Year.equals("1")){
                        TeacherType = oldType.replaceFirst("1", "2");
                    }else if(Year.equals("2")){
                        TeacherType = oldType.replaceFirst("2", "2");
                    }else if(Year.equals("3")){
                        TeacherType = oldType.replaceFirst("3", "2");
                    }else if(Year.equals("4")){
                        TeacherType = oldType.replaceFirst("4", "2");
                    }
                    String FullName = documentSnapshot.getString("FullName");
                    String SemChecker = "2";
                    Intent intent = new Intent(TeacherHomeActivity.this, class_chat_activity.class);
                    intent.putExtra("TeacherType", TeacherType);
                    intent.putExtra("FullName", FullName);
                    intent.putExtra("SemChecker", SemChecker);
                    startActivity(intent);
                }
            });
        }

        if(item.getItemId() == R.id.thirdYear){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String oldType = documentSnapshot.getString("TypeOfTeacher");
                    String Year = oldType.substring(0,1);
                    String TeacherType = "";
                    if(Year.equals("0")){
                        TeacherType = oldType.replaceFirst("0", "3");
                    }else if(Year.equals("1")){
                        TeacherType = oldType.replaceFirst("1", "3");
                    }else if(Year.equals("2")){
                        TeacherType = oldType.replaceFirst("2", "3");
                    }else if(Year.equals("3")){
                        TeacherType = oldType.replaceFirst("3", "3");
                    }else if(Year.equals("4")){
                        TeacherType = oldType.replaceFirst("4", "3");
                    }
                    String FullName = documentSnapshot.getString("FullName");
                    String SemChecker = "3";
                    Intent intent = new Intent(TeacherHomeActivity.this, class_chat_activity.class);
                    intent.putExtra("TeacherType", TeacherType);
                    intent.putExtra("FullName", FullName);
                    intent.putExtra("SemChecker", SemChecker);
                    startActivity(intent);
                }
            });
        }

        if(item.getItemId() == R.id.deptChat){
            DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String oldType = documentSnapshot.getString("TypeOfTeacher");
                    String Year = oldType.substring(0,1);
                    String TeacherType = "";
                    if(Year.equals("0")){
                        TeacherType = oldType.replaceFirst("0", "4");
                    }else if(Year.equals("1")){
                        TeacherType = oldType.replaceFirst("1", "4");
                    }else if(Year.equals("2")){
                        TeacherType = oldType.replaceFirst("2", "4");
                    }else if(Year.equals("3")){
                        TeacherType = oldType.replaceFirst("3", "4");
                    }else if(Year.equals("4")){
                        TeacherType = oldType.replaceFirst("4", "4");
                    }
                    String FullName = documentSnapshot.getString("FullName");
                    String SemChecker = "4";
                    Intent intent = new Intent(TeacherHomeActivity.this, class_chat_activity.class);
                    intent.putExtra("TeacherType", TeacherType);
                    intent.putExtra("FullName", FullName);
                    intent.putExtra("SemChecker", SemChecker);
                    startActivity(intent);
                }
            });
        }

        DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(item.getItemId() == R.id.appliedLeave) {
                    String TeacherType = documentSnapshot.getString("TypeOfTeacher");
//                    Toast.makeText(TeacherHomeActivity.this, ""+TeacherType, Toast.LENGTH_SHORT).show();
                    boolean teacherMatch = true, hodMatch = true;
                    String[] ClassTeacherType = new String[]{"1BCA", "2BCA", "3BCA", "1BCOM", "2BCOM", "3BCOM"};
                    //for teachers
                    for (int i = 0; i < ClassTeacherType.length; i++) {
                        if (TeacherType.equalsIgnoreCase(ClassTeacherType[i])) {
                            teacherMatch = false;
                            break;
                        }
                    }
                    if (teacherMatch == false) {
                        Intent intent = new Intent(TeacherHomeActivity.this, teacher_leave_acceptance.class);
                        intent.putExtra("TeacherType", TeacherType);
                        startActivity(intent);

                    } else {
                        Toast.makeText(TeacherHomeActivity.this, "You are not authorised to access this", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        if(item.getItemId() == R.id.studentAssignment){
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String Name = documentSnapshot.getString("FullName");
                    Bundle bundle = new Bundle();
                    bundle.putString("Name", Name);
                    student_type_checker_for_assignment_fragment fragClass = new student_type_checker_for_assignment_fragment();
                    fragClass.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragClass).commit();
                }
            });
        }


        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        //checking who can access and change time table
        /*Types of Teachers
        1. Normal teacher Eg: for BBA its 1BBA
        2. Class Teacher
            a. Ist year Eg: IBBA
            b. IInd year Eg: IIBBA
            c. III year Eg: IIIBBA
        3. HOD Eg: HODBBA
        * */
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String TeacherType = documentSnapshot.getString("TypeOfTeacher");
                if(item.getItemId() == R.id.changeTimeTable){
                    boolean teacherMatch = true, hodMatch = true;
                    String[] TeacherTypeArray = new String[]{"1BCA", "2BCA", "3BCA", "1BCOM", "2BCOM", "3BCOM"};
                    String[] HODType = new String[]{"4BCA", "4BCOM"};
                    //for teachers
                    for(int i = 0; i<TeacherTypeArray.length; i++){
                        if(TeacherType .equalsIgnoreCase(TeacherTypeArray[i])){
                            teacherMatch = false;
                            break;
                        }
                    }
                    //for HOD's
                    for(int j = 0; j<HODType.length; j++){
                        if(TeacherType .equalsIgnoreCase(HODType[j])){
                            hodMatch = false;
                            break;
                        }
                    }

                    if(teacherMatch==false){
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_fragment, new changeTimeTable());
                        fragmentTransaction.commit();
                    }else if(hodMatch==false){
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_fragment, new HOD_View_Timetable());
                        fragmentTransaction.commit();
                    }
                    else{
                        Toast.makeText(TeacherHomeActivity.this, "You are not authorised", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(item.getItemId() == R.id.studentMarksSideBar){
                    String TeacherType = documentSnapshot.getString("TypeOfTeacher");
                    boolean teacherMatch = true, hodMatch = true;
                    String[] ClassTeacherType = new String[]{"1BCA", "2BCA", "3BCA", "1BCOM", "2BCOM", "3BCOM"};
                    //for teachers
                    for(int i = 0; i<ClassTeacherType.length; i++){
                        if(TeacherType.equalsIgnoreCase(ClassTeacherType[i])){
                            teacherMatch = false;
                            break;
                        }
                    }
                    if(teacherMatch==false){
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_fragment, new insert_student_marks());
                            fragmentTransaction.commit();

                    }else{
                        Toast.makeText(TeacherHomeActivity.this, "You are not authorised to access this", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if(item.getItemId() == R.id.studentInfoSideBar){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new student_information_displayed());
            fragmentTransaction.commit();
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference = fStore.collection("Users").document(fAuth.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String TeacherType = documentSnapshot.getString("TypeOfTeacher");
                String FullName = documentSnapshot.getString("FullName");
                String Year = TeacherType.substring(0,1);

                Menu classChat = navigationView.getMenu();
                Menu leave = navigationView.getMenu();
                Menu marks = navigationView.getMenu();
                MenuItem Leave = leave.findItem(R.id.appliedLeave);
                MenuItem Marks = marks.findItem(R.id.studentMarksSideBar);

                if(Year.equals("1") || Year.equals("2") || Year.equals("3")){
                    Leave.setVisible(true);
                    Marks.setVisible(true);
                }
            }
        });
    }
}