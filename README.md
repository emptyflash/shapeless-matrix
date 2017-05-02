# shapeless-matrix

Typesafe matrix operations with Scala and Shapeless

### Example
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
