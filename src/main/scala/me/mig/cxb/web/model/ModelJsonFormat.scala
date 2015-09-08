package me.mig.cxb.web.model

import spray.json.DefaultJsonProtocol

object ModelJsonFormat extends DefaultJsonProtocol {
  implicit val PackageFormat = jsonFormat13(Package)
  implicit val TransactionFormat = jsonFormat17(Transaction)
}
