package io.github.emptyflash

import shapeless._
import shapeless.nat._
import shapeless.ops.nat._
import shapeless.newtype._
import shapeless.syntax.sized._
import shapeless.ops.hlist._

import shapeless.refinedstd.syntax._

package object matrix {
  type Vec[A, N <: Nat] = Sized[IndexedSeq[A], N]
  type Mat[A, M <: Nat, N <: Nat] = Vec[Vec[A, N], M]

  object Matrix {
    def ensureSized[A, M <: Nat, N <: Nat](unsized: IndexedSeq[IndexedSeq[A]])(
      implicit
      oToInt: ToInt[M],
      pToInt: ToInt[N]
    ): Matrix[A, M, N] =
      new Matrix(unsized.map(_.ensureSized[N]).ensureSized[M])

  }

  class Matrix[A, M <: Nat, N <: Nat](val underlying: Mat[A, M, N])(
    implicit 
    mToInt: ToInt[M], 
    nToInt: ToInt[N]
  ) {
    import Matrix._

    lazy val rows = mToInt.apply()
    lazy val columns = nToInt.apply()

    lazy val unsizedMatrix: IndexedSeq[IndexedSeq[A]] = underlying.unsized.map(_.unsized)

    def transpose: Matrix[A, N, M] = {
      val transposed = unsizedMatrix.transpose 
      Matrix.ensureSized[A, N, M](transposed)
    }

    def *[O <: Nat](other: Matrix[A, N, O])(implicit n: Numeric[A], oToInt: ToInt[O]): Matrix[A, M, O] = {
      import n._
      val multiplied: IndexedSeq[IndexedSeq[A]] =
        for (row <- unsizedMatrix) yield 
          for(col <- other.transpose.unsizedMatrix) yield
            row.zip(col).map(((_: A) * (_: A)).tupled).reduceLeft(_+_)
      Matrix.ensureSized[A, M, O](multiplied)
    }

    private def zip(other: Matrix[A, M, N]): Matrix[(A, A), M, N] =
      Matrix.ensureSized[(A, A), M, N](
        underlying
          .zip(other.underlying)
          .map({ case (a, b) => a.zip(b) })
      )

    def +(other: Matrix[A, M, N])(implicit n: Numeric[A]): Matrix[A, M, N] = {
      import n._
      val added =
        this.zip(other).underlying.map(_.map({ case (a, b) => a + b }))
      new Matrix(added)
    }

    def -(other: Matrix[A, M, N])(implicit n: Numeric[A]): Matrix[A, M, N] = {
      import n._
      val subtracted =
        this.zip(other).underlying.map(_.map({ case (a, b) => a - b }))
      new Matrix(subtracted)
    }
  }
}
