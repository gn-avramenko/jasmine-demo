plugins {
    kotlin("js") version "1.4.10"
}


repositories{
    mavenCentral()
}

kotlin {
    js {
        sourceSets["main"].apply {
            kotlin.srcDir("submodules/jasmine/plugins/com.gridnine.jasmine.web.core/source")
            kotlin.srcDir("submodules/jasmine/plugins/com.gridnine.jasmine.web.core/source-gen")
            kotlin.srcDir("submodules/jasmine/plugins/com.gridnine.jasmine.web.easyui/source")
            kotlin.srcDir("plugins/com.gridnine.jasmine.web.demo/source")
            kotlin.srcDir("plugins/com.gridnine.jasmine.web.demo/source-gen")
        }
        // To build distributions and run tests for browser or Node.js use one or both of:
        browser{
            distribution {
                directory = File("$projectDir/temp/js/output/")
            }
        }
    }
}