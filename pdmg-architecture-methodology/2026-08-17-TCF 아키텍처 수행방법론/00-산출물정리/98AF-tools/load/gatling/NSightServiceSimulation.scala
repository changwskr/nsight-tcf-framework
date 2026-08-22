package nsight.runtime

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
 * Reference Gatling plan for the same HTTP/JSON workload as the JMeter plan.
 * It is intentionally property-driven and must be compiled in the project's
 * approved Gatling build environment before use as runtime evidence.
 */
class NSightServiceSimulation extends Simulation {
  val baseUrl = System.getProperty("BASE_URL", "http://127.0.0.1:8080")
  val path = System.getProperty("PATH", "/api/service")
  val serviceId = System.getProperty("SERVICE_ID", "MG.TEST.sample")
  val bearer = System.getProperty("AUTH_BEARER", "")
  val targetTps = System.getProperty("TARGET_TPS", "600").toDouble
  val durationSec = System.getProperty("DURATION_SEC", "300").toInt
  val bodyFile = System.getProperty("REQUEST_BODY_FILE", "request.json")

  val httpProtocol = http.baseUrl(baseUrl).contentTypeHeader("application/json")
  val scn = scenario("NSIGHT Service")
    .exec(
      http("ServiceId")
        .post(path)
        .header("ServiceId", serviceId)
        .header("Authorization", s"Bearer $bearer")
        .header("X-NSIGHT-GUID", "#{guid}")
        .body(RawFileBody(bodyFile))
        .check(status.is(200))
    )

  setUp(
    scn.inject(
      constantUsersPerSec(targetTps).during(durationSec.seconds)
    )
  ).protocols(httpProtocol)
}
