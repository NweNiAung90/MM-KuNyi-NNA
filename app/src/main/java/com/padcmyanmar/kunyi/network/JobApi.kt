package com.padcmyanmar.kunyi.network

import com.padcmyanmar.kunyi.network.responses.JobInfoResponse
import com.padcmyanmar.kunyi.utils.JobConstants
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface JobApi {

    @FormUrlEncoded
    @POST(JobConstants.GET_JOB_INFO)
    fun loadJobs(
            @Field(JobConstants.PAGE_ACCESS_TOKEN) pageIndex: Int,
            @Field(JobConstants.PARAM_ACCESS_TOKEN) accessToken: String) : Call<JobInfoResponse>
}