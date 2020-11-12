/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.admin.complex

import com.gridnine.jasmine.server.core.model.l10n.L10nMetaRegistryJS
import com.gridnine.jasmine.web.core.ui.WebComponent
import com.gridnine.jasmine.web.core.ui.widgets.TileSpaceWidget
import com.gridnine.jasmine.web.demo.DemoComplexDocumentTileSpaceVMJS
import com.gridnine.jasmine.web.demo.DemoComplexDocumentTileSpaceVSJS
import com.gridnine.jasmine.web.demo.DemoComplexDocumentTileSpaceVVJS

class DemoComplexDocumentWebEditor(aParent: WebComponent): TileSpaceWidget<DemoComplexDocumentTileSpaceVMJS, DemoComplexDocumentTileSpaceVSJS, DemoComplexDocumentTileSpaceVVJS>(aParent, {widget->
    width = "100%"
    height = "100%"
    val overviewEditor = DemoComplexDocumentOverviewWebEditor(widget)
    overview(L10nMetaRegistryJS.get().messages["com.gridnine.jasmine.web.demo.DemoComplexDocumentTileSpace"]!!["overview"] ?: error(""), overviewEditor)
})



