import com.gridnine.jasmine.gradle.plugin.jasmine

buildscript {
    dependencies{
        "classpath"(files("submodules/jasmine/lib/spf-1.0.jar"))
        "classpath"(files("gradle/jasmine-gradle-plugin.jar"))
    }
}
apply<com.gridnine.jasmine.gradle.plugin.JasmineConfigPlugin>()


jasmine {
    kotlinVersion = "1.4.10"
    libRelativePath = "submodules/jasmine/lib"
    plugins("submodules/jasmine/plugins") {
        plugin("com.gridnine.jasmine.common.core")
        plugin("com.gridnine.jasmine.common.spf")
        plugin("com.gridnine.jasmine.common.core.test")
        plugin("com.gridnine.jasmine.server.core")
        plugin("com.gridnine.jasmine.server.db.h2")
        plugin("com.gridnine.jasmine.server.core.test")

    }
}

repositories{
    mavenCentral()
    jcenter()
}

apply<com.gridnine.jasmine.gradle.plugin.JasminePlugin>()


// task("deploy-locally", DeployApplicationTask::class){
//     group = "jenkins"
//     shouldRunAfter("jenkins-dist")
//     host = "localhost"
//     port = 21567
// }


//tasks.create("deploy-test", Deplo)

//project.configurations.create("compile")
//
//dependencies{
//    "compile"(files("submodules/jasmine/lib/spf-1.0.jar"))
//}

