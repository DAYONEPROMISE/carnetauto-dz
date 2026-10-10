package dz.carnautodz.logic

import dz.carnautodz.data.Expense
import java.time.LocalDate

/** Pure expense aggregations. Amounts in whole DZD. */
object Expenses {
    fun total(expenses: List<Expense>): Long = expenses.sumOf { it.amountDzd }

    fun byCategory(expenses: List<Expense>): Map<String, Long> =
        expenses.groupBy { it.category }.mapValues { (_, v) -> v.sumOf { it.amountDzd } }

    /** Total of expenses in the month containing [today] (epoch-day based). */
    fun currentMonthTotal(expenses: List<Expense>, today: LocalDate = LocalDate.now()): Long {
        val first = today.withDayOfMonth(1).toEpochDay()
        val last = today.plusMonths(1).withDayOfMonth(1).toEpochDay()
        return expenses.filter { it.dateEpochDay in first until last }.sumOf { it.amountDzd }
    }

    /** "12 500 DZD" style formatting with narrow spaces. */
    fun formatDzd(amount: Long): String {
        if (amount == 0L) return "0 DZD"
        val neg = amount < 0
        var n = kotlin.math.abs(amount)
        val parts = ArrayDeque<String>()
        while (n > 0) {
            parts.addFirst((n % 1000).toString().padStart(if (n >= 1000) 3 else 1, '0'))
            n /= 1000
        }
        return (if (neg) "-" else "") + parts.joinToString("\u2009") + " DZD"
    }
}
