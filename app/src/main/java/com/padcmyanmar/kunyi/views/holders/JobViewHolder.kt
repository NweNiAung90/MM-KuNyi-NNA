package com.padcmyanmar.kunyi.views.holders


import android.annotation.SuppressLint
import android.view.View
import com.padcmyanmar.kunyi.R
import com.padcmyanmar.kunyi.delegates.JobItemDelegates
import com.padcmyanmar.kunyi.data.vos.JobsVO
import kotlinx.android.synthetic.main.view_holder_job_list.view.*


class JobViewHolder(itemView: View, private val mDelegates: JobItemDelegates) : BaseViewHolder<JobsVO>(itemView) {
    @SuppressLint("StringFormatInvalid")
    override fun setData(data: JobsVO) {

        mData = data

        itemView.tvTitle.text = data.shortDesc
        itemView.tvDescription.text = data.fullDesc
        itemView.tvLocation.text = data.location

        val salary = itemView.context.getString(R.string.format_salary, data.offerAmount!!.amount.toString())
        itemView.tvSalary.text = salary

        val workingHours = itemView.context.getString(R.string.format_working_hours,data.jobDuration!!.workingHoursPerDay.toString())
       itemView.tvWorkingTime.text = workingHours

        val personApplied = itemView.context.getString(R.string.format_persons_applied,data.availablePostCount.toString())
        itemView.tvPersonApplied.text = personApplied
    }

    override fun onClick(v: View?) {
        mDelegates.onTabItem(mData)
    }

}