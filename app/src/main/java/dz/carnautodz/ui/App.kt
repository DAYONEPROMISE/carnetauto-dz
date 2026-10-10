package dz.carnautodz.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

private data class Tab(val route: String, val label: String, val icon: ImageVector)

private val tabs = listOf(
    Tab("home", "Suivi", Icons.Filled.Home),
    Tab("maintenance", "Entretien", Icons.Filled.Build),
    Tab("codes", "Codes", Icons.Filled.Search),
    Tab("expenses", "Dépenses", Icons.Filled.ShoppingCart),
    Tab("vehicles", "Véhicules", Icons.Filled.DirectionsCar)
)

@Composable
fun CarnetApp(vm: CarnetViewModel) {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val current = backStack?.destination?.route
    CarnetAutoDZTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    tabs.forEach { t ->
                        NavigationBarItem(
                            selected = current == t.route,
                            onClick = { nav.navigate(t.route) { launchSingleTop = true } },
                            icon = { Icon(t.icon, contentDescription = t.label) },
                            label = { Text(t.label) }
                        )
                    }
                }
            }
        ) { inner ->
            NavHost(nav, startDestination = "home", modifier = Modifier.padding(inner)) {
                composable("home") { DashboardScreen(vm) }
                composable("maintenance") { MaintenanceScreen(vm) }
                composable("codes") { FaultCodesScreen() }
                composable("expenses") { ExpensesScreen(vm) }
                composable("vehicles") { VehiclesScreen(vm) }
            }
        }
    }
}
