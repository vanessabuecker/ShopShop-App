package com.vbuecker.shopshop.network

import com.vbuecker.shopshop.App
import com.vbuecker.shopshop.model.LoginRequest
import com.vbuecker.shopshop.util.FakeDatabase

class RemoteDataSource {
    fun login(loginRequest: LoginRequest, onUserLoggedIn: (String?, Throwable?) -> Unit) {
        Thread {
            FakeDatabase.login(loginRequest) { res ->
                if (res != null) {
                    App.saveToken(res.token)
                    onUserLoggedIn(res.token, null)
                } else {
                    onUserLoggedIn(null, null)
                }
            }

        }.start()
    }

}