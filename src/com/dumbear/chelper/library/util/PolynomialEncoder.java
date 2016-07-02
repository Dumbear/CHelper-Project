package com.dumbear.chelper.library.util;

public class PolynomialEncoder {
    private int[] weights;
    private int[] accWeights;

    public PolynomialEncoder(int[] weights) {
        this.weights = weights;
        accWeights = new int[weights.length + 1];
        accWeights[0] = 1;
        for (int i = 0; i < weights.length; ++i) {
            accWeights[i + 1] = accWeights[i] * weights[i];
        }
    }

    public int maxValue() {
        return accWeights[accWeights.length - 1];
    }

    public int maxValue(int index) {
        return weights[index];
    }

    public int get(int s, int index) {
        return s / accWeights[index] % weights[index];
    }

    public int set(int s, int index, int value) {
        return s - get(s, index) * accWeights[index] + value * accWeights[index];
    }

    public int inc(int s, int index) {
        return s + accWeights[index];
    }

    public int dec(int s, int index) {
        return s - accWeights[index];
    }

    public int max(int s1, int s2) {
        int s = 0;
        for (int i = 0; i < weights.length; ++i) {
            s = set(s, i, Math.max(get(s1, i), get(s2, i)));
        }
        return s;
    }

    public int min(int s1, int s2) {
        int s = 0;
        for (int i = 0; i < weights.length; ++i) {
            s = set(s, i, Math.min(get(s1, i), get(s2, i)));
        }
        return s;
    }
}
