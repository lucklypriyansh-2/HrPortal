package com.hrportal.main.integrationTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeManagementIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	private String cookieString;

	@Before
	public void login() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/login")
						.content("{\n" + "	\"userName\":\"clarion\",\n"
								+ "	\"password\":\"Password@3\"\n" + "}")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		cookieString = mvcResult.getResponse().getContentAsString();

	}

	@Test
	public void testWhenPostProperHeriarchy_thenReturnProperHeriarchy() throws Exception {

		MockHttpServletRequestBuilder postHeriarchyRequest = MockMvcRequestBuilders
				.post("/Hierarchy")
				.content("{\n" + "\"Pete\": \"Nick\",\n" + "\"Barbara\": \"Nick\",\n"
						+ "\"Nick\": \"Sophie\",\n" + "\"Sophie\": \"Jonas\"\n" + "}")
				.cookie(new Cookie("sessionId", cookieString));

		MvcResult mvcResult        = mockMvc.perform(postHeriarchyRequest)
				.andExpect(status().isOk()).andReturn();
		String    expectedresponse = "{\"Jonas\":[{\"Sophie\":[{\"Nick\":[{\"Pete\":[]},{\"Barbara\":[]}]}]}]}";
		Assert.assertEquals(expectedresponse, mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void testWhenPostMultipleRootHeriarchy_thenReturnException() throws Exception {

		MockHttpServletRequestBuilder postHeriarchyRequest = MockMvcRequestBuilders
				.post("/Hierarchy")
				.content("{\n" + "\"Pete\": \"Nick\",\n" + "\"Barbara\": \"Nick\",\n"
						+ "\"Nick\": \"Sophie\",\n" + "\"Sophie\": \"Jonas\",\n"
						+ "\"Sophie\": \"Karan\"\n" + "}")
				.cookie(new Cookie("sessionId", cookieString));

		MvcResult mvcresult = mockMvc.perform(postHeriarchyRequest)
				.andExpect(status().isInternalServerError()).andReturn();

		Assert.assertTrue(mvcresult.getResolvedException().getMessage()
				.contains("Error multiple roots in request json Roots[ [Jonas, Karan]]"));
	}

	@Test
	public void testWhenPostCyclicHeriarchy_thenReturnException() throws Exception {

		MockHttpServletRequestBuilder postHeriarchyRequest = MockMvcRequestBuilders
				.post("/Hierarchy")
				.content("{\n" + "\"Pete\": \"Nick\",\n" + "\"Barbara\": \"Nick\",\n"
						+ "\"Nick\": \"Sophie\",\n" + "\"Sophie\": \"Jonas\",\n"
						+ "\"Jonas\":\"Barbara\"\n" + "\n" + "}")
				.cookie(new Cookie("sessionId", cookieString));

		MvcResult mvcresult = mockMvc.perform(postHeriarchyRequest)
				.andExpect(status().isInternalServerError()).andReturn();

		Assert.assertTrue(mvcresult.getResolvedException().getMessage()
				.contains("Cycle detected between -->Jonas-->Barbara-->Nick-->Sophie"));
	}

	@Test
	public void testGivenHerirachalData_WhenPostForGetHeriararchy_GetSupervisour()
			throws Exception {

		MockHttpServletRequestBuilder postHeriarchyRequest = MockMvcRequestBuilders
				.post("/Hierarchy")
				.content("{\n" + "\"Pete\": \"Nick\",\n" + "\"Barbara\": \"Nick\",\n"
						+ "\"Nick\": \"Sophie\",\n" + "\"Sophie\": \"Jonas\"\n" + "}")
				.cookie(new Cookie("sessionId", cookieString));

		mockMvc.perform(postHeriarchyRequest).andExpect(status().isOk());

		String response= mockMvc
				.perform(MockMvcRequestBuilders.get("/Hierarchy/Pete")
						.cookie(new Cookie("sessionId", cookieString)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		Assert.assertTrue(response.equals(
				"{\"employeeName\":\"Pete\",\"superVisors\":[{\"employeeName\":\"Nick\",\"superVisors\":[{\"employeeName\":\"Sophie\",\"superVisors\":null}]}]}"));

	}

}