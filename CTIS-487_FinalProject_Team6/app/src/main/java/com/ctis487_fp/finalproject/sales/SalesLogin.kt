package com.ctis487_fp.finalproject.sales

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ctis487_fp.finalproject.databinding.ActivitySalesLoginBinding
import com.ctis487_fp.finalproject.service.impl.AccountServiceImpl
import kotlinx.coroutines.launch

class SalesLogin : AppCompatActivity() {
    lateinit var binding: ActivitySalesLoginBinding
    private val accountSignIn = AccountServiceImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySalesLoginBinding.inflate(layoutInflater)

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

                        Toast.makeText(this@SalesLogin, "Sales Login Successful!", Toast.LENGTH_SHORT).show()


                        val intent = Intent(this@SalesLogin, SalesMainPage::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {

                        Toast.makeText(this@SalesLogin, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {

                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SalesSignUp::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SalesSignUp::class.java)
            startActivity(intent)
        }

    }
}
