package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.ReferralFriend
import com.example.data.model.TaskItem
import com.example.data.model.TransactionRecord
import com.example.data.model.UserProfile
import com.example.data.model.WalletConnections

@Database(
    entities = [
        UserProfile::class,
        WalletConnections::class,
        TaskItem::class,
        TransactionRecord::class,
        ReferralFriend::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DogsDatabase : RoomDatabase() {
    abstract fun dogsDao(): DogsDao

    companion object {
        @Volatile
        private var INSTANCE: DogsDatabase? = null

        fun getDatabase(context: Context): DogsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogsDatabase::class.java,
                    "dogs_crypto_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
