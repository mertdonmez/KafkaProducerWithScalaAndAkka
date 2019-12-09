package com.example.Models

import spray.json.{DefaultJsonProtocol, DeserializationException, JsArray, JsNumber, JsObject, JsString, JsValue, RootJsonFormat}

case class User (name: String,
                   age: Int,
                   city: String)

object User extends DefaultJsonProtocol {

  implicit object UserJsonFormat extends RootJsonFormat[User] {
    def write(user: User): JsValue = JsObject(
      "name" -> JsString(user.name),
      "age" -> JsNumber(user.age),
      "city" -> JsString(user.city)
    )

    def read(value: JsValue) = {
      value.asJsObject.getFields("name", "age", "city") match {
        case Seq(JsString(name), JsNumber(age), JsString(city)) =>
          new User(name, age.toInt, city)
        case _ => throw new DeserializationException("User expected")
      }
    }
  }

}


