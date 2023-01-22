import java.math.BigDecimal

enum class UnitOfMeasure(
    val names: List<String>,
    val primaryUnit: String,
    val toPrimaryUnit: (BigDecimal) -> BigDecimal,
    val fromPrimaryUnit: (BigDecimal) -> BigDecimal
) {

    // Length units (with meter as a primary unit).
    Millimeter(listOf("mm", "millimeter", "millimeters"), "meter", { it / BigDecimal("1000") }, { it * BigDecimal("1000") }),
    CentiMeter(listOf("cm", "centimeter", "centimeters"), "meter", { it / BigDecimal("100") }, { it * BigDecimal("100") }),
    Meter(listOf("m", "meter", "meters"), "meter", { it }, { it }),
    Kilometer(listOf("km", "kilometer", "kilometers"), "meter", { it * BigDecimal("1000") }, { it / BigDecimal("1000") }),
    Mile(listOf("mi", "mile", "miles"), "meter", { it * BigDecimal("1609.35") }, { it / BigDecimal("1609.35") }),
    Yard(listOf("yd", "yard", "yards"), "meter", { it * BigDecimal("0.9144") }, { it / BigDecimal("0.9144") }),
    Foot(listOf("ft", "foot", "feet"), "meter", { it * BigDecimal("0.3048") }, { it / BigDecimal("0.3048") }),
    Inch(listOf("in", "inch", "inches"), "meter", { it * BigDecimal("0.0254") }, { it / BigDecimal("0.0254") }),

    // Weight units (with gram as a primary unit).
    Milligram(listOf("mg", "milligram", "milligrams"), "gram", { it / BigDecimal("1000") }, { it * BigDecimal("1000") }),
    Gram(listOf("g", "gram", "grams"), "gram", { it }, { it }),
    Kilogram(listOf("kg", "kilogram", "kilograms"), "gram", { it * BigDecimal("1000") }, { it / BigDecimal("1000") }),
    Pound(listOf("lb", "pound", "pounds"), "gram", { it * BigDecimal("453.592") }, { it / BigDecimal("453.592") }),
    Ounce(listOf("oz", "ounce", "ounces"), "gram", { it * BigDecimal("28.3495") }, { it / BigDecimal("28.3495") }),

    // Temperature units (with degree Celsius as a primary unit).
    Celsius(listOf("c", "degree Celsius", "degrees Celsius", "celsius", "dc"), "degree Celsius", { it }, { it }),
    Fahrenheit(listOf("f", "degree Fahrenheit", "degrees Fahrenheit", "fahrenheit", "df"), "degree Celsius",
        { (it - BigDecimal("32")) * BigDecimal("5") / BigDecimal("9") }, { it * BigDecimal("1.8") + BigDecimal("32") }),
    Kelvin(listOf("k", "kelvin", "kelvins"), "degree Celsius", { it - BigDecimal("273.15") }, { it + BigDecimal("273.15") });

    companion object {

        /** List of all units of measure that cannot have a negative value. */
        val nonNegativeUnits = UnitOfMeasure.values().filter { it.primaryUnit in listOf("meter", "gram") }

        /** Returns the unit of measure that have a name equal to [unitName] (case-insensitive), or `null` if [unitName] is unknown. */
        operator fun get(unitName: String): UnitOfMeasure? {
            for (unitOfMeasure in UnitOfMeasure.values())
                if (unitName.lowercase() in unitOfMeasure.names.map { it.lowercase() }) return unitOfMeasure
            return null
        }
    }
}
