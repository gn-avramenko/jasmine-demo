/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.ui

import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVM
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVS
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVV
import com.gridnine.jasmine.web.server.components.*
import com.gridnine.jasmine.web.server.mainframe.BaseServerUiObjectHandler
import com.gridnine.jasmine.web.server.widgets.ServerUiGridCellWidget
import com.gridnine.jasmine.web.server.widgets.ServerUiTextBoxWidget
import com.gridnine.jasmine.web.server.widgets.ServerUiTextBoxWidgetConfiguration

class DemoUserAccountServerUiEditor:ServerUiViewEditor<DemoUserAccountEditorVM, DemoUserAccountEditorVS, DemoUserAccountEditorVV>,BaseServerUiNodeWrapper<ServerUiGridLayoutContainer>(){

    val loginWidget:ServerUiTextBoxWidget

    val nameWidget:ServerUiTextBoxWidget

    init{
        _node = ServerUiLibraryAdapter.get().createGridLayoutContainer(ServerUiGridLayoutContainerConfiguration{
            columns.add(ServerUiGridLayoutColumnConfiguration("300px"))
        })
        _node.addRow()
        loginWidget= ServerUiTextBoxWidget{
            width = "100%"
        }
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Логин:", loginWidget)))
        _node.addRow()
        nameWidget= ServerUiTextBoxWidget{
            width = "100%"
        }
        _node.addCell(ServerUiGridLayoutCell(ServerUiGridCellWidget("Имя:", nameWidget)))
    }

    override fun setData(data: DemoUserAccountEditorVM, settings: DemoUserAccountEditorVS?) {
         loginWidget.setValue(data.login)
         settings?.login?.let { loginWidget.configure(it)}
        nameWidget.setValue(data.name)
        settings?.name?.let { nameWidget.configure(it)}
    }

    override fun getData(): DemoUserAccountEditorVM {
        val result = DemoUserAccountEditorVM()
        result.login = loginWidget.getValue()
        result.name = nameWidget.getValue()
        return result
    }

    override fun showValidation(validation: DemoUserAccountEditorVV?) {
        loginWidget.showValidation(validation?.login)
        nameWidget.showValidation(validation?.name)
    }

    override fun setReadonly(value: Boolean) {
        loginWidget.setReadonly(true)
        nameWidget.setReadonly(value)
    }

    override fun navigate(key: String) :Boolean{
        return false
    }

}

class DemoUserAccountServerUiHandler: BaseServerUiObjectHandler(DemoUserAccount::class){
    override fun createEditor(): ServerUiViewEditor<*, *, *> {
        return DemoUserAccountServerUiEditor()
    }

}