package com.oppwa.mobile.connect.dataweb.task;

public interface CardTokenRequestListener {
    void onErrorOccurred();
    void onTokenCardStatusReceived(String paymentStatus);
}
