package com.campus.vcamp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HOD_View_Timetable extends Fragment {

    TextView HOD1IMon, HOD1IIMon, HOD1IIIMon, HOD1IVMon, HOD1VMon, HOD1ITue, HOD1IITue, HOD1IIITue, HOD1IVTue, HOD1VTue, HOD1IWed, HOD1IIWed, HOD1IIIWed, HOD1IVWed, HOD1VWed,
            HOD1IThu, HOD1IIThu, HOD1IIIThu, HOD1IVThu, HOD1VThu, HOD1IFri, HOD1IIFri, HOD1IIIFri, HOD1IVFri, HOD1VFri, HOD2IMon, HOD2IIMon, HOD2IIIMon, HOD2IVMon, HOD2VMon,
            HOD2ITue, HOD2IITue, HOD2IIITue, HOD2IVTue, HOD2VTue, HOD2IWed, HOD2IIWed, HOD2IIIWed, HOD2IVWed, HOD2VWed, HOD2IThu, HOD2IIThu, HOD2IIIThu, HOD2IVThu, HOD2VThu,
            HOD2IFri, HOD2IIFri, HOD2IIIFri, HOD2IVFri, HOD2VFri, HOD3IMon, HOD3IIMon, HOD3IIIMon, HOD3IVMon, HOD3VMon, HOD3ITue, HOD3IITue, HOD3IIITue, HOD3IVTue, HOD3VTue,
            HOD3IWed, HOD3IIWed, HOD3IIIWed, HOD3IVWed, HOD3VWed, HOD3IThu, HOD3IIThu, HOD3IIIThu, HOD3IVThu, HOD3VThu, HOD3IFri, HOD3IIFri, HOD3IIIFri, HOD3IVFri, HOD3VFri,
            timeTable1, timeTable2, timeTable3;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_h_o_d__view__timetable, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        timeTable1 = view.findViewById(R.id.HODI);
        timeTable2 = view.findViewById(R.id.HODII);
        timeTable3 = view.findViewById(R.id.HODIII);

        HOD1IMon = view.findViewById(R.id.HOD1IMon);
        HOD1IIMon = view.findViewById(R.id.HOD1IIMon);
        HOD1IIIMon = view.findViewById(R.id.HOD1IIIMon);
        HOD1IVMon = view.findViewById(R.id.HOD1IVMon);
        HOD1VMon = view.findViewById(R.id.HOD1VMon);

        HOD1ITue = view.findViewById(R.id.HOD1ITue);
        HOD1IITue = view.findViewById(R.id.HOD1IITue);
        HOD1IIITue = view.findViewById(R.id.HOD1IIITue);
        HOD1IVTue = view.findViewById(R.id.HOD1IVTue);
        HOD1VTue = view.findViewById(R.id.HOD1VTue);

        HOD1IWed = view.findViewById(R.id.HOD1IWed);
        HOD1IIWed = view.findViewById(R.id.HOD1IIWed);
        HOD1IIIWed = view.findViewById(R.id.HOD1IIIWed);
        HOD1IVWed = view.findViewById(R.id.HOD1IVWed);
        HOD1VWed = view.findViewById(R.id.HOD1VWed);

        HOD1IThu = view.findViewById(R.id.HOD1IThu);
        HOD1IIThu = view.findViewById(R.id.HOD1IIThu);
        HOD1IIIThu = view.findViewById(R.id.HOD1IIIThu);
        HOD1IVThu = view.findViewById(R.id.HOD1IVThu);
        HOD1VThu = view.findViewById(R.id.HOD1VThu);

        HOD1IFri = view.findViewById(R.id.HOD1IFri);
        HOD1IIFri = view.findViewById(R.id.HOD1IIFri);
        HOD1IIIFri = view.findViewById(R.id.HOD1IIIFri);
        HOD1IVFri = view.findViewById(R.id.HOD1IVFri);
        HOD1VFri = view.findViewById(R.id.HOD1VFri);

        HOD2IMon = view.findViewById(R.id.HOD2IMon);
        HOD2IIMon = view.findViewById(R.id.HOD2IIMon);
        HOD2IIIMon = view.findViewById(R.id.HOD2IIIMon);
        HOD2IVMon = view.findViewById(R.id.HOD2IVMon);
        HOD2VMon = view.findViewById(R.id.HOD2VMon);

        HOD2ITue = view.findViewById(R.id.HOD2ITue);
        HOD2IITue = view.findViewById(R.id.HOD2IITue);
        HOD2IIITue = view.findViewById(R.id.HOD2IIITue);
        HOD2IVTue = view.findViewById(R.id.HOD2IVTue);
        HOD2VTue = view.findViewById(R.id.HOD2VTue);

        HOD2IWed = view.findViewById(R.id.HOD2IWed);
        HOD2IIWed = view.findViewById(R.id.HOD2IIWed);
        HOD2IIIWed = view.findViewById(R.id.HOD2IIIWed);
        HOD2IVWed = view.findViewById(R.id.HOD2IVWed);
        HOD2VWed = view.findViewById(R.id.HOD2VWed);

        HOD2IThu = view.findViewById(R.id.HOD2IThu);
        HOD2IIThu = view.findViewById(R.id.HOD2IIThu);
        HOD2IIIThu = view.findViewById(R.id.HOD2IIIThu);
        HOD2IVThu = view.findViewById(R.id.HOD2IVThu);
        HOD2VThu = view.findViewById(R.id.HOD2VThu);

        HOD2IFri = view.findViewById(R.id.HOD2IFri);
        HOD2IIFri = view.findViewById(R.id.HOD2IIFri);
        HOD2IIIFri = view.findViewById(R.id.HOD2IIIFri);
        HOD2IVFri = view.findViewById(R.id.HOD2IVFri);
        HOD2VFri = view.findViewById(R.id.HOD2VFri);

        HOD3IMon = view.findViewById(R.id.HOD3IMon);
        HOD3IIMon = view.findViewById(R.id.HOD3IIMon);
        HOD3IIIMon = view.findViewById(R.id.HOD3IIIMon);
        HOD3IVMon = view.findViewById(R.id.HOD3IVMon);
        HOD3VMon = view.findViewById(R.id.HOD3VMon);

        HOD3ITue = view.findViewById(R.id.HOD3ITue);
        HOD3IITue = view.findViewById(R.id.HOD3IITue);
        HOD3IIITue = view.findViewById(R.id.HOD3IIITue);
        HOD3IVTue = view.findViewById(R.id.HOD3IVTue);
        HOD3VTue = view.findViewById(R.id.HOD3VTue);

        HOD3IWed = view.findViewById(R.id.HOD3IWed);
        HOD3IIWed = view.findViewById(R.id.HOD3IIWed);
        HOD3IIIWed = view.findViewById(R.id.HOD3IIIWed);
        HOD3IVWed = view.findViewById(R.id.HOD3IVWed);
        HOD3VWed = view.findViewById(R.id.HOD3VWed);

        HOD3IThu = view.findViewById(R.id.HOD3IThu);
        HOD3IIThu = view.findViewById(R.id.HOD3IIThu);
        HOD3IIIThu = view.findViewById(R.id.HOD3IIIThu);
        HOD3IVThu = view.findViewById(R.id.HOD3IVThu);
        HOD3VThu = view.findViewById(R.id.HOD3VThu);

        HOD3IFri = view.findViewById(R.id.HOD3IFri);
        HOD3IIFri = view.findViewById(R.id.HOD3IIFri);
        HOD3IIIFri = view.findViewById(R.id.HOD3IIIFri);
        HOD3IVFri = view.findViewById(R.id.HOD3IVFri);
        HOD3VFri = view.findViewById(R.id.HOD3VFri);


        DocumentReference df = fStore.collection("Users").document(fAuth.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String position = documentSnapshot.getString("TypeOfTeacher");
                String dept = position.substring(1);

                timeTable1.setText("First "+dept);
                timeTable2.setText("Second "+dept);
                timeTable3.setText("Third "+dept);

                String first = null, second = null, third = null;
                if(dept.equalsIgnoreCase("BCA")){
                    first = "IBCATimeTable";
                    second = "IIBCATimeTable";
                    third = "IIIBCATimeTable";
                }

//Retrieving timetables
//For first Year
                DocumentReference timeTableRetrieval_Iyear = fStore.collection(""+first).document(""+first);
                DocumentReference timeTableRetrieval_IIyear = fStore.collection(""+second).document(""+second);
                DocumentReference timeTableRetrieval_IIIyear = fStore.collection(""+third).document(""+third);
                timeTableRetrieval_Iyear.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        HOD1IMon.setText(documentSnapshot.getString("IMon"));
                        HOD1IIMon.setText(documentSnapshot.getString("IIMon"));
                        HOD1IIIMon.setText(documentSnapshot.getString("IIIMon"));
                        HOD1IVMon.setText(documentSnapshot.getString("IVMon"));
                        HOD1VMon.setText(documentSnapshot.getString("VMon"));

                        HOD1ITue.setText(documentSnapshot.getString("ITue"));
                        HOD1IITue.setText(documentSnapshot.getString("IITue"));
                        HOD1IIITue.setText(documentSnapshot.getString("IIITue"));
                        HOD1IVTue.setText(documentSnapshot.getString("IVTue"));
                        HOD1VTue.setText(documentSnapshot.getString("VTue"));

                        HOD1IWed.setText(documentSnapshot.getString("IWed"));
                        HOD1IIWed.setText(documentSnapshot.getString("IIWed"));
                        HOD1IIIWed.setText(documentSnapshot.getString("IIIWed"));
                        HOD1IVWed.setText(documentSnapshot.getString("IVWed"));
                        HOD1VWed.setText(documentSnapshot.getString("VWed"));

                        HOD1IThu.setText(documentSnapshot.getString("IThu"));
                        HOD1IIThu.setText(documentSnapshot.getString("IIThu"));
                        HOD1IIIThu.setText(documentSnapshot.getString("IIIThu"));
                        HOD1IVThu.setText(documentSnapshot.getString("IVThu"));
                        HOD1VThu.setText(documentSnapshot.getString("VThu"));

                        HOD1IFri.setText(documentSnapshot.getString("IFri"));
                        HOD1IIFri.setText(documentSnapshot.getString("IIFri"));
                        HOD1IIIFri.setText(documentSnapshot.getString("IIIFri"));
                        HOD1IVFri.setText(documentSnapshot.getString("IVFri"));
                        HOD1VFri.setText(documentSnapshot.getString("VFri"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Can't connect to database", Toast.LENGTH_SHORT).show();
                    }
                });
//For second year
                timeTableRetrieval_IIyear.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        HOD2IMon.setText(documentSnapshot.getString("IMon"));
                        HOD2IIMon.setText(documentSnapshot.getString("IIMon"));
                        HOD2IIIMon.setText(documentSnapshot.getString("IIIMon"));
                        HOD2IVMon.setText(documentSnapshot.getString("IVMon"));
                        HOD2VMon.setText(documentSnapshot.getString("VMon"));

                        HOD2ITue.setText(documentSnapshot.getString("ITue"));
                        HOD2IITue.setText(documentSnapshot.getString("IITue"));
                        HOD2IIITue.setText(documentSnapshot.getString("IIITue"));
                        HOD2IVTue.setText(documentSnapshot.getString("IVTue"));
                        HOD2VTue.setText(documentSnapshot.getString("VTue"));

                        HOD2IWed.setText(documentSnapshot.getString("IWed"));
                        HOD2IIWed.setText(documentSnapshot.getString("IIWed"));
                        HOD2IIIWed.setText(documentSnapshot.getString("IIIWed"));
                        HOD2IVWed.setText(documentSnapshot.getString("IVWed"));
                        HOD2VWed.setText(documentSnapshot.getString("VWed"));

                        HOD2IThu.setText(documentSnapshot.getString("IThu"));
                        HOD2IIThu.setText(documentSnapshot.getString("IIThu"));
                        HOD2IIIThu.setText(documentSnapshot.getString("IIIThu"));
                        HOD2IVThu.setText(documentSnapshot.getString("IVThu"));
                        HOD2VThu.setText(documentSnapshot.getString("VThu"));

                        HOD2IFri.setText(documentSnapshot.getString("IFri"));
                        HOD2IIFri.setText(documentSnapshot.getString("IIFri"));
                        HOD2IIIFri.setText(documentSnapshot.getString("IIIFri"));
                        HOD2IVFri.setText(documentSnapshot.getString("IVFri"));
                        HOD2VFri.setText(documentSnapshot.getString("VFri"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Can't connect to database", Toast.LENGTH_SHORT).show();
                    }
                });
                
