package dz.carnautodz.logic

import dz.carnautodz.data.Expense
import dz.carnautodz.data.ExpenseCategory
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class ExpensesTest {

    private fun expense(amount: Long, cat: String = ExpenseCategory.CARBURANT, day: Long = 20000) =
        Expense(vehicleId = 1, amountDzd = amount, category = cat, dateEpochDay = day)

    @Test
    fun `total sums all expenses`() {
        assertEquals(17_500L, Expenses.total(listOf(expense(5_000), expense(12_500))))
    }

    @Test
    fun `total of empty list is zero`() {
        assertEquals(0L, Expenses.total(emptyList()))
    }

    @Test
    fun `byCategory groups correctly`() {
        val list = listOf(
            expense(5_000, ExpenseCategory.CARBURANT),
            expense(3_000, ExpenseCategory.CARBURANT),
            expense(20_000, ExpenseCategory.PIECES)
        )
        val byCat = Expenses.byCategory(list)
        assertEquals(8_000L, byCat[ExpenseCategory.CARBURANT])
        assertEquals(20_000L, byCat[ExpenseCategory.PIECES])
    }

    @Test
    fun `currentMonthTotal only counts this month`() {
        val today = LocalDate.of(2026, 10, 9)
        val inMonth = expense(7_000, day = LocalDate.of(2026, 10, 1).toEpochDay())
        val outMonth = expense(9_000, day = LocalDate.of(2026, 9, 30).toEpochDay())
        assertEquals(7_000L, Expenses.currentMonthTotal(listOf(inMonth, outMonth), today))
    }

    @Test
    fun `formatDzd groups thousands`() {
        assertEquals("0 DZD", Expenses.formatDzd(0))
        assertEquals("500 DZD", Expenses.formatDzd(500))
        assertEquals("12\u2009500 DZD", Expenses.formatDzd(12_500))
        assertEquals("1\u2009000\u2009000 DZD", Expenses.formatDzd(1_000_000))
    }
}
