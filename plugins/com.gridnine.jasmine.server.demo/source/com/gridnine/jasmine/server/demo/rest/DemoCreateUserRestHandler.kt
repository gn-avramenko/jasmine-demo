/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.rest

import com.gridnine.jasmine.common.core.storage.Storage
import com.gridnine.jasmine.common.core.utils.TextUtils
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccountIndex
import com.gridnine.jasmine.common.demo.model.ui.DemoUserNewAccountEditorVV
import com.gridnine.jasmine.common.demo.rest.DemoCreateUserRequest
import com.gridnine.jasmine.common.demo.rest.DemoCreateUserResponse
import com.gridnine.jasmine.common.standard.model.l10n.StandardL10nMessagesFactory
import com.gridnine.jasmine.server.core.rest.RestHandler
import com.gridnine.jasmine.server.core.rest.RestOperationContext
import com.gridnine.jasmine.server.standard.helpers.ValidationUtils

class DemoCreateUserRestHandler :RestHandler<DemoCreateUserRequest,DemoCreateUserResponse>{
    override fun service(request: DemoCreateUserRequest, ctx: RestOperationContext): DemoCreateUserResponse {
        val result = DemoCreateUserResponse()
        val validation = DemoUserNewAccountEditorVV()
        if(TextUtils.isBlank(request.vm.login)){
            validation.login = StandardL10nMessagesFactory.Empty_field()
        }
        if(TextUtils.isBlank(request.vm.password)){
            validation.password =  StandardL10nMessagesFactory.Empty_field()
        }
        if(TextUtils.isBlank(request.vm.retypePassword)){
            validation.retypePassword =  StandardL10nMessagesFactory.Empty_field()
        }
        if(!TextUtils.isBlank(request.vm.login)){
            if(Storage.get().findUniqueDocumentReference(DemoUserAccountIndex::class, DemoUserAccountIndex.loginProperty, request.vm.login) != null){
                validation.login = "Логин уже существует"
            }
        }
        if(!TextUtils.isBlank(request.vm.password) && !TextUtils.isBlank(request.vm.retypePassword) && request.vm.password != request.vm.retypePassword){
            validation.retypePassword = "Пароли должны совпадать"
        }
        if(ValidationUtils.hasValidationErrors(validation)){
            result.vv = validation
            return result
        }
        val account = DemoUserAccount()
        account.login = request.vm.login
        account.password = request.vm.password
        Storage.get().saveDocument(account)
        result.objectUid = account.uid
        return result
    }
}