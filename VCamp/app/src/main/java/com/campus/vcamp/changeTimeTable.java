package com.campus.vcamp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class changeTimeTable extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    static String TeacherType;
    EditText IMon, IIMon, IIIMon, IVMon, VMon, ITue, IITue, IIITue, IVTue, VTue, IWed, IIWed, IIIWed, IVWed, VWed,
            IThu, IIThu, IIIThu, IVThu, VThu, IFri, IIFri, IIIFri, IVFri, VFri;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_time_table, container, false);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        submit = view.findViewById(R.id.setTimeTable);

        IMon = view.findViewById(R.id.IMonTeacher);
        IIMon = view.findViewById(R.id.IIMonTeacher);
        IIIMon = view.findViewById(R.id.IIIMonTeacher);
        IVMon = view.findViewById(R.id.IVMonTeacher);
        VMon = view.findViewById(R.id.VMonTeacher);

        ITue = view.findViewById(R.id.ITueTeacher);
        IITue = view.findViewById(R.id.IITueTeacher);
        IIITue = view.findViewById(R.id.IIITueTeacher);
        IVTue = view.findViewById(R.id.IVTueTeacher);
        VTue = view.findViewById(R.id.VTueTeacher);

        IWed = view.findViewById(R.id.IWedTeacher);
        IIWed = view.findViewById(R.id.IIWedTeacher);
        IIIWed = view.findViewById(R.id.IIIWedTeacher);
        IVWed = view.findViewById(R.id.IVWedTeacher);
        VWed = view.findViewById(R.id.VWedTeacher);

        IThu = view.findViewById(R.id.IThuTeacher);
        IIThu = view.findViewById(R.id.IIThuTeacher);
        IIIThu = view.findViewById(R.id.IIThuTeacher);
        IVThu = view.findViewById(R.id.IVThuTeacher);
        VThu = view.findViewById(R.id.VThuTeacher);

        IFri = view.findViewById(R.id.IFriTeacher);
        IIFri = view.findViewById(R.id.IIFriTeacher);
        IIIFri = view.findViewById(R.id.IIIFriTeacher);
        IVFri = view.findViewById(R.id.IVFriTeacher);
        VFri = view.findViewById(R.id.VFriTeacher);

//Setting default Null Values
        /*IMon.setText("");
        IIMon.setText("");
        IIIMon.setText("");
        IVMon.setText("");
        VMon.setText("");

        ITue.setText("");
        IITue.setText("");
        IIITue.setText("");
        IVTue.setText("");
        VTue.setText("");

        IWed.setText("");
        IIWed.setText("");
        IIIWed.setText("");
        IVWed.setText("");
        VWed.setText("");

        IThu.setText("");
        IIThu.setText("");
        IIIThu.setText("");
        IVThu.setText("");
        VThu.setText("");

        IFri.setText("");
        IIFri.setText("");
        IIIFri.setText("");
        IVFri.setText("");
        VFri.setText("");*/


//retrieveing teacher type

        Source source = Source.CACHE;
        DocumentReference teacherType = fStore.collection("Users").document(fAuth.getUid());
        teacherType.get(source).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                TeacherType = documentSnapshot.getString("TypeOfTeacher");
                String year, bca = null;
                year = TeacherType.substring(0,1);
                boolean checkClassTeacher = true;
                String[] classTeacher = new String[]{"1BCA", "2BCA","3BCA", "1BCOM", "2BCOM", "3BCOM"};
