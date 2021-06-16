/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.rest

import com.gridnine.jasmine.common.core.model.Xeption
import com.gridnine.jasmine.common.demo.rest.DemoThrowExceptionRequest
import com.gridnine.jasmine.common.demo.rest.DemoThrowExceptionResponse
import com.gridnine.jasmine.server.core.rest.RestHandler
import com.gridnine.jasmine.server.core.rest.RestOperationContext

class DemoThrowExceptionRestHandler:RestHandler<DemoThrowExceptionRequest,DemoThrowExceptionResponse> {
    override fun service(request: DemoThrowExceptionRequest, ctx: RestOperationContext): DemoThrowExceptionResponse {
        throw Xeption.forDeveloper("test server error")
    }
}