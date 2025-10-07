package com.polman.oop.diagram2code

/**
 * class Librarian : Person {
 * +staffCode: String
 * +showInfo(): String
 * +calculateFee(daysLate: Int): Double
 * }
 *
 * Catatan:
 * - staffCode non-blank
 * - calculateFee() selalu 0.0
 */
class Librarian(
    id: String,
    name: String,
    val staffCode: String
) : Person(id, name) {

    init {
        require(staffCode.isNotBlank()) { "Kode staf tidak boleh kosong" }
    }

    override fun showInfo(): String {
        return "Librarian[id=$id, name='$name', staffCode='$staffCode']"
    }

    override fun calculateFee(daysLate: Int): Double {
        return 0.0 // Pustakawan tidak dikenakan denda.
    }
}