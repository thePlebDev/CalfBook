package com.elliottsoftware.calfbook.util

import android.app.Application
import com.elliottsoftware.calfbook.db.CalfRoomDatabase
import com.elliottsoftware.calfbook.repositories.CalfRepository

class CalfApplication : Application() {

    //using lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    val database by lazy { CalfRoomDatabase.getDatabase(this)} //created a database instance
    val repository by lazy { CalfRepository(database.calfDao()) }// created a repository instance and passed it the DAO
}