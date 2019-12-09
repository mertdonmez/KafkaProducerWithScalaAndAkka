package com.example.Services

import com.example.Models.User
import com.example.Services.ProduceeService
class UserService(messageProducerService: ProduceeService) {


  def sendUserToQueue(user: User) = {

    messageProducerService.writeToKafka("user_topic1" , user)
  }





}
