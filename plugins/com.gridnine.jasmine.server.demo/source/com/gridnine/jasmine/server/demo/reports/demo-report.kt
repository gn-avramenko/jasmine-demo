/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.reports

import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.common.reports.model.misc.ReportRequestedParameterDescription
import com.gridnine.jasmine.server.demo.model.reports.DemoReportParametersIds
import com.gridnine.jasmine.server.demo.model.reports.DemoReportType
import com.gridnine.jasmine.server.reports.model.*
import com.gridnine.jasmine.server.reports.model.EndDateReportRequestedParameterHandler

object DemoUserReportRequestedParameterHandler: BaseObjectReferenceReportRequestedParameterHandler<DemoUserAccount>(DemoReportParametersIds.USER, DemoUserAccount::class)

class DemoReportServerHandler : BaseSeverReportHandler(DemoReportType.DEMO_REPORT){

    override fun getRequestedParametersDescriptions(): List<ReportRequestedParameterDescription> {
        return arrayListOf(StartDateReportRequestedParameterHandler,
                EndDateReportRequestedParameterHandler,
                DemoUserReportRequestedParameterHandler).map { it.createParameterDescription() }
    }


}