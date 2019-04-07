package com.mrka.circegames

import org.scalatest.{ FunSpec, Matchers }
import io.circe.Json
import io.circe.syntax._
import io.circe.generic.auto._

class CirceGameSpec extends FunSpec with Matchers {
  
  val thing1Name = "bob"
  val thing1Number = 5
  val thing1 = Thing(thing1Name, thing1Number)
  val thing2Name = "nancy"
  val thing2Number = 1.01
  val thing2 = Thing(thing2Name, thing2Number)

  val deal1Name = "magpie"
  val deal1Count = 18
  val deal1 = Deal(deal1Name, deal1Count)
  val deal2Name = "jerry"
  val deal2Count = 4
  val deal2 = Deal(deal2Name, deal2Count)
 
  val somethingsName = "handful"
  val somethings = SomeThings(
    somethingsName,
    Vector(thing1, thing2),
    deal1
  )

  describe("Run simple circe encoder") {
    it("properly encodes things") {
      val thing1JsonStringActual = """{"name":"bob","number":5.0}"""
      println(thing1.asJson)
      println(thing1.asJson.noSpaces)
      thing1.asJson.noSpaces.toString shouldBe thing1JsonStringActual
    }
  }
}
