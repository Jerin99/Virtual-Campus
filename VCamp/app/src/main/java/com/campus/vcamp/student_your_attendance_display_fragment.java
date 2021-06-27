package com.campus.vcamp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

public class student_your_attendance_display_fragment extends Fragment {
    
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView displayFirstSemAttendance, displaySecondSemAttendance, displayThirdSemAttendance, displayFourthSemAttendance, displayFifthSemAttendance, displaySixthSemAttendance,
            displayFirstSemDays, displaySecondSemDays, displayThirdSemDays, displayFourthSemDays, displayFifthSemDays, displaySixthSemDays;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_your_attendance_display_fragment, container, false);

        String Admission, Name, Year, Department;

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        displayFirstSemAttendance = view.findViewById(R.id.displayFirstSemAttendance);
        displaySecondSemAttendance = view.findViewById(R.id.displaySecondSemAttendance);
        displayThirdSemAttendance = view.findViewById(R.id.displayThirdSemAttendance);
        displayFourthSemAttendance = view.findViewById(R.id.displayFourthSemAttendance);
        displayFifthSemAttendance = view.findViewById(R.id.displayFifthSemAttendance);
        displaySixthSemAttendance = view.findViewById(R.id.displaySixthSemAttendance);

        displayFirstSemDays = view.findViewById(R.id.displayFirstSemDays);
        displaySecondSemDays = view.findViewById(R.id.displaySecondSemDays);
        displayThirdSemDays = view.findViewById(R.id.displayThirdSemDays);
        displayFourthSemDays = view.findViewById(R.id.displayFourthSemDays);
        displayFifthSemDays = view.findViewById(R.id.displayFifthSemDays);
        displaySixthSemDays = view.findViewById(R.id.displaySixthSemDays);

        Admission = getArguments().getString("admission");
        Name = getArguments().getString("name");
        Year = getArguments().getString("year");
        Department = getArguments().getString("dept");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDate = simpleDateFormat.format(date);

        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();

        Query fetchFirst = fStore.collection("studentAboutInfo")
                .document(""+Admission)
                .collection("First_semester_attendance")
                .orderBy("Millis", Query.Direction.DESCENDING)
                .limit(1);
        fetchFirst.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    Double day, attendance;
                    for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                        attendance = documentSnapshot.getDouble("Attendance");
                        day = documentSnapshot.getDouble("Day");
                        int dayForAttendance = (int) Math.round(day);
                        displayFirstSemDays.setText(attendance+" for "+dayForAttendance+" days ");
                        Double totalPercentage = ((attendance) * 100D)/180D;
                        Double total = Math.round((totalPercentage*100D))/100D;
                        displayFirstSemAttendance.setText(total+"%");
                    }
                }
            }
        });

        Query fetchSecond = fStore.collection("studentAboutInfo")
                .document(""+Admission)
                .collection("2")
                .orderBy("Millis", Query.Direction.DESCENDING)
                .limit(1);
        fetchSecond.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    Double day, attendance;
                    for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                        attendance = documentSnapshot.getDouble("Attendance");
                        day = documentSnapshot.getDouble("Day");
                        int dayForAttendance = (int) Math.round(day);
                        displayFirstSemDays.setText(attendance+" for "+dayForAttendance+" days ");
                        Double totalPercentage = ((attendance) * 180D)/180;
                        displayFirstSemAttendance.setText(totalPercentage+"%");
                    }
                }
            }
        });

        return view;
    }
}