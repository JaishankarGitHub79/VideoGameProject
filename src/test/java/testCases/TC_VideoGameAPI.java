package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.HashMap;

public class TC_VideoGameAPI {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	// Test GET : view all resources
	@Test(priority = 1)
	public void test_getAllVideoGames() {
				given()
				.when().get("http://localhost:8084/app/videogames")
				.then().statusCode(200);
		}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	// Test POST : adding a new resource
	@Test(priority = 2)
	public void test_addNewVideoGame() {
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2020-21-20T08:55:58.510Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

		Response res = given().contentType("application/json").body(data)
						.when().post("http://localhost:8084/app/videogames")
						.then().statusCode(200).log().body().extract().response();
						String jsonString = res.asString();
						Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);

	}

	// Test GET : view 1 resource
	@Test(priority = 3)
	public void test_getVideoGame() {
				given()
				.when().get("http://localhost:8084/app/videogames/100")
				.then().statusCode(200).log().body()
				.body("videoGame.id", equalTo("100")).body("videoGame.name", equalTo("Spider-Man"));
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	// // Test PUT : Update Existing Resource
	@Test(priority = 4)
	public void test_UpdateVideoGame() {
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Pacman");
		data.put("releaseDate", "2021-21-20T08:57:58.510Z");
		data.put("reviewScore", "4");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

				given().contentType("application/json").body(data)
				.when().put("http://localhost:8084/app/videogames/100")
				.then().statusCode(200).log().body().body("videoGame.id", equalTo("100"))
				.body("videoGame.name", equalTo("Pacman"));

	}

	// // Test DELETE : Delete Existing Resource
	@Test(priority = 5)
	public void test_DeleteVideoGame() throws InterruptedException {
		Response res = given()
				.when().delete("http://localhost:8084/app/videogames/100")
				.then().statusCode(200).log()
				.body().extract().response();

				Thread.sleep(3000);
				String jsonString = res.asString();

		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}

}
