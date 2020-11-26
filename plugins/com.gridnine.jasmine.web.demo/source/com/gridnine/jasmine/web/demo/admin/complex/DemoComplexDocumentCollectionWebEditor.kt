/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.complex

import com.gridnine.jasmine.server.core.model.l10n.L10nMetaRegistryJS
import com.gridnine.jasmine.server.core.model.ui.*
import com.gridnine.jasmine.web.core.ui.*
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutCell
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutContainer
import com.gridnine.jasmine.web.core.ui.widgets.*
import com.gridnine.jasmine.web.demo.*

class DemoComplexDocumentCollectionWebEditor(private val parent:WebComponent): WebEditor<DemoComplexDocumentTableEditorVMJS, DemoComplexDocumentTableEditorVSJS, DemoComplexDocumentTableEditorVVJS> {

    private val delegate:WebGridLayoutContainer = UiLibraryAdapter.get().createGridLayoutContainer(this) {
        width ="100%"
    }

    private val tableWidget:TableBoxWidget<DemoComplexDocumentTableVMJS, DemoComplexDocumentTableVSJS, DemoComplexDocumentTableVVJS>

    init {
        delegate.defineColumn("100%")
        delegate.addRow()
        tableWidget = TableBoxWidget(parent){
            width = "100%"
            column("enumColumn", EnumSelectBoxWidgetDescriptionJS(false, "com.gridnine.jasmine.server.demo.model.domain.DemoEnumJS" ), L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentTable"]!!["enumColumn"] ?: error(""), null)
            column("entityRefColumn", EntitySelectBoxWidgetDescriptionJS(false, "com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountJS"), L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentTable"]!!["entityRefColumn"] ?: error(""), null)
            column("integerColumn",IntegerNumberBoxWidgetDescriptionJS(false, false), L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentTable"]!!["integerColumn"] ?: error(""), null)
            column("floatColumn", FloatNumberBoxWidgetDescriptionJS(false), L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentTable"]!!["floatColumn"] ?: error(""), null)
            column("textColumn", TextBoxWidgetDescriptionJS(false), L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentTable"]!!["textColumn"] ?: error(""), null)
            showToolsColumn = true
            toolsColumnMaxWidth = "50px"

        }
        delegate.addCell(WebGridLayoutCell(tableWidget))

    }

    override fun getParent(): WebComponent? {
        return parent
    }

    override fun getChildren(): List<WebComponent> {
        return arrayListOf(delegate)
    }

    override fun getHtml(): String {
        return delegate.getHtml()
    }

    override fun decorate() {
        delegate.decorate()
    }

    override fun destroy() {
        delegate.destroy()
    }

    override fun getData(): DemoComplexDocumentTableEditorVMJS {
        val result = DemoComplexDocumentTableEditorVMJS()
        result.table.addAll(tableWidget.getData())
        return result
    }

    override fun readData(vm: DemoComplexDocumentTableEditorVMJS, vs: DemoComplexDocumentTableEditorVSJS) {
        tableWidget.readData(vm.table, vs.table)
    }

    override fun setReadonly(value: Boolean) {
        tableWidget.setReadonly(value)
    }

    override fun showValidation(validation: DemoComplexDocumentTableEditorVVJS) {
        tableWidget.showValidation(validation.table)
    }


}