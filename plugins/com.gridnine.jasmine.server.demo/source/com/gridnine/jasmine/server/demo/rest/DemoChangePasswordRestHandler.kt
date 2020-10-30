/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.rest

import com.gridnine.jasmine.server.core.rest.RestHandler
import com.gridnine.jasmine.server.core.rest.RestOperationContext
import com.gridnine.jasmine.server.core.storage.Storage
import com.gridnine.jasmine.server.core.utils.TextUtils
import com.gridnine.jasmine.server.demo.DemoServerMessagesFactory
import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.server.standard.StandardServerMessagesFactory
import com.gridnine.jasmine.server.standard.rest.MessageType
import com.gridnine.jasmine.server.standard.rest.StandardRestHelper
import com.gridnine.jasmine.web.demo.DemoChangePasswordEditorVV

class DemoChangePasswordRestHandler : RestHandler<DemoChangePasswordRequest, DemoChangePasswordResponse>{
    override fun service(request: DemoChangePasswordRequest, ctx: RestOperationContext): DemoChangePasswordResponse {
        if(TextUtils.isBlank(request.vm.password) || request.vm.password != request.vm.retypePassword){
            val result = DemoChangePasswordResponse()
            result.message = StandardRestHelper.createMessage(MessageType.ERROR, StandardServerMessagesFactory.VALIDATION_ERRORS_EXIST().toString())
            result.success = false
            val vv = DemoChangePasswordEditorVV()
            result.vv = vv
            if(TextUtils.isBlank(request.vm.password) ){
                vv.password = StandardServerMessagesFactory.EMPTY_FIELD().toString()
            } else {
                vv.retypePassword = DemoServerMessagesFactory.PASSWORDS_MUST_BE_EQUALS().toString()
            }
            return result
        }
        val userAccount = Storage.get().loadDocument(DemoUserAccount::class, request.userAccountUid, true)!!
        userAccount.password = request.vm.password
        Storage.get().saveDocument(userAccount, "change-password")
        val result = DemoChangePasswordResponse()
        result.success = true
        result.message = StandardRestHelper.createMessage(MessageType.MESSAGE, StandardServerMessagesFactory.OPERATION_COMPLETED().toString())
        return result
    }

}
