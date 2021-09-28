package com.company;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FulfilmentCenter {

    private String nazwaMagazynu;
    private List<Item> listaProduktow;
    private double maxPojemnosc;

    public FulfilmentCenter(String nazwaMagazynu, double maxPojemnosc) {
        this.listaProduktow = new LinkedList<>();
        this.nazwaMagazynu = nazwaMagazynu;
        this.maxPojemnosc = maxPojemnosc;
    }

    public void addProduct(Item i) {

        double obecnaPojemnosc = 0;

        for (Item item : listaProduktow) {
            obecnaPojemnosc += item.getIlosc() * item.getMasa();
        }

        if (obecnaPojemnosc + i.getMasa() * i.getIlosc() > maxPojemnosc) {
            System.err.println("Nie można dodać więcej produktów! Max pojemność zostałaby przekroczona.");
            return;
        }

        if (listaProduktow.stream().anyMatch(o -> o.compareTo(i) == 0)) {
            ((listaProduktow.stream().filter(o -> o.compareTo(i) == 0).findFirst().get())).addIlosc(i.getIlosc());
        } else {
            Item item = new Item(i);

            listaProduktow.add(item);
        }

    }

    public void getProduct(Item i) {

        Item toRemove = null;

        for (Item item : listaProduktow) {
            if (item.compareTo(i) == 0) {
                if (item.getIlosc() - 1 <= 0) {
                    toRemove = item;
                } else {
                    item.setIlosc(item.getIlosc() - 1);
                }
            }
        }

        if (toRemove != null)
            listaProduktow.remove(toRemove);

    }

    public void removeProduct(Item i) {

        Item item = listaProduktow.stream().filter(o -> o.compareTo(i) == 0).findAny().orElse(null);

        if (item != null) {
            listaProduktow.remove(item);
        }
    }

    public Item search(String nazwa) {
        int index = Collections.binarySearch(listaProduktow, new Item(nazwa), Comparator.comparing(Item::getNazwa));

        if (index < 0)
            return null;

        return listaProduktow.get(index);
    }

    public List<Item> searchPartial(String partial) {

        List<Item> itemMatches = new LinkedList<>();

        for (Item item : listaProduktow) {
            if (item.getNazwa().matches("(.*)" + partial + "(.*)"))
                itemMatches.add(item);

        }

        return itemMatches;
    }

    public int countByCondition(ItemCondition condition) {
        int ilosc = 0;

        for (Item item : listaProduktow)
            if (item.getStan() == condition)
                ilosc++;

        return ilosc;
    }

    public void summary() {

        if (isEmpty()) {
            System.out.println("Center " + this.nazwaMagazynu + " is empty!\n");
            return;
        }
        System.out.println("Center " + this.nazwaMagazynu + " contains");
        for (Item item : listaProduktow)
            item.print();

        System.out.println();

    }

    public List<Item> sortByName() {

        List<Item> sortedListaProduktow = new LinkedList<>(listaProduktow);

        sortedListaProduktow.sort(Item::compareTo);

        return sortedListaProduktow;
    }

    public List<Item> sortByAmount() {
        List<Item> sortedListaProduktow = new LinkedList<>(listaProduktow);

        sortedListaProduktow.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Integer.compare(o2.getIlosc(), o1.getIlosc());
            }
        });

        return sortedListaProduktow;
    }

    public Item max() {
        return Collections.max(listaProduktow, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Integer.compare(o1.getIlosc(), o2.getIlosc());

            }
        });
    }

    public double getPercentageFill() {

        double currentFill = 0;
        for (Item item : listaProduktow) {
            currentFill += item.getIlosc() * item.getMasa();
        }

        return currentFill / this.maxPojemnosc * 100;

    }

    public boolean isEmpty() {
        return listaProduktow.size() == 0;
    }

    public String getName() {
        return this.nazwaMagazynu;
    }
}
