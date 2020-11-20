/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.complex

import com.gridnine.jasmine.server.core.model.l10n.L10nMetaRegistryJS
import com.gridnine.jasmine.web.core.ui.DefaultUIParameters
import com.gridnine.jasmine.web.core.ui.UiLibraryAdapter
import com.gridnine.jasmine.web.core.ui.WebComponent
import com.gridnine.jasmine.web.core.ui.WebEditor
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutCell
import com.gridnine.jasmine.web.core.ui.components.WebGridLayoutContainer
import com.gridnine.jasmine.web.core.ui.widgets.DateBoxWidget
import com.gridnine.jasmine.web.core.ui.widgets.GridCellWidget
import com.gridnine.jasmine.web.demo.DemoComplexDocumentVariant2EditorVMJS
import com.gridnine.jasmine.web.demo.DemoComplexDocumentVariant2EditorVSJS
import com.gridnine.jasmine.web.demo.DemoComplexDocumentVariant2EditorVVJS

class DemoComplexDocumentVariant2WebEditor(private val parent: WebComponent): WebEditor<DemoComplexDocumentVariant2EditorVMJS, DemoComplexDocumentVariant2EditorVSJS, DemoComplexDocumentVariant2EditorVVJS> {

    private val delegate:WebGridLayoutContainer

    val dateValueWidget: DateBoxWidget

    private lateinit var title:String

    private lateinit var uid:String

    init {
        delegate = UiLibraryAdapter.get().createGridLayoutContainer(this) {}
        delegate.defineColumn(DefaultUIParameters.controlWidthAsString)
        delegate.addRow()
        val dateWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentVariant2Editor"]?.get("dateValue")
                ?: "???") { par ->
            DateBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(dateWidgetCell))
        dateValueWidget = dateWidgetCell.widget
    }

    override fun readData(vm: DemoComplexDocumentVariant2EditorVMJS, vs: DemoComplexDocumentVariant2EditorVSJS) {
        title = vm.title
        uid = vm.uid
        dateValueWidget.setValue(vm.dateValue)
        vs.dateValue?.let { dateValueWidget.configure(it) }
    }

    override fun setReadonly(value: Boolean) {
        dateValueWidget.setReadonly(value)
    }

    override fun getParent(): WebComponent? {
        return parent
    }

    override fun destroy() {
       delegate.destroy()
    }

    override fun getData(): DemoComplexDocumentVariant2EditorVMJS {
        val result = DemoComplexDocumentVariant2EditorVMJS()
        result.uid = uid
        result.title = title
        result.dateValue = dateValueWidget.getValue()
        return result
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

    override fun showValidation(validation: DemoComplexDocumentVariant2EditorVVJS) {
        validation.dateValue?.let { dateValueWidget.showValidation(it) }
    }

}