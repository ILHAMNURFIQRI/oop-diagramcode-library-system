package com.polman.oop.diagram2code

/**
 * class Book : Loanable {
 * +id: String
 * +title: String
 * +author: String
 * +year: Int
 * +totalCount: Int
 * -availableCount: Int
 * +inStock(): Boolean
 * +loan(to: Member): Boolean
 * +returnOne(): void
 * +available(): Int
 * +info(): String
 * }
 *
 * Invarian:
 * - 0 <= availableCount <= totalCount
 * - year E [1400..2100]
 * - loan(): false jika stok habis
 * - returnOne(): tidak boleh melebihi totalCount (lempar exception)
 */
class Book(
    val id: String,
    val title: String,
    val author: String,
    val year: Int,
    val totalCount: Int
) : Loanable {

    private var availableCount: Int

    init {
        require(id.isNotBlank()) { "ID buku tidak boleh kosong" }
        require(title.isNotBlank()) { "Judul buku tidak boleh kosong" }
        require(author.isNotBlank()) { "Penulis buku tidak boleh kosong" }
        require(year in 1400..2100) { "Tahun terbit harus antara 1400 dan 2100" }
        require(totalCount >= 0) { "Jumlah total buku tidak boleh negatif" }
        this.availableCount = totalCount
    }

    fun inStock(): Boolean {
        return availableCount > 0
    }

    override fun loan(to: Member): Boolean {
        if (inStock()) {
            availableCount--
            return true
        }
        return false
    }

    fun returnOne() {
        require(availableCount < totalCount) { "Tidak dapat mengembalikan buku, stok sudah penuh." }
        availableCount++
    }

    fun available(): Int {
        return availableCount
    }

    fun info(): String {
        return "Book[id=$id, title='$title', author='$author', year=$year, stock=$availableCount/$totalCount]"
    }
}