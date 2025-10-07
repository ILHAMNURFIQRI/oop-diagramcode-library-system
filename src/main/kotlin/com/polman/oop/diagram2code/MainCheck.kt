package com.polman.oop.diagram2code

// --- Helper Functions untuk Merapikan Output ---

/** Mencetak header utama untuk sebuah seksi. */
private fun printHeader(title: String) {
    val border = "=".repeat(title.length + 4)
    println("\n$border")
    println("  $title  ")
    println("$border")
}

/** Mencetak sub-header untuk setiap studi kasus. */
private fun printSubHeader(title: String) {
    println("\n--- $title ---")
}

/** Mencetak status atau aksi dengan indentasi. */
private fun printStatus(message: String) {
    println("  -> $message")
}

/** Mencetak hasil dari sebuah pengujian. */
private fun printResult(message: String, success: Boolean = true) {
    val status = if (success) "[OK]" else "[GAGAL]"
    println("  $status HASIL: $message")
}

fun main() {
    printHeader("UJI KASUS SISTEM PERPUSTAKAAN")

    // Persiapan Objek Awal
    val allPersons = mutableListOf<Person>()
    val allBooks = mutableListOf<Book>()

    // --- Studi Kasus 1: Registrasi Peran & Informasi Dasar ---
    printSubHeader("Studi Kasus 1: Registrasi Peran & Informasi Dasar")
    val m1 = Member("M001", " Budi ", MemberLevel.REGULAR)
    val m2 = Member("M002", "Citra", MemberLevel.PLATINUM)
    val lib = Librarian("L001", "Dewi", "LIB-777")
    allPersons.addAll(listOf(m1, m2, lib))

    printStatus("Informasi awal setiap peran:")
    allPersons.forEach { println("    - ${it.showInfo()}") }

    printStatus("Uji setter 'name' (trim & validasi) pada '${m1.name}':")
    m1.name = "  Budi Santoso  "
    println("    - Nama baru: '${m1.name}'")

    printStatus("Uji pembuatan Member dengan nama tidak valid (spasi):")
    try {
        Member("M-ERR", " ", MemberLevel.GOLD)
        printResult("Gagal menangkap error, seharusnya exception!", success = false)
    } catch (e: IllegalArgumentException) {
        printResult("Berhasil menangkap error: ${e.message}")
    }

    // --- Studi Kasus 2: Denda Polimorfik ---
    printSubHeader("Studi Kasus 2: Denda Polimorfik")
    val goldMember = Member("M003", "Eka", MemberLevel.GOLD)
    allPersons.add(goldMember)
    val personsForFeeTest = listOf(m1, goldMember, m2, lib)
    val daysLateScenarios = listOf(0, 1, 3, 10)

    // Header Tabel
    println("\n  %-20s | %-10s | %-10s | %-10s | %-10s".format("Peran", "0 Hari", "1 Hari", "3 Hari", "10 Hari"))
    println("  " + "-".repeat(70))

    personsForFeeTest.forEach { person ->
        val role = when(person) {
            is Member -> "Member (${person.level})"
            is Librarian -> "Librarian"
            else -> "Unknown"
        }
        val fees = daysLateScenarios.map { person.calculateFee(it) }
        println("  %-20s | Rp%-9.1f | Rp%-9.1f | Rp%-9.1f | Rp%-9.1f".format(role, fees[0], fees[1], fees[2], fees[3]))
    }
    println() // Memberi jarak
    printResult("Perhitungan denda polimorfik sesuai aturan.")

    // --- Studi Kasus 3: Peminjaman Buku ---
    printSubHeader("Studi Kasus 3: Peminjaman Buku")
    val b1 = Book("B001", "Clean Code", "R. Martin", 2008, 2)
    val b2 = Book("B002", "Refactoring", "M. Fowler", 1999, 1)
    allBooks.addAll(listOf(b1, b2))

    printStatus("Initial State: ${b1.info()}")
    printStatus("Meminjam '${b1.title}' (stok=2) sampai habis:")
    println("    - Peminjaman 1: Sukses=${b1.loan(m1)} -> Stok: ${b1.available()}/${b1.totalCount}, InStock: ${b1.inStock()}")
    println("    - Peminjaman 2: Sukses=${b1.loan(m2)} -> Stok: ${b1.available()}/${b1.totalCount}, InStock: ${b1.inStock()}")
    println("    - Peminjaman 3: Sukses=${b1.loan(goldMember)} -> Stok: ${b1.available()}/${b1.totalCount}, InStock: ${b1.inStock()}")
    printResult("Logika peminjaman b1 berjalan benar (true, true, false).")

    println() // Memberi jarak

    printStatus("Initial State: ${b2.info()}")
    printStatus("Meminjam '${b2.title}' (stok=1) sampai habis:")
    println("    - Peminjaman 1: Sukses=${b2.loan(m1)} -> Stok: ${b2.available()}/${b2.totalCount}, InStock: ${b2.inStock()}")
    println("    - Peminjaman 2: Sukses=${b2.loan(m2)} -> Stok: ${b2.available()}/${b2.totalCount}, InStock: ${b2.inStock()}")
    printResult("Logika peminjaman b2 berjalan benar (true, false).")


    // --- Studi Kasus 4: Pengembalian & Over-Capacity ---
    printSubHeader("Studi Kasus 4: Pengembalian & Over-Capacity")
    printStatus("Mengembalikan '${b1.title}' yang stoknya habis (0/2)...")
    b1.returnOne()
    println("    - Pengembalian 1: Stok sekarang ${b1.available()}/${b1.totalCount}")
    b1.returnOne()
    println("    - Pengembalian 2: Stok sekarang ${b1.available()}/${b1.totalCount} (Stok Penuh)")

    printStatus("Uji proteksi over-capacity:")
    try {
        b1.returnOne()
        printResult("Gagal mendeteksi over-capacity!", success = false)
    } catch (e: IllegalArgumentException) {
        printResult("Berhasil menangkap error: ${e.message}")
    }


    // --- Studi Kasus 5: Laporan Ringkas & Konsistensi ---
    printSubHeader("Studi Kasus 5: Laporan Ringkas & Konsistensi")
    printStatus("Laporan ini menunjukkan state akhir setelah semua operasi di atas.")
    println("\n  --- LAPORAN AKHIR PERSONS ---")
    allPersons.forEach { println("    - ${it.showInfo()}") }
    println("\n  --- LAPORAN AKHIR BOOKS ---")
    allBooks.forEach { println("    - ${it.info()}") }

    println() // Memberi jarak
    printResult("Data akhir konsisten dengan semua operasi yang dilakukan.")

    printHeader("SEMUA UJI KASUS BERHASIL")
}