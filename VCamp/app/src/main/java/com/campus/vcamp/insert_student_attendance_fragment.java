package com.campus.vcamp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class insert_student_attendance_fragment extends Fragment {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    TextView errorMsgForCheckBoxesInAttendance, mainErrorMsginAttedance;
    EditText semForInsertingAttendance;
    CheckBox insertAttendanceBeforeInterval, insertAttendanceAfterInterval;
    Button enterAttendanceButton;
    Double attendanceValue = 0D;
    Double yesterdaysAttendance = 0D;
    Double YesterDay = 0D;
    boolean checkValidation = true;
    String semesterCheck;//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_insert_student_attendance_fragment, container, false);

        String Admission, Name, Year, Department;
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        semForInsertingAttendance = view.findViewById(R.id.semForInsertingAttendance);
        insertAttendanceBeforeInterval = view.findViewById(R.id.insertAttendanceBeforeInterval);
        insertAttendanceAfterInterval = view.findViewById(R.id.insertAttendanceAfterInterval);
        enterAttendanceButton = view.findViewById(R.id.enterAttendanceButton);
        errorMsgForCheckBoxesInAttendance = view.findViewById(R.id.errorMsgForCheckBoxesInAttendance);
        mainErrorMsginAttedance = view.findViewById(R.id.mainErrorMsginAttedance);

        Admission = getArguments().getString("admission");
        Name = getArguments().getString("name");
        Year = getArguments().getString("year");
        Department = getArguments().getString("dept");

        enterAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation = true;
                errorMsgForCheckBoxesInAttendance.setVisibility(View.GONE);
                mainErrorMsginAttedance.setVisibility(View.GONE);
                String Sem = semForInsertingAttendance.getText().toString();
                if(Sem.isEmpty()){
                    semForInsertingAttendance.setError("Please fill this field");
                    semForInsertingAttendance.requestFocus();
                    checkValidation = false;
                }else if(Year.equals("1")){
                    if(!Sem.equals("1") && !Sem.equals("2") ){
                        semForInsertingAttendance.setError("You can't enter attendance for this semester");
                        semForInsertingAttendance.requestFocus();
                        boolean checkValidation = false;
                    }
                }
                if(!insertAttendanceBeforeInterval.isChecked() && !insertAttendanceAfterInterval.isChecked()){
                    errorMsgForCheckBoxesInAttendance.setVisibility(View.VISIBLE);
                    errorMsgForCheckBoxesInAttendance.setText("*Please select any one of the checkboxes");
                    checkValidation = false;
                }
                if(insertAttendanceBeforeInterval.isChecked()){
                    attendanceValue = 0.5D;
                }
                if(insertAttendanceAfterInterval.isChecked()){
                    attendanceValue = 0.5D;
                }
                if(insertAttendanceBeforeInterval.isChecked() && insertAttendanceAfterInterval.isChecked()){
                    attendanceValue = 1D;
                }

            //For creating semester
                if(Sem.equals("1")){
                    semesterCheck = "First_semester_attendance";
                }else if(Sem.equals("2")){
                    semesterCheck = "Second_semester_attendance";
                }else if(Sem.equals("3")){
                    semesterCheck = "Third_semester_attendance";
                }else if(Sem.equals("4")){
                    semesterCheck = "Fourth_semester_attendance";
                }else if(Sem.equals("5")){
                    semesterCheck = "Fifth_semester_attendance";
                }else if(Sem.equals("6")){
                    semesterCheck = "Sixth_semester_attendance";
                }

                if(checkValidation){
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = new Date();
                    String currentdate = sdf.format(date);
                    Query checkLeave = (Query) fStore.collection("studentAboutInfo")
                            .document(""+Admission)
                            .collection("Leave")
                            .whereEqualTo("LeaveGrantedFor", currentdate);
                    checkLeave.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(!queryDocumentSnapshots.isEmpty()){
                                mainErrorMsginAttedance.setVisibility(View.VISIBLE);
                                mainErrorMsginAttedance.setText("*You can't enter attendance for today because you are on leave today");
                            }else{
                            //Previous day attendance
                                String dateOfCurrentDate = currentdate.substring(0, 2);
                                int firstTwoDate = Integer.parseInt(dateOfCurrentDate)-1;
                                String firstTwoDateString = firstTwoDate+"";
                                String getYesterdayDate = currentdate.replaceFirst(dateOfCurrentDate, firstTwoDateString);

                                DocumentReference yesterdayDate = fStore.collection("studentAboutInfo")
                                        .document(""+Admission)
                                        .collection(""+semesterCheck)
                                        .document(""+getYesterdayDate);
                                yesterdayDate.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document1 = task.getResult();
                                            if(document1.exists()){
                                                yesterdayDate.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        yesterdaysAttendance = documentSnapshot.getDouble("Attendance");
                                                        YesterDay = documentSnapshot.getDouble("Day");
                                                        DocumentReference documentReference = fStore.collection("studentAboutInfo")
                                                                .document(""+Admission)
                                                                .collection(""+semesterCheck)
                                                                .document(""+currentdate);
                                                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                                if(task.isSuccessful()){
                                                                    DocumentSnapshot document = task.getResult();
                                                                    if(document.exists()){
                                                                        mainErrorMsginAttedance.setVisibility(View.VISIBLE);
                                                                        mainErrorMsginAttedance.setText("*You have already inserted your attendance. If you need to change, contact admin");
                                                                    }
                                                                    else{
                                                                        Double todaysAttendance = attendanceValue + yesterdaysAttendance;
                                                                        Double Day = YesterDay+1;
                                                                        Calendar calendar = Calendar.getInstance();
                                                                        long millis = calendar.getTimeInMillis();

                                                                        Map<String, Object> insertAttendance = new HashMap<>();
                                                                        insertAttendance.put("Name", Name);
                                                                        insertAttendance.put("Admission", Admission);
                                                                        insertAttendance.put("Department", Department);
                                                                        insertAttendance.put("Year", Year);
                                                                        insertAttendance.put("Day", Day);
                                                                        insertAttendance.put("Date", currentdate);
                                                                        insertAttendance.put("Millis", millis);
                                                                        insertAttendance.put("Semester", Sem);
                                                                        insertAttendance.put("Attendance", todaysAttendance);
                                                                        documentReference.set(insertAttendance).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void unused) {
                                                                                Toast.makeText(getContext(), "Attendance Inserted Successfully", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                                                Toast.makeText(getContext(), "Failed to insert attendance", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        });

                                                    }
                                                });
                                            }else{
                                                DocumentReference documentReference = fStore.collection("studentAboutInfo")
                                                        .document(""+Admission)
                                                        .collection(""+semesterCheck)
                                                        .document(""+currentdate);
                                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            DocumentSnapshot document = task.getResult();
                                                            if(document.exists()){
                                                                mainErrorMsginAttedance.setVisibility(View.VISIBLE);
                                                                mainErrorMsginAttedance.setText("*You have already inserted your attendance. If you need to change, contact admin");
                                                            }
                                                            else{
                                                                Double todaysAttendance = attendanceValue + yesterdaysAttendance;
                                                                Double Day = YesterDay+1;
                                                                Calendar calendar = Calendar.getInstance();
                                                                long millis = calendar.getTimeInMillis();

                                                                Map<String, Object> insertAttendance = new HashMap<>();
                                                                insertAttendance.put("Name", Name);
                                                                insertAttendance.put("Admission", Admission);
                                                                insertAttendance.put("Department", Department);
                                                                insertAttendance.put("Year", Year);
                                                                insertAttendance.put("Day", Day);
                                                                insertAttendance.put("Date", currentdate);
                                                                insertAttendance.put("Millis", millis);
                                                                insertAttendance.put("Semester", Sem);
                                                                insertAttendance.put("Attendance", todaysAttendance);
                                                                documentReference.set(insertAttendance).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        Toast.makeText(getContext(), "Attendance Inserted Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull @NotNull Exception e) {
                                                                        Toast.makeText(getContext(), "Failed to insert attendance", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });

//                                yesterdayDate.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                        yesterdaysAttendance = documentSnapshot.getDouble("Attendance");
//                                        DocumentReference documentReference = fStore.collection("studentAboutInfo")
//                                                .document(""+Admission)
//                                                .collection("Attendance")
//                                                .document(""+currentdate);
//                                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
//                                                if(task.isSuccessful()){
//                                                    DocumentSnapshot document = task.getResult();
//                                                    if(document.exists()){
//                                                        mainErrorMsginAttedance.setVisibility(View.VISIBLE);
//                                                        mainErrorMsginAttedance.setText("*You have already inserted your attendance. If you need to change, contact admin");
//                                                    }
//                                                    else{
//                                                        Double todaysAttendance = attendanceValue + yesterdaysAttendance;
//                                                        Map<String, Object> insertAttendance = new HashMap<>();
//                                                        insertAttendance.put("Name", Name);
//                                                        insertAttendance.put("Admission", Admission);
//                                                        insertAttendance.put("Department", Department);
//                                                        insertAttendance.put("Year", Year);
//                                                        insertAttendance.put("Date", currentdate);
//                                                        insertAttendance.put("Semester", Sem);
//                                                        insertAttendance.put("Attendance", todaysAttendance);
//                                                        documentReference.set(insertAttendance).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void unused) {
//                                                                Toast.makeText(getContext(), "Attendance Inserted Successfully", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        }).addOnFailureListener(new OnFailureListener() {
//                                                            @Override
//                                                            public void onFailure(@NonNull @NotNull Exception e) {
//                                                                Toast.makeText(getContext(), "Failed to insert attendance", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        });
//                                                    }
//                                                }
//                                            }
//                                        });
//
//                                    }
//                                });
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}