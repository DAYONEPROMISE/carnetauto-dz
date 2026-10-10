package dz.carnautodz.logic

import dz.carnautodz.data.MaintenanceRecord
import dz.carnautodz.data.MaintenanceType
import org.junit.Assert.assertEquals
import org.junit.Test

class RemindersTest {

    private fun record(type: String, km: Int) =
        MaintenanceRecord(vehicleId = 1, type = type, kmAtService = km, dateEpochDay = 20000)

    @Test
    fun `never serviced returns NEVER with next due at current plus interval`() {
        val r = Reminders.forType(MaintenanceType.VIDANGE, 50_000, emptyList())
        assertEquals(ReminderStatus.NEVER, r.status)
        assertEquals(60_000, r.nextDueKm)
    }

    @Test
    fun `overdue when current km past due`() {
        val r = Reminders.forType(MaintenanceType.VIDANGE, 61_000, listOf(record(MaintenanceType.VIDANGE, 50_000)))
        assertEquals(ReminderStatus.OVERDUE, r.status)
        assertEquals(60_000, r.nextDueKm)
        assertEquals(-1_000, r.remainingKm)
    }

    @Test
    fun `soon within 1000 km`() {
        val r = Reminders.forType(MaintenanceType.VIDANGE, 59_500, listOf(record(MaintenanceType.VIDANGE, 50_000)))
        assertEquals(ReminderStatus.SOON, r.status)
        assertEquals(500, r.remainingKm)
    }

    @Test
    fun `ok when far from due`() {
        val r = Reminders.forType(MaintenanceType.VIDANGE, 52_000, listOf(record(MaintenanceType.VIDANGE, 50_000)))
        assertEquals(ReminderStatus.OK, r.status)
        assertEquals(8_000, r.remainingKm)
    }

    @Test
    fun `uses latest service when several exist`() {
        val history = listOf(
            record(MaintenanceType.COURROIE, 60_000),
            record(MaintenanceType.COURROIE, 120_000)
        )
        val r = Reminders.forType(MaintenanceType.COURROIE, 150_000, history)
        assertEquals(180_000, r.nextDueKm)
        assertEquals(ReminderStatus.OK, r.status)
    }

    @Test
    fun `autre type with no interval is NEVER`() {
        val r = Reminders.forType(MaintenanceType.AUTRE, 10_000, emptyList())
        assertEquals(ReminderStatus.NEVER, r.status)
        assertEquals(null, r.nextDueKm)
    }

    @Test
    fun `forVehicle returns worst first`() {
        val history = listOf(record(MaintenanceType.VIDANGE, 40_000)) // due 50k, current 55k -> overdue
        val list = Reminders.forVehicle(55_000, history)
        assertEquals(MaintenanceType.VIDANGE, list.first().type)
        assertEquals(ReminderStatus.OVERDUE, list.first().status)
    }

    @Test
    fun `default intervals match spec`() {
        assertEquals(10_000, MaintenanceType.defaultIntervalKm(MaintenanceType.VIDANGE))
        assertEquals(60_000, MaintenanceType.defaultIntervalKm(MaintenanceType.COURROIE))
        assertEquals(40_000, MaintenanceType.defaultIntervalKm(MaintenanceType.PNEUS))
    }
}
