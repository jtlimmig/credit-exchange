package me.mig.cxb.web.auth

import me.mig.fission.dao.OAuth2DAO
import me.mig.fission.dao.models.auth.PersistedAccessToken
import spray.http.HttpHeaders.Authorization
import spray.routing.{RequestContext, AuthenticationFailedRejection}
import spray.routing.AuthenticationFailedRejection.{CredentialsMissing, CredentialsRejected}
import spray.routing.authentication._

import scala.concurrent.Future

trait ResourceAuthenticator {

  import scala.concurrent.ExecutionContext.Implicits.global

  //private val REGEXP_AUTHORIZATION = """^\s*(OAuth|OAuth2|Bearer)\s+(\S*)""".r
  private val REGEXP_AUTHORIZATION = """^\s*(Bearer|Oauth|OAuth2)\s+(\S+)""".r

  def findAuthInfoByAccessToken(token: String): Option[PersistedAccessToken] = {
    val pt = OAuth2DAO().getTokenSync(token)
    if (pt != null && pt.isExpired) Some(pt) else None
  }

  def tokenAuthenticator: ContextAuthenticator[PersistedAccessToken] = { ctx =>
    fetchAccessToken(ctx) match {
      case Some(token) =>
        findAuthInfoByAccessToken(token) match {
          case Some(info) => Future(Right(info))
          case None => Future(Left(AuthenticationFailedRejection(CredentialsRejected, List())))
        }
      case None => Future(Left(AuthenticationFailedRejection(CredentialsMissing, List())))
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

