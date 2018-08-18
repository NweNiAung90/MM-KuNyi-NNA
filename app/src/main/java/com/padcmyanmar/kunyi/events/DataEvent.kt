/*------------------------------------------------------------------------------

This source is part of the assignment of the PADC Fun5 class.

Modification History


Date		Version		Author			Description
----------	-----------	--------------- ----------------------------------------
05 08 2018	1.0			Nwe Ni Aung		Initial Version.
------------------------------------------------------------------------------*/
package com.padcmyanmar.kunyi.events
//------------------------------------------------------------------------------
import com.padcmyanmar.kunyi.data.vos.JobsVO
//------------------------------------------------------------------------------
class DataEvent {
    // start define event broadcast
    class JobInfoLoadedEvent(val loadedJobInfo: List<JobsVO>)

    class EmptyDataLoadedEvent(val errorMsg: String? = "Empty Body Response")
    // end define event broadcast

}