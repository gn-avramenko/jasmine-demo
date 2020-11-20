/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.complex

import com.gridnine.jasmine.server.core.model.ui.BaseNavigatorVariantVMJS
import com.gridnine.jasmine.web.core.ui.WebEditorInterceptor
import com.gridnine.jasmine.web.core.utils.UiUtils

class DemoComplexDocumentNestedDocumentWebEditorInterceptor : WebEditorInterceptor<DemoComplexDocumentNestedDocumentsWebEditor>{
    override fun onInit(editor: DemoComplexDocumentNestedDocumentsWebEditor) {
        editor.setRemoveHandler { item ->
            val data = item.getData() as BaseNavigatorVariantVMJS
            UiUtils.confirm("<nobr>Вы действительно хотите удалить<nobr> ${data.title}?"){
                editor.removeTab(data.uid)
            }
        }
    }
}