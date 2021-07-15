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
import com.vbuecker.shopshop.model.RegisterRequest
import com.vbuecker.shopshop.network.RemoteDataSource
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSource() }

    companion object {
        fun laught(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        register_button_send.setOnClickListener {
            if (validateForm()) {
                register_button_send.showProgress {
                    progressColor = Color.WHITE
                }
                createAccount()
            }
        }

        register_now_txtClick.setOnClickListener{
            finish()
        }
    }

    private fun createAccount() {
        val email = register_editText_email.text.toString()
        val password = register_editText_password.text.toString()
        val name = register_editText_name.text.toString()

        remoteDataSource.register(RegisterRequest(email, password, name)) { token, throwable ->
            runOnUiThread {
                register_button_send.hideProgress(R.string.register_now)
                if (token != null) {
                    MainActivity.laught(this)
                } else {
                    Toast.makeText(this, R.string.email_and_password_incorrect, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        val name = register_editText_name.text.toString()
        val email = register_editText_email.text.toString()
        val password = register_editText_password.text.toString()
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.email_and_password_incorrect, Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }

}
