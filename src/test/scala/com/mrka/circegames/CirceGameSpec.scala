package com.mrka.circegames

import org.scalatest.{ FunSpec, Matchers }
import io.circe.Json
import io.circe.syntax._
import io.circe.generic.auto._
import io.circe.parser.decode


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

  val somethingsWhat = "handful"
  val somethings = SomeThings(
    somethingsWhat,
    Vector(thing1, thing2),
    deal1
  )

  describe("Run implicit encoder on simple/complex case classes") {
    it("properly encodes things") {
      val thing1JsonStringExpected = """{"name":"bob","number":5.0}"""
      thing1.asJson.noSpaces.toString shouldBe thing1JsonStringExpected
    }

    it("properly encodes deals") {
      val deal1JsonStringExpected = """{"name":"magpie","count":18}"""
      deal1.asJson.noSpaces.toString shouldBe deal1JsonStringExpected
    }

    it("properly encodes SomeThings") {
      val somethingsJsonStringExpected = """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}}"""
      somethings.asJson.noSpaces.toString shouldBe somethingsJsonStringExpected
    }
  }

  describe("Run implicit decoder on simple/complex case classes") {
    it("properly decodes things") {
      val thingString = """{"name":"bob","number":5.0}"""
      val actual = decode[Thing](thingString)

      actual shouldBe Right(thing1)
    }
  }

}
