package dz.carnautodz.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dz.carnautodz.data.CarnetDatabase
import dz.carnautodz.data.Expense
import dz.carnautodz.data.MaintenanceRecord
import dz.carnautodz.data.Vehicle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class CarnetViewModel(app: Application) : AndroidViewModel(app) {
    private val db = CarnetDatabase.get(app)
    private val vehicles = db.vehicleDao()
    private val maintenance = db.maintenanceDao()
    private val expenses = db.expenseDao()

    val allVehicles: StateFlow<List<Vehicle>> =
        vehicles.observeAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedVehicleId = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val maintenanceHistory: StateFlow<List<MaintenanceRecord>> =
        selectedVehicleId.flatMapLatest { id ->
            if (id == null) flowOf(emptyList()) else maintenance.observeForVehicle(id)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val expenseHistory: StateFlow<List<Expense>> =
        selectedVehicleId.flatMapLatest { id ->
            if (id == null) flowOf(emptyList()) else expenses.observeForVehicle(id)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectedVehicle(): Vehicle? = allVehicles.value.firstOrNull { it.id == selectedVehicleId.value }

    fun selectVehicle(id: Long?) {
        selectedVehicleId.value = id
    }

    fun addVehicle(name: String, plate: String, km: Int) = viewModelScope.launch {
        val id = vehicles.insert(Vehicle(name = name, plate = plate, currentKm = km))
        if (selectedVehicleId.value == null) selectedVehicleId.value = id
    }

    fun updateKm(vehicle: Vehicle, km: Int) = viewModelScope.launch {
        vehicles.update(vehicle.copy(currentKm = km))
    }

    fun deleteVehicle(vehicle: Vehicle) = viewModelScope.launch {
        vehicles.delete(vehicle)
        if (selectedVehicleId.value == vehicle.id) {
            selectedVehicleId.value = allVehicles.value.firstOrNull { it.id != vehicle.id }?.id
        }
    }

    fun addMaintenance(type: String, km: Int, note: String) = viewModelScope.launch {
        val vid = selectedVehicleId.value ?: return@launch
        maintenance.insert(
            MaintenanceRecord(
                vehicleId = vid,
                type = type,
                kmAtService = km,
                dateEpochDay = LocalDate.now().toEpochDay(),
                note = note
            )
        )
        // Keep the odometer in sync when the service km is ahead.
        vehicles.getById(vid)?.let { v ->
            if (km > v.currentKm) vehicles.update(v.copy(currentKm = km))
        }
    }

    fun deleteMaintenance(record: MaintenanceRecord) = viewModelScope.launch {
        maintenance.delete(record)
    }

    fun addExpense(amountDzd: Long, category: String, note: String) = viewModelScope.launch {
        val vid = selectedVehicleId.value ?: return@launch
        expenses.insert(
            Expense(
                vehicleId = vid,
                amountDzd = amountDzd,
                category = category,
                dateEpochDay = LocalDate.now().toEpochDay(),
                note = note
            )
        )
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        expenses.delete(expense)
    }
}
