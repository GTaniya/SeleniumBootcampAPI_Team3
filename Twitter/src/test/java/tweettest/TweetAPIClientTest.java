package tweettest;

import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tweet.TweetAPIClient;

import java.util.UUID;

public class TweetAPIClientTest {

    private TweetAPIClient tweetAPIClient;

    ////////////////////////////////////////////////////////////////////////////////////////
    @BeforeClass public void setUpTweetAPI() {
        this.tweetAPIClient = new TweetAPIClient();
    }
    ///////////////////////////////////////////////////////////////////////////////////////


    //Test #1
    @Test
    public void testUserCanTweetSuccessfully() {
        // User sent a tweet tweet
        String tweet = "Hello Tweeter"+ UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
       // response.statusCode(200);
        String actualTweet=response.extract().body().path("text");
        Assert.assertEquals(actualTweet,tweet,"Tweet is not match");
    }


    //Test #2
    @Test
    public void testUserCanNotTweetTheSameTweetTwiceInARow() {
        // User sent a tweet
        String tweet = "4 days until survival";
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        // Verify that the tweet is successful
       response.statusCode(403);
        // Verity Retweet
        System.out.println(response.extract().body().asPrettyString());
        String expectedMessage="Status is a duplicate.";
        String actualTweet=response.extract().body().path("errors[0].message");
        Assert.assertEquals(actualTweet,expectedMessage,"Tweet is match");
        Assert.assertNotEquals("403",200);
    }

    //Test #3
    @Test
    public void testDeleteTweet(){
        String tweet="We are learning Rest API using Rest Assured and our First Tweet82d120dd-9045-44f3-a3c9-8720409fae20";
        ValidatableResponse deleteResponse= this.tweetAPIClient.deleteTweet(1378590700409921541L);
        deleteResponse.statusCode(200);
        String actualTweet= deleteResponse.extract().body().path("text");
        Assert.assertEquals(tweet,actualTweet);

    }

    //Test #4
    @Test(enabled = true)
    public void testResponseTime() {
        String expectedResponse = "Sat Apr 24 02:10:35 +0000 2021";
        ValidatableResponse response = this.tweetAPIClient.responseTime();
        System.out.println(response.extract().body().asPrettyString());
        String actualResponse = response.extract().body().path("[0].created_at");
        Assert.assertEquals(actualResponse,expectedResponse,"\n*** Tweet Not Found - Try Again ***");
    }
    @Test(enabled = false)
    public void testHeaderValue() {
        this.tweetAPIClient.headerValue();
    }

    //Test #5
    @Test(enabled = false)
    public void testPropertyFromResponse() {
        //1. User send a tweet
        String tweet = "Survival of the Fittest" + UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(actualTweet, tweet, "Tweet is not match");
    }

    //Test #6
    @Test
    public void testGetStatusShowByID(){
        String expectedTweet = "4 days until survival";
        ValidatableResponse response = this.tweetAPIClient.getStatusShowByID(1385769295515471874l);
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet=response.extract().body().path("text");
        Assert.assertEquals(actualTweet,expectedTweet,"\n*** Tweet Not Found - Try Again ***");
    }

    //Test #7
    @Test
    public void testGetTrendsByPlaceID(){
        String expectedTrend = "#DragRace";
        ValidatableResponse response = this.tweetAPIClient.getTrendsByPlaceId(1);
        System.out.println(response.extract().body().asPrettyString());
        String actualTrend = response.extract().body().path("[0].trends[0].name");
        Assert.assertEquals(actualTrend,expectedTrend,"\n*** Tweet Not Found - Try Again ***");
    }

    //Test #8
    @Test
    public void testGetAccountSettings(){
        String expectedSetting = "";
        ValidatableResponse response = this.tweetAPIClient.getAccountSettings();
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("screen_name");
        Assert.assertEquals(actualSetting,expectedSetting,"Setting Not Found");

    }
    //Test #9
    @Test
    public void testPostAccountSettings(){
        String expectedSetting = "";
        ValidatableResponse response = this.tweetAPIClient.postAccountSettings();
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("screen_name");
        Assert.assertEquals(actualSetting,expectedSetting,"Setting Not Found");
    }



}
