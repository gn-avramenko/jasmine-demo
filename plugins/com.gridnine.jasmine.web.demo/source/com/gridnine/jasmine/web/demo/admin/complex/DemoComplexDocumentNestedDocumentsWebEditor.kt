/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.complex

import com.gridnine.jasmine.server.core.model.ui.BaseNavigatorVariantVMJS
import com.gridnine.jasmine.web.core.ui.WebComponent
import com.gridnine.jasmine.web.core.ui.WebEditorInterceptor
import com.gridnine.jasmine.web.core.ui.widgets.NavigatorWidget
import com.gridnine.jasmine.web.core.utils.MiscUtilsJS
import com.gridnine.jasmine.web.core.utils.UiUtils
import com.gridnine.jasmine.web.demo.*
import kotlin.js.Date

class DemoComplexDocumentNestedDocumentsWebEditor(parent:WebComponent):
        NavigatorWidget<DemoComplexDocumentNestedDocumentsEditorVMJS, DemoComplexDocumentNestedDocumentsEditorVSJS, DemoComplexDocumentNestedDocumentsEditorVVJS>(parent,{widget ->
            width = "100%"
            height = "100%"
            vmFactory = {DemoComplexDocumentNestedDocumentsEditorVMJS()}
            factory(DemoComplexDocumentVariant1EditorVMJS::class){DemoComplexDocumentVariant1WebEditor(widget)}
            factory(DemoComplexDocumentVariant2EditorVMJS::class){DemoComplexDocumentVariant2WebEditor(widget)}
        }){
    private val interceptors = arrayListOf<WebEditorInterceptor<DemoComplexDocumentNestedDocumentsWebEditor>>()
    init {
        interceptors.add(DemoComplexDocumentNestedDocumentWebEditorInterceptor())
        interceptors.forEach {
            it.onInit(this)
        }
    }
}