// src/script/scala/progscala3/implicits/InfixTypeRevisited.scala
import scala.annotation.{alpha, infix}

@alpha("BangBang") case class !![A,B](a: A, b: B)     // <1>
extension [A,B] (a: A):                               // <2>
	def !!(b: B): A !! B = !!(a, b)

val ab1: Int !! String = 1 !! "one"                   // <3>
val ab2: Int !! String = !!(1, "one")

@infix case class bangbang[A,B](a: A, b: B)
extension [A,B] (a: A):
	def bangbang(b: B): A bangbang B = bangbang(a, b)
val ab1: Int bangbang String = 1 bangbang "one"
val ab2: Int bangbang String = bangbang(1, "one")
