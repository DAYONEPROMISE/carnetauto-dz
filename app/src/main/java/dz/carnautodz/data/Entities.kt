package dz.carnautodz.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/** A vehicle tracked in the logbook. currentKm is the odometer reading in km. */
@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val plate: String = "",
    val currentKm: Int = 0
)

/** Maintenance job kinds with their default service intervals in km. */
object MaintenanceType {
    const val VIDANGE = "vidange"
    const val COURROIE = "courroie"
    const val PNEUS = "pneus"
    const val AUTRE = "autre"

    fun defaultIntervalKm(type: String): Int = when (type) {
        VIDANGE -> 10_000
        COURROIE -> 60_000
        PNEUS -> 40_000
        else -> 0
    }

    fun labelFr(type: String): String = when (type) {
        VIDANGE -> "Vidange"
        COURROIE -> "Courroie"
        PNEUS -> "Pneus"
        else -> "Autre"
    }

    val all = listOf(VIDANGE, COURROIE, PNEUS, AUTRE)
}

/** One performed maintenance job on a vehicle. */
@Entity(
    tableName = "maintenance",
    foreignKeys = [ForeignKey(
        entity = Vehicle::class,
        parentColumns = ["id"],
        childColumns = ["vehicleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("vehicleId")]
)
data class MaintenanceRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val vehicleId: Long,
    val type: String,
    val kmAtService: Int,
    val dateEpochDay: Long,
    val note: String = ""
)

/** Expense categories tracked in Algerian dinars (DZD). */
object ExpenseCategory {
    const val CARBURANT = "carburant"
    const val ENTRETIEN = "entretien"
    const val PIECES = "pieces"
    const val ASSURANCE = "assurance"
    const val AUTRE = "autre"

    fun labelFr(cat: String): String = when (cat) {
        CARBURANT -> "Carburant"
        ENTRETIEN -> "Entretien"
        PIECES -> "Pièces"
        ASSURANCE -> "Assurance"
        else -> "Autre"
    }

    val all = listOf(CARBURANT, ENTRETIEN, PIECES, ASSURANCE, AUTRE)
}

/** One expense. Amounts are stored as whole dinars (Long) — centimes are unused in DZ. */
@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(
        entity = Vehicle::class,
        parentColumns = ["id"],
        childColumns = ["vehicleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("vehicleId")]
)
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val vehicleId: Long,
    val amountDzd: Long,
    val category: String,
    val dateEpochDay: Long,
    val note: String = ""
)
