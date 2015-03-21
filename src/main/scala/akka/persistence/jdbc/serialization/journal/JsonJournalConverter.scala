package akka.persistence.jdbc.serialization.journal

import akka.persistence.PersistentRepr
import akka.serialization.Serialization
import spray.json._

object JsonJournalConverter extends DefaultJsonProtocol {
  case class Persistent(payload: String, persistentRepr: String)

  implicit val persistentFormat = jsonFormat2(Persistent)
}

/**
 * The converter assumes the payload is a String
 */
class JsonJournalConverter extends Base64JournalConverter {
  import JsonJournalConverter._

  override def marshal(value: PersistentRepr)(implicit serialization: Serialization): String =
    Persistent(value.payload.toString, super.marshal(value.withPayload(null).asInstanceOf[PersistentRepr])).toJson.compactPrint

  override def unmarshal(value: String)(implicit serialization: Serialization): PersistentRepr = {
   val p = value.parseJson.convertTo[Persistent]
    super.unmarshal(p.persistentRepr).withPayload(p.payload).asInstanceOf[PersistentRepr]
  }
}
