package com.padcmyanmar.kunyi.activities

import android.os.Bundle
import android.util.Log
import com.padcmyanmar.kunyi.R
import com.padcmyanmar.kunyi.data.models.JobAppModel
import com.padcmyanmar.kunyi.data.vos.JobsVO
import kotlinx.android.synthetic.main.activity_job_details.*

class JobDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details)

        val jobId: Int = intent.getIntExtra("jobId", -1)
        Log.d("JobDetailsActivity ", "JobId : " + jobId.toString())
        bindData(JobAppModel.getInstance().getJobById(jobId)!!)
    }

    private fun bindData(job: JobsVO) {

        tvLocationVal.text = job.location

        val workingHours = tvVacancyVal.context.getString(R.string.format_working_hours,job.jobDuration!!.workingHoursPerDay.toString())
        tvVacancyVal.text = workingHours

        tvFullDesc.text = job.fullDesc

        val salary = tvPaymentVal.context.getString(R.string.format_salary, job.offerAmount!!.amount.toString())
        tvPaymentVal.text = salary

        val personApplied = tvPeriodVal.context.getString(R.string.format_persons_applied, job.availablePostCount.toString())
        tvPeriodVal.text = personApplied


    }
}