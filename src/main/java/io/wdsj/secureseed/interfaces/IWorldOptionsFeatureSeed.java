package io.wdsj.secureseed.interfaces;

public interface IWorldOptionsFeatureSeed {
    long[] featureSeed();
    void setFeatureSeed(long[] seed);
    String featureSeedSerialize();
}