//CHECKING WHTHER TEACHER IS A CLASS TEACHER OR NOT
                for(int i = 0; i<classTeacher.length; i++){
                    if(TeacherType.equalsIgnoreCase(classTeacher[i])){
                        checkClassTeacher = false;
                        break;
                    }
                }

                if(TeacherType.equalsIgnoreCase("1BCA") || TeacherType.equalsIgnoreCase("2BCA") || TeacherType.equalsIgnoreCase("3BCA")){
                    if(year.equals("1")){
                        bca = "IBCATimeTable";
                    }else if(year.equals("2")){
                        bca = "IIBCATimeTable";
                    }else if(year.equals("3")){
                        bca = "IIIBCATimeTable";
                    }
                }
                Toast.makeText(getContext(), ""+bca, Toast.LENGTH_SHORT).show();

                if(checkClassTeacher==false) {
                    DocumentReference timeTableRetrieval = fStore.collection(""+bca).document(""+bca);
                    timeTableRetrieval.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //    if(TeacherType.equalsIgnoreCase("IBCA")) {
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
                            //    }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Not able to connect with database", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Not able to connect with database", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mon1 = IMon.getText().toString().toUpperCase();
                String Mon2 = IIMon.getText().toString().toUpperCase();
                String Mon3 = IIIMon.getText().toString().toUpperCase();
                String Mon4 = IVMon.getText().toString().toUpperCase();
                String Mon5 = VMon.getText().toString().toUpperCase();

                String Tue1 = ITue.getText().toString().toUpperCase();
                String Tue2 = IITue.getText().toString().toUpperCase();
                String Tue3 = IIITue.getText().toString().toUpperCase();
                String Tue4 = IVTue.getText().toString().toUpperCase();
                String Tue5 = VTue.getText().toString().toUpperCase();

                String Wed1 = IWed.getText().toString().toUpperCase();
                String Wed2 = IIWed.getText().toString().toUpperCase();
                String Wed3 = IIIWed.getText().toString().toUpperCase();
                String Wed4 = IVWed.getText().toString().toUpperCase();
                String Wed5 = VWed.getText().toString().toUpperCase();

                String Thu1 = IThu.getText().toString().toUpperCase();
                String Thu2 = IIThu.getText().toString().toUpperCase();
                String Thu3 = IIIThu.getText().toString().toUpperCase();
                String Thu4 = IVThu.getText().toString().toUpperCase();
                String Thu5 = VThu.getText().toString().toUpperCase();

                String Fri1 = IFri.getText().toString().toUpperCase();
                String Fri2 = IIFri.getText().toString().toUpperCase();
                String Fri3 = IIIFri.getText().toString().toUpperCase();
                String Fri4 = IVFri.getText().toString().toUpperCase();
                String Fri5 = VFri.getText().toString().toUpperCase();

                if(TeacherType.equalsIgnoreCase("1BCA")) {
                    DocumentReference IBCATTable = fStore.collection("IBCATimeTable").document("IBCATimeTable");
                    Map<String, Object> IBCASubmit = new HashMap<>();
                    IBCASubmit.put("IMon", Mon1);
                    IBCASubmit.put("IIMon", Mon2);
                    IBCASubmit.put("IIIMon", Mon3);
                    IBCASubmit.put("IVMon", Mon4);
                    IBCASubmit.put("VMon", Mon5);

                    IBCASubmit.put("ITue", Tue1);
                    IBCASubmit.put("IITue", Tue2);
                    IBCASubmit.put("IIITue", Tue3);
                    IBCASubmit.put("IVTue", Tue4);
                    IBCASubmit.put("VTue", Tue5);

                    IBCASubmit.put("IWed", Wed1);
                    IBCASubmit.put("IIWed", Wed2);
                    IBCASubmit.put("IIIWed", Wed3);
                    IBCASubmit.put("IVWed", Wed4);
                    IBCASubmit.put("VWed", Wed5);

                    IBCASubmit.put("IThu", Thu1);
                    IBCASubmit.put("IIThu", Thu2);
                    IBCASubmit.put("IIIThu", Thu3);
                    IBCASubmit.put("IVThu", Thu4);
                    IBCASubmit.put("VThu", Thu5);

                    IBCASubmit.put("IFri", Fri1);
                    IBCASubmit.put("IIFri", Fri2);
                    IBCASubmit.put("IIIFri", Fri3);
                    IBCASubmit.put("IVFri", Fri4);
                    IBCASubmit.put("VFri", Fri5);

                    IBCATTable.set(IBCASubmit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Time Table for "+TeacherType+" updated succesfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(TeacherType.equalsIgnoreCase("2BCA")){
                    DocumentReference IIBCATTable = fStore.collection("IIBCATimeTable").document("IIBCATimeTable");
                    Map<String, Object> IIBCASubmit = new HashMap<>();
                    IIBCASubmit.put("IMon", Mon1);
                    IIBCASubmit.put("IIMon", Mon2);
                    IIBCASubmit.put("IIIMon", Mon3);
                    IIBCASubmit.put("IVMon", Mon4);
                    IIBCASubmit.put("VMon", Mon5);

                    IIBCASubmit.put("ITue", Tue1);
                    IIBCASubmit.put("IITue", Tue2);
                    IIBCASubmit.put("IIITue", Tue3);
                    IIBCASubmit.put("IVTue", Tue4);
                    IIBCASubmit.put("VTue", Tue5);

                    IIBCASubmit.put("IWed", Wed1);
                    IIBCASubmit.put("IIWed", Wed2);
                    IIBCASubmit.put("IIIWed", Wed3);
                    IIBCASubmit.put("IVWed", Wed4);
                    IIBCASubmit.put("VWed", Wed5);

                    IIBCASubmit.put("IThu", Thu1);
                    IIBCASubmit.put("IIThu", Thu2);
                    IIBCASubmit.put("IIIThu", Thu3);
                    IIBCASubmit.put("IVThu", Thu4);
                    IIBCASubmit.put("VThu", Thu5);

                    IIBCASubmit.put("IFri", Fri1);
                    IIBCASubmit.put("IIFri", Fri2);
                    IIBCASubmit.put("IIIFri", Fri3);
                    IIBCASubmit.put("IVFri", Fri4);
                    IIBCASubmit.put("VFri", Fri5);

                    IIBCATTable.set(IIBCASubmit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Time Table for "+TeacherType+" updated succesfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(TeacherType.equalsIgnoreCase("3BCA")){
                    DocumentReference IIIBCATTable = fStore.collection("IIIBCATimeTable").document("IIIBCATimeTable");
                    Map<String, Object> IIIBCASubmit = new HashMap<>();
                    IIIBCASubmit.put("IMon", Mon1);
                    IIIBCASubmit.put("IIMon", Mon2);
                    IIIBCASubmit.put("IIIMon", Mon3);
                    IIIBCASubmit.put("IVMon", Mon4);
                    IIIBCASubmit.put("VMon", Mon5);

                    IIIBCASubmit.put("ITue", Tue1);
                    IIIBCASubmit.put("IITue", Tue2);
                    IIIBCASubmit.put("IIITue", Tue3);
                    IIIBCASubmit.put("IVTue", Tue4);
                    IIIBCASubmit.put("VTue", Tue5);

                    IIIBCASubmit.put("IWed", Wed1);
                    IIIBCASubmit.put("IIWed", Wed2);
                    IIIBCASubmit.put("IIIWed", Wed3);
                    IIIBCASubmit.put("IVWed", Wed4);
                    IIIBCASubmit.put("VWed", Wed5);

                    IIIBCASubmit.put("IThu", Thu1);
                    IIIBCASubmit.put("IIThu", Thu2);
                    IIIBCASubmit.put("IIIThu", Thu3);
                    IIIBCASubmit.put("IVThu", Thu4);
                    IIIBCASubmit.put("VThu", Thu5);

                    IIIBCASubmit.put("IFri", Fri1);
                    IIIBCASubmit.put("IIFri", Fri2);
                    IIIBCASubmit.put("IIIFri", Fri3);
                    IIIBCASubmit.put("IVFri", Fri4);
                    IIIBCASubmit.put("VFri", Fri5);

                    IIIBCATTable.set(IIIBCASubmit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Time Table for "+TeacherType+" updated succesfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(TeacherType.equalsIgnoreCase("1BCOM")){
                    DocumentReference IBCOMTTable = fStore.collection("IBCOMTimeTable").document("IBCOMTimeTable");
                    Map<String, Object> IBCOMSubmit = new HashMap<>();
                    IBCOMSubmit.put("IMon", Mon1);
                    IBCOMSubmit.put("IIMon", Mon2);
                    IBCOMSubmit.put("IIIMon", Mon3);
                    IBCOMSubmit.put("IVMon", Mon4);
                    IBCOMSubmit.put("VMon", Mon5);

                    IBCOMSubmit.put("ITue", Tue1);
                    IBCOMSubmit.put("IITue", Tue2);
                    IBCOMSubmit.put("IIITue", Tue3);
                    IBCOMSubmit.put("IVTue", Tue4);
                    IBCOMSubmit.put("VTue", Tue5);

                    IBCOMSubmit.put("IWed", Wed1);
                    IBCOMSubmit.put("IIWed", Wed2);
                    IBCOMSubmit.put("IIIWed", Wed3);
                    IBCOMSubmit.put("IVWed", Wed4);
                    IBCOMSubmit.put("VWed", Wed5);

                    IBCOMSubmit.put("IThu", Thu1);
                    IBCOMSubmit.put("IIThu", Thu2);
                    IBCOMSubmit.put("IIIThu", Thu3);
                    IBCOMSubmit.put("IVThu", Thu4);
                    IBCOMSubmit.put("VThu", Thu5);

                    IBCOMSubmit.put("IFri", Fri1);
                    IBCOMSubmit.put("IIFri", Fri2);
                    IBCOMSubmit.put("IIIFri", Fri3);
                    IBCOMSubmit.put("IVFri", Fri4);
                    IBCOMSubmit.put("VFri", Fri5);

                    IBCOMTTable.set(IBCOMSubmit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Time Table for "+TeacherType+" updated succesfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(TeacherType.equalsIgnoreCase("2BCOM")){
                    DocumentReference IIBCOMTTable = fStore.collection("IIBCOMTimeTable").document("IIBCOMTimeTable");
                    Map<String, Object> IIBCOMSubmit = new HashMap<>();
                    IIBCOMSubmit.put("IMon", Mon1);
                    IIBCOMSubmit.put("IIMon", Mon2);
                    IIBCOMSubmit.put("IIIMon", Mon3);
                    IIBCOMSubmit.put("IVMon", Mon4);
                    IIBCOMSubmit.put("VMon", Mon5);

                    IIBCOMSubmit.put("ITue", Tue1);
                    IIBCOMSubmit.put("IITue", Tue2);
                    IIBCOMSubmit.put("IIITue", Tue3);
                    IIBCOMSubmit.put("IVTue", Tue4);
                    IIBCOMSubmit.put("VTue", Tue5);

                    IIBCOMSubmit.put("IWed", Wed1);
                    IIBCOMSubmit.put("IIWed", Wed2);
                    IIBCOMSubmit.put("IIIWed", Wed3);
                    IIBCOMSubmit.put("IVWed", Wed4);
                    IIBCOMSubmit.put("VWed", Wed5);

                    IIBCOMSubmit.put("IThu", Thu1);
                    IIBCOMSubmit.put("IIThu", Thu2);
                    IIBCOMSubmit.put("IIIThu", Thu3);
                    IIBCOMSubmit.put("IVThu", Thu4);
                    IIBCOMSubmit.put("VThu", Thu5);

                    IIBCOMSubmit.put("IFri", Fri1);
                    IIBCOMSubmit.put("IIFri", Fri2);
                    IIBCOMSubmit.put("IIIFri", Fri3);
                    IIBCOMSubmit.put("IVFri", Fri4);
                    IIBCOMSubmit.put("VFri", Fri5);

                    IIBCOMTTable.set(IIBCOMSubmit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Time Table for "+TeacherType+" updated succesfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(TeacherType.equalsIgnoreCase("3BCOM")){
                    DocumentReference IIIBCOMTTable = fStore.collection("IIIBCOMTimeTable").document("IIIBCOMTimeTable");
                    Map<String, Object> IIIBCOMSubmit = new HashMap<>();
                    IIIBCOMSubmit.put("IMon", Mon1);
                    IIIBCOMSubmit.put("IIMon", Mon2);
                    IIIBCOMSubmit.put("IIIMon", Mon3);
                    IIIBCOMSubmit.put("IVMon", Mon4);
                    IIIBCOMSubmit.put("VMon", Mon5);

                    IIIBCOMSubmit.put("ITue", Tue1);
                    IIIBCOMSubmit.put("IITue", Tue2);
                    IIIBCOMSubmit.put("IIITue", Tue3);
                    IIIBCOMSubmit.put("IVTue", Tue4);
                    IIIBCOMSubmit.put("VTue", Tue5);

                    IIIBCOMSubmit.put("IWed", Wed1);
                    IIIBCOMSubmit.put("IIWed", Wed2);
                    IIIBCOMSubmit.put("IIIWed", Wed3);
                    IIIBCOMSubmit.put("IVWed", Wed4);
                    IIIBCOMSubmit.put("VWed", Wed5);

                    IIIBCOMSubmit.put("IThu", Thu1);
                    IIIBCOMSubmit.put("IIThu", Thu2);
                    IIIBCOMSubmit.put("IIIThu", Thu3);
                    IIIBCOMSubmit.put("IVThu", Thu4);
                    IIIBCOMSubmit.put("VThu", Thu5);

                    IIIBCOMSubmit.put("IFri", Fri1);
                    IIIBCOMSubmit.put("IIFri", Fri2);
                    IIIBCOMSubmit.put("IIIFri", Fri3);
                    IIIBCOMSubmit.put("IVFri", Fri4);
                    IIIBCOMSubmit.put("VFri", Fri5);

                    IIIBCOMTTable.set(IIIBCOMSubmit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Time Table for "+TeacherType+" updated succesfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}