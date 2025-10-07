package com.polman.oop.diagram2code

/**
 * class Member : Person {
 * +level: MemberLevel
 * +showInfo(): String
 * +calculateFee(daysLate: Int): Double
 * }
 */
class Member(
    id: String,
    name: String,
    val level: MemberLevel
) : Person(id, name) {

    override fun showInfo(): String {
        return "Member[id=$id, name='$name', level=$level]"
    }

    override fun calculateFee(daysLate: Int): Double {
        if (daysLate <= 0) return 0.0
        val rate = when (level) {
            MemberLevel.REGULAR -> 2000.0
            MemberLevel.GOLD -> 1500.0
            MemberLevel.PLATINUM -> 1000.0
        }
        return rate * daysLate
    }
}