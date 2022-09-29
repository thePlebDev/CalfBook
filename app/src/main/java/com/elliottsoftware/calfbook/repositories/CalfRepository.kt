package com.elliottsoftware.calfbook.repositories

import androidx.annotation.WorkerThread
import com.elliottsoftware.calfbook.domain.daos.CalfDao
import com.elliottsoftware.calfbook.domain.models.Calf
import kotlinx.coroutines.flow.Flow

class CalfRepository(private val calfDao: CalfDao) {

    //Room executes all the queries on a separate thread
    // Observed Flow will notify the observer when the data has changed
    val allCalves: Flow<MutableList<Calf>> = calfDao.getAllCalves();


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(calf: Calf) {
        //suspend tells the compiler the this needs to be called from a coroutine or another suspending function
        calfDao.insert(calf)
    }

    @WorkerThread
    suspend fun findCalf(calfId:Long):Calf{
        return calfDao.findCalf(calfId)
    }
    @WorkerThread
    suspend fun updateCalf(calf:Calf){
        calfDao.updateCalf(calf)
    }
    @WorkerThread
    suspend fun deleteCalf(calf:Calf){
        calfDao.deleteCalf(calf)
    }

    fun searchDatabase(searchQuery:String): Flow<List<Calf>> {
        return calfDao.searchDatabase(searchQuery)
    }
}