package com.vbuecker.shopshop.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.vbuecker.shopshop.R
import com.vbuecker.shopshop.model.LoginRequest
import com.vbuecker.shopshop.network.RemoteDataSource
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSource() }

    companion object {
        fun laught(context: Context) {
            val intent = Intent(context, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        register_now_tv.setOnClickListener {
            SignUpActivity.laught(this)
        }

        login_button_send.setOnClickListener {
            if (validateForm()) {
                login_button_send.showProgress {
                    progressColor = Color.WHITE
                }
                doLogin()
            }
        }
    }

    private fun doLogin() {
        val email = login_edit_email.text.toString()
        val password = login_edit_password.text.toString()

        remoteDataSource.login(LoginRequest(email, password)) { token, throwable ->
            runOnUiThread {
                login_button_send.hideProgress(R.string.login)
                // SERÁ EXECUTADO APENAS DEPOIS (ASYNC)
                if (token != null) {
                    // abrir a tela principal
                    MainActivity.laught(this)
                } else {
                    Toast.makeText(this, R.string.email_and_password_incorrect, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val email = login_edit_email.text.toString()
        val password = login_edit_password.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.email_and_password_incorrect, Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }

}