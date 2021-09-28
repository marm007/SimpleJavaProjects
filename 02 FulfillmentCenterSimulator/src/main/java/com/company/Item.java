package com.company;

public class Item implements Comparable<Item> {

    private String nazwa;
    private ItemCondition stan;
    private double masa;
    private int ilosc;

    public Item(String nazwa) {
        this.nazwa = nazwa;
    }

    public Item(String nazwa, ItemCondition stan, double masa, int ilosc) {
        this.nazwa = nazwa;
        this.stan = stan;
        this.masa = masa;
        this.ilosc = ilosc;
    }

    public Item(Item item) {
        this.nazwa = item.nazwa;
        this.stan = item.stan;
        this.masa = item.masa;
        this.ilosc = item.ilosc;
    }

    public void print() {
        System.out.println(
                "Item {" + "nazwa='" + nazwa + '\'' + ", stan=" + stan + ", masa=" + masa + ", ilosc=" + ilosc + " }");
    }

    @Override
    public int compareTo(Item o) {
        return this.nazwa.compareTo(o.nazwa);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public ItemCondition getStan() {
        return stan;
    }

    public void setStan(ItemCondition stan) {
        this.stan = stan;
    }

    public double getMasa() {
        return masa;
    }

    public void setMasa(double masa) {
        this.masa = masa;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public void addIlosc(int ilosc) {
        this.ilosc += ilosc;
    }
}
