package com.example.threadsafety;

import com.example.threadsafety.DTO.PurchaseProductDto;
import com.example.threadsafety.DTO.UserDto;
import com.example.threadsafety.events.MessageBroker;
import com.example.threadsafety.events.ProductPurchasedEvent;
import com.example.threadsafety.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = ThreadSafetyApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThreadSafetyApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	private UserService userService;

	@Test
	@SneakyThrows
	void testSingleton() {
		final String baseUrl = "http://localhost:"+randomServerPort+"/users/create";
		URI url = new URI(baseUrl);
		UserDto user1 = new UserDto("user1", "123");
		UserDto user2 = new UserDto("user2", "456");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<UserDto> request1 = new HttpEntity<>(user1, headers);
		HttpEntity<UserDto> request2 = new HttpEntity<>(user2, headers);

		Thread thread1 = new Thread(new PostRequestTask<>(url, request1));
		Thread thread2 = new Thread(new PostRequestTask<>(url, request2));
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();

		System.out.println("number of users created: " + userService.numberOfCreatedUsers);

		assertEquals(2, userService.numberOfCreatedUsers,
				"number of users created: " + userService.numberOfCreatedUsers);
	}

	@Test
	@SneakyThrows
	void testSingletonWithSync() {
		final String baseUrl = "http://localhost:"+randomServerPort+"/users/create-sync";
		URI url = new URI(baseUrl);
		UserDto user1 = new UserDto("user1", "123");
		UserDto user2 = new UserDto("user2", "456");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<UserDto> request1 = new HttpEntity<>(user1, headers);
		HttpEntity<UserDto> request2 = new HttpEntity<>(user2, headers);

		Thread thread1 = new Thread(new PostRequestTask<>(url, request1));
		Thread thread2 = new Thread(new PostRequestTask<>(url, request2));
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();

		System.out.println("number of users created: " + userService.numberOfCreatedUsers);

		assertEquals(2, userService.numberOfCreatedUsers,
				"number of users created: " + userService.numberOfCreatedUsers);
	}

	@Test
	@SneakyThrows
	void testDefaultBehaviourOfPrototypeWithSingleton() {
		final String baseUrl = "http://localhost:"+randomServerPort+"/purchase";
		URI url = new URI(baseUrl);
		PurchaseProductDto purchaseProductDto1 = new PurchaseProductDto("book1", "user1");
		PurchaseProductDto purchaseProductDto2 = new PurchaseProductDto("book2", "user2");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<PurchaseProductDto> request1 = new HttpEntity<>(purchaseProductDto1, headers);
		HttpEntity<PurchaseProductDto> request2 = new HttpEntity<>(purchaseProductDto2, headers);

		Thread thread1 = new Thread(new PostRequestTask<>(url, request1));
		Thread thread2 = new Thread(new PostRequestTask<>(url, request2));
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();


		System.out.println(MessageBroker.messages);
		assertEquals(2, MessageBroker.eventsCount.get(),
				"number of ProductPurchasedEvent instances: " + MessageBroker.eventsCount.get());
		assertNotEquals(MessageBroker.messages.get(0).getEventId(), MessageBroker.messages.get(0).getEventId());
	}

	@Test
	@SneakyThrows
	void testProxiedPrototypeWithSingleton() {
		final String baseUrl = "http://localhost:"+randomServerPort+"/purchase/with-proxy";
		URI url = new URI(baseUrl);
		PurchaseProductDto purchaseProductDto1 = new PurchaseProductDto("book1", "user1");
		PurchaseProductDto purchaseProductDto2 = new PurchaseProductDto("book2", "user2");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<PurchaseProductDto> request1 = new HttpEntity<>(purchaseProductDto1, headers);
		HttpEntity<PurchaseProductDto> request2 = new HttpEntity<>(purchaseProductDto2, headers);

		Thread thread1 = new Thread(new PostRequestTask<>(url, request1));
		Thread thread2 = new Thread(new PostRequestTask<>(url, request2));
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();


		System.out.println("messages list: " + MessageBroker.messages);
		System.out.println("eventId list: " + MessageBroker.createdEventsIdList);
		assertEquals(2, MessageBroker.eventsCount.get(),
				"number of ProductPurchasedEvent instances: " + MessageBroker.eventsCount.get());
		assertNotEquals(MessageBroker.messages.get(0).getEventId(), MessageBroker.messages.get(0).getEventId());

	}

	@Test
	@SneakyThrows
	void testPrototypeObjectFactoryWithSingleton() {
		final String baseUrl = "http://localhost:"+randomServerPort+"/purchase/with-object-factory";
		URI url = new URI(baseUrl);
		PurchaseProductDto purchaseProductDto1 = new PurchaseProductDto("book1", "user1");
		PurchaseProductDto purchaseProductDto2 = new PurchaseProductDto("book2", "user2");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<PurchaseProductDto> request1 = new HttpEntity<>(purchaseProductDto1, headers);
		HttpEntity<PurchaseProductDto> request2 = new HttpEntity<>(purchaseProductDto2, headers);

		Thread.sleep(5000);
		// getting initial eventId value before sending http requests
		String initEventId = MessageBroker.createdEventsIdList.get(0);
		System.out.println("####################### DELAY ##########################");

		Thread thread1 = new Thread(new PostRequestTask<>(url, request1));
		Thread thread2 = new Thread(new PostRequestTask<>(url, request2));
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();


		System.out.println("messages list: " + MessageBroker.messages);
		System.out.println("eventId list: " + MessageBroker.createdEventsIdList);

		// we could ignore first created instance so 3 is ok.
		assertEquals(3, MessageBroker.eventsCount.get(),
				"number of ProductPurchasedEvent instances: " + MessageBroker.eventsCount.get());
		// if event instances are 3 the first one should be equal to initial eventId set at application startup
		assertEquals(initEventId, MessageBroker.createdEventsIdList.get(0));

		// finally messages delivered to message broker should not have same event id
		assertNotEquals(MessageBroker.messages.get(0).getEventId(), MessageBroker.messages.get(1).getEventId());
	}
}

