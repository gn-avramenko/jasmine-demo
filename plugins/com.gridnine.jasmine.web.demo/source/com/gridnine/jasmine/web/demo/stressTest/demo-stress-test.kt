/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.web.demo.stressTest

import com.gridnine.jasmine.web.core.ui.components.SimpleActionHandler
import com.gridnine.jasmine.web.standard.mainframe.MainFrame
import kotlinx.browser.window
import kotlinx.coroutines.delay

class DemoStartStressTestActionHandler:SimpleActionHandler{
    override suspend fun invoke() {
        console.log("test started")
        val item = window.asDynamic().testItem
        console.log(item)
        val mainFrame = MainFrame.get()
        window.asDynamic().stopStressTest = false
        while (window.asDynamic().stopStressTest != true){
            mainFrame.openTab(item)
            delay(1500)
            val lastTab = mainFrame.tabs.getTabs().last()
            mainFrame.tabs.removeTab(lastTab.id)
            delay(1500)
        }
        console.log("test finished")
    }
}

class DemoFinishStressTestActionHandler:SimpleActionHandler{
    override suspend fun invoke() {
        window.asDynamic().stopStressTest = true
        console.log("test is being finished")
    }
}