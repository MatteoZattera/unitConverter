import java.math.BigDecimal

class Quantity(val value: BigDecimal, val unitOfMeasure: UnitOfMeasure) {

    /** Returns a new equivalent Quantity with [otherUnitOfMeasure] or `null` if this Quantity cannot be converted in the other. */
    fun convertTo(otherUnitOfMeasure: UnitOfMeasure): Quantity? {

        // If the primary units are different the two units are incompatible for the conversion.
        if (otherUnitOfMeasure.primaryUnit != unitOfMeasure.primaryUnit) return null

        // Converts the value of this instance in the primary unit equivalent value, and then in the requested unit of measure.
        val equivalentPrimaryUnitQuantity = Quantity(unitOfMeasure.toPrimaryUnit(value.setScale(20)), UnitOfMeasure[unitOfMeasure.primaryUnit]!!)
        return Quantity(otherUnitOfMeasure.fromPrimaryUnit(equivalentPrimaryUnitQuantity.value), otherUnitOfMeasure)
    }

    /** Returns `true` if this quantity cannot be negative. */
    fun cannotBeNegative() = unitOfMeasure in UnitOfMeasure.nonNegativeUnits

    override fun toString(): String {

        val unitOfMeasureName = if (value.toDouble() == 1.0) unitOfMeasure.names[1] else unitOfMeasure.names[2]
        //return "${value.stripTrailingZeros().toPlainString()} $unitOfMeasureName"
        return "${value.toDouble()} $unitOfMeasureName"
    }
}
