import java.util.Scanner;
// Removed Random import as it is no longer needed

class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }
}

public class GroceryShopping {
    
    // --- CHALLENGE METHODS ---
    public static int searchItem(String[] items, String itemName) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return -1;
    }

    public static float calculateAveragePrice(float[] prices) {
        float sum = 0;
        for (float p : prices) {
            sum += p;
        }
        return sum / prices.length;
    }

    public static void filterItemsBelowPrice(String[] items, float[] prices, float threshold) {
        System.out.println("\n--- Discovery: Items below $" + threshold + " ---");
        for (int i = 0; i < items.length; i++) {
            if (prices[i] < threshold) {
                System.out.println(items[i] + ": $" + prices[i]);
            }
        }
    }

    // --- MAIN APP ---
    public static void main(String[] args) {
        // Arrays
        String[] item = new String[25];
        float[] price = new float[25];
        int[] stock = new int[25];
        // Removed discount array (per-item discount logic removed)

        // Setup Data
        String[] baseNames = {"Apple", "Banana", "Bread", "Milk", "Eggs", "Cheese", "Chicken", "Rice", "Pasta", "Tomato", "Soda", "Water"};
        float[] basePrices = {0.50f, 0.30f, 2.00f, 1.50f, 2.50f, 3.00f, 5.00f, 1.00f, 1.20f, 0.80f, 1.50f, 1.00f};

        for(int i = 0; i < 25; i++) {
            if (i < baseNames.length) {
                item[i] = baseNames[i];
                price[i] = basePrices[i];
            } else {
                item[i] = "Item" + (i + 1);
                price[i] = 2.00f; 
            }
            
            stock[i] = 100; // Keeping stock at 100 per your previous request
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Grocery Store!");

        boolean storeOpen = true;
        while (storeOpen) {
            System.out.println("\n--- New Customer ---");
            float totalBill = 0.0f; // Reset bill for new customer

            while (true) {
                try {
                    System.out.println("Enter item name (or type 'finish'):");
                    String inputItem = scanner.nextLine();

                    if (inputItem.equalsIgnoreCase("finish")) break;

                    int index = searchItem(item, inputItem);

                    if (index == -1) {
                        throw new ItemNotFoundException("Item not found.");
                    }

                    System.out.println("Enter quantity for " + item[index] + " (Price: $" + price[index] + "):");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

                    if (quantity <= 0) {
                        System.out.println("Error: Positive numbers only.");
                        continue;
                    }
                    if (stock[index] < quantity) {
                        System.out.println("Error: Out of stock (Only " + stock[index] + " left).");
                        continue;
                    }

                    // Standard calculation (No random discount)
                    float cost = price[index] * quantity;
                    totalBill += cost;
                    stock[index] -= quantity;

                    System.out.printf("Added %d x %s. Subtotal: $%.2f%n", quantity, item[index], totalBill);

                } catch (ItemNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    scanner.nextLine();
                }
            }

            // --- RECEIPT & DISCOUNT LOGIC ---
            
            System.out.println("\n--- Receipt ---");
            System.out.printf("Original Total:   $%.2f%n", totalBill);

            // Reverted Logic: If total exceeds $100, apply 10%
            if (totalBill > 100.0f) {
                float discountAmount = totalBill * 0.10f; // 10% calc
                float finalTotal = totalBill - discountAmount;
                
                System.out.println("Discount Applied: 10% (Orders over $100)");
                System.out.printf("Discounted Total: $%.2f%n", finalTotal);
            } else {
                System.out.println("Discount Applied: None (Spend over $100 to get 10% off)");
                System.out.printf("Final Total:      $%.2f%n", totalBill);
            }
            
            // Challenge Method Test
            filterItemsBelowPrice(item, price, 1.00f);

            System.out.println("\nType 'exit' to close, or Enter to continue:");
            if (scanner.nextLine().equalsIgnoreCase("exit")) storeOpen = false;
        }
        scanner.close();
    }
}
