package com.syrous.cinemabuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.cinemabuddy.data.local.model.NotificationDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNotification(notificationDBModel: NotificationDBModel)

    @Query("SELECT * FROM notificationdbmodel WHERE timestamp > :minTime AND timestamp <= :maxTime AND isNotified = 0 AND productionCompanyId = :productionCompanyId")
    suspend fun getUnNotifiedNotificationWithInTimeRangeForAComp(minTime: Long, maxTime: Long, productionCompanyId: Int): List<NotificationDBModel>?

    @Query("UPDATE notificationdbmodel SET isNotified = 1 WHERE id = :notificationId")
    suspend fun updateNotificationNotified(notificationId: Int)

}