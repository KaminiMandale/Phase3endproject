package phaseend;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class EndtToEndTest extends ReusableMethods {

	@Test
	public void runtestCases() {
		
		Response response = getEmployee();
		// validating ResponseCode as 200
		Assert.assertEquals(response.statusCode(), 200);
		// Validating count of Employees
		Assert.assertEquals(numberOfEmployees, 3);

		
		response = createNewEmployee("John", "Dae", "100000", "johnDae@gmail.com");
		// validating ResponseCode as 200
		Assert.assertEquals(response.statusCode(), 201);
		// Validating the name in the response is John
		Assert.assertEquals(employeeName, "John");
		response = getEmployee();
		numberOfEmployees = response.jsonPath().getList("employees").size();
		Assert.assertEquals(numberOfEmployees, 4);

		response = updateEmployee("5");
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(employeeName, "Tom");
		Assert.assertNotEquals(response.jsonPath().getString("firstName"), "John");


		response = getEmployee("5");
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(employeeName, "Tom");
	
		response = deleteEmployee("5");
		Assert.assertEquals(response.statusCode(), 200);
		response=getEmployee("5");
		Assert.assertNotEquals(response.jsonPath().getString("firstName"), "Tom");

		response=getEmployee("5");
		Assert.assertEquals(response.statusCode(), 400);
		
		
		response = getEmployee();
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(numberOfEmployees, 3);
	}
}
