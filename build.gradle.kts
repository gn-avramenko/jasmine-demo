import com.gridnine.jasmine.gradle.plugin.jasmine

buildscript {
    dependencies{
        "classpath"(files("submodules/jasmine/build-scripts/build/classes/kotlin/main"))
        "classpath"(files("submodules/jasmine/lib/spf-1.0.jar"))
    }
}

apply<com.gridnine.jasmine.gradle.plugin.JasmineConfigPlugin>()

jasmine {
    kotlinVersion = "1.3.71"
    plugins("submodules/jasmine/plugins") {
        plugin("com.gridnine.jasmine.server.core")
    }
}

apply<com.gridnine.jasmine.gradle.plugin.JasminePlugin>()

