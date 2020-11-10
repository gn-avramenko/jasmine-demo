/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: jasmine
 * This file is auto generated, don't modify it manually
 *****************************************************************/

@file:Suppress("unused", "RemoveRedundantQualifierName", "UNCHECKED_CAST", "MemberVisibilityCanBePrivate", "RemoveEmptyPrimaryConstructor", "FunctionName")

package com.gridnine.jasmine.server.demo.storage

import com.gridnine.jasmine.server.core.storage.Storage
import com.gridnine.jasmine.server.core.utils.AuthUtils
import com.gridnine.jasmine.server.demo.model.domain.DemoComplexDocumentIndex
import com.gridnine.jasmine.server.demo.model.domain.DemoComplexDocumentVariantIndex
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountIndex
import com.gridnine.jasmine.server.standard.model.domain.*
import com.gridnine.jasmine.server.standard.rest.WorkspaceProvider
import kotlin.IllegalArgumentException

class DemoWorkspaceProvider : WorkspaceProvider {
    override fun getWorkspace(): Workspace {
        val loginName = AuthUtils.getCurrentUser()
                ?: throw IllegalArgumentException("user is not logged in")
        return   Storage.get().loadDocument(Workspace::class, "${loginName}_workspace")
                ?: createDemoWorkspace()
    }

    override fun saveWorkspace(workspace: Workspace): Workspace {
        TODO("Not yet implemented")
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
            result.groups.add(group)
        }
        return result
    }

}