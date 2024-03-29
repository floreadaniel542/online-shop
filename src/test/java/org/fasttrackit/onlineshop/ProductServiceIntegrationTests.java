package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourcesNotFoundException;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

	@Autowired
	private ProductService productService;

	@Test
	public void testCreateProduct_whenValidRequest_thenProductIsSaved() {
		createProduct();
	}

	@Test(expected = TransactionSystemException.class)
	public void testCreateProduct_whenInvalidRequest_thenThrowException() {
		SaveProductRequest request = new SaveProductRequest();
		// leaving request properties with default null values
		// to validate the negative flow

		productService.createProduct(request);
	}

	@Test
	public void testGetProduct_whenExistingProduct_thenReturnProduct() {
		Product createdProduct = createProduct();

		Product product = productService.getProduct(createdProduct.getId());

		assertThat(product, notNullValue());
		assertThat(product.getId(), is(createdProduct.getId()));
		assertThat(product.getName(), is(createdProduct.getName()));
		assertThat(product.getPrice(), is(createdProduct.getPrice()));
		assertThat(product.getQuantity(), is(createdProduct.getQuantity()));
		assertThat(product.getDescription(), is(createdProduct.getDescription()));
	}

	@Test(expected = ResourcesNotFoundException.class)
	public void testGetProduct_whenNonExistingProduct_thenThrowResourceNotFoundException() {
		productService.getProduct(9999999999L);
	}

	@Test
	public void testUpdateProduct_whenValidRequest_thenReturnUpdateProduct() {
		Product createdProduct = createProduct();

		SaveProductRequest request = new SaveProductRequest();
		request.setName(createdProduct.getName() + "updated");
		request.setPrice(createdProduct.getPrice() + 10);
		request.setDescription(createdProduct.getDescription() + "updated");
		request.setQuantity(createdProduct.getQuantity() + 10);

		Product updatedProduct = productService.updateProduct(createdProduct.getId(),request);

	}

	private Product createProduct() {
		SaveProductRequest request = new SaveProductRequest();
		request.setName("Banana " + System.currentTimeMillis());
		request.setPrice(5.0);
		request.setQuantity(100);
		request.setDescription("Healthy food");

		Product createdProduct = productService.createProduct(request);

		assertThat(createdProduct, notNullValue());
		assertThat(createdProduct.getId(), notNullValue());
		assertThat(createdProduct.getId(), greaterThan(0L));
		assertThat(createdProduct.getName(), is(request.getName()));
		assertThat(createdProduct.getPrice(), is(request.getPrice()));
		assertThat(createdProduct.getQuantity(), is(request.getQuantity()));
		assertThat(createdProduct.getDescription(), is(request.getDescription()));

		return createdProduct;
	}
}