# ImprovedDataTables -- Efficient Data Sorting and Filtering

## JAVA’S BUILT-IN FUNCTIONAL INTERFACES SUMMARY

A **functional interface** has exactly one abstract method.
*Bi* prefix denotes two parameters (BiConsumer, BiPredicate, BiFunction – BinaryOperator extends BiFunction).

#### The following common functional interfaces are in the java.util.function package:
- **Supplier**: generates or supplies values without taking any input
  - No parameters
  - Return type T 
  - get()                                                                    
- **Consumer**: acts upon a parameter but does not return anything 
  - 1 parameter T
  - Return type void
  - accept()
- **Predicate**: used to test a condition (often used when filtering or matching)
  - 1 parameter T
  - Return type boolean
  - test()
- **Function**: turns one parameter into a value of a potentially different type and returns it
  - 1 parameter T
  - Return type R
  - apply()
- **UnaryOperator**: transforms its value into one of the same type
  - 1 parameter T
  - Return type T
  - apply()
