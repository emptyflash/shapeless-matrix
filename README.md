# shapeless-matrix

Typesafe matrix operations with Scala and Shapeless

### Example
Multiplication works for matrices with the correct dimensions
``` scala
import shapeless.Sized
import shapeless.nat._
import io.github.emptyflash.matrix.Matrix

val matrix = new Matrix[Int, _2, _3](Sized(
  Sized(1, 2, 3),
  Sized(4, 5, 6)
))
val other = new Matrix[Int, _3, _2](Sized(
  Sized(1, 2),
  Sized(3, 4),
  Sized(5, 6)
))

matrix * other
// Matrix[Int,shapeless.nat._2,shapeless.nat._2] = Matrix(Sized(Sized(22, 28), Sized(49, 64)))
```

But will fail at compile time for matrices with incorrect dimensions
``` scala
val matrix = new Matrix[Int, _2, _3](Sized(
  Sized(1, 2, 3),
  Sized(4, 5, 6)
))
val other = new Matrix[Int, _2, _2](Sized(
  Sized(1, 2),
  Sized(3, 4)
))

matrix * other
/*
error: type mismatch;
 found   : io.github.emptyflash.matrix.Matrix[Int,shapeless.nat._2,shapeless.nat._2]
 required: io.github.emptyflash.matrix.Matrix[Int,shapeless.nat._3,?]
       matrix * other
                ^
*/
```
