import java.util.List;

import com.company.FulfilmentCenter;
import com.company.FulfilmentCenterContainer;
import com.company.Item;
import com.company.ItemCondition;

public class Main {

    public static void main(String[] args) {

        FulfilmentCenterContainer fulfilmentCenterContainer = new FulfilmentCenterContainer();

        fulfilmentCenterContainer.addCenter("Kaufland", 2000);
        fulfilmentCenterContainer.addCenter("Aldi", 1500);

        fulfilmentCenterContainer.summary();

        List<FulfilmentCenter> emptyCenters = fulfilmentCenterContainer.findEmpty();

        for (FulfilmentCenter center : emptyCenters)
            center.summary();

        Item bananas = new Item("Banana", ItemCondition.NEW, 0.5, 10);
        Item apple = new Item("Apple", ItemCondition.NEW, 1, 25);
        Item grape = new Item("Grape", ItemCondition.NEW, 2, 60);

        emptyCenters.get(0).addProduct(bananas);
        emptyCenters.get(0).addProduct(bananas);

        emptyCenters.get(1).addProduct(bananas);

        for (FulfilmentCenter center : emptyCenters)
            center.summary();

        System.out.println();

        emptyCenters.get(0).removeProduct(bananas);

        for (FulfilmentCenter center : emptyCenters)
            center.summary();

        System.out.println();

        emptyCenters.get(0).addProduct(bananas);
        emptyCenters.get(1).addProduct(bananas);

        for (FulfilmentCenter center : emptyCenters)
            center.summary();

        fulfilmentCenterContainer.summary();

        System.out.println();

        emptyCenters.get(0).getProduct(bananas);
        emptyCenters.get(0).getProduct(bananas);
        emptyCenters.get(0).getProduct(bananas);
        emptyCenters.get(0).getProduct(bananas);
        emptyCenters.get(0).getProduct(bananas);
        emptyCenters.get(0).getProduct(bananas);
        emptyCenters.get(0).getProduct(bananas);
        emptyCenters.get(0).getProduct(bananas);
        emptyCenters.get(0).getProduct(bananas);

        emptyCenters.get(0).addProduct(apple);
        emptyCenters.get(0).addProduct(grape);

        emptyCenters.get(1).addProduct(apple);
        emptyCenters.get(1).addProduct(apple);
        emptyCenters.get(1).addProduct(grape);

        for (FulfilmentCenter center : emptyCenters)
            center.summary();

        fulfilmentCenterContainer.summary();

        System.out.println();

        System.out.println("Center " + emptyCenters.get(0).getName() + " sortByAmount:");
        for (Item i : emptyCenters.get(0).sortByAmount())
            i.print();

        System.out.println();

        System.out.println("Center " + emptyCenters.get(1).getName() + " sortByName:");
        for (Item i : emptyCenters.get(1).sortByName())
            i.print();

        System.out.println();
        System.out.println();

        for (FulfilmentCenter center : emptyCenters)
            center.summary();

        fulfilmentCenterContainer.summary();

        System.out.println();

        emptyCenters.get(0).max().print();
        emptyCenters.get(1).max().print();

        System.out.println();

        System.out.println("Center " + emptyCenters.get(0).getName() + " searchPartial:");

        for (Item i : emptyCenters.get(0).searchPartial("n"))
            i.print();

        System.out.println();

        System.out.println("Center " + emptyCenters.get(0).getName() + " search:");
        Item i = emptyCenters.get(0).search("apple");
        if (i != null)
            i.print();

        System.out.println();

        System.out.println("Center " + emptyCenters.get(0).getName() + " countByCondition:");
        int count = emptyCenters.get(0).countByCondition(ItemCondition.NEW);

        System.out.println("Count = " + count);
        System.out.println();

        fulfilmentCenterContainer.removeCenter("Aldi");

        fulfilmentCenterContainer.summary();

        System.out.println();

        fulfilmentCenterContainer.removeCenter("Kaufland");

        fulfilmentCenterContainer.summary();

        System.out.println();

        fulfilmentCenterContainer.addCenter("Albert", 2000);

        Item potatoes = new Item("Potatoes", ItemCondition.NEW, 1, 100);

        emptyCenters = fulfilmentCenterContainer.findEmpty();

        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);

        fulfilmentCenterContainer.summary();

        System.out.println();

        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);

        fulfilmentCenterContainer.summary();

        System.out.println();

        emptyCenters.get(0).addProduct(potatoes);
        emptyCenters.get(0).addProduct(potatoes);

        fulfilmentCenterContainer.summary();

        System.out.println();

    }
}
