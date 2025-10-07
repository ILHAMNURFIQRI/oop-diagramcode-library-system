package com.polman.oop.diagram2code

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BookLoanAndReturnTest {

    @Test
    fun `fun loan menurunkan stok hingga nol lalu gagal jika habis`() {
        val m1 = Member("M001", "Budi", MemberLevel.REGULAR)
        val m2 = Member("M002", "Budi", MemberLevel.PLATINUM)
        val b = Book("B001", "Kotlin in Action", "Jetbrains", 2017, totalCount = 2)

        // Stok: 2 -> 1
        assertTrue(b.loan(m1))
        assertEquals(1, b.available())

        // Stok: 1 -> 0
        assertTrue(b.loan(m2))
        assertEquals(0, b.available())

        // Stok habis: loan() mengembalikan false, stok tetap 0
        assertFalse(b.loan(m1))
        assertEquals(0, b.available())
        assertFalse(b.inStock())
    }

    @Test
    fun `fun returnOne menambah stok jika belum penuh dan melempar exception jika over-capacity`() {
        val m = Member("M010", "Danu", MemberLevel.REGULAR)
        val b = Book("B010", "Refactoring", "Martin Fowler", 1999, totalCount = 1)

        // Pinjam dulu agar stok menjadi 0
        assertTrue(b.loan(m))
        assertEquals(0, b.available())

        // Pengembalian sah: stok 0 -> 1
        b.returnOne()
        assertEquals(1, b.available())

        // Over-capacity: stok sudah 1/1, return lagi akan melempar exception
        val ex = assertThrows<IllegalArgumentException> {
            b.returnOne()
        }
        // Memastikan pesan error relevan
        assertTrue(ex.message!!.contains("penuh", ignoreCase = true))
    }

    @Test
    fun `fun siklus pinjam-kembali berulang menjaga invarian 0 dan totalCount`() {
        val m = Member("M020", "Eka", MemberLevel.GOLD)
        val b = Book("B020", "Clean Architecture", "Robert C. Martin", 2017, totalCount = 2)

        repeat(5) { // Ulangi siklus ini 5 kali untuk memastikan konsistensi
            // 1. Pinjam sampai habis
            assertTrue(b.loan(m), "Pinjaman pertama harus berhasil")
            assertTrue(b.loan(m), "Pinjaman kedua harus berhasil")
            assertEquals(0, b.available(), "Stok harus 0 setelah dipinjam habis")
            assertFalse(b.loan(m), "Pinjaman ketiga harus gagal karena stok habis")

            // 2. Kembalikan sampai penuh
            b.returnOne()
            assertEquals(1, b.available(), "Stok harus 1 setelah pengembalian pertama")
            b.returnOne()
            assertEquals(2, b.available(), "Stok harus 2 (penuh) setelah pengembalian kedua")

            // 3. Pastikan tidak bisa mengembalikan lagi (over-capacity)
            assertThrows<IllegalArgumentException>("Harus melempar exception saat mengembalikan buku yang stoknya sudah penuh") {
                b.returnOne()
            }

            // 4. Pastikan invarian tetap terjaga di akhir setiap siklus
            assertTrue(b.available() in 0..b.totalCount)
        }

        assertEquals(2, b.available(), "Stok di akhir semua siklus harus penuh")
    }
}