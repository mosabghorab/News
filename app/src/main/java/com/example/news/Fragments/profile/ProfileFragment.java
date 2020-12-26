package com.example.news.Fragments.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.news.R;
import com.example.news.Models.Shared;
import com.example.news.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    FirebaseAuth firebaseAuth;

    private ProfileViewModel profileViewModel;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReferenceUsers;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReferenceUsers = firebaseFirestore.collection("Users");

        final Button btnSave = root.findViewById(R.id.btn_save);
        final EditText editTextEmail = root.findViewById(R.id.email);
        final EditText editTextPassword = root.findViewById(R.id.password);
        final EditText editTextName = root.findViewById(R.id.editTextName);
        editTextName.setText(Shared.currentUser.getName());
        editTextEmail.setText(Shared.currentUser.getEmail());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getContext(), null, "Loading...");
                if(editTextPassword.getText() != null && !editTextPassword.getText().toString().equals("")) {
                    firebaseAuth.getCurrentUser().updatePassword(editTextPassword.getText().toString());
                }
                if(editTextEmail.getText() != null && !editTextEmail.getText().toString().equals("")) {
                    firebaseAuth.getCurrentUser().updateEmail(editTextEmail.getText().toString());
                }
               final User user = new User(firebaseAuth.getCurrentUser().getUid(),  editTextName.getText().toString(), editTextEmail.getText().toString());
                collectionReferenceUsers.document(firebaseAuth.getCurrentUser().getUid()).set(user).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Shared.currentUser = user;
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return root;
    }
}
