package com.mbass.examples;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
import org.scribe.model.Verb;

public class OAuthApi extends DefaultApi10a {

    @Override
    public String getRequestTokenEndpoint()
    {
        return Defaults.BASE + "oauth/request_token";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return Defaults.BASE + "oauth/access_token";
    }

    @Override
    public String getAuthorizationUrl(Token token) {
        return Defaults.BASE + "oauth/authorize?oauth_token=" + token;
    }

    @Override
    public Verb getRequestTokenVerb() {
        return Verb.GET;
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.GET;
    }

}
