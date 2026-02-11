package question3_restaurant_menu_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    // In-memory storage for menu items
    private List<MenuItem> menuItems = new ArrayList<>();
    private Long nextId = 1L;

    // Constructor - initialize with 8+ sample menu items across all categories
    public MenuController() {
        // Appetizers
        menuItems.add(new MenuItem(nextId++, "Bruschetta", "Grilled bread with tomatoes, garlic, and basil", 8.99, "Appetizer", true));
        menuItems.add(new MenuItem(nextId++, "Caesar Salad", "Romaine lettuce with Caesar dressing and croutons", 9.50, "Appetizer", true));
        
        // Main Courses
        menuItems.add(new MenuItem(nextId++, "Grilled Salmon", "Fresh Atlantic salmon with lemon butter sauce", 24.99, "Main Course", true));
        menuItems.add(new MenuItem(nextId++, "Beef Burger", "Angus beef burger with cheese, lettuce, and tomato", 15.99, "Main Course", true));
        menuItems.add(new MenuItem(nextId++, "Chicken Alfredo", "Fettuccine pasta with creamy Alfredo sauce", 18.50, "Main Course", false));
        
        // Desserts
        menuItems.add(new MenuItem(nextId++, "Chocolate Cake", "Rich chocolate layer cake with ganache", 7.99, "Dessert", true));
        menuItems.add(new MenuItem(nextId++, "Tiramisu", "Classic Italian coffee-flavored dessert", 8.50, "Dessert", true));
        
        // Beverages
        menuItems.add(new MenuItem(nextId++, "Fresh Lemonade", "Homemade lemonade with mint", 4.50, "Beverage", true));
        menuItems.add(new MenuItem(nextId++, "Cappuccino", "Espresso with steamed milk foam", 5.50, "Beverage", true));
    }

    // GET /api/menu - Get all menu items (200 OK)
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    // GET /api/menu/{id} - Get specific menu item
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        Optional<MenuItem> menuItem = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (menuItem.isPresent()) {
            return new ResponseEntity<>(menuItem.get(), HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // GET /api/menu/category/{category} - Get items by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable String category) {
        List<MenuItem> foundItems = new ArrayList<>();

        for (MenuItem item : menuItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                foundItems.add(item);
            }
        }

        if (foundItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }

        return new ResponseEntity<>(foundItems, HttpStatus.OK); // 200 OK
    }

    // GET /api/menu/available - Get only available items (query parameter: ?available=true)
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems(@RequestParam(defaultValue = "true") boolean available) {
        List<MenuItem> foundItems = new ArrayList<>();

        for (MenuItem item : menuItems) {
            if (item.isAvailable() == available) {
                foundItems.add(item);
            }
        }

        return new ResponseEntity<>(foundItems, HttpStatus.OK); // 200 OK
    }

    // GET /api/menu/search?name={name} - Search menu items by name
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItemsByName(@RequestParam String name) {
        List<MenuItem> foundItems = new ArrayList<>();

        for (MenuItem item : menuItems) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                foundItems.add(item);
            }
        }

        if (foundItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }

        return new ResponseEntity<>(foundItems, HttpStatus.OK); // 200 OK
    }

    // POST /api/menu - Add new menu item (201 Created)
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        menuItem.setId(nextId++); // Auto-generate ID
        menuItems.add(menuItem);
        return new ResponseEntity<>(menuItem, HttpStatus.CREATED); // 201 Created
    }

    // PUT /api/menu/{id}/availability - Toggle item availability
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        Optional<MenuItem> existingItem = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (existingItem.isPresent()) {
            MenuItem item = existingItem.get();
            // Toggle the availability
            item.setAvailable(!item.isAvailable());
            return new ResponseEntity<>(item, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // DELETE /api/menu/{id} - Remove menu item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));

        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }
}
