package dz.carnautodz.logic

import dz.carnautodz.data.MaintenanceRecord
import dz.carnautodz.data.MaintenanceType

/** Reminder status of one maintenance kind for one vehicle. */
enum class ReminderStatus { OK, SOON, OVERDUE, NEVER }

data class Reminder(
    val type: String,
    val lastKm: Int?,
    val nextDueKm: Int?,
    val remainingKm: Int?,
    val status: ReminderStatus
)

object Reminders {
    /** Km before due date at which a reminder becomes SOON. */
    const val SOON_THRESHOLD_KM = 1_000

    /** Pure function: compute the reminder for one maintenance kind. */
    fun forType(
        type: String,
        currentKm: Int,
        history: List<MaintenanceRecord>,
        intervalKm: Int = MaintenanceType.defaultIntervalKm(type)
    ): Reminder {
        if (intervalKm <= 0) return Reminder(type, null, null, null, ReminderStatus.NEVER)
        val last = history.filter { it.type == type }.maxByOrNull { it.kmAtService }
            ?: return Reminder(type, null, currentKm + intervalKm, intervalKm, ReminderStatus.NEVER)
        val nextDue = last.kmAtService + intervalKm
        val remaining = nextDue - currentKm
        val status = when {
            remaining <= 0 -> ReminderStatus.OVERDUE
            remaining <= SOON_THRESHOLD_KM -> ReminderStatus.SOON
            else -> ReminderStatus.OK
        }
        return Reminder(type, last.kmAtService, nextDue, remaining, status)
    }

    /** Pure function: reminders for every known maintenance kind, worst first. */
    fun forVehicle(currentKm: Int, history: List<MaintenanceRecord>): List<Reminder> =
        MaintenanceType.all
            .map { forType(it, currentKm, history) }
            .sortedBy { it.status.ordinal }
            .sortedWith(compareBy({ rank(it.status) }, { it.remainingKm ?: Int.MAX_VALUE }))

    private fun rank(s: ReminderStatus): Int = when (s) {
        ReminderStatus.OVERDUE -> 0
        ReminderStatus.SOON -> 1
        ReminderStatus.NEVER -> 2
        ReminderStatus.OK -> 3
    }
}
