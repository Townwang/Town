package com.townwang.town.daemon

import android.annotation.TargetApi
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.Build

/**
 * Description:   方法4：JobScheduler
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)   //API 21  5.0
class MyJobService : JobService() {
    override fun onCreate() {
        super.onCreate()
        startJobSheduler()
    }

    private fun startJobSheduler() {
        try {
            val builder = JobInfo.Builder(1, ComponentName(packageName, MyJobService::class.java.name))
            builder.setPeriodic(5)
            builder.setPersisted(true)
            val jobScheduler = this.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(builder.build())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }
}