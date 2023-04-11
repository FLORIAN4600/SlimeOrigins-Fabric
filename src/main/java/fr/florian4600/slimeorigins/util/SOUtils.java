package fr.florian4600.slimeorigins.util;

import virtuoel.pehkui.api.ScaleData;

public class SOUtils {

    public SOUtils() {

    }

    public static void applyScaleMultiplier(ScaleData scaleData, float power, float multiplier, boolean inverted) {
        scaleData.setScale(applyMultiplier(scaleData.getScale(), power, multiplier, inverted));
    }

    public static void revertScaleMultiplier(ScaleData scaleData, double instanceAttr, float multiplier, long power, boolean inverted) {
        if(multiplier <= 0f) multiplier = 1f;
        scaleData.setScale(inverted ? scaleData.getScale()*(float) (instanceAttr/Math.pow(multiplier, power)) : scaleData.getScale()/(float) (instanceAttr/Math.pow(multiplier, power)));
    }

    public static float applyMultiplier(float original, float power, float multiplier, boolean inverted) {
        if(power <= 0f) power = 1f;
        if(multiplier <= 0f) multiplier = 1f;
        return (inverted ? original*power*multiplier : original/(power*multiplier));
    }

    public static double applyModifier(double original, double power, double multiplier, boolean inverted) {
        if(power <= 0f) power = 1f;
        if(multiplier <= 0f) multiplier = 1f;
        return (inverted ? original*power*multiplier : original/(power*multiplier)) - original;
    }

}
