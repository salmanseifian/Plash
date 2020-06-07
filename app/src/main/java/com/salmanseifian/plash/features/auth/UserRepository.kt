package com.salmanseifian.plash.features.auth

import com.salmanseifian.plash.room.dao.UserDao
import com.salmanseifian.plash.room.models.DbUser

class UserRepository(private val userDao: UserDao) {

    suspend fun login(email: String, password: String) {
        val dbUser = DbUser(email, password, System.currentTimeMillis().toString())
        userDao.insert(dbUser)
    }
}