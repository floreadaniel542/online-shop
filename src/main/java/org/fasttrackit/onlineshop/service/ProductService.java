package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourcesNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductService.class);

    //IoC - Inversion of Control
    private final ProductRepository productRepository;

    //Dependency injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product {} ", request);
        Product prodcut = new Product();
        prodcut.setDescription(request.getDescription());
        prodcut.setName(request.getName());
        prodcut.setPrice(request.getPrice());
        prodcut.setQuantity(request.getQuantity());
        prodcut.setImageUrl(request.getImageUrl());

       return productRepository.save(prodcut);
    }

    public Product getProduct(long id) {
        LOGGER.info("Retrieving product {} ", id);
        //using Optional
        return productRepository.findById(id)
                  .orElseThrow(() -> new ResourcesNotFoundException("Product " + id + " does not exist."));
    }

     public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {} : {}", id, request);

        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);


     }

     public void deleteProduct(long id) {
        LOGGER.info("Deleting product {} ", id);
        productRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);
     }
}
