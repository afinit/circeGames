package com.mrka.circegames

import io.circe.syntax._
import io.circe.generic.auto._

case class Thing (
  name: String,
  number: Double
)

case class Deal (
  name: String,
  count: Int
)

case class SomeThings (
  what: String,
  things: Vector[Thing],
  deal: Deal
)


object CirceGame {

}
