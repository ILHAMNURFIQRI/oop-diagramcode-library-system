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
}