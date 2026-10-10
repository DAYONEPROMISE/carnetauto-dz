package dz.carnautodz.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles ORDER BY id DESC")
    fun observeAll(): Flow<List<Vehicle>>

    @Query("SELECT * FROM vehicles WHERE id = :id")
    suspend fun getById(id: Long): Vehicle?

    @Insert
    suspend fun insert(vehicle: Vehicle): Long

    @Update
    suspend fun update(vehicle: Vehicle)

    @Delete
    suspend fun delete(vehicle: Vehicle)
}

@Dao
interface MaintenanceDao {
    @Query("SELECT * FROM maintenance WHERE vehicleId = :vehicleId ORDER BY kmAtService DESC")
    fun observeForVehicle(vehicleId: Long): Flow<List<MaintenanceRecord>>

    @Query("SELECT * FROM maintenance WHERE vehicleId = :vehicleId AND type = :type ORDER BY kmAtService DESC LIMIT 1")
    suspend fun lastOfType(vehicleId: Long, type: String): MaintenanceRecord?

    @Query("SELECT * FROM maintenance WHERE vehicleId = :vehicleId ORDER BY dateEpochDay DESC")
    suspend fun allForVehicle(vehicleId: Long): List<MaintenanceRecord>

    @Insert
    suspend fun insert(record: MaintenanceRecord): Long

    @Delete
    suspend fun delete(record: MaintenanceRecord)
}

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE vehicleId = :vehicleId ORDER BY dateEpochDay DESC")
    fun observeForVehicle(vehicleId: Long): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE vehicleId = :vehicleId ORDER BY dateEpochDay DESC")
    suspend fun allForVehicle(vehicleId: Long): List<Expense>

    @Insert
    suspend fun insert(expense: Expense): Long

    @Delete
    suspend fun delete(expense: Expense)
}
