/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.ui

import com.gridnine.jasmine.common.core.utils.TextUtils
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditorVM
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditorVS
import com.gridnine.jasmine.common.demo.model.ui.DemoUserAccountEditorVV
import com.gridnine.jasmine.common.standard.model.l10n.StandardL10nMessagesFactory
import com.gridnine.jasmine.server.core.ui.common.ObjectEditorHandler
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

    override fun fillSettings(entity: DemoUserAccount, vsEntity: DemoUserAccountEditorVS, vmEntity: DemoUserAccountEditorVM, ctx: MutableMap<String, Any?>) {
        vsEntity.setLogin { notEditable = true}
    }
    override fun read(entity: DemoUserAccount, vmEntity: DemoUserAccountEditorVM, ctx: MutableMap<String, Any?>) {
        vmEntity.login  = entity.login
        vmEntity.name = entity.name
    }

    override fun getTitle(entity: DemoUserAccount, vmEntity: DemoUserAccountEditorVM, vsEntity: DemoUserAccountEditorVS, ctx: MutableMap<String, Any?>): String? {
        return entity.name
    }

    override fun write(entity: DemoUserAccount, vmEntity: DemoUserAccountEditorVM, ctx: MutableMap<String, Any?>) {
        entity.login = vmEntity.login
        entity.name = vmEntity.name
    }

    override fun validate(vmEntity: DemoUserAccountEditorVM, vvEntity: DemoUserAccountEditorVV, ctx: MutableMap<String, Any?>) {
        if(TextUtils.isBlank(vmEntity.login)){
            vvEntity.login = StandardL10nMessagesFactory.Empty_field()
        }
        if(TextUtils.isBlank(vmEntity.name)){
            vvEntity.name = StandardL10nMessagesFactory.Empty_field()
        }
    }
}