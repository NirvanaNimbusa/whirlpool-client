package com.samourai.whirlpool.client.wallet;

import com.samourai.api.client.SamouraiApi;
import com.samourai.http.client.IHttpClient;
import com.samourai.stomp.client.IStompClient;
import com.samourai.wallet.util.FormatsUtilGeneric;
import com.samourai.whirlpool.client.wallet.beans.WhirlpoolServer;
import com.samourai.whirlpool.client.wallet.pushTx.PushTxService;
import com.samourai.whirlpool.client.whirlpool.WhirlpoolClientConfig;
import java.util.Collection;
import org.bitcoinj.core.NetworkParameters;

public class WhirlpoolWalletConfig extends WhirlpoolClientConfig {
  private String feeXpub;
  private int maxClients;
  private int clientDelay;
  private boolean autoTx0;
  private boolean autoMix;
  private Collection<String> poolIdsByPriority;

  private SamouraiApi samouraiApi;
  private PushTxService pushTxService;
  private int tx0Delay;
  private Integer tx0MaxOutputs;
  private int refreshUtxoDelay;
  private int mixsTarget;
  private int persistDelay;
  private int persistCleanDelay;

  public WhirlpoolWalletConfig(
      IHttpClient httpClient,
      IStompClient stompClient,
      WhirlpoolServer whirlpoolServer,
      boolean useOnion) {
    this(
        httpClient,
        stompClient,
        useOnion && whirlpoolServer.getServerOnion() != null
            ? whirlpoolServer.getServerOnion()
            : whirlpoolServer.getServerUrl(),
        whirlpoolServer.getParams(),
        whirlpoolServer.isSsl(),
        whirlpoolServer.getFeeData());
  }

  public WhirlpoolWalletConfig(
      IHttpClient httpClient,
      IStompClient stompClient,
      String server,
      NetworkParameters params,
      boolean ssl,
      String feeXpub) {
    super(httpClient, stompClient, server, params, ssl);

    this.feeXpub = feeXpub;

    // default settings
    this.maxClients = 1;
    this.clientDelay = 30;
    this.autoTx0 = false;
    this.autoMix = false;
    this.poolIdsByPriority = null;

    // technical settings
    boolean isTestnet = FormatsUtilGeneric.getInstance().isTestNet(params);
    this.samouraiApi = new SamouraiApi(httpClient, isTestnet); // single instance to SamouraiApi
    this.pushTxService = samouraiApi; // use backend as default push service
    this.tx0Delay = 30;
    this.tx0MaxOutputs = null; // spend whole utxo when possible
    this.refreshUtxoDelay = 60; // 1min
    this.mixsTarget = 1;
    this.persistDelay = 2; // 2s
    this.persistCleanDelay = 300; // 5min
  }

  public String getFeeXpub() {
    return feeXpub;
  }

  public int getMaxClients() {
    return maxClients;
  }

  public void setMaxClients(int maxClients) {
    this.maxClients = maxClients;
  }

  public int getClientDelay() {
    return clientDelay;
  }

  public void setClientDelay(int clientDelay) {
    this.clientDelay = clientDelay;
  }

  public boolean isAutoTx0() {
    return autoTx0;
  }

  public void setAutoTx0(boolean autoTx0) {
    this.autoTx0 = autoTx0;
  }

  public boolean isAutoMix() {
    return autoMix;
  }

  public void setAutoMix(boolean autoMix) {
    this.autoMix = autoMix;
  }

  public Collection<String> getPoolIdsByPriority() {
    return poolIdsByPriority;
  }

  public void setPoolIdsByPriority(Collection<String> poolIdsByPriority) {
    this.poolIdsByPriority = poolIdsByPriority;
  }

  public PushTxService getPushTxService() {
    return pushTxService;
  }

  public void setPushTxService(PushTxService pushTxService) {
    this.pushTxService = pushTxService;
  }

  public SamouraiApi getSamouraiApi() {
    return samouraiApi;
  }

  public int getTx0Delay() {
    return tx0Delay;
  }

  public void setTx0Delay(int tx0Delay) {
    this.tx0Delay = tx0Delay;
  }

  public Integer getTx0MaxOutputs() {
    return tx0MaxOutputs;
  }

  public void setTx0MaxOutputs(Integer tx0MaxOutputs) {
    this.tx0MaxOutputs = tx0MaxOutputs;
  }

  public int getRefreshUtxoDelay() {
    return refreshUtxoDelay;
  }

  public void setRefreshUtxoDelay(int refreshUtxoDelay) {
    this.refreshUtxoDelay = refreshUtxoDelay;
  }

  public int getMixsTarget() {
    return mixsTarget;
  }

  public void setMixsTarget(int mixsTarget) {
    this.mixsTarget = mixsTarget;
  }

  public int getPersistDelay() {
    return persistDelay;
  }

  public void setPersistDelay(int persistDelay) {
    this.persistDelay = persistDelay;
  }

  public int getPersistCleanDelay() {
    return persistCleanDelay;
  }

  public void setPersistCleanDelay(int persistCleanDelay) {
    this.persistCleanDelay = persistCleanDelay;
  }
}
