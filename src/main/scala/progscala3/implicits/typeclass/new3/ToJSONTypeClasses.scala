// src/main/scala/progscala3/implicits/typeclass/new3/ToJSONTypeClasses.scala
package progscala3.implicits.typeclass.new3

import progscala3.introscala.shapes.{Point, Shape, Circle, Rectangle, Triangle}
import progscala3.implicits.json.ToJSON

given pointToJSON as ToJSON[Point]:
  extension (point: Point) def toJSON(name: String, level: Int): String =
    val (outdent, indent) = indentation(level)
    s""""$name": {
      |${indent}"x": "${point.x}",
      |${indent}"y": "${point.y}"
      |$outdent}""".stripMargin

given ToJSON[Shape]:
  extension (shape: Shape) def toJSON(name: String, level: Int): String =
    shape match
      case c: Circle    => circleToJSON.extension_toJSON(c)(name, level)
      case r: Rectangle => rectangleToJSON.extension_toJSON(r)(name, level)
      case t: Triangle  => triangleToJSON.extension_toJSON(t)(name, level)

given circleToJSON as ToJSON[Circle]:
  extension (circle: Circle) def toJSON(name: String, level: Int): String =
    val (outdent, indent) = indentation(level)
    s""""$name": {
      |${indent}${circle.center.toJSON("center", level + 1)},
      |${indent}"radius": ${circle.radius}
      |$outdent}""".stripMargin

given rectangleToJSON as ToJSON[Rectangle]:
  extension (rect: Rectangle) def toJSON(name: String, level: Int): String =
    val (outdent, indent) = indentation(level)
    s""""$name": {
      |${indent}${rect.lowerLeft.toJSON("lowerLeft", level + 1)},
      |${indent}"height":    ${rect.height}
      |${indent}"width":     ${rect.width}
      |$outdent}""".stripMargin

given triangleToJSON as ToJSON[Triangle]:
  extension (tri: Triangle) def toJSON(name: String, level: Int): String =
    val (outdent, indent) = indentation(level)
    s""""$name": {
      |${indent}${tri.point1.toJSON("point1", level + 1)},
      |${indent}${tri.point2.toJSON("point2", level + 1)},
      |${indent}${tri.point3.toJSON("point3", level + 1)},
      |$outdent}""".stripMargin

@main def TryJSONTypeClasses() =
  val c = Circle(Point(1.0,2.0), 1.0)
  val r = Rectangle(Point(2.0,3.0), 2, 5)
  val t = Triangle(Point(0.0,0.0), Point(2.0,0.0), Point(1.0,2.0))
  println("==== Use shape.toJSON:")
  Seq(c, r, t).foreach(s => println(s.toJSON("shape", 0)))
  println("==== call toJSON on each shape explicitly:")
  println(c.toJSON("circle", 0))
  println(r.toJSON("rectangle", 0))
  println(t.toJSON("triangle", 0))
