/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.rest

import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.server.standard.rest.ObjectEditorHandler
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVM
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVS
import com.gridnine.jasmine.web.demo.DemoUserAccountEditorVV
import kotlin.reflect.KClass

class DemoUserAccountEditorHandler:ObjectEditorHandler<DemoUserAccount, DemoUserAccountEditorVM, DemoUserAccountEditorVS, DemoUserAccountEditorVV>{
    override fun getObjectClass(): KClass<DemoUserAccount> {
        return DemoUserAccount::class
    }

    override fun getVMClass(): KClass<DemoUserAccountEditorVM> {
        return DemoUserAccountEditorVM::class
    }

    override fun getVSClass(): KClass<DemoUserAccountEditorVS> {
        return DemoUserAccountEditorVS::class
    }

    override fun getVVClass(): KClass<DemoUserAccountEditorVV> {
        return DemoUserAccountEditorVV::class
    }

    override fun read(entity: DemoUserAccount, vmEntity: DemoUserAccountEditorVM, ctx: MutableMap<String, Any?>) {
        vmEntity.login  = entity.login
        vmEntity.name = entity.name
    }

    override fun getTitle(entity: DemoUserAccount, vmEntity: DemoUserAccountEditorVM, vsEntity: DemoUserAccountEditorVS, ctx: MutableMap<String, Any?>): String? {
        return entity.name
    }
}