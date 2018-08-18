/*------------------------------------------------------------------------------

This source is part of the assignment of the PADC Fun5 class.

Modification History


Date		Version		Author			Description
----------	-----------	--------------- ----------------------------------------
05 08 2018	1.0			Nwe Ni Aung		Initial Version.
------------------------------------------------------------------------------*/
package com.padcmyanmar.kunyi.utils
//------------------------------------------------------------------------------
class JobConstants {
    companion object {
        /*
         Endpoints Url to invoke
         -root path
        */
        const val API_BASE = "http://padcmyanmar.com/padc-3/final-projects/one-time-jobs/"
        const val GET_JOB_INFO = "getJobs.php"

        /*
       Request Paramenter
       */
        const val PARAM_ACCESS_TOKEN = "access_token"
        const val PAGE_ACCESS_TOKEN = "page"

        const val JOB_ID = "jobId"


    }
}