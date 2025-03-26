package com.flipperdevices.bcloudmock.plugins

import com.flipperdevices.bcloudmock.buildkonfig.BuildKonfig
import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.ExampleEncoder
import io.github.smiley4.ktoropenapi.config.SchemaGenerator
import io.github.smiley4.ktoropenapi.openApi
import io.github.smiley4.ktorswaggerui.swaggerUI
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

fun Application.configureSwagger() {
    val json = Json {
        prettyPrint = true
        encodeDefaults = true
        explicitNulls = false
        namingStrategy = JsonNamingStrategy.SnakeCase
    }

    routing {
        // Create a route for the openapi-spec file.
        // This route will not be included in the spec.
        route("api.json") {
            openApi()
        }
        // Create a route for the swagger-ui using the openapi-spec at "/api.json".
        // This route will not be included in the spec.
        route("swagger") {
            swaggerUI(openApiUrl = "/api.json")
        }
    }

    install(OpenApi) {
        schemas {
            // configure the schema generator to use the default kotlinx-serializer
            generator = SchemaGenerator.kotlinx(json)

//            generator = { type ->
//                type
//                    // process type using kotlinx-serialization instead of reflection
//                    // requires additional dependency
//                    // "io.github.smiley4:schema-kenerator-kotlinx-serialization:<VERSION>"
//                    // see https://github.com/SMILEY4/schema-kenerator for more information
//                    .analyzeTypeUsingKotlinxSerialization()
//                    .addJsonClassDiscriminatorProperty()
//                    .addMissingSupertypeSubtypeRelations()
//                    .handleNameAnnotation()
//                    .generateSwaggerSchema()
//                    .withTitle(TitleType.SIMPLE)
//                    .handleCoreAnnotations()
//                    .compileReferencingRoot()
//            }
        }
        examples {
            exampleEncoder = ExampleEncoder.kotlinx(json)
        }
        info {
            title = BuildKonfig.PROJECT_NAME
            version = BuildKonfig.VERSION_NAME
            summary = "API for Flipper IFR"
            description = BuildKonfig.PROJECT_DESC
            termsOfService = "https://somesite.com"
            contact {
                name = "Some Name"
                url = "https://someurl.corp"
                email = "somemail@mail.mail"
            }
        }
        server {
            description = "Web-Api server"
            variable("version") {
                default = BuildKonfig.VERSION_NAME
                enum = listOf(BuildKonfig.VERSION_NAME)
            }
        }
    }
}
