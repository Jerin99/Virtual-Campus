package com.campus.vcamp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class student_type_checker_for_assignment_fragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button firstSemAssignmentDisplay, secondSemAssignmentDisplay, thirdSemAssignmentDisplay, fourthSemAssignmentDisplay, fifthSemAssignmentDisplay, sixthSemAssignmentDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_type_checker_for_assignment_fragment, container, false);

        String Name = getArguments().getString("Name");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        firstSemAssignmentDisplay = view.findViewById(R.id.firstSemAssignmentDisplay);
        secondSemAssignmentDisplay = view.findViewById(R.id.secondSemAssignmentDisplay);
        thirdSemAssignmentDisplay = view.findViewById(R.id.thirdSemAssignmentDisplay);
        fourthSemAssignmentDisplay = view.findViewById(R.id.fourthSemAssignmentDisplay);
        fifthSemAssignmentDisplay = view.findViewById(R.id.fifthSemAssignmentDisplay);
        sixthSemAssignmentDisplay = view.findViewById(R.id.sixthSemAssignmentDisplay);

        firstSemAssignmentDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Semester = "1";
                Bundle bundle = new Bundle();
                bundle.putString("Semester", Semester);
                bundle.putString("Name", Name);
                teacher_assignment_display fragClass = new teacher_assignment_display();
                fragClass.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_fragment, fragClass).commit();
            }
        });

        secondSemAssignmentDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Semester = "2";
                Bundle bundle = new Bundle();
                bundle.putString("Semester", Semester);
                bundle.putString("Name", Name);
                teacher_assignment_display fragClass = new teacher_assignment_display();
                fragClass.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_fragment, fragClass).commit();
            }
        });

        thirdSemAssignmentDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Semester = "3";
                Bundle bundle = new Bundle();
                bundle.putString("Semester", Semester);
                bundle.putString("Name", Name);
                teacher_assignment_display fragClass = new teacher_assignment_display();
                fragClass.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_fragment, fragClass).commit();
            }
        });

        fourthSemAssignmentDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Semester = "4";
                Bundle bundle = new Bundle();
                bundle.putString("Semester", Semester);
                bundle.putString("Name", Name);
                teacher_assignment_display fragClass = new teacher_assignment_display();
                fragClass.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_fragment, fragClass).commit();
            }
        });

        fifthSemAssignmentDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Semester = "5";
                Bundle bundle = new Bundle();
                bundle.putString("Semester", Semester);
                bundle.putString("Name", Name);
                teacher_assignment_display fragClass = new teacher_assignment_display();
                fragClass.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_fragment, fragClass).commit();
            }
        });

        sixthSemAssignmentDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Semester = "6";
                Bundle bundle = new Bundle();
                bundle.putString("Semester", Semester);
                bundle.putString("Name", Name);
                teacher_assignment_display fragClass = new teacher_assignment_display();
                fragClass.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_fragment, fragClass).commit();
            }
        });

        return view;
    }
}