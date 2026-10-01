package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ReferralFriend
import com.example.data.model.TaskItem
import com.example.data.model.TransactionRecord
import com.example.data.model.UserProfile
import com.example.data.model.WalletConnections
import kotlinx.coroutines.flow.Flow

@Dao
interface DogsDao {

    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfile)

    @Query("SELECT * FROM wallet_connections WHERE id = 1")
    fun getWalletConnections(): Flow<WalletConnections?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateWallet(wallet: WalletConnections)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskItem>)

    @Update
    suspend fun updateTask(task: TaskItem)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionRecord)

    @Query("SELECT * FROM referrals")
    fun getAllReferrals(): Flow<List<ReferralFriend>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferrals(referrals: List<ReferralFriend>)
}
