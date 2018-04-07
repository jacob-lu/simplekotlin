// Explore a simple class

println("UW Homework: Simple Kotlin")

// write a "whenFn" that takes an arg of type "Any" and returns a String
    fun whenFn(arg: Any): String {
        when (arg) {
            "Hello" -> return "world"
            is String -> return "Say what?"
            0 -> return "zero"
            1 -> return "one"
            in 2..10 -> return "low number"
            is Int -> return "a number"
            else -> {
                return "I don't understand"
            }
        }
    }
// write an "add" function that takes two Ints, returns an Int, and adds the values
    fun add (num1: Int, num2: Int) : Int {
        return num1 + num2
    }
// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
    fun sub (num1: Int, num2: Int) : Int {
        return num1 - num2
    }
// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
    fun mathOp(num1: Int, num2: Int, function: (a: Int, b: Int)-> Int) :Int {
        return function(num1, num2)
    }

// write a class "Person" with first name, last name and age
    class Person constructor(var firstName: String, var lastName: String, var age: Int){
        val debugString : String by lazy {
            "[Person firstName:${firstName} lastName:${lastName} age:${age}]"
        }
        fun equals(person1: Person, person2: Person) : Boolean {
            return person1.hashCode() == person2.hashCode()
        }
    }

// write a class "Money"
    class Money constructor(amount: Int, currency: String)  {
        var amount = checkAmount(amount)
        var currency = checkCurrency(currency)
        val rates = listOf (
                Pair("USD", 10) to Pair("GBP", 5),
                Pair("USD", 10) to Pair("EUR", 15),
                Pair("USD", 12) to Pair("CAN", 15),
                Pair("GBP", 5) to Pair("USD", 10),
                Pair("EUR", 15) to Pair("USD", 10),
                Pair("CAN", 15) to Pair("USD", 12)
        )
        fun checkAmount(amountPar: Int):Int {
            if(amountPar > 0){
                return amountPar
            } else {
                throw Exception("Amount can't be below 0")
            }
        }
        fun checkCurrency (currencyPar: String) :String {
            when (currencyPar) {
                "USD" -> return "USD"
                "EUR" -> return "EUR"
                "CAN" -> return "CAN"
                "GBP" -> return "GBP"
                else -> {
                    throw Exception("Not right currency")
                }
            }
        }
        fun convert(currencyType: String): Money {
            var money = Money(this.amount, this.currency)
            var storeCurrency = this.currency
            var storeAmount = this.amount
            if(this.currency == (currencyType)) {
                return money
            } else {
                // if both converter and current are not usd
                if(this.currency != "USD" && currencyType != "USD") {
                    for ((k, v) in rates) {
                        if (k.first == this.currency) {
                            //change current to amount of money in usd
                            this.amount = this.amount * v.second / k.second
                            this.currency = "USD"
                        }
                    }
                }
                // current is usd, now convert
                for((k,v) in rates) {
                    if(k.first == this.currency && v.first == currencyType) {
                        money.amount = this.amount * v.second / k.second
                        money.currency = currencyType
                    }
                }
                this.currency = storeCurrency
                this.amount = storeAmount
                return money
            }
        }

        operator fun plus(otherMoney: Money): Money {
            var money = Money(this.amount, this.currency)
            var addMoney: Money = otherMoney.convert(this.currency)
            money.amount = this.amount + addMoney.amount
            return money
        }
    }

// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
    "Hello" to "world",
    "Howdy" to "Say what?",
    "Bonjour" to "Say what?",
    0 to "zero",
    1 to "one",
    5 to "low number",
    9 to "low number",
    17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
    Pair(0, 0) to 0,
    Pair(1, 2) to 3,
    Pair(-2, 2) to 0,
    Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
    Pair(0, 0) to 0,
    Pair(2, 1) to 1,
    Pair(-2, 2) to -4,
    Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
    Pair(tenUSD, tenUSD),
    Pair(tenUSD, fiveGBP),
    Pair(tenUSD, fifteenEUR),
    Pair(twelveUSD, fifteenCAN),
    Pair(fiveGBP, tenUSD),
    Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
    Pair(tenUSD, tenUSD) to Money(20, "USD"),
    Pair(tenUSD, fiveGBP) to Money(20, "USD"),
    Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
              (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
