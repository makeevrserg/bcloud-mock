package com.flipperdevices.bcloudmock.route.auth.presentation

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.model.ErrorResponseModel
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode
import kotlin.reflect.typeOf

object AuthSwagger {
    fun RouteConfig.createSwaggerDefinition() {
        description = "Remember user"
        request {
            headerParameter("Authorization", typeOf<String>()) {
                description = "Authorization token"
            }
            headerParameter("Authorization-Firebase", typeOf<String?>()) {
                description = "Authorization token for firebase"
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "New user data remembered"
                body<BSBApiUserObject> {
                    description = "User data object"
                }
            }

            default {
                description = "Could not process request"
                body<ErrorResponseModel> {
                    description = "Some error happened"
                }
            }
        }
    }
}
