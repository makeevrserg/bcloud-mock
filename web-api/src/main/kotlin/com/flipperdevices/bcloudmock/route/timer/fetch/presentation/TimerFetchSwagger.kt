package com.flipperdevices.bcloudmock.route.timer.fetch.presentation

import com.flipperdevices.bcloudmock.model.ErrorResponseModel
import com.flipperdevices.bcloudmock.model.TimerTimestamp
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.ktor.http.HttpStatusCode
import kotlin.reflect.typeOf

object TimerFetchSwagger {
    fun RouteConfig.createSwaggerDefinition() {
        description = "Read last timer timestamp"
        request {
            headerParameter("Authorization", typeOf<String>()) {
                description = "Authorization token"
            }
        }
        response {
            HttpStatusCode.OK to {
                description = "Timer timestamp saved"
                body<TimerTimestamp> {
                    description = "Last remembered timestamp"
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
