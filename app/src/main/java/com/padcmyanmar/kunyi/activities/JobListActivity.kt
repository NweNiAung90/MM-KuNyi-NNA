/*------------------------------------------------------------------------------

This source is part of the assignment of the PADC Fun5 class.

Modification History


Date		Version		Author			Description
----------	-----------	--------------- ----------------------------------------
05 08 2018	1.0			Nwe Ni Aung		Initial Version.
------------------------------------------------------------------------------*/
package com.padcmyanmar.kunyi.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.padcmyanmar.kunyi.R
import com.padcmyanmar.kunyi.adapters.JobAdapter
import com.padcmyanmar.kunyi.components.SmartScrollListener
import com.padcmyanmar.kunyi.delegates.BeforeLoginDelegate
import com.padcmyanmar.kunyi.delegates.JobItemDelegates
import com.padcmyanmar.kunyi.events.DataEvent
import com.padcmyanmar.kunyi.events.ErrorEvent
import com.padcmyanmar.kunyi.data.models.JobAppModel
import com.padcmyanmar.kunyi.views.pods.BeforeLoginViewPod
import com.padcmyanmar.kunyi.data.vos.JobsVO
import kotlinx.android.synthetic.main.activity_job_list.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/*2nd step of Controller Pattern HealthCareItemDelegates */
class JobListActivity : BaseActivity(), JobItemDelegates, BeforeLoginDelegate {


    private var mJobAdapter: JobAdapter? = null
    private var mSmartScrollListener: SmartScrollListener? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_list)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        //Add menu icon in tool bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)

        rvJobList.setEmptyView(vpEmptyJobs)
        rvJobList.layoutManager = LinearLayoutManager(applicationContext
                , LinearLayoutManager.VERTICAL
                , false)

        mSmartScrollListener = SmartScrollListener(object : SmartScrollListener.OnSmartScrollListener{
            override fun onListEndReach() {
                swipeRefreshLayout.isRefreshing = true
                JobAppModel.getInstance().loadJobInfo()
            }
        })
        rvJobList.addOnScrollListener(mSmartScrollListener)

        mJobAdapter = JobAdapter(applicationContext,this)
        rvJobList.adapter = mJobAdapter
        swipeRefreshLayout.isRefreshing = true

        //Load Response Data
        JobAppModel.getInstance().loadJobInfo()

        swipeRefreshLayout.setOnRefreshListener {
            JobAppModel.getInstance().forceLoadJobInfo()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_applied_jobs -> {
                    Snackbar.make(navigationView,"Tapped Job Posts", Snackbar.LENGTH_LONG).show()
                }
                R.id.menu_favorite_jobs -> {
                    Snackbar.make(navigationView,"Tapped About Us", Snackbar.LENGTH_LONG).show()
                }
                R.id.menu_just_for_you ->{
                    Snackbar.make(navigationView,"Tapped Profile", Snackbar.LENGTH_LONG).show()
                }
            }
            for( menuItemIndex in 0 until navigationView.menu.size()){ // until => same with size()-1
                navigationView.menu.getItem(menuItemIndex).isChecked = false
            }
            it.isChecked = true
            drawerLayout.closeDrawer(GravityCompat.END) // close navigation view
            return@setNavigationItemSelectedListener true
        }

        val vpBeforeLogin = navigationView.getHeaderView(0) as BeforeLoginViewPod
        vpBeforeLogin.setDelegate(this)
    }

    /*2nd step of Controller Pattern */
    override fun onTabItem(job: JobsVO?) {
        val intent = Intent(applicationContext, JobDetailsActivity::class.java)
        intent.putExtra("jobId", job!!.jobPostId)
        startActivity(intent)
    }

    override fun onTapLogin() {
        val intent = Intent(applicationContext, AccountControlActivity::class.java)
        intent.putExtra("action_type", AccountControlActivity.ACTION_TYPE_LOGIN)
        startActivity(intent)
    }

    override fun onTapRegister() {
        val intent = Intent(applicationContext, AccountControlActivity::class.java)
        intent.putExtra(AccountControlActivity.ACTION_TYPE, AccountControlActivity.ACTION_TYPE_REGISTER)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(item.itemId == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /* 2n Step Event Bus Listen Method */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onJobLoadedEvent(jobLoadedEvent: DataEvent.JobInfoLoadedEvent){

        swipeRefreshLayout.isRefreshing = false
        mJobAdapter!!.setData(jobLoadedEvent.loadedJobInfo as MutableList<JobsVO>)
        vpEmptyJobs.visibility = View.GONE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onErrorJobLoadedEvent(apiErrorEvent: ErrorEvent.ApiErrorEvent) {
        swipeRefreshLayout.isRefreshing = false
        Snackbar.make(rvJobList, "ERROR : " + apiErrorEvent.getMsg(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        //vpEmptyJobs.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEmptyJobLoadedEvent(emptyDataLoadedEvent: DataEvent.EmptyDataLoadedEvent) {
        swipeRefreshLayout.isRefreshing = false
        Snackbar.make(rvJobList, "ERROR : " + emptyDataLoadedEvent.errorMsg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        vpEmptyJobs.visibility = View.VISIBLE
    }
}
