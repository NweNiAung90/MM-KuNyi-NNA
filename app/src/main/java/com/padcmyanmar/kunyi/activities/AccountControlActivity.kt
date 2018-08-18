package com.padcmyanmar.kunyi.activities

import android.os.Bundle
import com.padcmyanmar.kunyi.R
import com.padcmyanmar.kunyi.fragments.LoginFragment
import com.padcmyanmar.kunyi.fragments.RegisterFragment


class AccountControlActivity : BaseActivity(){

    companion object {
        const val ACTION_TYPE = "action_type"
        const val ACTION_TYPE_LOGIN = 1111
        const val ACTION_TYPE_REGISTER = 2222
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_control)

        val actionType = intent.extras.getInt(ACTION_TYPE)

        if (actionType == 1111){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.flContainer, LoginFragment())
                    .commit()
        } else if(actionType == 2222){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.flContainer, RegisterFragment())
                    .commit()
        }

    }
}