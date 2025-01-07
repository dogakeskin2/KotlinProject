package com.ctis487_fp.finalproject.customer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ctis487_fp.finalproject.databinding.ActivityCustomerLoginBinding
import com.ctis487_fp.finalproject.service.impl.AccountServiceImpl
import kotlinx.coroutines.launch

class CustomerLogin : AppCompatActivity() {
    lateinit var binding: ActivityCustomerLoginBinding
    private val accountSignIn = AccountServiceImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCustomerLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        accountSignIn.signIn(email, password)

                        Toast.makeText(this@CustomerLogin, "Sales Login Successful!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@CustomerLogin, CustomerMainPage::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(this@CustomerLogin, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, CustomerSignUp::class.java)
            startActivity(intent)
        }
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, CustomerSignUp::class.java)
            startActivity(intent)
        }
    }
}