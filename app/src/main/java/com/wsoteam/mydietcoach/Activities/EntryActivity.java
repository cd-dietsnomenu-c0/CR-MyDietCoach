package com.wsoteam.mydietcoach.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wsoteam.mydietcoach.Fragment.FragmentListDiets;
import com.wsoteam.mydietcoach.ObjectHolder;
import com.wsoteam.mydietcoach.POJOS.POJOS.GlobalObject;
import com.wsoteam.mydietcoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);
        bindDataFB();
    }

    private void bindDataFB() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("GLOBAL");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ObjectHolder objectHolder = new ObjectHolder();
                objectHolder.bindObject(dataSnapshot.getValue(GlobalObject.class));
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentContainer, new FragmentListDiets()).commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
