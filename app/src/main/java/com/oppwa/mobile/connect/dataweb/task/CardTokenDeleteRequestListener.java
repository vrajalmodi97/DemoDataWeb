package com.oppwa.mobile.connect.dataweb.task;

public interface CardTokenDeleteRequestListener {
    void onErrorOccurred();
    void onTokenCardDeleteReceived(String cardId);
}
