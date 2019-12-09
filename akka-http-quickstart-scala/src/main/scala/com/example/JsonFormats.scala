package com.example

//#json-formats
import spray.json.DefaultJsonProtocol
import com.example.Models.User


object JsonFormats  {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

}
//#json-formats
