package com.shopping.ecommerceapp.ui.login;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shopping.ecommerceapp.classes.Statics;
import com.shopping.ecommerceapp.db.RoomDatabaseManager;
import com.shopping.ecommerceapp.db.UserDao;
import com.shopping.ecommerceapp.modules.UserModule;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {

    UserDao dao;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        dao = RoomDatabaseManager.getInstance(application).getUserDao();
    }

    public MutableLiveData<Pair<UserModule, String>> userLogin(String email, String password) {
        MutableLiveData<Pair<UserModule, String>> data = new MutableLiveData<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getUserData(auth.getCurrentUser().getUid(), data);
                    } else {
                        data.setValue(new Pair<>(null, null));
                    }
                })
                .addOnFailureListener(e -> data.setValue(new Pair<>(null, e.getMessage())));


        return data;
    }

    private void getUserData(String uid, MutableLiveData<Pair<UserModule, String>> data) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(Statics.USERS_REF);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot dataSnapshot = snapshot.child(uid);
                if (dataSnapshot.exists()) {
                    UserModule userModule = dataSnapshot.getValue(UserModule.class);
                    data.setValue(new Pair<>(userModule, null));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                data.setValue(new Pair<>(null, error.getMessage()));
            }
        });
    }

    public void putUserInDb(UserModule userModule) {
        Single.create((SingleOnSubscribe<UserModule>) emitter -> emitter.onSuccess(userModule))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> dao.insert(o));
    }

}
