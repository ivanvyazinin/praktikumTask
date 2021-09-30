package com.google.main;

public enum Workload {
    MAXIMUM(1.6f),
    HIGH(1.4f),
    MEDIUM(1.2f),
    DEFAULT(1f);

    private float ratio;

    Workload(float ratio) {
        this.ratio = ratio;
    }

    public float getRatio() {
        return ratio;
    }
}
