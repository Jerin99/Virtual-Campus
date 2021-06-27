package com.campus.vcamp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainStudentFragment extends Fragment {
    TextView IMon, IIMon, IIIMon, IVMon, VMon, ITue, IITue, IIITue, IVTue, VTue, IWed, IIWed, IIIWed, IVWed, VWed,
    IThu, IIThu, IIIThu, IVThu, VThu, IFri, IIFri, IIIFri, IVFri, VFri;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    static String StudentType, StudentDepartment;
    Button studentClassChat, studentDeptChat, studentAttendanceInsert, studentLeaveApply, studentUploadAssignment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_student, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        studentClassChat = view.findViewById(R.id.studentClassChat);
        studentDeptChat = view.findViewById(R.id.studentDeptChat);
        studentAttendanceInsert = view.findViewById(R.id.studentAttendanceInsert);
        studentLeaveApply = view.findViewById(R.id.studentLeaveApply);
        studentUploadAssignment = view.findViewById(R.id.studentUploadAssignment);

        IMon = view.findViewById(R.id.IMon);
        IIMon = view.findViewById(R.id.IIMon);
        IIIMon = view.findViewById(R.id.IIIMon);
        IVMon = view.findViewById(R.id.IVMon);
        VMon = view.findViewById(R.id.VMon);

        ITue = view.findViewById(R.id.ITue);
        IITue = view.findViewById(R.id.IITue);
        IIITue = view.findViewById(R.id.IIITue);
        IVTue = view.findViewById(R.id.IVTue);
        VTue = view.findViewById(R.id.VTue);

        IWed = view.findViewById(R.id.IWed);
        IIWed = view.findViewById(R.id.IIWed);
        IIIWed = view.findViewById(R.id.IIIWed);
        IVWed = view.findViewById(R.id.IVWed);
        VWed = view.findViewById(R.id.VWed);

        IThu = view.findViewById(R.id.IThu);
        IIThu = view.findViewById(R.id.IIThu);
        IIIThu = view.findViewById(R.id.IIIThu);
        IVThu = view.findViewById(R.id.IVThu);
        VThu = view.findViewById(R.id.VThu);

        IFri = view.findViewById(R.id.IFri);
        IIFri = view.findViewById(R.id.IIFri);
        IIIFri = view.findViewById(R.id.IIIFri);
        IVFri = view.findViewById(R.id.IVFri);
        VFri = view.findViewById(R.id.VFri);

        DocumentReference getAdmissionId = fStore.collection("Users").document(fAuth.getUid());
        getAdmissionId.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String AdmissionID = documentSnapshot.getString("Admission");

                try{
                    DocumentReference studentType = fStore.collection("studentAboutInfo").document(""+AdmissionID);
                    studentType.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String bca = null;
                            StudentType = documentSnapshot.getString("Year");
                            StudentDepartment = documentSnapshot.getString("Department");

                            try{
                                if(StudentType.equalsIgnoreCase("1")){
                                    if(StudentDepartment.equalsIgnoreCase("BCA")){
                                        bca = "IBCATimeTable";
                                    }
                                }else if(StudentType.equalsIgnoreCase("2")){
                                    if(StudentDepartment.equalsIgnoreCase("BCA")){
                                        bca = "IIBCATimeTable";
                                    }
                                }else if(StudentType.equalsIgnoreCase("3")){
                                    if(StudentDepartment.equalsIgnoreCase("BCA")){
                                        bca = "IIIBCATimeTable";
                                    }
                                }
                            }catch(Exception e){
                                Toast.makeText(getContext(), "Please fill student information", Toast.LENGTH_SHORT).show();
                            }

                            DocumentReference df = fStore.collection("studentAboutInfo").document(""+AdmissionID);
                            String finalBca = bca;
                            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String dept = documentSnapshot.getString("Department");
                                    String year = documentSnapshot.getString("Year");

                                    try{
                                        DocumentReference timeTableRetrieval = fStore.collection(""+ finalBca).document(""+ finalBca);
                                        timeTableRetrieval.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                IMon.setText(documentSnapshot.getString("IMon"));
                                                IIMon.setText(documentSnapshot.getString("IIMon"));
                                                IIIMon.setText(documentSnapshot.getString("IIIMon"));
                                                IVMon.setText(documentSnapshot.getString("IVMon"));
                                                VMon.setText(documentSnapshot.getString("VMon"));

                                                ITue.setText(documentSnapshot.getString("ITue"));
                                                IITue.setText(documentSnapshot.getString("IITue"));
                                                IIITue.setText(documentSnapshot.getString("IIITue"));
                                                IVTue.setText(documentSnapshot.getString("IVTue"));
                                                VTue.setText(documentSnapshot.getString("VTue"));

                                                IWed.setText(documentSnapshot.getString("IWed"));
                                                IIWed.setText(documentSnapshot.getString("IIWed"));
                                                IIIWed.setText(documentSnapshot.getString("IIIWed"));
                                                IVWed.setText(documentSnapshot.getString("IVWed"));
                                                VWed.setText(documentSnapshot.getString("VWed"));

                                                IThu.setText(documentSnapshot.getString("IThu"));
                                                IIThu.setText(documentSnapshot.getString("IIThu"));
                                                IIIThu.setText(documentSnapshot.getString("IIIThu"));
                                                IVThu.setText(documentSnapshot.getString("IVThu"));
                                                VThu.setText(documentSnapshot.getString("VThu"));

                                                IFri.setText(documentSnapshot.getString("IFri"));
                                                IIFri.setText(documentSnapshot.getString("IIFri"));
                                                IIIFri.setText(documentSnapshot.getString("IIIFri"));
                                                IVFri.setText(documentSnapshot.getString("IVFri"));
                                                VFri.setText(documentSnapshot.getString("VFri"));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Not able to connect with database", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }catch (Exception e){
                                        Toast.makeText(getContext(), "Please fill your about information to continue", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to connect to Database", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getContext(), "Please fill your about information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        studentClassChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                Intent intent = new Intent(getContext(), student_class_notification_activity.class);
                                intent.putExtra("StudentType", StudentYear);
                                intent.putExtra("Department", Department);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });

        studentDeptChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                Intent intent = new Intent(getContext(), student_dept_notification_activity.class);
                                intent.putExtra("Department", Department);
                                intent.putExtra("StudentType", studentType);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });

        studentAttendanceInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                FragmentTransaction frag  = getActivity().getSupportFragmentManager().beginTransaction();
                                insert_student_attendance_fragment fragClass = new insert_student_attendance_fragment();
                                fragClass.setArguments(bundle);
                                frag.replace(R.id.student_container_fragment, fragClass);
                                frag.commit();
                            }
                        });
                    }
                });
            }
        });

        studentLeaveApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.student_container_fragment, new student_attendance());
                                        fragmentTransaction.commit();
                                    }
                                }catch (Exception e){
                                    Toast.makeText(getContext(), "Please fill your information", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        studentUploadAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                FragmentTransaction frag  = getActivity().getSupportFragmentManager().beginTransaction();
                                student_assignment_upload_fragment fragClass = new student_assignment_upload_fragment();
                                fragClass.setArguments(bundle);
                                frag.replace(R.id.student_container_fragment, fragClass);
                                frag.commit();
                            }
                        });
                    }
                });
            }
        });

        return view;
    }
}
