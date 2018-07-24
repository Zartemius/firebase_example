package com.example.artem.firstappwithnotification.data

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.artem.firstappwithnotification.iddatainfirebase.UserIdInFireBase
import com.example.artem.firstappwithnotification.model.AddressOfOrder
import com.example.artem.firstappwithnotification.model.Order
import com.example.artem.firstappwithnotification.model.Report
import com.example.artem.firstappwithnotification.model.PersonalData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot

class FireBaseDataBase {
    companion object {

        fun addPersonToDataBase(personalData: PersonalData){
            val uid = UserIdInFireBase.getUserUid()
            val fireStoreDataBase = getInstanceOfFireStoreDb()

            fireStoreDataBase.collection("users")
                    .document(uid)
                    .set(personalData)
                    .addOnSuccessListener { aVoid ->
                        Log.i("WORK", "Works ")
                    }
                    .addOnFailureListener { exception ->
                        Log.i("Error", "Error occurred during a personal data being submitted in database $exception")
                    }

            var order = Order()
            order.userId = uid
            var addressOfOrder = AddressOfOrder()
            order.addressOfOrder = addressOfOrder

            fireStoreDataBase.collection("orders")
                    .document().set(order)
        }

        fun submitReportInDataBase(report: Report){
            val fireStoreDataBase = getInstanceOfFireStoreDb()

            fireStoreDataBase.collection("reports").document()
                    .set(report)
                    .addOnSuccessListener { aVoid ->
                        Log.i("SUBMITTING_OF_REPORT", "Works")
                    }
                    .addOnFailureListener { exception ->
                        Log.i("Error", "Error occurred during a report being submitted in database $exception")
                    }
        }

        fun loadOrders(orders:MutableLiveData<List<Order>>){
            val uid = UserIdInFireBase.getUserUid()

            val listOfOrders:MutableList<Order> = ArrayList<Order>()

            if(!uid.isEmpty()){
                val fireStoreDataBase = getInstanceOfFireStoreDb()

                fireStoreDataBase.collection("orders")
                        .whereEqualTo("userId",uid)
                        .get()
                        .addOnCompleteListener { task: Task<QuerySnapshot> ->
                            if(task.isSuccessful){
                                for(doc:DocumentSnapshot in task.getResult()){
                                    val order = doc.toObject(Order::class.java)!!
                                    order.orderId = doc.id
                                    listOfOrders.add(order)
                                    Log.i("ERROR_" +
                                            "DB_GET_LOAD", "error " + order.userId)
                                }
                                orders.value = listOfOrders
                            } else{
                                Log.e("ERROR_DB_GET_LOAD", "error " + task.exception!!)
                            }
                        }
            }
        }

        fun loadReports(orderId:String,reports:MutableLiveData<List<Report>>){
            val fireStoreDataBase = getInstanceOfFireStoreDb()
            val listOfReports:MutableList<Report> = ArrayList<Report>()

            fireStoreDataBase.collection("reports")
                    .whereEqualTo("orderId",orderId)
                    .whereEqualTo("reportIsApproved",true)
                    .get()
                    .addOnCompleteListener { task: Task<QuerySnapshot> ->
                        for(doc:DocumentSnapshot in task.result){
                            val report = doc.toObject(Report::class.java)!!
                            listOfReports.add(report)
                        }
                        reports.value = listOfReports
                    }
        }

        //new function
        fun changeStatusOfOrderInFb(statusOfOrder:String, orderId:String){
            val fireBaseDataBase = getInstanceOfFireStoreDb()
            fireBaseDataBase.collection("orders")
                    .document(orderId)
                    .update("statusOfOrder", statusOfOrder)
        }

        private fun getInstanceOfFireStoreDb():FirebaseFirestore{
            val fireStoreDataBase = FirebaseFirestore.getInstance()
            val settings = FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build()

            fireStoreDataBase.firestoreSettings = settings
            return fireStoreDataBase
        }
    }
}