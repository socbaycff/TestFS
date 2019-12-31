package com.example.testfs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MyAdaper adaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_layout); // doi layout thanh activity_main de chay vi du 1, doi thanh recycle_layout de chay vd recycle

        /* LAM VIEC THOI GIAN THUC-----------------------------------------

        // CACH 1: doc 1 lan duy nhat

        final FirebaseFirestore root = FirebaseFirestore.getInstance();

        final EditText nameET = findViewById(R.id.ten);
        final EditText ageET = findViewById(R.id.tuoi);
        Button saveBT = findViewById(R.id.save);

       root.document("data/info").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               nameET.setText(documentSnapshot.getString("name"));
               ageET.setText(String.valueOf(documentSnapshot.getLong("age")));
           }
       });


        // CACH 2: doc theo thoi gian thuc realtime

        final DocumentReference document = root.document("data/info");
        document.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                nameET.setText(documentSnapshot.getString("name"));
                ageET.setText(String.valueOf(documentSnapshot.getLong("age")));

            }
        });



       saveBT.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String name = nameET.getText().toString();
               int age = Integer.parseInt(ageET.getText().toString());
               Map<String, Object> data = new HashMap<>();
               data.put("name", name);
               data.put("age",age);

               document.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Toast.makeText(getApplicationContext(), "UPdate thanh cong", Toast.LENGTH_SHORT).show();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getApplicationContext(), "UPdate thant bai", Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });
        */


        /* LAM VIEC VOI RECYCLE VIEW --------------------------------------------------------
        FirebaseFirestore root = FirebaseFirestore.getInstance();
        Query query = root.collection("data").limit(50);
        RecyclerView myRe = findViewById(R.id.recycler);
        myRe.setLayoutManager(new LinearLayoutManager(this));

        FirestoreRecyclerOptions<Info> options = new FirestoreRecyclerOptions.Builder<Info>().setQuery(query, Info.class).build();

        adaper = new MyAdaper(options);
        myRe.setAdapter(adaper);
        */
    }

    class MyAdaper extends FirestoreRecyclerAdapter<Info, MyAdaper.MyViewHolder> { // adapter dac biet cung cap tu thu vien ngoai


        public MyAdaper(@NonNull FirestoreRecyclerOptions<Info> options) { // buoc co
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Info model) { //
            holder.age.setText(String.valueOf(model.getAge()));
            holder.name.setText(model.getName());
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
            return new MyViewHolder(view);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder { // viewholder nhu recycle bth
            TextView name;
            TextView age;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.nameItem);
                age = itemView.findViewById(R.id.ageItem);

            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adaper.startListening(); // bat dau lang nghe
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaper.stopListening();
    }
}
