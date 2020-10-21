import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class HttpMethods {
	
	static String baseURI = "http://localhost:3000";
	static String usersResource = "/users";
	static String subjectsResource = "/subjects";
	static JSONObject request;
	
	// add 3 base users
	public static void AddBaseUsers() { 
		
		Map<String, Object> map = new HashMap<String, Object>(); 
	
		map.put("firstName","Dor");
		map.put("lastName","Chen");
		map.put("age", 33);
		map.put("subjectId", 1);
		HttpMethods.postUser(map);
		
		map.put("firstName","Ran");
		map.put("lastName","Markovich");
		map.put("age", 28);
		map.put("subjectId", 2);
		HttpMethods.postUser(map);
		
		map.put("firstName","Max");
		map.put("lastName","Nudler");
		map.put("age", 22);
		map.put("subjectId", 2);
		HttpMethods.postUser(map);
		given().get(baseURI+usersResource).then().body("userId", hasSize(3));
	}
	
	// return count of users in current JSON
	public static int retCountOfUsers() { 	
		Response response = given().get(baseURI+usersResource);
		String jsonBody = response.body().asString();
		return StringUtils.countMatches(jsonBody, "\"id\":");
	}

	public static void getUser(int userId) { 
		
		given().
			get(baseURI+usersResource+"/"+userId).
		then().
			statusCode(200).
			log().body();
	}
	
	public static int postUser(Map<String, Object> map) {
		
		request = new JSONObject(map); 
		Response response = given()
		.given()
			.contentType(ContentType.JSON) 
			.body(request.toJSONString())
		.when()
		    .post("http://localhost:3000/users");
		
		// get id from posted User
		JsonPath jp = new JsonPath(response.asString());
		int id = jp.get("id");
		return id;
	}
	
	public static void patchUser(int userId, Map<String, Object> map) {  

		request = new JSONObject(map);
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON). 
			body(request.toJSONString()).
		when().
			patch(baseURI+usersResource+"/"+userId).
		then().
			statusCode(200).
			log().body();
	}
	
	public static void putUser(int userId, Map<String, Object> map) {  
		
		request = new JSONObject(map);		
		given().
			contentType(ContentType.JSON).
			accept(ContentType.JSON). 
			body(request.toJSONString()).
		when().
			put(baseURI+usersResource+"/"+userId).
		then().
			statusCode(200).
			log().body();
	}
	
	public static void deleteUser(int userId) {
		
		when().
			delete(baseURI+usersResource+"/"+userId).
		then().
			statusCode(200);
	}
	
	public static void isKeyValExistInNode(int id, String resource, String key, String value) { 
		
		given().
			get(baseURI+resource+"/"+id).
		then().
			assertThat().body(key, equalTo(value)).
			statusCode(200).
			log().body();
	}
}
