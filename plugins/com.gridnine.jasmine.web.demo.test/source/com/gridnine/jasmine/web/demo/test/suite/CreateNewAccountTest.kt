package com.gridnine.jasmine.web.demo.test.suite

import com.gridnine.jasmine.web.core.test.ext.Assert
import com.gridnine.jasmine.web.core.test.ext.describe
import com.gridnine.jasmine.web.core.test.ext.it
import com.gridnine.jasmine.web.core.ui.debugger

class CreateNewAccountTest {
    fun createNewAccountTest() {
        val assert = com.gridnine.jasmine.web.core.test.ext.require("assert") as Assert
        describe("work-with-account") {
            it("test-create-new-account") {
                console.log("new account created")
                assert.ok(true)
            }
        }
    }
}