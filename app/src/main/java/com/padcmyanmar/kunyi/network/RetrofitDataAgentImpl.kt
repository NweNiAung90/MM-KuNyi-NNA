/*------------------------------------------------------------------------------

This source is part of the assignment of the PADC Fun5 class.

Modification History


Date		Version		Author			Description
----------	-----------	--------------- ----------------------------------------
21 007 2018	1.0			Nwe Ni Aung		Initial Version.
------------------------------------------------------------------------------*/
package com.padcmyanmar.kunyi.network
//------------------------------------------------------------------------------

import com.google.gson.Gson
import com.padcmyanmar.kunyi.events.DataEvent
import com.padcmyanmar.kunyi.events.ErrorEvent
import com.padcmyanmar.kunyi.network.responses.JobInfoResponse
import com.padcmyanmar.kunyi.utils.JobConstants
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitDataAgentImpl private constructor() :  JobDataAgent {

    companion object {
        /*Factory Logic
              * only one obj for this class type
             */
        private var INSTANCE: RetrofitDataAgentImpl? = null
        fun getInstance(): RetrofitDataAgentImpl {
            if (INSTANCE == null){
                INSTANCE = RetrofitDataAgentImpl()
            }
            val i = INSTANCE
            return i!!
        }
    }

    private val mJobApi: JobApi

    override fun loadJobInfo(accessToken: String, page: Int) {
        /* call Interface Type method with param and get Call object*/
        val jobResponseCall = mJobApi.loadJobs(page, accessToken)

        // pass Callback Interface as Anonymous Inner Class

        jobResponseCall.enqueue(object : Callback<JobInfoResponse> {

            override fun onResponse(call: Call<JobInfoResponse>, response: Response<JobInfoResponse>) {
                //Retrieve Response Body
                val jobsResponse: JobInfoResponse? = response.body()
                //Response is ok.
                if (jobsResponse != null
                        && jobsResponse.getCode() == 200
                        && jobsResponse.getJobList().isNotEmpty()) {
                    val jobsLoadedEvent = DataEvent.JobInfoLoadedEvent(jobsResponse.getJobList()) // not use new operator
                    EventBus.getDefault().post(jobsLoadedEvent)
                } else {//Response is not ok. Error Case. Response Null Case
                    if(jobsResponse != null)
                    //For 200OK
                    EventBus.getDefault().post(DataEvent.EmptyDataLoadedEvent(jobsResponse.getMessage()))
                    else
                    //shows server message
                    EventBus.getDefault().post(DataEvent.EmptyDataLoadedEvent())
                }
            }
            override fun onFailure(call: Call<JobInfoResponse>?, t: Throwable?) {
                EventBus.getDefault().post(ErrorEvent.ApiErrorEvent(t))
            }
        })
    }

    // add required initialization for Retrofit
    init {
        //configure okHtttpClient
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

        /* configure Retrofit Object
    API_BASE = "http://padcmyanmar.com/padc-3/final-projects/one-time-jobs/"
    */
        val retrofit = Retrofit.Builder()
                .baseUrl(JobConstants.API_BASE)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(okHttpClient)
                .build()

        /*pass interface type from retrofit object and take interface object
        JobApi Object Initialization
        */
        mJobApi = retrofit.create(JobApi::class.java)
    }
}