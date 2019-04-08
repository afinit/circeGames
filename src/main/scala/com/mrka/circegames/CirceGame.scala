package com.mrka.circegames

import io.circe.{ Encoder, Decoder, HCursor, Json }
import io.circe.syntax._

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

case class AllTheThings (
  stuff: SomeThings,
  why: String
)

case class SmartThing (
  name: String,
  number: Double
)

object SmartThing {
  implicit val encodeSmartThing: Encoder[SmartThing] = new Encoder[SmartThing] {
    final def apply(s: SmartThing): Json = Json.obj(
      ("name", Json.fromString(s.name)),
      ("number", Json.fromDoubleOrNull(s.number))
    )
  }

  implicit val decodeSmartThing: Decoder[SmartThing] = new Decoder[SmartThing] {
    final def apply(c: HCursor): Decoder.Result[SmartThing] = 
      for {
        name <- c.downField("name").as[String]
        number <- c.downField("number").as[Double]
      } yield new SmartThing(name, number)
  }
}

case class SmartDeal (
  name: String,
  count: Int
)

object SmartDeal {
  implicit val encodeSmartDeal: Encoder[SmartDeal] = new Encoder[SmartDeal] {
    final def apply(s: SmartDeal): Json = Json.obj(
      ("name", Json.fromString(s.name)),
      ("count", Json.fromInt(s.count))
    )
  }

  implicit val decodeSmartDeal: Decoder[SmartDeal] = new Decoder[SmartDeal] {
    final def apply(c: HCursor): Decoder.Result[SmartDeal] = 
      for {
        name <- c.downField("name").as[String]
        count <- c.downField("count").as[Int]
      } yield new SmartDeal(name, count)
  }
}

case class SmartSomeThings (
  what: String,
  things: Vector[SmartThing],
  deal: SmartDeal
)

object SmartSomeThings {
  implicit val encodeSmartSomeThings: Encoder[SmartSomeThings] = new Encoder[SmartSomeThings] {
    final def apply(s: SmartSomeThings): Json = Json.obj(
      ("what", Json.fromString(s.what)),
      ("things", Json.fromValues(s.things.map(_.asJson))),
      ("deal", s.deal.asJson)
    )
  }

  implicit val decodeSmartSomeThings: Decoder[SmartSomeThings] = new Decoder[SmartSomeThings] {
    final def apply(c: HCursor): Decoder.Result[SmartSomeThings] = 
      for {
        what <- c.downField("what").as[String]
        things <- c.downField("things").as[Vector[SmartThing]]
        deal <- c.downField("deal").as[SmartDeal]
      } yield new SmartSomeThings(what, things, deal)
  }
}
