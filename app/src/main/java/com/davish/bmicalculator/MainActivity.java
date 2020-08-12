package com.davish.bmicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText heightWidget,weightWidget;
    int height, weight;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences("Evolve-X", Context.MODE_PRIVATE);

        heightWidget = findViewById(R.id.height_input);
        weightWidget = findViewById(R.id.weight_input);
        result = findViewById(R.id.result);

        height= Integer.parseInt(heightWidget.getText().toString());
        weight= Integer.parseInt(weightWidget.getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", "Parithi");
        user.put("age", 21);
        user.put("married", false);


        String name = sharedpreferences.getString("name", "akarsh");
        String section = sharedpreferences.getString("ieee-section", "kl");

        Log.d("Myxzptls", name+ " " + section);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("name", "evolve-X");
        editor.putString("ieee-section","hyd");
        editor.apply();

// Add a new document with a generated ID
        db.collection("user-details")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("firestore", "Error adding document", e);
                    }
                });

        db.collection("user-details")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("firestore", document.get("married").toString());
                            }
                        } else {
                            Log.w("firestore", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void newScreen(View view){
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra("eventName", "Evolve-X");
        startActivity(resultIntent);
    }



}