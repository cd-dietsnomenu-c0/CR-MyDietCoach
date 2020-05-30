package com.wsoteam.mydietcoach.common

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.wsoteam.mydietcoach.POJOS.Global
import com.wsoteam.mydietcoach.POJOS.interactive.*
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

        fun clearList(global: Global) {
            for (i in global.allDiets.dietList.indices) {
                for (j in global.allDiets.dietList[i].days.indices) {
                    var k = 0
                    while (k < global.allDiets.dietList[i].days[j].eats.size) {
                        if (global.allDiets.dietList[i].days[j].eats[k].text == "") {
                            global.allDiets.dietList[i].days[j].eats.drop(k)
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

        fun falseLoadEmpty() {
            val review = Review("",
                    "",
                    "")


            val eat = Eat(0, "")
            val eat1 = Eat(1, "")
            val eat2 = Eat(2, "")
            val eat3 = Eat(3, "")
            val eat4 = Eat(4, "")

            var eatList = ArrayList<Eat>()
            eatList.add(eat)
            eatList.add(eat1)
            eatList.add(eat4)
            eatList.add(eat2)
            eatList.add(eat3)

            val day = DietDay("", "", eatList)
            var days = ArrayList<DietDay>()
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)
            days.add(day)

            var benList = ArrayList<String>()
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")
            benList.add("")

            var consList = ArrayList<String>()
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")
            consList.add("")

            var diet = Diet("", "", "", "", "", benList, "", consList, "", "", days, "", "", review)

            var listDiets = ArrayList<Diet>()
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)
            listDiets.add(diet)

            var allDiets = AllDiets("", listDiets)

            FirebaseDatabase.getInstance().getReference("empty").setValue(allDiets)
        }
    }
}