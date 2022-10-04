import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ArrayListProduct {

    public static void main(String[] args) {

        ArrayList<Product> productList = new ArrayList<Product>();
        productList.add(new Product("Notebook", 20.0));
        productList.add(new Product("Pen", 11.0));
        productList.add(new Product("Pencil", 10.5));
        productList.add(new Product("Stapler", 16.0));
        productList.add(new Product("Ruler", 13.0));

        //for loop iteration
        System.out.println("---Before Sorting---");
        for (Product product:productList){
            System.out.println("Product: " + product.getName() + " | Price: " + product.getPrice());
        }

        //sorted by price
        Collections.sort(productList,new PriceComparator());
        System.out.println();
        System.out.println("---Sorted By Price---");
        for (Product product:productList){
            System.out.println("Product: " + product.getName() + " | Price: " + product.getPrice());
        }
    }

}

//implementing Comparator
class PriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return Double.compare(o1.getPrice(), o2.getPrice());
    }

}