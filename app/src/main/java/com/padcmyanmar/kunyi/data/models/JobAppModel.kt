/*------------------------------------------------------------------------------

This source is part of the assignment of the PADC Fun5 class.

Modification History


Date		Version		Author			Description
----------	-----------	--------------- ----------------------------------------
05 08 2018	1.0			Nwe Ni Aung		Initial Version.
------------------------------------------------------------------------------*/
package com.padcmyanmar.kunyi.data.models

import com.padcmyanmar.kunyi.data.vos.JobsVO
import com.padcmyanmar.kunyi.events.DataEvent
import com.padcmyanmar.kunyi.network.RetrofitDataAgentImpl
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class JobAppModel {
    companion object {
        /* Singleton Design Pattern */
        //2. static class type attribute
        private var INSTANCE: JobAppModel? = null

        fun getInstance(): JobAppModel {
            if (INSTANCE == null)
                INSTANCE = JobAppModel()

            var i = INSTANCE
            return i!!
        }
    }

    //1. private constructor
    private constructor() {
        /* 2 steps for listen  broadcast event from EventBus
         1. register event
         2. define Event listen method
      * */
        EventBus.getDefault().register(this)
    }

    private var mJobsPage: Int = 1
    private var mJobsData: HashMap<Int, JobsVO> = HashMap()

    private val DUMMY_ACCESS_TOKEN = "b002c7e1a528b7cb460933fc2875e916"

    //private var mDataAgent: JobDataAgent = RetrofitDataAgentImpl.getInstance()

    /*
     * loadJobInfo - This method is called load Job List from network call
     *
     * @param accessToken
     * @return void         listen response asynchronously
     */
    fun loadJobInfo() {
        RetrofitDataAgentImpl.getInstance().loadJobInfo(DUMMY_ACCESS_TOKEN, mJobsPage)
    }

    fun forceLoadJobInfo() {
        mJobsPage = 1
        mJobsData = HashMap()
        RetrofitDataAgentImpl.getInstance().loadJobInfo(DUMMY_ACCESS_TOKEN, mJobsPage)
    }

    //composite
    /* Define Event Listen Method */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onJobsLoadedEvent(jobLoadedEvent: DataEvent.JobInfoLoadedEvent){
        for(jobinfo : JobsVO in jobLoadedEvent.loadedJobInfo){
            mJobsData[jobinfo.jobPostId] = jobinfo
        }
        //mJobsPage = jobInfoLoadedEvent.loadedPageIndex +1
    }

    /*Send Id to details activity
        Retrieve data from data Repository
      */
    fun getJobById(jobId: Int): JobsVO?{
        return mJobsData[jobId]
    }
}