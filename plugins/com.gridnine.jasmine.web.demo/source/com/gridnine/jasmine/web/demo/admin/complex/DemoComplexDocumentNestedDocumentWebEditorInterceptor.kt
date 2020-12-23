/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.complex

import com.gridnine.jasmine.server.core.model.ui.BaseNavigatorVariantVMJS
import com.gridnine.jasmine.web.core.ui.WebEditorInterceptor
import com.gridnine.jasmine.web.core.utils.MiscUtilsJS
import com.gridnine.jasmine.web.core.utils.UiUtils
import com.gridnine.jasmine.web.demo.*
import kotlin.js.Date

class DemoComplexDocumentNestedDocumentWebEditorInterceptor : WebEditorInterceptor<DemoComplexDocumentNestedDocumentsEditor>{
    override fun onInit(editor: DemoComplexDocumentNestedDocumentsEditor) {
        editor.setRemoveHandler {item->
            val data = item.getData() as BaseNavigatorVariantVMJS
            UiUtils.confirm("<nobr>Вы действительно хотите удалить<nobr> ${data.title}?"){
                editor.removeTab(data.uid)
            }
        }
        editor.setAddHandler {
            UiUtils.choseVariant(NestedDocumentVariantJS::class){
                when(it){
                    NestedDocumentVariantJS.VARIANT1 ->{
                        val item = DemoComplexDocumentVariant1EditorVMJS()
                        item.intValue = 0
                        item.uid = MiscUtilsJS.createUUID()
                        item.title = "Новый вариант1"
                        editor.addTab(item, DemoComplexDocumentVariant1EditorVSJS())
                    }
                    NestedDocumentVariantJS.VARIANT2 ->{
                        val item = DemoComplexDocumentVariant2EditorVMJS()
                        item.dateValue = Date()
                        item.uid = MiscUtilsJS.createUUID()
                        item.title = "Новый вариант2"
                        editor.addTab(item, DemoComplexDocumentVariant2EditorVSJS())
                    }
                }

            }
        }
    }
}