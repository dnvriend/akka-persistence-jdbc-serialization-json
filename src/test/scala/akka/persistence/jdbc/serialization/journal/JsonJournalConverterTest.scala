package akka.persistence.jdbc.serialization.journal

import akka.actor.ActorSystem
import akka.persistence.PersistentRepr
import akka.serialization.SerializationExtension
import org.scalatest.{BeforeAndAfterAll, Matchers, FlatSpec}
import spray.json._

class JsonJournalConverterTest extends FlatSpec with Matchers with BeforeAndAfterAll with DefaultJsonProtocol {

  case class Person(firstName: String, lastName: String, dateOfBirth: Option[String] = None)

  implicit val personJsonFormat = jsonFormat3(Person)

  val system = ActorSystem("Test")
  implicit val serialization = SerializationExtension(system)

  lazy val jsonJournalConverter = new JsonJournalConverter

  "jsonJournal" should "marshal to json" in {
    val repr = PersistentRepr("hello", 1, "p-1", true, 10, List("c-1", "c-2"), true, null, null, null)
    val jsonStr = jsonJournalConverter.marshal(repr)
    jsonStr shouldBe """{"payload":"hello","persistentRepr":"CgQIABIAEAEaA3AtMSABMAo6A2MtMToDYy0yQAE="}"""
  }

  it should "unmarshal json" in {
    val jsonStr = """{"payload":"hello","persistentRepr":"CgQIABIAEAEaA3AtMSABMAo6A2MtMToDYy0yQAE="}"""
    val newRepr = jsonJournalConverter.unmarshal(jsonStr)
    newRepr.payload shouldBe "hello"
    newRepr.sequenceNr shouldBe 1
    newRepr.persistenceId shouldBe "p-1"
    newRepr.deleted shouldBe true
    newRepr.redeliveries shouldBe 10
    newRepr.confirms shouldBe List("c-1", "c-2")
    newRepr.confirmable shouldBe true
  }

  it should "marshal json as payload" in {
    jsonJournalConverter.marshal(PersistentRepr(Person("John", "Doe", Option("1970-01-01")).toJson.compactPrint, 1)) shouldBe
      """{"payload":"{\"firstName\":\"John\",\"lastName\":\"Doe\",\"dateOfBirth\":\"1970-01-01\"}","persistentRepr":"CgQIABIAEAEgADAAQAA="}"""
  }

  it should "unmarshal the json" in {
    val repr = jsonJournalConverter.unmarshal("""{"payload":"{\"firstName\":\"John\",\"lastName\":\"Doe\",\"dateOfBirth\":\"1970-01-01\"}","persistentRepr":"CgQIABIAEAEgADAAQAA="}""")
    repr.payload.asInstanceOf[String].parseJson.convertTo[Person] shouldBe Person("John", "Doe", Option("1970-01-01"))
  }

  override protected def afterAll(): Unit = {
    system.shutdown()
    system.awaitTermination()
  }
}
