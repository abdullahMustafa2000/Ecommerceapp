package com.shopping.ecommerceapp.ui.signup;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public MutableLiveData<Pair<Task<Void>, DatabaseError>> createAccount(@NotNull UserModule userModule) {
        MutableLiveData<Pair<Task<Void>, DatabaseError>> taskMutableLiveData = new MutableLiveData<>();
        String username, email, password, phone;
        username = userModule.getUsername();
        email = userModule.getEmail();
        password = userModule.getPassword();
        phone = userModule.getPhone();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child(Statics.USERS_REF).child(phone).exists())) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(Statics.PHONE_REF, phone);
                    map.put(Statics.USER_NAME_REF, username);
                    map.put(Statics.EMAIL_REF, email);
                    map.put(Statics.PASSWORD_REF, password);
                    dbRef.child(Statics.USERS_REF).child(phone).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                taskMutableLiveData.setValue(new Pair<>(task, null));
                            }
                        }
                    });
                } else {
                    taskMutableLiveData.setValue(new Pair<>(null, null));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                taskMutableLiveData.setValue(new Pair<>(null, error));
            }
        });
        return taskMutableLiveData;
    }
}
