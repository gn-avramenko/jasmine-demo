import com.gridnine.jasmine.gradle.plugin.jasmine
//plugins {
//    kotlin("js") version "1.4.10"
//}
buildscript {
    dependencies{
        "classpath"(files("submodules/jasmine/build-scripts/build/classes/kotlin/main"))
        "classpath"(files("submodules/jasmine/lib/spf-1.0.jar"))
    }
}

apply<com.gridnine.jasmine.gradle.plugin.JasmineConfigPlugin>()

jasmine {
    kotlinVersion = "1.4.10"
    libRelativePath = "submodules/jasmine/lib"
    plugins("submodules/jasmine/plugins") {
        plugin("com.gridnine.jasmine.server.core")
        plugin("com.gridnine.jasmine.server.spf")
        plugin("com.gridnine.jasmine.server.core.test")
        plugin("com.gridnine.jasmine.server.db.h2")
        plugin("com.gridnine.jasmine.server.standard")
        plugin("com.gridnine.jasmine.web.core")
        plugin("com.gridnine.jasmine.web.easyui")

    }
    plugins("plugins"){
        plugin("com.gridnine.jasmine.server.demo")
        plugin("com.gridnine.jasmine.web.demo")
    }
}

apply<com.gridnine.jasmine.gradle.plugin.JasminePlugin>()

repositories{
    mavenCentral()
}

project.configurations.create("compile")
dependencies{
    "compile"(files("submodules/jasmine/lib/spf-1.0.jar"))
}


//kotlin {
//    js {
//        sourceSets["main"].apply {
//            kotlin.srcDir("submodules/jasmine/plugins/com.gridnine.jasmine.web.core/source")
//            kotlin.srcDir("submodules/jasmine/plugins/com.gridnine.jasmine.web.core/source-gen")
//            kotlin.srcDir("submodules/jasmine/plugins/com.gridnine.jasmine.web.easyui/source")
//            kotlin.srcDir("plugins/com.gridnine.jasmine.web.demo/source")
//            kotlin.srcDir("plugins/com.gridnine.jasmine.web.demo/source-gen")
//        }
//        // To build distributions and run tests for browser or Node.js use one or both of:
//        browser()
//    }
//}