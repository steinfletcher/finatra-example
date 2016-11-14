package com.tapatron.data

import com.google.inject.{Inject, Singleton}
import com.typesafe.config.Config
import org.flywaydb.core.Flyway

@Singleton
class FlywayMigration @Inject()(config: Config) {

  def migrate(): Unit = {
    val flyway = new Flyway()

    flyway.setDataSource(
      config.getString("db.default.url"),
      config.getString("db.default.user"),
      config.getString("db.default.password")
    )

    flyway.migrate()
  }
}
