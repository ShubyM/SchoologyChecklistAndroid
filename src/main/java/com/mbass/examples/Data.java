package com.mbass.examples;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.model.Response;

public class Data implements Runnable {

    private String data;
    private String resource;
    public Data(String resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        OAuthService schoology = makeService();
        // OAuthRequest request = new OAuthRequest(Verb.GET, Defaults.BASE + "users/16053918/sections");
        OAuthRequest request = new OAuthRequest(Verb.GET, Defaults.BASE + resource);
        schoology.signRequest(Token.empty(), request);
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        Response response = request.send();
        data = response.getBody();
    }

     private OAuthService makeService() {
        return new ServiceBuilder()
                .provider(new OAuthApi())
                .apiKey(Defaults.SCHOOLOGY_KEY)
                .apiSecret(Defaults.SCHOOLOGY_SECRET)
                .signatureType(SignatureType.Header)
                .build();
    }

    public String getData() {
        return data;
    }

}
