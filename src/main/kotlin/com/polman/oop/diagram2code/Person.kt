package com.polman.oop.diagram2code

/**
 * Sesuai diagram:
 * abstract class Person {
 * -id: String
 * +name: String
 * #validateName(name: String): void
 * +showInfo(): String
 * +calculateFee(daysLate: Int): Double
 * }
 */

/**
 * Invarian:
 * - id non-blank
 * - name 2..100 chars (trim), validateName() dipanggil di init/setter
 */
abstract class Person(
    val id: String,
    name: String
) {
    var name: String = name
        set(value) {
            validateName(value)
            field = value.trim()
        }

    init {
        require(id.isNotBlank()) { "ID tidak boleh kosong" }
        validateName(name)
        this.name = name.trim()
    }

    /**
     * Validasi nama:
     * - tidak kosong setelah trim
     * - panjang 2..100
     * - hanya huruf dan spasi (tidak boleh ada angka/simbol)
     * Lempar IllegalArgumentException jika tidak valid.
     */
    protected fun validateName(n: String) {
        val trimmedName = n.trim()
        require(trimmedName.isNotBlank()) { "Nama tidak boleh kosong" }
        require(trimmedName.length in 2..100) { "Panjang nama harus antara 2 hingga 100 karakter" }
        require(trimmedName.matches(Regex("^[A-Za-z ]+$"))) {
            "Nama mengandung karakter tidak valid (hanya huruf dan spasi diperbolehkan)"
        }
    }

    /** Ringkasan identitas untuk audit/log. */
    abstract fun showInfo(): String

    /**
     * Hitung denda keterlambatan.
     * - kontrak: daysLate >= 0 -> hasil >= 0.0
     * - Implementasi di subclass (polimorfik).
     */
    abstract fun calculateFee(daysLate: Int): Double
}
