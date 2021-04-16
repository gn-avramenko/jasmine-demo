/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.ui

import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditor
import com.gridnine.jasmine.server.core.ui.common.ViewEditor
import com.gridnine.jasmine.server.standard.ui.mainframe.BaseUiObjectHandler


class DemoUserAccountUiHandler: BaseUiObjectHandler(DemoUserAccount::class){
    override fun createEditor(): ViewEditor<*, *, *> {
        return DemoUserAccountEditor()
    }
}