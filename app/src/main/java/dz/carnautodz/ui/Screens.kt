package dz.carnautodz.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dz.carnautodz.data.ExpenseCategory
import dz.carnautodz.data.MaintenanceType
import dz.carnautodz.data.Vehicle
import dz.carnautodz.logic.Expenses
import dz.carnautodz.logic.ReminderStatus
import dz.carnautodz.logic.Reminders
import java.time.LocalDate

// ---------- Dashboard (reminders at a glance) ----------

@Composable
fun DashboardScreen(vm: CarnetViewModel) {
    val vehicle = vm.selectedVehicle()
    val history by vm.maintenanceHistory.collectAsState()
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        VehiclePicker(vm)
        Spacer(Modifier.height(12.dp))
        if (vehicle == null) {
            Text("Ajoutez un véhicule pour commencer.", style = MaterialTheme.typography.bodyLarge)
            return@Column
        }
        Text("Échéances — ${vehicle.name}", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        val reminders = remember(vehicle.currentKm, history) {
            Reminders.forVehicle(vehicle.currentKm, history)
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(reminders) { r ->
                val color = when (r.status) {
                    ReminderStatus.OVERDUE -> MaterialTheme.colorScheme.errorContainer
                    ReminderStatus.SOON -> MaterialTheme.colorScheme.tertiaryContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
                Card(colors = CardDefaults.cardColors(containerColor = color), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(MaintenanceType.labelFr(r.type), fontWeight = FontWeight.Bold)
                        Text(
                            when (r.status) {
                                ReminderStatus.OVERDUE -> "Dépassé de ${-(r.remainingKm ?: 0)} km — dû à ${r.nextDueKm} km"
                                ReminderStatus.SOON -> "Dû dans ${r.remainingKm} km (à ${r.nextDueKm} km)"
                                ReminderStatus.NEVER -> "Jamais enregistré — prévoir vers ${r.nextDueKm} km"
                                ReminderStatus.OK -> "OK — prochain à ${r.nextDueKm} km"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VehiclePicker(vm: CarnetViewModel) {
    val vehicles by vm.allVehicles.collectAsState()
    val selected = vm.selectedVehicle()
    var expanded by remember { mutableStateOf(false) }
    @OptIn(ExperimentalMaterial3Api::class)
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selected?.let { "${it.name} — ${it.currentKm} km" } ?: "Choisir un véhicule",
            onValueChange = {},
            readOnly = true,
            label = { Text("Véhicule") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            vehicles.forEach { v ->
                DropdownMenuItem(
                    text = { Text("${v.name} — ${v.currentKm} km") },
                    onClick = { vm.selectVehicle(v.id); expanded = false }
                )
            }
        }
    }
}

// ---------- Vehicles ----------

@Composable
fun VehiclesScreen(vm: CarnetViewModel) {
    val vehicles by vm.allVehicles.collectAsState()
    var showAdd by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var plate by remember { mutableStateOf("") }
    var km by remember { mutableStateOf("") }
    var editKmFor by remember { mutableStateOf<Vehicle?>(null) }
    var newKm by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Véhicules", style = MaterialTheme.typography.titleLarge)
            Button(onClick = { showAdd = true }) { Text("+ Ajouter") }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(vehicles, key = { it.id }) { v ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { vm.selectVehicle(v.id) },
                    colors = CardDefaults.cardColors(
                        if (v.id == vm.selectedVehicleId.collectAsState().value)
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(v.name, fontWeight = FontWeight.Bold)
                        Text(if (v.plate.isBlank()) "${v.currentKm} km" else "${v.plate} — ${v.currentKm} km")
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(onClick = { editKmFor = v; newKm = v.currentKm.toString() }) {
                                Text("Compteur")
                            }
                            TextButton(onClick = { vm.deleteVehicle(v) }) { Text("Supprimer") }
                        }
                    }
                }
            }
        }
    }

    if (showAdd) {
        AlertDialog(
            onDismissRequest = { showAdd = false },
            title = { Text("Nouveau véhicule") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(name, { name = it }, label = { Text("Nom (ex. Clio 4)") })
                    OutlinedTextField(plate, { plate = it }, label = { Text("Immatriculation (optionnel)") })
                    OutlinedTextField(km, { km = it.filter(Char::isDigit) }, label = { Text("Compteur (km)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (name.isNotBlank()) {
                        vm.addVehicle(name.trim(), plate.trim(), km.toIntOrNull() ?: 0)
                        name = ""; plate = ""; km = ""; showAdd = false
                    }
                }) { Text("Ajouter") }
            },
            dismissButton = { TextButton(onClick = { showAdd = false }) { Text("Annuler") } }
        )
    }

    editKmFor?.let { v ->
        AlertDialog(
            onDismissRequest = { editKmFor = null },
            title = { Text("Compteur — ${v.name}") },
            text = {
                OutlinedTextField(newKm, { newKm = it.filter(Char::isDigit) }, label = { Text("Km actuels") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            },
            confirmButton = {
                Button(onClick = {
                    vm.updateKm(v, newKm.toIntOrNull() ?: v.currentKm)
                    editKmFor = null
                }) { Text("Enregistrer") }
            },
            dismissButton = { TextButton(onClick = { editKmFor = null }) { Text("Annuler") } }
        )
    }
}

// ---------- Maintenance ----------

@Composable
fun MaintenanceScreen(vm: CarnetViewModel) {
    val vehicle = vm.selectedVehicle()
    val history by vm.maintenanceHistory.collectAsState()
    var type by remember { mutableStateOf(MaintenanceType.VIDANGE) }
    var km by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        VehiclePicker(vm)
        Spacer(Modifier.height(8.dp))
        if (vehicle == null) {
            Text("Sélectionnez un véhicule.")
            return@Column
        }
        Text("Nouvel entretien", style = MaterialTheme.typography.titleMedium)
        @OptIn(ExperimentalMaterial3Api::class)
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            OutlinedTextField(
                value = MaintenanceType.labelFr(type), onValueChange = {}, readOnly = true,
                label = { Text("Type") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                MaintenanceType.all.forEach { t ->
                    DropdownMenuItem(
                        text = { Text("${MaintenanceType.labelFr(t)} (tous les ${MaintenanceType.defaultIntervalKm(t)} km)") },
                        onClick = { type = t; expanded = false }
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(km, { km = it.filter(Char::isDigit) }, label = { Text("Km") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
            OutlinedTextField(note, { note = it }, label = { Text("Note") }, modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                val k = km.toIntOrNull()
                if (k != null) {
                    vm.addMaintenance(type, k, note.trim())
                    km = ""; note = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Enregistrer") }
        Spacer(Modifier.height(12.dp))
        Text("Historique", style = MaterialTheme.typography.titleMedium)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(history, key = { it.id }) { r ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(Modifier.weight(1f)) {
                            Text("${MaintenanceType.labelFr(r.type)} — ${r.kmAtService} km", fontWeight = FontWeight.Bold)
                            Text("Le ${LocalDate.ofEpochDay(r.dateEpochDay)}" + if (r.note.isNotBlank()) " — ${r.note}" else "")
                        }
                        TextButton(onClick = { vm.deleteMaintenance(r) }) { Text("×") }
                    }
                }
            }
        }
    }
}

// ---------- Fault codes ----------

@Composable
fun FaultCodesScreen() {
    var query by remember { mutableStateOf("") }
    var expandedCode by remember { mutableStateOf<String?>(null) }
    val results = remember(query) { dz.carnautodz.data.FaultCodeCatalog.search(query) }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Codes défaut OBD-II (hors ligne)", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = query, onValueChange = { query = it },
            label = { Text("Rechercher (ex. P0300, ralenti, حساس)") },
            modifier = Modifier.fillMaxWidth(), singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        Text("${results.size} codes", style = MaterialTheme.typography.bodySmall)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(results, key = { it.code }) { fc ->
                Card(Modifier.fillMaxWidth().clickable {
                    expandedCode = if (expandedCode == fc.code) null else fc.code
                }) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${fc.code} — ${fc.titleFr}", fontWeight = FontWeight.Bold)
                        Text(fc.titleAr)
                        if (expandedCode == fc.code) {
                            Spacer(Modifier.height(4.dp))
                            Text("Système : ${fc.systemFr}", style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(4.dp))
                            Text("Conseil : ${fc.adviceFr}")
                            Text("نصيحة: ${fc.adviceAr}")
                        }
                    }
                }
            }
        }
    }
}

// ---------- Expenses ----------

@Composable
fun ExpensesScreen(vm: CarnetViewModel) {
    val vehicle = vm.selectedVehicle()
    val history by vm.expenseHistory.collectAsState()
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(ExpenseCategory.CARBURANT) }
    var note by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        VehiclePicker(vm)
        Spacer(Modifier.height(8.dp))
        if (vehicle == null) {
            Text("Sélectionnez un véhicule.")
            return@Column
        }
        val total = remember(history) { Expenses.total(history) }
        val month = remember(history) { Expenses.currentMonthTotal(history) }
        Text("Total : ${Expenses.formatDzd(total)} — ce mois : ${Expenses.formatDzd(month)}",
            style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(amount, { amount = it.filter(Char::isDigit) }, label = { Text("Montant (DZD)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
            @OptIn(ExperimentalMaterial3Api::class)
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }, modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = ExpenseCategory.labelFr(category), onValueChange = {}, readOnly = true,
                    label = { Text("Catégorie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    ExpenseCategory.all.forEach { c ->
                        DropdownMenuItem(
                            text = { Text(ExpenseCategory.labelFr(c)) },
                            onClick = { category = c; expanded = false }
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(note, { note = it }, label = { Text("Note (optionnel)") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    val a = amount.toLongOrNull()
                    if (a != null && a > 0) {
                        vm.addExpense(a, category, note.trim())
                        amount = ""; note = ""
                    }
                },
                modifier = Modifier.weight(1f)
            ) { Text("Ajouter") }
            OutlinedButton(onClick = { /* totals shown above; per-category shown below */ }, modifier = Modifier.weight(1f)) {
                Text("Détail par catégorie ↓")
            }
        }
        Spacer(Modifier.height(8.dp))
        val byCat = remember(history) { Expenses.byCategory(history) }
        if (byCat.isNotEmpty()) {
            Text(byCat.entries.joinToString(" · ") { "${ExpenseCategory.labelFr(it.key)}: ${Expenses.formatDzd(it.value)}" },
                style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(4.dp))
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(history, key = { it.id }) { e ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(Modifier.weight(1f)) {
                            Text("${Expenses.formatDzd(e.amountDzd)} — ${ExpenseCategory.labelFr(e.category)}",
                                fontWeight = FontWeight.Bold)
                            Text("Le ${LocalDate.ofEpochDay(e.dateEpochDay)}" + if (e.note.isNotBlank()) " — ${e.note}" else "")
                        }
                        TextButton(onClick = { vm.deleteExpense(e) }) { Text("×") }
                    }
                }
            }
        }
    }
}
