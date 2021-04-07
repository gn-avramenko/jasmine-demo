/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.ui

import com.gridnine.jasmine.server.core.model.ui.*
import com.gridnine.jasmine.server.demo.model.domain.DemoComplexDocument
import com.gridnine.jasmine.server.demo.model.domain.DemoEnum
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.web.demo.*
import com.gridnine.jasmine.web.server.components.*
import com.gridnine.jasmine.web.server.mainframe.BaseServerUiObjectHandler
import com.gridnine.jasmine.web.server.utils.ServerUiUtils
import com.gridnine.jasmine.web.server.widgets.*
import java.time.LocalDate
import java.util.*
import kotlin.reflect.KClass


class DemoComplexDocumentNestedDocumentTileSpaceEditorInterceptor : ServerUiEditorInterceptor<DemoComplexDocumentNestedDocumentsEditor> {
    override fun onInit(editor: DemoComplexDocumentNestedDocumentsEditor) {
        editor.setRemoveHandler { item ->
            val data = item.getData() as BaseNavigatorVariantVM
            ServerUiUtils.confirm("Вы действительно хотите удалить\n\r${data.title}?") {
                editor.removeTab(data.uid)
            }
        }
        editor.setAddHandler {
            ServerUiUtils.choseVariant(NestedDocumentVariant::class) {
                when (it) {
                    NestedDocumentVariant.VARIANT1 -> {
                        val item = DemoComplexDocumentVariant1EditorVM()
                        item.intValue = 0
                        item.uid = UUID.randomUUID().toString()
                        item.title = "Новый вариант1"
                        editor.addTab(item, null)
                    }
                    NestedDocumentVariant.VARIANT2 -> {
                        val item = DemoComplexDocumentVariant2EditorVM()
                        item.dateValue = LocalDate.now()
                        item.uid = UUID.randomUUID().toString()
                        item.title = "Новый вариант2"
                        editor.addTab(item, null)
                    }
                }
            }
        }
    }

    override fun getEditorClass(): KClass<DemoComplexDocumentNestedDocumentsEditor> {
        return DemoComplexDocumentNestedDocumentsEditor::class
    }

}
class DemoComplexDocumentServerUiHandler : BaseServerUiObjectHandler(DemoComplexDocument::class) {
    override fun createEditor(): ServerUiViewEditor<*, *, *> {
        return DemoComplexDocumentTileSpace()
    }
}

