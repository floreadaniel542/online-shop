package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

	@Autowired
	private ProductService productService;

	@org.junit.Test
	public void testCreateProduct_whenValidRequest_thenProductIsSaved() {
		SaveProductRequest request = new SaveProductRequest();
		request.setName("Banana " + System.currentTimeMillis());
		request.setPrice(5);
		request.setQuantity(100);
		request.setDescription("Healthy food");
		productService.createProduct(request);

		Product createProduct = productService.createProduct(request);

		assertThat(createProduct, notNullValue());
		assertThat(createProduct.getId(), notNullValue());
		assertThat(createProduct.getId(), greaterThan(0L));
		assertThat(createProduct.getName(), is(request.getName()));
		assertThat(createProduct.getPrice(), is (request.getPrice()));
		assertThat(createProduct.getQuantity(), is(request.getQuantity()));
		assertThat(createProduct.getDescription(), is (request.getDescription()));


	}

	@Test(expected = NullPointerException.class)
	public void testCreateProduct_whenInvalidRequest_thenThrowException() {
		SaveProductRequest request = new SaveProductRequest();
		//leaving request properties with default null values
		//to validate the negative flow
		productService.createProduct(request);
	}



}