//For third year
                timeTableRetrieval_IIIyear.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        HOD3IMon.setText(documentSnapshot.getString("IMon"));
                        HOD3IIMon.setText(documentSnapshot.getString("IIMon"));
                        HOD3IIIMon.setText(documentSnapshot.getString("IIIMon"));
                        HOD3IVMon.setText(documentSnapshot.getString("IVMon"));
                        HOD3VMon.setText(documentSnapshot.getString("VMon"));

                        HOD3ITue.setText(documentSnapshot.getString("ITue"));
                        HOD3IITue.setText(documentSnapshot.getString("IITue"));
                        HOD3IIITue.setText(documentSnapshot.getString("IIITue"));
                        HOD3IVTue.setText(documentSnapshot.getString("IVTue"));
                        HOD3VTue.setText(documentSnapshot.getString("VTue"));

                        HOD3IWed.setText(documentSnapshot.getString("IWed"));
                        HOD3IIWed.setText(documentSnapshot.getString("IIWed"));
                        HOD3IIIWed.setText(documentSnapshot.getString("IIIWed"));
                        HOD3IVWed.setText(documentSnapshot.getString("IVWed"));
                        HOD3VWed.setText(documentSnapshot.getString("VWed"));

                        HOD3IThu.setText(documentSnapshot.getString("IThu"));
                        HOD3IIThu.setText(documentSnapshot.getString("IIThu"));
                        HOD3IIIThu.setText(documentSnapshot.getString("IIIThu"));
                        HOD3IVThu.setText(documentSnapshot.getString("IVThu"));
                        HOD3VThu.setText(documentSnapshot.getString("VThu"));

                        HOD3IFri.setText(documentSnapshot.getString("IFri"));
                        HOD3IIFri.setText(documentSnapshot.getString("IIFri"));
                        HOD3IIIFri.setText(documentSnapshot.getString("IIIFri"));
                        HOD3IVFri.setText(documentSnapshot.getString("IVFri"));
                        HOD3VFri.setText(documentSnapshot.getString("VFri"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Can't connect to database", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Can't connect to database", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}