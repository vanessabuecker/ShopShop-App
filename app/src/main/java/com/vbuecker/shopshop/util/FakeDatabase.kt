package com.vbuecker.shopshop.util

import android.graphics.Color
import com.vbuecker.shopshop.model.*
import java.util.*

data class User(
    val name: String,
    val email: String,
    val password: String,
    val token: String
)

class FakeDatabase {

    companion object {
        private var users: HashSet<User> = hashSetOf()

        //linkedHashSet ordena conforme os itens sao criados
        private var items: LinkedHashSet<ItemResponse> = linkedSetOf()

        init {
            users.add(User("123", "123@gmail.com", "123", "abcd"))
            users.add(User("nmm", "nmm@gmail.com", "12345", "abcd"))

            items.add(
                ItemResponse(
                    "Pão para hotdog",
                    "preciso de 15 pães", Date().time, Color.GREEN
                )
            )
            items.add(
                ItemResponse(
                    "Açucar",
                    "preciso de 5kg", Date().time, Color.RED
                )
            )
        }

        //simulação do database fake...
        fun login(loginRequest: LoginRequest, response: (LoginResponse?) -> Unit) {
            Thread.sleep(1000) // simula latencia da rede
            val user: User? = users.filter {
                it.email == loginRequest.email && it.password == loginRequest.password
            }.firstOrNull()
            if (user != null) {
                response(LoginResponse(user.token))
            } else {
                response(null)
            }
        }

        fun register(request: RegisterRequest, response: (CreateResponse?) -> Unit) {
            Thread.sleep(800)

            val user = User(
                request.name,
                request.email,
                request.password,
                UUID.randomUUID().toString()
            ) //UUID gera um token randomico, cria uma hash randomica e ja devolve a hash pra quem chama-la no remoteDataSource
            val added: Boolean = users.add(user)

            if (added) {
                response(CreateResponse(user.token))
            } else {
                response(null)
            }
        }
        fun getAll(token: String?, response: (GetAllResponse?)-> Unit){
            Thread.sleep(2000)
            val list = mutableListOf<ItemResponse>()
            items.toCollection(list)
            response(GetAllResponse(list))
        }
    }

}