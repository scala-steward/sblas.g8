import scalanative.native._
import org.ekrich.blas.snic.blas._

object Main {
  def main(args: Array[String]): Unit = {
    println("Starting blas test...")

    val N     = 3
    val alpha = 2
    val incX  = 1
    val incY  = 1
    val xt    = (1, 3, -5)
    val yt    = (4, -2, -1)

    {
      // Using data on the stack - use caution so they remain scope
      val X = stackalloc[CFloat](3)
      X(0) = xt._1
      X(1) = xt._2
      X(2) = xt._3

      val Y = stackalloc[CFloat](3)
      Y(0) = yt._1
      Y(1) = yt._2
      Y(2) = yt._3

      println("X = " + xt + "Y = " + yt)
      println("CFloat routines (single precision)")

      val res2 = cblas_sdot(N, X, incX, Y, incY)
      println(s"""cblas_sdot: $res2""")

      val res = cblas_sdsdot(N, alpha, X, incX, Y, incY)
      println(s"""cblas_sdsdot: $res alpha: $alpha""")
    }

    // Using a Zone and data on the heap
    Zone { implicit z =>
      val X = alloc[CDouble](3)
      X(0) = 1
      X(1) = 3
      X(2) = -5

      val Y = alloc[CDouble](3)
      Y(0) = 4
      Y(1) = -2
      Y(2) = -1

      println("CDouble routines (double precision)")

      val res3 = cblas_ddot(N, X, incX, Y, incY)
      println(s"""cblas_ddot: $res3""")

    }
    println("Done.")
  }
}
