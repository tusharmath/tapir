package sttp.tapir.server.vertx

import io.vertx.core.Vertx
import io.vertx.ext.web.{Route, Router}
import sttp.tapir.server.ServerEndpoint

import scala.concurrent.Future

class VertxTestServerBlockingInterpreter(vertx: Vertx) extends VertxTestServerInterpreter(vertx) {
  override def route(es: List[ServerEndpoint[Any, Future]], interceptors: Interceptors): Router => Route = { router =>
    val options: VertxFutureServerOptions = interceptors(VertxFutureServerOptions.customiseInterceptors).options
    val interpreter = VertxFutureServerInterpreter(options)
    es.map(interpreter.blockingRoute(_)(router)).last
  }
}
