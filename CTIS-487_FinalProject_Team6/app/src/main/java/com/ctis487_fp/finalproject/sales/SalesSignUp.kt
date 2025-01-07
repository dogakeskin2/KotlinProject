package com.ctis487_fp.finalproject.sales

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ctis487_fp.finalproject.databinding.ActivitySalesSignUpBinding
import com.ctis487_fp.finalproject.model.Sales
import com.ctis487_fp.finalproject.service.impl.AccountServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class SalesSignUp : AppCompatActivity() {
    lateinit var binding: ActivitySalesSignUpBinding
    var accountSignUp = AccountServiceImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySalesSignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        var database = FirebaseDatabase.getInstance().getReference("sales")

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, SalesLogin::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            val name = binding.etSalesName.text.toString()
            val email = binding.etSalesEmail.text.toString()
            val password = binding.etSalesPassword.text.toString()
            val phone = binding.etSalesPhone.text.toString()
            val region = binding.etSalesRegion.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty() && region.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        accountSignUp.signUp(email, password)

                        val uid = FirebaseAuth.getInstance().currentUser?.uid
                        if (uid != null) {

                            val salesData = Sales(name, email, password, phone, region)
                            database.child(uid).setValue(salesData)
                                .addOnSuccessListener {

                                    Toast.makeText(this@SalesSignUp, "Successfully signed up as Sales!", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this@SalesSignUp, SalesLogin::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->

                                    Toast.makeText(this@SalesSignUp, "Error saving data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this@SalesSignUp, "Failed to retrieve UID.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {

                        Toast.makeText(this@SalesSignUp, "Error signing up: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}