package com.example.bg_pay.presenter.fragment.home

import androidx.lifecycle.ViewModel
import com.example.bg_pay.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow


class HomeViewModel : ViewModel() {

    private val db: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _newMoney = MutableStateFlow<UserModel>(UserModel())

    fun getMoney() {
        db.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java) ?: return
                _newMoney.value = user

            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun updateMoney(newMoney: Float) {
        val balance = newMoney + _newMoney.value.cards.balance!!
        db.child(auth.currentUser?.uid!!).child("cards").child("balance").setValue(balance)
    }
}