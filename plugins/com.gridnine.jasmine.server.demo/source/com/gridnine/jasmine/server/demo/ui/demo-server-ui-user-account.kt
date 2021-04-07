/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.ui

import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.web.demo.DemoUserAccountEditor
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVM
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVS
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVV
import com.gridnine.jasmine.web.server.components.*
import com.gridnine.jasmine.web.server.mainframe.BaseServerUiObjectHandler
import com.gridnine.jasmine.web.server.widgets.ServerUiGridCellWidget
import com.gridnine.jasmine.web.server.widgets.ServerUiTextBoxWidget
import com.gridnine.jasmine.web.server.widgets.ServerUiTextBoxWidgetConfiguration


class DemoUserAccountServerUiHandler: BaseServerUiObjectHandler(DemoUserAccount::class){
    override fun createEditor(): ServerUiViewEditor<*, *, *> {
        return DemoUserAccountEditor()
    }
}