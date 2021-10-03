package com.shopping.ecommerceapp.ui.signup;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shopping.ecommerceapp.classes.Statics;
import com.shopping.ecommerceapp.modules.UserModule;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SignUpViewModel extends ViewModel {

    public MutableLiveData<Pair<Task<Void>, String>> createAccount(@NotNull UserModule userModule) {
        MutableLiveData<Pair<Task<Void>, String>> taskMutableLiveData = new MutableLiveData<>();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        String email, password;
        email = userModule.getEmail();
        password = userModule.getPassword();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            uploadDataToRealtime(userModule, taskMutableLiveData, firebaseAuth.getCurrentUser().getUid());
                        } else {
                            taskMutableLiveData.setValue(new Pair<>(null, "email is found"));
                        }
                }).addOnFailureListener(e -> taskMutableLiveData.setValue(new Pair<>(null, e.getMessage())));
        return taskMutableLiveData;
    }

    private void uploadDataToRealtime(UserModule userModule, MutableLiveData<Pair<Task<Void>, String>> taskMutableLiveData, String uid) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(Statics.USERS_REF);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child(uid).exists())) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(Statics.PHONE_REF, userModule.getPhone());
                    map.put(Statics.USER_NAME_REF, userModule.getUsername());
                    map.put(Statics.EMAIL_REF, userModule.getEmail());
                    map.put(Statics.PASSWORD_REF, userModule.getPassword());
                    map.put(Statics.UID_REF, userModule.getUid());
                    dbRef.child(uid).updateChildren(map).addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                            taskMutableLiveData.setValue(new Pair<>(task, null));
                    });
                } else {
                    taskMutableLiveData.setValue(new Pair<>(null, null));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                taskMutableLiveData.setValue(new Pair<>(null, error.getMessage()));
            }
        });
    }

}
