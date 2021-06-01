/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: jasmine
 * This file is auto generated, don't modify it manually
 *****************************************************************/


package com.gridnine.jasmine.server.demo.storage

import com.gridnine.jasmine.common.core.model.BaseAsset
import com.gridnine.jasmine.common.core.storage.Storage
import com.gridnine.jasmine.common.core.utils.AuthUtils
import com.gridnine.jasmine.common.demo.model.domain.DemoComplexDocumentIndex
import com.gridnine.jasmine.common.demo.model.domain.DemoComplexDocumentVariantIndex
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndex
import com.gridnine.jasmine.common.reports.model.domain.ReportDescriptionIndex
import com.gridnine.jasmine.common.reports.model.domain.ReportsWorkspaceItem
import com.gridnine.jasmine.common.standard.model.domain.*
import com.gridnine.jasmine.server.standard.model.WorkspaceProvider

class DemoWorkspaceProvider : WorkspaceProvider {
    override fun getWorkspace(): Workspace {
        val loginName = AuthUtils.getCurrentUser()
        return   Storage.get().loadDocument(Workspace::class, "${loginName}_workspace")
                ?: createDemoWorkspace()
    }

    override fun saveWorkspace(workspace: Workspace):Workspace {
        val loginName = AuthUtils.getCurrentUser()
        workspace.uid = "${loginName}_workspace"
        workspace.setValue(BaseAsset.revision, -1)
        Storage.get().saveDocument(workspace)
        return workspace
    }

    private fun createDemoWorkspace(): Workspace {
        val result = Workspace()
        result.uid = "admin_workspace"
        run {
            val group = WorkspaceGroup()
            group.displayName = "Настройки"
            val item = ListWorkspaceItem()
            item.columns.add(DemoUserAccountIndex.loginProperty.name)
            item.columns.add(DemoUserAccountIndex.nameProperty.name)
            item.filters.add(DemoUserAccountIndex.loginProperty.name)
            val order = SortOrder()
            order.orderType = SortOrderType.ASC
            order.field = DemoUserAccountIndex.loginProperty.name
            item.listId = DemoUserAccountIndex::class.qualifiedName
            item.displayName = "Профили"
            group.items.add(item)
            result.groups.add(group)
        }
        run {
            val group = WorkspaceGroup()
            group.displayName = "Объекты"
            run {
                val item = ListWorkspaceItem()
                item.columns.add(DemoComplexDocumentIndex.stringPropertyProperty.name)
                item.columns.add(DemoComplexDocumentIndex.enumPropertyProperty.name)
                item.columns.add(DemoComplexDocumentIndex.booleanPropertyProperty.name)
                item.columns.add(DemoComplexDocumentIndex.datePropertyProperty.name)
                item.columns.add(DemoComplexDocumentIndex.dateTimePropertyProperty.name)
                item.columns.add(DemoComplexDocumentIndex.floatPropertyProperty.name)
                item.columns.add(DemoComplexDocumentIndex.entityRefPropertyProperty.name)
                item.filters.addAll(item.columns)
                item.listId = DemoComplexDocumentIndex::class.qualifiedName
                item.displayName = "Сложные объекты"
                group.items.add(item)
            }
            run {
                val item = ListWorkspaceItem()
                item.columns.add(DemoComplexDocumentVariantIndex.titleProperty.name)
                item.listId = DemoComplexDocumentVariantIndex::class.qualifiedName
                item.displayName = "Вложенные объекты"
                group.items.add(item)
            }
            run {
                val item = ReportsWorkspaceItem()
                item.columns.add(ReportDescriptionIndex.nameProperty.name)
                item.listId = ReportDescriptionIndex::class.qualifiedName
                item.displayName = "Отчеты"
                group.items.add(item)
            }
            result.groups.add(group)
        }
        saveWorkspace(result)
        return result
    }

}