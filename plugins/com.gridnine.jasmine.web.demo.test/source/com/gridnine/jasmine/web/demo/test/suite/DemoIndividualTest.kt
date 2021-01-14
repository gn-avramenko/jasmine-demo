package com.gridnine.jasmine.web.demo.test.suite

import com.gridnine.jasmine.web.core.test.ext.describe

class DemoIndividualTest{
    fun describeSuite(){
        describe("demo-individual-test") {
            DemoTestSuite().buildBefore()
            CreateNewAccountTest().createNewAccountTest()
        }
    }
}