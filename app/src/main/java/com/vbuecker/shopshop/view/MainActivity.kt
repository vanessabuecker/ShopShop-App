package com.vbuecker.shopshop.view

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vbuecker.shopshop.App
import com.vbuecker.shopshop.R
import com.vbuecker.shopshop.network.RemoteDataSource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MainAdapter
    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSource() }

    companion object {
        fun laught(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter()
        main_rv.adapter = adapter

        val token = App.getToken()
        if (token == null) {
            SignInActivity.laught(this)
        }
    }

    override fun onStart() {
        super.onStart()
        remoteDataSource.getAll { list, throwable ->
            runOnUiThread {
                list?.let {
                adapter.list.clear()
                    adapter.list.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}