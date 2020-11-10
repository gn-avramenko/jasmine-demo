/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.rest

import com.gridnine.jasmine.server.core.rest.RestHandler
import com.gridnine.jasmine.server.core.rest.RestOperationContext
import com.gridnine.jasmine.server.core.storage.Storage
import com.gridnine.jasmine.server.core.utils.TextUtils
import com.gridnine.jasmine.server.core.utils.ValidationUtils
import com.gridnine.jasmine.server.demo.DemoServerMessagesFactory
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountIndex
import com.gridnine.jasmine.server.standard.StandardServerMessagesFactory
import com.gridnine.jasmine.web.demo.DemoUserNewAccountEditorVV

class DemoCreateUserRestHandler : RestHandler<DemoCreateUserRequest, DemoCreateUserResponse>{
    override fun service(request: DemoCreateUserRequest, ctx: RestOperationContext): DemoCreateUserResponse {
        val result = DemoCreateUserResponse()
        val validation = DemoUserNewAccountEditorVV()
        if(TextUtils.isBlank(request.vm.login)){
            validation.login = StandardServerMessagesFactory.EMPTY_FIELD().toString()
        }
        if(TextUtils.isBlank(request.vm.password)){
            validation.password = StandardServerMessagesFactory.EMPTY_FIELD().toString()
        }
        if(TextUtils.isBlank(request.vm.retypePassword)){
            validation.retypePassword = StandardServerMessagesFactory.EMPTY_FIELD().toString()
        }
        if(!TextUtils.isBlank(request.vm.login)){
            if(Storage.get().findUniqueDocumentReference(DemoUserAccountIndex::class, DemoUserAccountIndex.loginProperty, request.vm.login) != null){
                validation.login = DemoServerMessagesFactory.LOGIN_ALREADY_EXIST().toString()
            }
        }
        if(!TextUtils.isBlank(request.vm.password) && !TextUtils.isBlank(request.vm.retypePassword) && request.vm.password != request.vm.retypePassword){
            validation.retypePassword = DemoServerMessagesFactory.PASSWORDS_MUST_BE_EQUALS().toString()
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