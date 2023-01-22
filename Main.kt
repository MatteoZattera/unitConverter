import java.math.BigDecimal

/** Prints `this` argument to the standard output stream. */
fun <T> T.printlnIt() = println(this)

/** Prints `this` argument to the standard output stream, then reads an input and returns it. */
fun <T> T.reply() = this.printlnIt().run { readln() }

fun main() {

    while (true) {
        try {

            val input = "Enter what you want to convert (or exit):".reply().replace(Regex("(degree|degrees) ", RegexOption.IGNORE_CASE), "$1-").split(" ")
            if (input[0] == "exit") break
            if (input.size != 4) throw InvalidInputException("Parse error")

            val value = input[0].toBigDecimalOrNull() ?: throw InvalidInputException("Parse error")
            val unitOfMeasure = UnitOfMeasure[input[1].replace("-", " ")]
            val targetUnitOfMeasure = UnitOfMeasure[input[3].replace("-", " ")]

            if (unitOfMeasure == null || targetUnitOfMeasure == null)
                throw InvalidConversionException("Conversion from ${unitOfMeasure?.names?.get(2) ?: "???"} to ${targetUnitOfMeasure?.names?.get(2) ?: "???"} is impossible")

            val quantity = Quantity(value, unitOfMeasure)

            val result = quantity.convertTo(targetUnitOfMeasure)
                ?: throw InvalidConversionException("Conversion from ${unitOfMeasure.names[2]} to ${targetUnitOfMeasure.names[2]} is impossible")

            if (quantity.value < BigDecimal.ZERO && quantity.cannotBeNegative()) {
                when (quantity.unitOfMeasure.primaryUnit) {
                    "meter" -> throw InvalidInputException("Length shouldn't be negative.")
                    "gram" -> throw InvalidInputException("Weight shouldn't be negative.")
                }
            }

            "$quantity is $result".printlnIt()

        } catch (e: InvalidInputException) {
            e.message.printlnIt()
        } catch (e: InvalidConversionException) {
            e.message.printlnIt()
        }
        println()
    }
}
