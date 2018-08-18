package com.padcmyanmar.kunyi.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.kunyi.data.vos.JobsVO

class JobInfoResponse {

    @SerializedName("code")
    private val code: Int = 0

    @SerializedName("message")
    private val message: String? = null

    @SerializedName("apiVersion")
    private val apiVersion: String? = null

    @SerializedName("jobs")
    private var jobsList : List<JobsVO>? = null

    fun getJobList(): List<JobsVO>{
        if(jobsList == null){
                jobsList = ArrayList<JobsVO>()
        }
        val jobsListVal = jobsList
        return jobsListVal!!
    }

    fun getCode(): Int {
        return code
    }

    fun getMessage(): String? {
        return message
    }

    fun getApiVersion():String?{
        return apiVersion
    }
}