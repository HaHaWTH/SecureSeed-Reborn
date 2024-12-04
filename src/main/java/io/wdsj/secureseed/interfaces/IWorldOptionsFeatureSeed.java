package io.wdsj.secureseed.interfaces;

public interface IWorldOptionsFeatureSeed {
    long[] secureSeed$featureSeed();
    void secureSeed$setFeatureSeed(long[] seed);
    String secureSeed$featureSeedSerialize();
}
