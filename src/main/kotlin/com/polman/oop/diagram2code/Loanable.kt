package com.polman.oop.diagram2code

/**
 * interface Loanable {
 * +loan(to: Member): Boolean
 * }
 *
 * Kontrak:
 * - Mengembalikan true jika peminjaman berhasil, false jika stok habis (bukan exception).
 */
interface Loanable {
    fun loan(to: Member): Boolean
}