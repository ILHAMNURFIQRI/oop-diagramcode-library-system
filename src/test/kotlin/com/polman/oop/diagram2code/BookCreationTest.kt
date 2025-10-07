package com.polman.oop.diagram2code

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BookCreationTest {

    @Test
    fun `fun membuat Book valid - stok awal sama dengan totalCount dan info sesuai`() {
        val b = Book(id = "B001", title = "Clean Code", author = "Robert C. Martin", year = 2008, totalCount = 2)
        assertEquals(2, b.available())
        assertTrue(b.inStock(), "Buku harus inStock saat totalCount>0")
        val info = b.info()
        assertTrue(info.contains("Book"), "info harus menampilkan ringkasan")
        assertTrue(info.contains("stock=2/2"), "info harus memuat stok 2/2")
    }

    @Test
    fun `fun gagal membuat Book id blank atau year tidak wajar`() {
        val exId = assertThrows<IllegalArgumentException> {
            Book(id = "", title = "X", author = "Y", year = 2000, totalCount = 1)
        }
        assertTrue(exId.message!!.contains("id", ignoreCase = true))

        val exYear = assertThrows<IllegalArgumentException> {
            Book(id = "B002", title = "X", author = "Y", year = 1200, totalCount = 1)
        }
        // --- PERBAIKAN DI SINI ---
        // Mengganti pencarian "year" menjadi "Tahun" agar sesuai dengan pesan error
        assertTrue(exYear.message!!.contains("Tahun", ignoreCase = true))
    }

    @Test
    fun `fun totalCount nol - tidak inStock dan loan pertama harus false`() {
        val m = Member("M001", "Cici", MemberLevel.GOLD)
        val b = Book(id = "B004", title = "No Copies", author = "Anon", year = 2020, totalCount = 0)
        assertFalse(b.inStock())
        assertFalse(b.loan(m))
        assertEquals(0, b.available())
    }
}