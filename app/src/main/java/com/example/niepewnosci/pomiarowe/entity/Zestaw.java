package com.example.niepewnosci.pomiarowe.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 05.01.15.
 */
public class Zestaw implements Serializable, Runnable{

    List<Float> dane;
    float deviceUncertainty;
    float average;
    List<Float> differencesFromAverage;
    List<Float> differencesFromAveragePower;
    float sumDifferencesFromAveragePower;
    float aUncertainty;
    float bUncertainty;
    float cUncertainty;


    public List<Float> getDane() {
        return dane;
    }

    public Float getDeviceUncertainty() {
        return deviceUncertainty;
    }

    public String getDifferencesFromAverage() {
        return StringUtils.join(differencesFromAverage.toString(),"\n");
    }

    public String getDifferencesFromAveragePower() {
        return StringUtils.join(differencesFromAveragePower.toString(),"\n");
    }

    public String getSumDifferencesFromAveragePower() {
        return String.valueOf(sumDifferencesFromAveragePower);
    }

    public String getaUncertainty() {
        return String.valueOf(aUncertainty);
    }

    public String getbUncertainty() {
        return String.valueOf(bUncertainty);
    }

    public String getcUncertainty() {
        return String.valueOf(cUncertainty);
    }
    public String getAverageAsString(){
        return String.valueOf(average);
    }
    public Zestaw(List<Float> dane) {
        this.dane = dane;
    }

    public void setDeviceUncertainty(float deviceUncertainty) {
        this.deviceUncertainty = deviceUncertainty;
    }

    public String getDaneAsString() {
        return StringUtils.join(dane.toString(),"\n");
    }

    public float getAverage(){
        float suma = 0;
        for(float liczba : dane) {
            suma += liczba;
        }
        return suma / dane.size();
    }

    public List<Float> getDifferencesFromAverage(float average) {
        List<Float> differencesFromAverage = new ArrayList<Float>();
        for(float liczba : dane) {
            differencesFromAverage.add(liczba - average);
        }
        return differencesFromAverage;
    }

    public List<Float> getDifferencesFromAveragePower(List<Float> differencesFromAverage) {
        List<Float> differencesFromAveragePower = new ArrayList<Float>();
        for(float liczba : differencesFromAverage)
        {
            differencesFromAveragePower.add(liczba*liczba);
        }
        return differencesFromAveragePower;
    }

    public float getDifferencesFromAveragePowerSum(List<Float> differencesFromAveragePower) {
        float sumPowers = 0;
        for(float liczba : differencesFromAveragePower)
        {
            sumPowers += liczba;
        }
        return sumPowers;
    }

    public float getAUncertainty(float sumPowers, int dataSize) {
        return (float) Math.sqrt((sumPowers/(dataSize*(dataSize-2))));
    }

    public float getBUncertainty() {
        return (float) Math.pow(this.deviceUncertainty,1/3);
    }

    public float getCUncertainty(float AUncertainty, float BUncertainty) {
        return (float) Math.sqrt(AUncertainty*AUncertainty+BUncertainty*BUncertainty);
    }

    @Override
    public void run() {
        average = getAverage();
        differencesFromAverage = getDifferencesFromAverage(average);
        differencesFromAveragePower = getDifferencesFromAveragePower(differencesFromAverage);
        sumDifferencesFromAveragePower = getDifferencesFromAveragePowerSum(differencesFromAveragePower);
        aUncertainty = getAUncertainty(sumDifferencesFromAveragePower, dane.size());
        bUncertainty = getBUncertainty();
        cUncertainty = getCUncertainty(aUncertainty, bUncertainty);
    }
}
