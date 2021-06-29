package com.example.leanclouddemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import cn.leancloud.LCUser
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login_layout.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_layout)
        editTextSignUpUserName.addTextChangedListener(watcher)
        editTextSignUpPassword.addTextChangedListener(watcher)
        buttonSignUpConfirm.isEnabled = false
        progressBarSignUpLogin.visibility = View.INVISIBLE
        buttonToSignUp.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SignUpActivity::class.java
                )
            )
        }
        buttonSignUpConfirm.setOnClickListener {
            val name = editTextSignUpUserName.text?.trim().toString()
            val pwd = editTextSignUpPassword.text?.trim().toString()
            LCUser.logIn(name,pwd).safeSubscribe(object :Observer<LCUser>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: LCUser) {
                    startActivity(Intent(
                        this@LoginActivity,MainActivity::class.java
                    ))
                    finish()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@LoginActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                    progressBarSignUpLogin.visibility = View.INVISIBLE
                }

                override fun onComplete() {

                }
            })

        }

    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val t1 = editTextSignUpUserName.text.toString().isNotEmpty()
            val t2 = editTextSignUpPassword.text.toString().isNotEmpty()

            buttonSignUpConfirm.isEnabled = t1 and t2
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }
}