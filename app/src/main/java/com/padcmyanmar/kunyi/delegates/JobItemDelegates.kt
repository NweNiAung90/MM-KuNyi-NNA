/*------------------------------------------------------------------------------

This source is part of the assignment of the PADC Fun5 class.

Modification History


Date		Version		Author			Description
----------	-----------	--------------- ----------------------------------------
4 08 2018	1.0			Nwe Ni Aung		Initial Version.
------------------------------------------------------------------------------*/
package com.padcmyanmar.kunyi.delegates

import com.padcmyanmar.kunyi.data.vos.JobsVO
/*1st step of Controller Pattern */
interface JobItemDelegates {
    // add param to know user tap which itemView
    fun onTabItem(job: JobsVO?)
}