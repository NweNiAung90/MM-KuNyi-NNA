/*------------------------------------------------------------------------------

This source is part of the assignment of the PADC Fun5 class.

Modification History


Date		Version		Author			Description
----------	-----------	--------------- ----------------------------------------
05 08 2018	1.0			Nwe Ni Aung		Initial Version.
------------------------------------------------------------------------------*/
package com.padcmyanmar.kunyi.adapters

import android.content.Context
import android.view.ViewGroup
import com.padcmyanmar.kunyi.R
import com.padcmyanmar.kunyi.delegates.JobItemDelegates
import com.padcmyanmar.kunyi.views.holders.BaseViewHolder
import com.padcmyanmar.kunyi.views.holders.JobViewHolder
import com.padcmyanmar.kunyi.data.vos.JobsVO

/* 1 Adapter tightly connected with 1 View Holder */
class JobAdapter(context: Context, private val mjobItemDelegates: JobItemDelegates) : BaseRecyclerAdapter<JobViewHolder, JobsVO>(context) {
    //Framework call these methods.
    //call onCreateViewHolder() when Recycler View needs to create item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<JobsVO> {
        val jobItemView = mLayoutInflator.inflate(R.layout.view_holder_job_list, parent, false)
        return JobViewHolder(jobItemView, mjobItemDelegates)
    }


}