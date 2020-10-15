/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.user

import com.gridnine.jasmine.server.demo.model.domain.DemoUserAccountIndexJS
import com.gridnine.jasmine.web.core.ui.AutocompleteHandler
import com.gridnine.jasmine.web.core.ui.BaseObjectHandler

class DemoUserAccountObjectHandler : BaseObjectHandler(DemoUserAccountIndexJS.objectId){
    override fun createAutocompleteHandler(): AutocompleteHandler {
        return AutocompleteHandler.createMetadataBasedAutocompleteHandler(objectId)
    }

    override fun getId(): String {
        return DemoUserAccountIndexJS.objectId
    }

}