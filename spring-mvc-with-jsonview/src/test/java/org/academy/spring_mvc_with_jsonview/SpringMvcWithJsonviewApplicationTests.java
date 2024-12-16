package org.academy.spring_mvc_with_jsonview;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.academy.spring_mvc_with_jsonview.model.Good;
import org.academy.spring_mvc_with_jsonview.model.User;
import org.academy.spring_mvc_with_jsonview.repository.GoodRepository;
import org.academy.spring_mvc_with_jsonview.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class SpringMvcWithJsonviewApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GoodRepository goodRepository;

	@Autowired
	private RandomTestDataGenerator generator;

	@Autowired
	private ObjectMapper om;

	private List<Good> testGoods = new ArrayList<>();
	private final String baseUrl = "/api/users";

	@BeforeAll
    public void setUp() {
		testGoods = StaticTestDataGenerator.generateSharedTestGoods();
		goodRepository.saveAll(testGoods);
	}

	// AfterAll clean is not needed because we use h2 mem db

	// Show only username and email
	@Test
	void testGetAll() throws Exception {
		mockMvc.perform(get(baseUrl))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0]").doesNotExist());

		var user = generator.generateUser(testGoods);

		mockMvc.perform(get(baseUrl))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].id").doesNotExist())
				.andExpect(jsonPath("$[0].username").value(user.getUsername()))
				.andExpect(jsonPath("$[0].email").value(user.getEmail()))
				.andExpect(jsonPath("$[0].created_at").doesNotExist())
				.andExpect(jsonPath("$[0].orders").doesNotExist());
	}

	// Show full info
	@Test
	public void testGetOne() throws Exception {
		User user = generator.generateUser(testGoods);
		mockMvc.perform(get(baseUrl + "/" + user.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(user.getId().toString()))
				.andExpect(jsonPath("$.username").value(user.getUsername()))
				.andExpect(jsonPath("$.email").value(user.getEmail()))
				// Need Jackson time format config or mapping the response + truncate to compare time correctly
				// Same for nested objects in orders - need to deserialize response to compare them correctly
				// Not the main task
				.andExpect(jsonPath("$.createdAt").exists())
				.andExpect(jsonPath("$.orders").exists());
	}

	@Test
	public void testGetOneInvalidId() throws Exception {
		mockMvc.perform(get(baseUrl + "/6d42a718-33df-44fd-ab53-bd0cd6817810"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testCreate() throws Exception {
		User userData = new User();
		userData.setUsername("new_test_user");
		userData.setEmail("testmail@test.com");

		mockMvc.perform(post(baseUrl)
						.content(om.writeValueAsString(userData))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		var user = userRepository.findByUsername(userData.getUsername())
				.orElseThrow(() -> new RuntimeException("Cant find user in repository for 'create' test"));

		assert user.getEmail().equals(userData.getEmail());
		assert user.getId() != null;

		userRepository.deleteById(user.getId());
	}

	@Test
	public void testCreateWithInvalidEmail() throws Exception {
		var userData = new User();
		userData.setUsername("new_test_user");
		userData.setEmail("testmail");

		mockMvc.perform(post(baseUrl)
						.content(om.writeValueAsString(userData))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	/*
	Here is the downside of JsonView annotation - it's not supporting deserialization from the box,
	only serialization. So we still need either dto for request, or customize ObjectMapper to ignore some input,
	or something else. I expected this test to fail because I've applied `@JsonView(JsonViewRoles.UserConcise.class)`
	annotation to POST (create), but it still passes, so API accepts any field from User class in request.
	*/
	@Test
	public void testCreateWIthTimestampFromClient() throws Exception {
		var userData = new User();
		userData.setUsername("new_test_user");
		userData.setEmail("testmail@test.com");
		userData.setCreatedAt(LocalDateTime.now().minusDays(1));

		mockMvc.perform(post(baseUrl)
						.content(om.writeValueAsString(userData))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());		// Desired behavior is bad request
	}

	@Test
	public void testPatch() throws Exception {
		User user = generator.generateUser(testGoods);

		String patchNode = "{\"username\": \"kkk\"}";

		mockMvc.perform(patch(baseUrl + "/" + user.getId())
						.content(patchNode)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		var modifiedUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new RuntimeException("Cant find user after PATCH"));

		assert modifiedUser.getUsername().equals("kkk");
		assert modifiedUser.getEmail().equals(user.getEmail());

		userRepository.deleteById(user.getId());
	}

	@Test
	public void testDelete() throws Exception {
		var user = new User();
		user.setUsername("new_username");
		user.setEmail("somenaming@email.com");
		userRepository.save(user);

		assert userRepository.existsById(user.getId());

		mockMvc.perform(delete(baseUrl + "/" + user.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		assert !userRepository.existsById(user.getId());
	}

}
