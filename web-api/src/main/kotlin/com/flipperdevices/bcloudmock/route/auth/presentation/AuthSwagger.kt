package com.flipperdevices.bcloudmock.route.auth.presentation

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.model.AuthRequest
import com.flipperdevices.bcloudmock.model.ErrorResponseModel
import io.github.smiley4.ktorswaggerui.dsl.routes.OpenApiRoute
import io.ktor.http.HttpStatusCode

object AuthSwagger {
    fun OpenApiRoute.createSwaggerDefinition() {
        description = "Remember user"
        request {
            body<AuthRequest> {
                description = "Request with user data"
                required = true
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
