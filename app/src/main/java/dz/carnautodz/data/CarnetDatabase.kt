package dz.carnautodz.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Vehicle::class, MaintenanceRecord::class, Expense::class],
    version = 1,
    exportSchema = false
)
abstract class CarnetDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun maintenanceDao(): MaintenanceDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var instance: CarnetDatabase? = null

        fun get(context: Context): CarnetDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    CarnetDatabase::class.java,
                    "carnetautodz.db"
                ).build().also { instance = it }
            }
    }
}
