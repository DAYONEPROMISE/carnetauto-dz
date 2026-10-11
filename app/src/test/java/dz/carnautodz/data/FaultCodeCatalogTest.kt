package dz.carnautodz.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FaultCodeCatalogTest {

    @Test
    fun `catalog is non-empty and codes are unique`() {
        assertTrue(catalogSize() >= 40)
        val codes = FaultCodeCatalog.codes.map { it.code }
        assertEquals(codes.size, codes.toSet().size)
    }

    @Test
    fun `all codes match P0-P1 pattern`() {
        FaultCodeCatalog.codes.forEach {
            assertTrue("bad code ${it.code}", it.code.matches(Regex("P[01][0-9]{3}")))
        }
    }

    @Test
    fun `every entry has FR and AR text`() {
        FaultCodeCatalog.codes.forEach {
            assertTrue(it.titleFr.isNotBlank())
            assertTrue(it.titleAr.isNotBlank())
            assertTrue(it.adviceFr.isNotBlank())
            assertTrue(it.adviceAr.isNotBlank())
        }
    }

    @Test
    fun `search by exact code`() {
        val r = FaultCodeCatalog.search("P0300")
        assertTrue(r.any { it.code == "P0300" })
    }

    @Test
    fun `search is case and space insensitive`() {
        val a = FaultCodeCatalog.search("p0300")
        val b = FaultCodeCatalog.search("P 0300")
        assertTrue(a.any { it.code == "P0300" })
        assertTrue(b.any { it.code == "P0300" })
    }

    @Test
    fun `search by French keyword finds ignition codes`() {
        val r = FaultCodeCatalog.search("allumage")
        assertTrue(r.size >= 4)
    }

    @Test
    fun `search by Arabic keyword works`() {
        val r = FaultCodeCatalog.search("البطارية")
        assertTrue(r.any { it.code == "P0562" || it.code == "P0563" })
    }

    @Test
    fun `empty query returns full catalog`() {
        assertEquals(FaultCodeCatalog.codes.size, FaultCodeCatalog.search("  ").size)
    }

    private fun catalogSize() = FaultCodeCatalog.codes.size
}
