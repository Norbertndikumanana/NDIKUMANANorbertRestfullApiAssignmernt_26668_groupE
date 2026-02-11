package question4_E_CommerceProductapi.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import question4_E_CommerceProductapi.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // In-memory storage for products
    private List<Product> products = new ArrayList<>();
    private Long nextId = 1L;

    // Constructor - initialize with 10+ sample products
    public ProductController() {
        // Electronics
        products.add(new Product(nextId++, "iPhone 15 Pro", "Latest Apple smartphone with A17 chip", 999.99, "Electronics", 50, "Apple"));
        products.add(new Product(nextId++, "Samsung Galaxy S24", "Flagship Android smartphone", 899.99, "Electronics", 35, "Samsung"));
        products.add(new Product(nextId++, "MacBook Pro 14", "Professional laptop with M3 chip", 1999.99, "Electronics", 20, "Apple"));
        products.add(new Product(nextId++, "Dell XPS 15", "High-performance Windows laptop", 1599.99, "Electronics", 15, "Dell"));
        
        // Clothing
        products.add(new Product(nextId++, "Nike Air Max", "Comfortable running shoes", 129.99, "Clothing", 100, "Nike"));
        products.add(new Product(nextId++, "Adidas Ultraboost", "Premium athletic sneakers", 149.99, "Clothing", 75, "Adidas"));
        products.add(new Product(nextId++, "Levi's 501 Jeans", "Classic denim jeans", 69.99, "Clothing", 200, "Levi's"));
        
        // Home & Kitchen
        products.add(new Product(nextId++, "Instant Pot Duo", "7-in-1 electric pressure cooker", 89.99, "Home & Kitchen", 60, "Instant Pot"));
        products.add(new Product(nextId++, "Dyson V15", "Cordless vacuum cleaner", 649.99, "Home & Kitchen", 25, "Dyson"));
        products.add(new Product(nextId++, "KitchenAid Mixer", "Stand mixer for baking", 379.99, "Home & Kitchen", 40, "KitchenAid"));
        
        // Out of stock item for testing
        products.add(new Product(nextId++, "Sony PS5", "Gaming console - out of stock", 499.99, "Electronics", 0, "Sony"));
    }

    // GET /api/products - Get all products (with optional pagination)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        
        if (page != null && limit != null) {
            // Pagination logic
            int startIndex = page * limit;
            int endIndex = Math.min(startIndex + limit, products.size());
            
            if (startIndex >= products.size()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            
            List<Product> paginatedProducts = products.subList(startIndex, endIndex);
            return new ResponseEntity<>(paginatedProducts, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET /api/products/{productId} - Get product details
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();

        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/products/category/{category} - Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> foundProducts = products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (foundProducts.isEmpty()) {
            return new ResponseEntity<>(foundProducts, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }

    // GET /api/products/brand/{brand} - Get products by brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> foundProducts = products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());

        if (foundProducts.isEmpty()) {
            return new ResponseEntity<>(foundProducts, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }

    // GET /api/products/search?keyword={keyword} - Search products by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> foundProducts = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                           p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (foundProducts.isEmpty()) {
            return new ResponseEntity<>(foundProducts, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }

    // GET /api/products/price-range?min={min}&max={max} - Get products within price range
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        
        List<Product> foundProducts = products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());

        if (foundProducts.isEmpty()) {
            return new ResponseEntity<>(foundProducts, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }

    // GET /api/products/in-stock - Get products with stockQuantity > 0
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> foundProducts = products.stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());

        if (foundProducts.isEmpty()) {
            return new ResponseEntity<>(foundProducts, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }

    // POST /api/products - Add new product
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        product.setProductId(nextId++);
        products.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // PUT /api/products/{productId} - Update product details
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product updatedProduct) {
        
        Optional<Product> existingProduct = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            
            // Update all fields except ID
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setStockQuantity(updatedProduct.getStockQuantity());
            product.setBrand(updatedProduct.getBrand());

            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PATCH /api/products/{productId}/stock?quantity={quantity} - Update stock quantity
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStockQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        
        Optional<Product> existingProduct = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setStockQuantity(quantity);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/products/{productId} - Delete product
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));

        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

