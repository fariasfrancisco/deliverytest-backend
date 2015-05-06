package com.safira.api.responses;

/**
 * Created by Francisco on 02/05/2015.
 */
public class TokenVerificationResult {

    private boolean result;

    public TokenVerificationResult() {
    }

    public TokenVerificationResult(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
