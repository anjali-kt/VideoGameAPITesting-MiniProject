package TestCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class TC_VideoGameAPI {
	@Test(priority=1)
	public void test_getAllVideoGame() {
		given()
		.when()
			.get("http://localhost:8080/app/videogames")
		.then()
			.statusCode(200);
		
	}
	@Test(priority=2)
	public void test_addNewVideoGame() {
		HashMap data = new HashMap();
		data.put("id", 100);
		data.put("name", "Bumpy");
		data.put("releaseDate", "2025-04-04T01:29:34.422Z");
		data.put("reviewScore", 5);
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res = given()
							.contentType("application/json")
							.body(data)
						.when()
							.post("http://localhost:8080/app/videogames")
						.then()
							.statusCode(200)
							.log().body()
							.extract().response();
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);				
	}
	@Test(priority=3)
	public void tset_getVideoGame() {
		given()
		.when()
			.get("http://localhost:8080/app/videogames/100")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("100"))
			.body("videoGame.name", equalTo("Bumpy"));
	}
	@Test(priority=4)
	public void test_updateVideoGame() {
		HashMap data = new HashMap();
		data.put("id", 100);
		data.put("name", "Smoothie");
		data.put("releaseDate", "2025-04-04T01:29:34.422Z");
		data.put("reviewScore", 4);
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.put("http://localhost:8080/app/videogames/100")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("100"))
			.body("videoGame.reviewScore", equalTo("4"))
			.body("videoGame.name", equalTo("Smoothie"));
		
	}
	@Test(priority=5)
		public void test_deleteVideoGame() {
		Response res = given()
						.when()
							.delete("http://localhost:8080/app/videogames/100")
						.then()
							.statusCode(200)
							.log().body()
							.extract().response();
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}

}
