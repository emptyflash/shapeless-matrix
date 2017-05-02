import org.scalatest._
import io.github.emptyflash.matrix._
import shapeless._
import shapeless.nat._
import shapeless.ops.nat._
import shapeless.newtype._
import shapeless.test._

class MatrixSpec extends FunSpec with Matchers {
  describe("Matrix") {
    it("should return it number of rows and columns as an ints") {
      val matrix = new Matrix[Int, _2, _3](Sized(
        Sized(1, 2, 3),
        Sized(4, 5, 6)
      ))
      matrix.rows should be (2)
      matrix.columns should be (3)
    }

    it("shouldn't work with mixed types") {
      illTyped("""
        new Matrix[Int, _1, _2](Sized(
          Sized(1, "a")
        ))
      """)
    }

    describe("transpose") {
      it("should return a matrix with rows and columns flipped") {
        val matrix = new Matrix[Int, _2, _3](Sized(
          Sized(1, 2, 3),
          Sized(4, 5, 6)
        ))
        val initialRows = matrix.rows
        val initialColumns = matrix.columns
        val transposed = matrix.transpose
        transposed.rows should be (initialColumns)
        transposed.columns should be (initialRows)
      }

      it("double transpose should be the same matrix") {
        val matrix = new Matrix[Int, _3, _3](Sized(
          Sized(1, 2, 3),
          Sized(4, 5, 6),
          Sized(7, 8, 9)
        ))
        val newMatrix = matrix.transpose.transpose
        newMatrix.unsizedMatrix should be (matrix.unsizedMatrix)
      }
    }

    describe("multiply") {
      it("should output a matrix of the correct dimensions") {
        val matrix = new Matrix[Int, _2, _3](Sized(
          Sized(1, 2, 3),
          Sized(4, 5, 6)
        ))
        val other = new Matrix[Int, _3, _2](Sized(
          Sized(1, 2),
          Sized(3, 4),
          Sized(5, 5)
        ))
        val result = matrix * other
        result.rows should be (2)
        result.columns should be (2)
      }
    }

    describe("add") {
      it("should add two matrices together") {
        val matrix = new Matrix[Int, _2, _2](Sized(
          Sized(1, 1),
          Sized(1, 1)
        ))
        val expected = new Matrix[Int, _2, _2](Sized(
          Sized(2, 2),
          Sized(2, 2)
        ))
        val result = matrix + matrix
        result.unsizedMatrix should be (expected.unsizedMatrix)
      }
    }

    describe("subtract") {
      it("should subtract one matrix from another") {
        val matrix = new Matrix[Int, _2, _2](Sized(
          Sized(1, 1),
          Sized(1, 1)
        ))
        val expected = new Matrix[Int, _2, _2](Sized(
          Sized(0, 0),
          Sized(0, 0)
        ))
        val result = matrix - matrix
        result.unsizedMatrix should be (expected.unsizedMatrix)
      }
    }
  }
}
