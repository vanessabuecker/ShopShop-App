package com.vbuecker.shopshop.network

import com.vbuecker.shopshop.App
import com.vbuecker.shopshop.model.LoginRequest
import com.vbuecker.shopshop.model.RegisterRequest
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

    fun register(registerRequest: RegisterRequest, onUserCreated: (String?, Throwable?) -> Unit) {
        Thread {

            FakeDatabase.register(registerRequest) { res ->
                if (res != null) {
                    App.saveToken(res.token)
                    onUserCreated(res.token, null)
                } else {
                    onUserCreated(null, null)
                }
            }

        }.start()
    }

}