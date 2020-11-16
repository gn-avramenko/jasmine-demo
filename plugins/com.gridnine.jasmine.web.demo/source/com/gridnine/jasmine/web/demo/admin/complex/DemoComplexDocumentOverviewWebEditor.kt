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
import com.gridnine.jasmine.web.core.ui.widgets.GridCellWidget
import com.gridnine.jasmine.web.core.ui.widgets.TextBoxWidget
import com.gridnine.jasmine.web.demo.*

class DemoComplexDocumentOverviewWebEditor(aParent: WebComponent): WebEditor<DemoComplexDocumentOverviewEditorVMJS, DemoComplexDocumentOverviewEditorVSJS, DemoComplexDocumentOverviewEditorVVJS> {

    private val parent:WebComponent
    private val delegate:WebGridLayoutContainer

    val stringPropertyWidget: TextBoxWidget


    init {
        parent = aParent
        delegate = UiLibraryAdapter.get().createGridLayoutContainer(this) {}
        delegate.defineColumn(DefaultUIParameters.controlWidthAsString)
        delegate.addRow()
        val stringPropertyWidgetCell = GridCellWidget(delegate,L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentOverviewEditor"]?.get("stringProperty")
                ?: "???") { par ->
            TextBoxWidget(par) {
                width = "100%"
            }
        }
        delegate.addCell(WebGridLayoutCell(stringPropertyWidgetCell))
        stringPropertyWidget = stringPropertyWidgetCell.widget
    }

    override fun readData(vm: DemoComplexDocumentOverviewEditorVMJS, vs: DemoComplexDocumentOverviewEditorVSJS) {
        stringPropertyWidget.setValue(vm.stringProperty)
        vs.stringProperty?.let { stringPropertyWidget.configure(it) }
    }

    override fun setReadonly(value: Boolean) {
        stringPropertyWidget.setReadonly(value)
    }

    override fun getParent(): WebComponent? {
        return parent
    }

    override fun destroy() {
       delegate.destroy()
    }

    override fun getData(): DemoComplexDocumentOverviewEditorVMJS {
        val result = DemoComplexDocumentOverviewEditorVMJS()
        result.stringProperty = stringPropertyWidget.getValue()
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

    override fun showValidation(validation: DemoComplexDocumentOverviewEditorVVJS) {
        validation.stringProperty?.let { stringPropertyWidget.showValidation(it) }
    }

}