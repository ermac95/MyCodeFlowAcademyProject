package com.mycodeflow.work

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class CacheUpdateWorkManager(private val context: Context) {

    fun startBackgroundWork(){
        val workConstraints = setConstraints() //setting work start constrains
        val cacheUpdateRequest = getPeriodicWorkRequest(workConstraints) //creating periodic work
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(CacheUpdateWork.CACHE_UPDATE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, cacheUpdateRequest) //starting unique periodic work
    }

    private fun setConstraints(): Constraints {
        return Constraints.Builder()
            .build()
    }

    private fun getPeriodicWorkRequest(workConstraints: Constraints): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<CacheUpdateWork>(
            8,
            TimeUnit.HOURS)
            .setConstraints(workConstraints)
            .build()
    }

    /*
    private suspend fun checkOneTimeState(){

        val workInfos = WorkManager.getInstance(applicationContext).getWorkInfosByTag("my_unique_work").await()
        if (workInfos.size == 1){
            val workInfo = workInfos[0]
            when(workInfo.state){
                WorkInfo.State.ENQUEUED -> Log.d("myLogs", "Work is enqueued")
                WorkInfo.State.RUNNING -> Log.d("myLogs", "Work is Running")
                WorkInfo.State.BLOCKED -> Log.d("myLogs", "Work is blocked")
                WorkInfo.State.SUCCEEDED -> Log.d("myLogs", "Work is succeeded")
                WorkInfo.State.FAILED -> Log.d("myLogs", "Work is Failed")
                WorkInfo.State.CANCELLED -> Log.d("myLogs", "Work is cancelled")
            }
        }
    }
    */

    /*
    private fun WorkManager.isAnyWorkScheduled(tag: String): Boolean {
        return try {
            getWorkInfosByTag(tag).get().firstOrNull { !it.state.isFinished } != null
        } catch (e: Exception) {
            when (e) {
                is ExecutionException, is InterruptedException -> {
                    e.printStackTrace()
                }
                else -> throw e
            }
            false
        }
    }
    */

    /*
    private suspend fun checkWorkState(workName: String){
        val workInfos = WorkManager.getInstance(applicationContext).getWorkInfosForUniqueWork(workName).await()
        if (workInfos.size == 1){
            val workInfo = workInfos[0]
            when(workInfo.state){
                WorkInfo.State.ENQUEUED -> Log.d("myLogs", "Work is enqueued")
                WorkInfo.State.RUNNING -> Log.d("myLogs", "Work is Running")
                WorkInfo.State.BLOCKED -> Log.d("myLogs", "Work is blocked")
                WorkInfo.State.SUCCEEDED -> Log.d("myLogs", "Work is succeeded")
                WorkInfo.State.FAILED -> Log.d("myLogs", "Work is Failed")
                WorkInfo.State.CANCELLED -> Log.d("myLogs", "Work is cancelled")
            }
        }
    }
    */
}