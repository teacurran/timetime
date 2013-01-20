package com.approachingpi.timetime.api.oauth2.provider;

import net.oauth.v2.BaseTokenType;

public class TimeTimeTokenType extends BaseTokenType {

    public static final String MAC = "mac";

    static {
        addExtension(new TimeTimeTokenType());
    }

    protected TimeTimeTokenType() {
    }
}
