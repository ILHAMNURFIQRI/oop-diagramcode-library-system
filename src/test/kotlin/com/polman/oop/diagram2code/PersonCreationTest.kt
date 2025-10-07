package com.polman.oop.diagram2code

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PersonCreationTest {

    @Test
    fun `fun membuat Member valid - showInfo menampilkan level dan id`() {
        val m = Member("M001", "Ani", MemberLevel.REGULAR)
        val info = m.showInfo()
        assertEquals("Ani", m.name)
        assertTrue(info.contains("Member"), "showInfo harus memuat kata 'Member'")
        assertTrue(info.contains("M001"), "showInfo harus memuat id")
        assertTrue(info.contains("REGULAR"), "showInfo harus memuat level")
    }

    @Test
    fun `fun setter name melakukan trim dan validasi panjang`() {
        val m = Member("M003", "   Budi Santoso   ", MemberLevel.PLATINUM)
        assertEquals("Budi Santoso", m.name)

        val exShort = assertThrows<IllegalArgumentException> {
            m.name = "A" // < 2 huruf
        }
        assertTrue(exShort.message!!.contains("nama"))
    }

    @Test
    fun `fun membuat Librarian valid - staffcode nonblank dan fee selalu nol`() {
        val l = Librarian("L001", "Sari", "LIB-777")
        assertEquals("Sari", l.name)
        assertEquals(0.0, l.calculateFee(10), 0.000001)
        val info = l.showInfo()
        assertTrue(info.contains("Librarian"))
        assertTrue(info.contains("LIB-777"))
    }

    @Test
    fun `fun gagal membuat turunan Person dengan name blank`() {
        assertThrows<IllegalArgumentException> {
            Member("M003", " ", MemberLevel.GOLD)
        }
    }

    @Test
    fun `fun gagal membuat turunan Person dengan id blank`() {
        val ex = assertThrows<IllegalArgumentException> {
            Librarian(" ", "Dodi", "SC-01")
        }
        assertTrue(ex.message!!.contains("id"))
    }
    @Test
    fun `fun gagal membuat Member dengan level null atau tidak dikenal`() {
        assertThrows<IllegalArgumentException> {
            // Simulasi level tidak valid (harus enum valid)
            Member("M010", "Andi", MemberLevel.valueOf("INVALID"))
        }
    }

    @Test
    fun `fun calculateFee tidak boleh menghasilkan nilai negatif`() {
        val m = Member("M009", "Citra", MemberLevel.REGULAR)
        val fee = m.calculateFee(-5)
        assertEquals(0.0, fee, 0.000001, "Fee harus nol jika hari keterlambatan negatif")
    }

    @Test
    fun `fun Librarian tidak boleh dibuat dengan staffCode kosong atau spasi saja`() {
        assertThrows<IllegalArgumentException> {
            Librarian("L009", "Rina", "   ")
        }
    }

    @Test
    fun `fun Person name tidak boleh mengandung angka atau simbol aneh`() {
        val ex = assertThrows<IllegalArgumentException> {
            Member("M011", "Jo3#", MemberLevel.GOLD)
        }
        assertTrue(ex.message!!.contains("karakter"))
    }

}