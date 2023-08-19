import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndtoEnd {
	Response response;
	String BaseURI = "http://localhost:8088/employees";
	@Test
	public void test1() {
		
		response = GetMethodAll();
		Assert.assertEquals(response.getStatusCode(),200);
		
		response = PostMethod("John","2000");
		Assert.assertEquals(response.getStatusCode(),201);
		JsonPath jpath = response.jsonPath();
		int EmpId = jpath.get("id");
		System.out.println("id : - " +EmpId);
		
	}
	public Response GetMethodAll() {
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response= request.get();
		
		return response;
		
	}
	
	public Response PostMethod(String Name, String Salary) {
		
		RestAssured.baseURI = BaseURI;
		JSONObject jobj = new JSONObject();
		jobj.put("name",Name);
		jobj.put("salary", Salary );
		
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(jobj.toString())
				.post("/create");
		
		return response;
		
		
	}

}
