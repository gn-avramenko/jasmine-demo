/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.exceptions

import com.gridnine.jasmine.common.core.model.XeptionJS
import com.gridnine.jasmine.web.core.ui.components.SimpleActionHandler

class DemoThrowClientExceptionHandler: SimpleActionHandler {
    override suspend fun invoke() {
        throw XeptionJS.forDeveloper("Test developer error")
    }
}