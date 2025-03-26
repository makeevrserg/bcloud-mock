package com.flipperdevices.bcloudmock.dao.di

import com.flipperdevices.bcloudmock.busycloud.di.BusyCloudModule
import com.flipperdevices.bcloudmock.core.di.CoreModule
import com.flipperdevices.bcloudmock.dao.api.Dao
import com.flipperdevices.bcloudmock.dao.api.DaoImpl
import com.flipperdevices.bcloudmock.data.di.DataModule

class DaoModule(
    private val databaseModule: DataModule,
    private val busyCloudModule: BusyCloudModule,
    private val coreModule: CoreModule
) {
    val dao: Dao = DaoImpl(
        busyCloudApi = busyCloudModule.api,
        databaseFlow = databaseModule.database,
        stringFormat = coreModule.json
    )
}
