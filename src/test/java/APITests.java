import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;

public class APITests extends DataForTests{

	static String baseURI = "http://localhost:3000";
	static String usersResource = "/users";
	static String subjectsResource = "/subjects";
	Map<String, Object> map;
	
	// Initialize JSON - delete users and add three base users
	@Test (priority=0)
	public void restoreBaseUsers() {  	
		int userId = HttpMethods.retCountOfUsers();
		while(userId!=0) {
			HttpMethods.deleteUser(userId);
			userId--;
		}
		HttpMethods.AddBaseUsers();
	}
	
	// Get user by id
	@Test (dataProvider = "GetData", priority=1)
	public void getUsers(int userId) { 		
		HttpMethods.getUser(userId);
	}
	
	// Post user
	@Test (dataProvider = "PostData", priority=2)
	public void postUsers(String firstName, String lastName, int age, int subjectId) { 
	
		map = new HashMap<String, Object>();
		map.put("firstName",firstName);
		map.put("lastName",lastName);
		map.put("age",age);
		map.put("subjectId",subjectId);
		
		int userId = HttpMethods.postUser(map);
		
		given().
			get(baseURI+usersResource+"/"+userId).  
		then().
			assertThat().statusCode(200). // get status code of created user
			assertThat().body("firstName", equalTo(firstName)).
			assertThat().body("lastName", equalTo(lastName)).
			assertThat().body("age", equalTo(age)).
			assertThat().body("subjectId", equalTo(subjectId)).
			log().body();
	}

	// Patch user 
	@Test (dataProvider = "PatchData", priority=3)
	public void patchUsers(int userId, String firstName, int age) {  

		map = new HashMap<String, Object>();
		map.put("firstName",firstName);
		map.put("age",age);
		HttpMethods.patchUser(userId, map);
		
		given().
			get(baseURI+usersResource+"/"+userId).
		then().
			assertThat().body("firstName", equalTo(firstName)).
			assertThat().body("age", equalTo(age));
	}
	
	// Put user
	@Test (dataProvider = "PutData", priority=4)
	public void putUsers(int userId, String firstName, String lastName, int age, int subjectId) { 
		
		map = new HashMap<String, Object>();
		map.put("firstName",firstName);
		map.put("lastName",lastName);
		map.put("age",age);
		map.put("subjectId",subjectId);
		HttpMethods.putUser(userId, map);
			
		given().
			get(baseURI+usersResource+"/"+userId).
		then().
			assertThat().body("firstName", equalTo(firstName)).
			assertThat().body("lastName", equalTo(lastName)).
			assertThat().body("age", equalTo(age)).
			assertThat().body("subjectId", equalTo(subjectId));
	}
	
	// Delete user
	@Test (dataProvider = "DeleteData", priority=5)
	public void deleteUsers(int userId) {
		HttpMethods.deleteUser(userId);
		
		given().
			get(baseURI+usersResource+"/"+userId).
		then().
			assertThat().statusCode(404);
	}
  
	// check if key value exist in user. 
	@Test (dataProvider = "keyValueData", priority=6)
	public void isKeyValExistInNode(int id, String recource, String key, String value) {
		HttpMethods.isKeyValExistInNode(id, recource, key, value);
	}
}
