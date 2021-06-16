/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.exceptions

import com.gridnine.jasmine.common.demo.rest.DemoThrowExceptionRequestJS
import com.gridnine.jasmine.web.core.ui.components.SimpleActionHandler
import com.gridnine.jasmine.web.demo.DemoRestClient

class DemoThrowServerExceptionHandler: SimpleActionHandler {
    override suspend fun invoke() {
        DemoRestClient.demo_demo_throwException(DemoThrowExceptionRequestJS())
    }
}