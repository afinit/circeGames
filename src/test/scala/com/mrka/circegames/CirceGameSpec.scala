package com.mrka.circegames

import org.scalatest.{ FunSpec, Matchers }
import io.circe.Json
import io.circe.syntax._
import io.circe.parser.decode


class CirceGameSpec extends FunSpec with Matchers {
  
  val thing1Name = "bob"
  val thing1Number = 5
  val thing1 = Thing(thing1Name, thing1Number)
  val smartThing1 = SmartThing(thing1Name, thing1Number)
  val thing2Name = "nancy"
  val thing2Number = 1.01
  val thing2 = Thing(thing2Name, thing2Number)
  val smartThing2 = SmartThing(thing2Name, thing2Number)

  val deal1Name = "magpie"
  val deal1Count = 18
  val deal1 = Deal(deal1Name, deal1Count)
  val smartDeal1 = SmartDeal(deal1Name, deal1Count)
  val deal2Name = "jerry"
  val deal2Count = 4
  val smartDeal2 = SmartDeal(deal2Name, deal2Count)

  val somethingsWhat = "handful"
  val somethings = SomeThings(
    somethingsWhat,
    Vector(thing1, thing2),
    deal1
  )
  val smartSomethings = SmartSomeThings(
    somethingsWhat,
    Vector(smartThing1, smartThing2),
    smartDeal1
  )

  val allTheThingsWhy = "bc"
  val allTheThings = AllTheThings(
    stuff = somethings,
    why = allTheThingsWhy
  )
  val smartAllTheThings = SmartAllTheThings(
    stuff = smartSomethings,
    why = allTheThingsWhy
  )

  describe("Run autoderived encoder on simple/complex case classes") {
    import io.circe.generic.auto._
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

    it("properly encodes AllTheThings") {
      val allTheThingsJsonStringExpected = """{"stuff":""" +
        """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}},""" +
        """"why":"bc"}"""

      allTheThings.asJson.noSpaces.toString shouldBe allTheThingsJsonStringExpected
    }
  }

  describe("Run autoderived decoder on simple/complex case classes") {
    import io.circe.generic.auto._
    it("properly decodes Things") {
      val thingString = """{"name":"bob","number":5.0}"""
      val actual = decode[Thing](thingString)

      actual shouldBe Right(thing1)
    }
    
    it("properly decodes Deals") {
      val dealString = """{"name":"magpie","count":18}"""
      val actual = decode[Deal](dealString)

      actual shouldBe Right(deal1)
    }

    it("properly decodes SomeThings") {
      val someThingString = """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}}"""
      val actual = decode[SomeThings](someThingString)

      actual shouldBe Right(somethings)
    }

    it("properly decodes AllTheThings") {
      val allTheThingsString = """{"stuff":""" +
        """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}},""" +
        """"why":"bc"}"""
      val actual = decode[AllTheThings](allTheThingsString)

      actual shouldBe Right(allTheThings)
    }

    it("properly decodes AllTheThings out of order") {
      val allTheThingsString = """{"why":"bc","stuff":""" +
        """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}}}"""
      val actual = decode[AllTheThings](allTheThingsString)

      actual shouldBe Right(allTheThings)
    }

    it("properly decodes AllTheThings with extra info") {
      val allTheThingsString = """{"why":"bc","stuff":""" +
        """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}},""" +
        """"someOtherField":34}"""
      val actual = decode[AllTheThings](allTheThingsString)

      actual shouldBe Right(allTheThings)
    }

  }

  describe("Run custom codec encoder on simple/complex Json") {
    it("properly encodes SmartThings") {
      import SmartThing._
      val smartThing1JsonStringExpected = """{"name":"bob","number":5.0}"""
      smartThing1.asJson.noSpaces.toString shouldBe smartThing1JsonStringExpected
    }

    it("properly encodes SmartDeals") {
      import SmartDeal._
      val smartDeal1JsonStringExpected = """{"name":"magpie","count":18}"""
      smartDeal1.asJson.noSpaces.toString shouldBe smartDeal1JsonStringExpected
    }

    it("properly encodes SmartSomeThings") {
      import SmartSomeThings._
      val smartSomethingsJsonStringExpected = """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}}"""

      smartSomethings.asJson.noSpaces.toString shouldBe smartSomethingsJsonStringExpected
    }

    it("properly encodes SmartAllTheThings") {
      val smartAllTheThingsJsonStringExpected = """{"stuff":""" +
        """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}},""" +
        """"why":"bc"}"""

      smartAllTheThings.asJson.noSpaces.toString shouldBe smartAllTheThingsJsonStringExpected
    }
  }

  describe("Run custom codec decoder on simple/complex Json") {
    it("properly decodes SmartThings") {
      import SmartThing._
      val smartThingString = """{"name":"bob","number":5.0}"""
      val actual = decode[SmartThing](smartThingString)

      actual shouldBe Right(smartThing1)
    }

    it("properly decodes SmartDeals") {
      import SmartDeal._
      val smartDealString = """{"name":"magpie","count":18}"""
      val actual = decode[SmartDeal](smartDealString)

      actual shouldBe Right(smartDeal1)
    }

    it("properly decodes SmartSomeThings") {
      import SmartSomeThings._
      val smartSomeThingString = """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}}"""
      val actual = decode[SmartSomeThings](smartSomeThingString)

      actual shouldBe Right(smartSomethings)
    }

    it("properly decodes SmartAllTheThings") {
      val smartAllTheThingsString = """{"stuff":""" +
        """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}},""" +
        """"why":"bc"}"""
      val actual = decode[SmartAllTheThings](smartAllTheThingsString)

      actual shouldBe Right(smartAllTheThings)
    }

    it("properly decodes SmartAllTheThings out of order") {
      val smartAllTheThingsString = """{"why":"bc","stuff":""" +
        """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}}}"""
      val actual = decode[SmartAllTheThings](smartAllTheThingsString)

      actual shouldBe Right(smartAllTheThings)
    }

    it("properly decodes SmartAllTheThings with extra info") {
      val smartAllTheThingsString = """{"why":"bc","stuff":""" +
        """{"what":"handful",""" +
        """"things":[{"name":"bob","number":5.0},""" +
        """{"name":"nancy","number":1.01}],""" +
        """"deal":{"name":"magpie","count":18}},""" +
        """"someOtherField":34}"""
      val actual = decode[SmartAllTheThings](smartAllTheThingsString)

      actual shouldBe Right(smartAllTheThings)
    }
  }

  describe("From file json") {
    it("properly reads SomeThings from file (really just reads file into string and parses)") {
      val jsonString = scala.io.Source.fromResource("someThings.json").getLines().mkString("")
      val actual = decode[SmartSomeThings](jsonString)

      actual shouldBe Right(smartSomethings)
    }
  }

}
