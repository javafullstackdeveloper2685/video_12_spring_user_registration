package org.sergei.lunchApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sergei.lunchApp.dto.User;
import org.sergei.lunchApp.model.UserEntity;
import org.sergei.lunchApp.model.enums.UserRole;
import org.sergei.lunchApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class LunchAppApplicationTests {

	@Container
	private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3");

	@DynamicPropertySource
	static  void setProperties(DynamicPropertyRegistry registry){
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);
	}

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp(){
		userRepository.deleteAll();
	}

	@Test
	void shouldCreateUser() throws Exception{

		User user = getUser();
		String userConverteAsString = objectMapper.writeValueAsString(user);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
				.with(httpBasic("user", "192241"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(userConverteAsString))
				.andExpect(status().isCreated());



	}

	private User getUser() {
		return User.builder()
				.uuid(UUID.randomUUID())
				.userName("timognom")
				.password("12345666")
				.name("Timo")
				.surname("pupkin")
				.balance(new BigDecimal("55"))
				.userRole(UserRole.ADMIN)
				.createdAt(new Date())
				.build();
	}

	@Test
	void shouldReturnAllUsers() {
		UserEntity user1 = new UserEntity();
		user1.setUserName("user1");
		user1.setPassword("pass1");
		userRepository.save(user1);

		UserEntity user2 = new UserEntity();
		user2.setUserName("user2");
		user2.setPassword("password456");
		userRepository.save(user2);

		List<UserEntity> users = userRepository.findAll();

		assertThat(users).hasSize(2);
	}


}
