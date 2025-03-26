package com.flipperdevices.bcloudmock.route.timer.remember.presentation

import com.flipperdevices.bcloudmock.model.ErrorResponseModel
import com.flipperdevices.bcloudmock.model.TimerTimestamp
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode
import kotlin.reflect.typeOf

object TimerRememberSwagger {
    fun RouteConfig.createSwaggerDefinition() {
        description = "Save current timer timestamp"
        request {
            headerParameter("Authorization", typeOf<String>()) {
                description = "Authorization token"
            }
            body<TimerTimestamp> {
                description = "Current timer timestamp"
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Timer timestamp saved"
                body<TimerTimestamp> {
                    description = "Resolved timestamp. May be not the same was saved."
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
