package com.tapatron

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.twitter.finatra.json.modules.FinatraJacksonModule
import com.twitter.inject.TwitterModule
import com.typesafe.config.{Config, ConfigFactory}

object DyModule extends TwitterModule {

  override protected def configure(): Unit = {
    bind[Config].toInstance(ConfigFactory.load())
  }
}

object CustomJacksonModule extends FinatraJacksonModule {
  override val additionalJacksonModules = Seq(new JavaTimeModule)
}
