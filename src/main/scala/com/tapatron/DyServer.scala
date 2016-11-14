package com.tapatron

import com.google.inject.Module
import com.tapatron.data.FlywayMigration
import com.tapatron.web.PostController
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import scalikejdbc.config.DBs

object DyServerMain extends DyServer

class DyServer extends HttpServer {

  override def jacksonModule: Module = CustomJacksonModule

  override protected def modules: Seq[Module] = Seq(DyModule)

  override protected def configureHttp(router: HttpRouter): Unit = {
    initDB()

    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PostController]
  }

  def initDB(): Unit = {
    val flyway = injector.instance[FlywayMigration]
    flyway.migrate()
    DBs.setupAll()
  }
}
