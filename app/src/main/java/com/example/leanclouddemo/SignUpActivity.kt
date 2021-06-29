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
import kotlinx.android.synthetic.main.activity_sign_up_layout.*


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_layout)
        editTextSignUpUserName.addTextChangedListener(watcher)
        editTextSignUpPassword.addTextChangedListener(watcher)
        progressBarSignUpLogin.visibility = View.INVISIBLE
        buttonSignUpConfirm.setOnClickListener {
            val name = editTextSignUpUserName.text?.trim().toString()
            val pwd = editTextSignUpPassword.text?.trim().toString()
            LCUser().apply {
                progressBarSignUpLogin.visibility = View.VISIBLE
                username = name
                password = pwd
                signUpInBackground().safeSubscribe(object : Observer<LCUser> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: LCUser) {
                        Toast.makeText(this@SignUpActivity, "注册成功", Toast.LENGTH_SHORT).show()
                        LCUser.logIn(name, pwd).subscribe(object : Observer<LCUser> {
                            override fun onSubscribe(d: Disposable) {

                            }

                            override fun onNext(t: LCUser) {
                                startActivity(Intent(this@SignUpActivity, MainActivity::class.java).also {
                                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                })
                            }

                            override fun onError(e: Throwable) {
                                progressBarSignUpLogin.visibility = View.INVISIBLE

                                Toast.makeText(
                                    this@SignUpActivity,
                                    "${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun onComplete() {

                            }
                        })
                    }

                    override fun onError(e: Throwable) {
                        progressBarSignUpLogin.visibility = View.INVISIBLE

                        Toast.makeText(this@SignUpActivity, "${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onComplete() {

                    }
                })
            }
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