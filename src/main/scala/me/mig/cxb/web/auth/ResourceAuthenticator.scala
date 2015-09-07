package me.mig.cxb.web.auth

import me.mig.fission.dao.OAuth2DAO
import me.mig.fission.dao.models.auth.PersistedAccessToken
import spray.http.HttpHeaders.Authorization
import spray.routing.AuthenticationFailedRejection.{CredentialsMissing, CredentialsRejected}
import spray.routing.authentication._
import spray.routing.{AuthenticationFailedRejection, RequestContext}

import scala.concurrent.Future

trait ResourceAuthenticator {

  import scala.concurrent.ExecutionContext.Implicits.global

  private val REGEXP_AUTHORIZATION = """^\s*(Bearer|Oauth|OAuth2)\s+(\S+)""".r

  def findAuthInfoByAccessToken(token: String): Future[PersistedAccessToken] = Future( OAuth2DAO().getTokenSync(token) )

  def tokenAuthenticator: ContextAuthenticator[PersistedAccessToken] = { ctx =>
    fetchAccessToken(ctx) match {
      case Some(token) =>
        findAuthInfoByAccessToken(token) map {
          case info:PersistedAccessToken if !info.isExpired => Right(info)
          case _ => Left(AuthenticationFailedRejection(CredentialsRejected, List()))
        }
      case _ => Future(Left(AuthenticationFailedRejection(CredentialsMissing, List())))
    }
  }

  def allowedScopes(info: PersistedAccessToken, scopes: String*): Boolean = {
    info.scopes.intersect(scopes).nonEmpty
  }

  private def fetchAccessToken(ctx: RequestContext): Option[String] = {
    val found = ctx.request.header[`Authorization`]
    if (found.isDefined) {
      val matcher = REGEXP_AUTHORIZATION.findFirstMatchIn(found.get.value)
      if (matcher.isDefined && matcher.get.groupCount >= 2) Option(matcher.get.group(2)) else None
    } else ctx.request.uri.query.get("access_token")
  }
}

