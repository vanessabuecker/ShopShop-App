package com.vbuecker.shopshop.util

import com.vbuecker.shopshop.model.LoginRequest
import com.vbuecker.shopshop.model.LoginResponse

data class User(
    val name: String,
    val email: String,
    val password: String,
    val token: String
)

class FakeDatabase {

    companion object {
        private var users: HashSet<User> = hashSetOf()

        init {
            users.add(User("123", "123@gmail.com", "123", "abcd"))
            users.add(User("nmm", "nmm@gmail.com", "12345", "abcd"))
        }

        //requisição
        fun login(loginRequest: LoginRequest, response: (LoginResponse?) -> Unit) {
            Thread.sleep(1000)
            val user: User? = users.filter {
                it.email == loginRequest.email && it.password == loginRequest.password
            }.firstOrNull()
            if (user != null) {
                response(LoginResponse(user.token))
            } else {
                response(null)
            }
        }
    }

}