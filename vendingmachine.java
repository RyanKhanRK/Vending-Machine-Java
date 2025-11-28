import java.util.*;

public class VendingMachine {

    static class Product {
        String name;
        int price;
        int stock;

        Product(String name, int price, int stock) {
            this.name = name;
            this.price = price;
            this.stock = stock;
        }
    }

    static List<Product> products = new ArrayList<>();

    static int c10 = 10;
    static int c5 = 20;
    static int c2 = 50;
    static int c1 = 100;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadProducts();

        while (true) {
            System.out.println("\n======= VENDING MACHINE =======");
            showProducts();
            System.out.println("0. Exit");
            System.out.print("Select item number: ");

            int choice = getIntInput(sc);
            if (choice == 0) {
                System.out.println("Thank you for using the vending machine!");
                break;
            }

            if (choice < 1 || choice > products.size()) {
                System.out.println("Invalid selection.");
                continue;
            }

            Product p = products.get(choice - 1);

            if (p.stock <= 0) {
                System.out.println("Sorry, this item is out of stock.");
                continue;
            }

            System.out.println("Selected: " + p.name + " — Price: " + p.price + " baht");
            System.out.print("Insert money (accepted: 1, 2, 5, 10, 20, 50, 100, 500, 1000): ");

            int inserted = getIntInput(sc);

            if (!isValidMoney(inserted)) {
                System.out.println("Invalid money inserted.");
                continue;
            }

            if (inserted < p.price) {
                System.out.println("Not enough money inserted.");
                continue;
            }

            int change = inserted - p.price;

            if (change > 0) {
                if (!canGiveChange(change)) {
                    System.out.println("Machine does not have enough change. Please insert the exact amount.");
                    continue;
                }
            }

            // Transaction is successful
            acceptMoney(inserted);
            giveChange(change);
            p.stock--;

            System.out.println("Dispensed: " + p.name);
            if (change > 0) System.out.println("Returned change: " + change + " baht");
        }
    }

    // ------------------ Helper Methods ---------------------

    static void loadProducts() {
        products.add(new Product("Coke", 25, 10));
        products.add(new Product("Water", 10, 15));
        products.add(new Product("Green Tea", 30, 8));
        products.add(new Product("Sprite", 20, 6));
        products.add(new Product("Pepsi", 25, 9));
        products.add(new Product("Oishi Green Tea", 32, 7));
        products.add(new Product("M-150 Energy Drink", 15, 5));
        products.add(new Product("Red Bull", 12, 6));
        products.add(new Product("Nescafé Latte Can", 28, 4));
        products.add(new Product("Milk (Dutch Mill)", 20, 6));
        products.add(new Product("Lay Classic", 30, 8));
        products.add(new Product("Lay Nori Seaweed", 30, 7));
        products.add(new Product("Taokaenoi Seaweed", 25, 6));
        products.add(new Product("Mama Cup Noodles", 18, 10));
        products.add(new Product("Bread (7-Select Sandwich)", 27, 5));
        products.add(new Product("Sausage Bun", 28, 4));
        products.add(new Product("Yogurt", 25, 5));
        products.add(new Product("Chocolate Bar", 20, 8));
        products.add(new Product("Jelly Cup", 12, 6));
        products.add(new Product("Mineral Water (Large)", 15, 10));
    }

    static void showProducts() {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %-25s %5d baht (Stock: %d)%n",
                    (i + 1), p.name, p.price, p.stock);
        }
    }

    static int getIntInput(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.println("Enter a valid number: ");
        }
        return sc.nextInt();
    }

    static boolean isValidMoney(int money) {
        int[] valid = {1, 2, 5, 10, 20, 50, 100, 500, 1000};
        for (int v : valid) if (money == v) return true;
        return false;
    }

    static boolean canGiveChange(int change) {
        int temp10 = c10, temp5 = c5, temp2 = c2, temp1 = c1;

        int[] coinValues = {10, 5, 2, 1};
        int[] coinTemp = {temp10, temp5, temp2, temp1};

        for (int i = 0; i < coinValues.length; i++) {
            int need = change / coinValues[i];
            int used = Math.min(need, coinTemp[i]);
            change -= used * coinValues[i];
        }

        return change == 0;
    }

    static void acceptMoney(int money) {
        int[] notesToCoins = {500, 100, 50, 20};
        for (int n : notesToCoins) {
            if (money >= n) {
                money -= n;
            }
        }
        while (money >= 10) { c10++; money -= 10; }
        while (money >= 5) { c5++; money -= 5; }
        while (money >= 2) { c2++; money -= 2; }
        while (money >= 1) { c1++; money -= 1; }
    }

    static void giveChange(int change) {
        while (change >= 10 && c10 > 0) { c10--; change -= 10; }
        while (change >= 5 && c5 > 0) { c5--; change -= 5; }
        while (change >= 2 && c2 > 0) { c2--; change -= 2; }
        while (change >= 1 && c1 > 0) { c1--; change -= 1; }
    }
}
