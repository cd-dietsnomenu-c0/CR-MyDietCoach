package com.wsoteam.mydietcoach.common

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.wsoteam.mydietcoach.POJOS.Global
import com.wsoteam.mydietcoach.POJOS.interactive.AllDiets
import com.wsoteam.mydietcoach.POJOS.interactive.Diet
import java.util.*

class FBWork {
    companion object {
        private fun addNewDiets(global: Global) {
            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.getReference("empty")
            val fd = FirebaseDatabase.getInstance()
            val dr = fd.getReference("newDiets12")
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.getValue(AllDiets::class.java)!!.dietList.indices) {
                        //global.allDiets.dietList.add(dataSnapshot.getValue(AllDiets::class.java)!!.dietList[i])
                    }
                    dr.setValue(global)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }


        fun getFCMToken() {
            FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w("LOL", "getInstanceId failed", task.exception)
                            return@OnCompleteListener
                        }
                        Log.e("LOL", "FCM token -- ${task.result?.token}")
                    })
        }

        private fun swapList(global: Global) {
            var list: List<Diet> = global.allDiets.dietList
            Collections.reverse(list)
            //global.allDiets.dietList = list
            val fd = FirebaseDatabase.getInstance()
            val dr = fd.getReference("swapPlace")
            dr.setValue(global)
        }

        private fun clearList(global: Global) {
            for (i in global.allDiets.dietList.indices) {
                for (j in global.allDiets.dietList[i].days.indices) {
                    var k = 0
                    while (k < global.allDiets.dietList[i].days[j].eats.size) {
                        if (global.allDiets.dietList[i].days[j].eats[k].text == "") {
                            //global.allDiets.dietList[i].days[j].eats.removeAt(k)
                            k--
                        }
                        k++
                    }
                }
            }
            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.getReference("clearAdb")
            databaseReference.setValue(global)
        }
    }
}