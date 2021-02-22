package com.shalinaa.newsapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.shalinaa.newsapp.R
import com.shalinaa.newsapp.model.Users
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    var refUsers : DatabaseReference? = null
    var firebaseUser : FirebaseUser? = null
    companion object{
        fun getLaunchService (from: Context) = Intent(from, ProfileActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        iv_back_profile.setOnClickListener(this)
        tv_log_out.setOnClickListener(this)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!.uid)
        refUsers!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (p0 in snapshot.children){
                    val name = snapshot.child("fullname").value.toString()
                    val email = snapshot.child("email").value.toString()
                    val photo = snapshot.child("photo").value.toString()
                    tv_name_profile.text = name
                    tv_email_profile.text = email
                    Glide.with(this@ProfileActivity).load(photo).into(iv_profile)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.tv_log_out -> logOut()
        }
    }

    private fun logOut() {
        startActivity(Intent(
            SignInActivity.getLaunchService(
                this
            )
        ))
        FirebaseAuth.getInstance().signOut()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(MainActivity.getLaunchService(this)))
    }
}