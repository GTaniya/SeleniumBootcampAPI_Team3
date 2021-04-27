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
        String tweet = "Hello Tweeter";
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
        String tweet="Hello Tweeter";
        ValidatableResponse deleteResponse= this.tweetAPIClient.deleteTweet(1386555910345170946l);
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
        String expectedSetting = "azadeh50868277";
        ValidatableResponse response = this.tweetAPIClient.getAccountSettings();
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("screen_name");
        Assert.assertEquals(actualSetting,expectedSetting,"Setting Not Found");

    }
    //Test #9
    @Test
    public void testPostAccountSettings(){
        String expectedSetting = "azadeh50868277";
        ValidatableResponse response = this.tweetAPIClient.postAccountSettings();
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("screen_name");
        Assert.assertEquals(actualSetting,expectedSetting,"Setting Not Found");
    }
    //Test #10
    @Test
    public void testGetSearchTweet(){
        String expectedText = "#pitabread #galoti #kabab #uttapam #momos!! @TwitterFood @NDTVFood @food https://t.co/lN6brxi0ct";
        ValidatableResponse response = this.tweetAPIClient.getStatusTweets("@food");
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("statuses[0].text");
        Assert.assertEquals(actualText,expectedText);
    }
    //Test #11
    @Test
    public void testGetFriendList() {
        String expectedText = "Gawri Taniya";
        ValidatableResponse response = this.tweetAPIClient.getFriendList(1385051480491712526l);
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("users[0].name");
        Assert.assertEquals(actualText, expectedText);
    }
   // Test #12
    @Test
    public void testGetFriendId() {
        long expectedId = 702779075094519808l;
        ValidatableResponse response = this.tweetAPIClient.getFriendId(1385051480491712526l);
        System.out.println(response.extract().body().asPrettyString());
        long actualId = response.extract().body().path("ids[0].");
        Assert.assertEquals(actualId, expectedId);
    }
    // Test #13
    @Test
    public void testGetTrendAvailable() {
        String expectedText = "Winnipeg";
        ValidatableResponse response = this.tweetAPIClient.getTrendAvailable();
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("[1].name");
        Assert.assertEquals(actualText, expectedText);
    }
    // Test #14
    @Test
    public void testGetUserSearch() {
        String expectedText = "Mike Pompeo";
        ValidatableResponse response = this.tweetAPIClient.getUserSearch("Mike Pompeo");
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("[0].name");
        Assert.assertEquals(actualText, expectedText);
    }
    //Test #15
    @Test
    public void testUserCanTweet() {
        String tweet = "I am practicing API"+ UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        String actualTweet=response.extract().body().path("text");
        Assert.assertEquals(actualTweet,tweet,"Tweet is not match");
    }
    //Test #16
    @Test
    public void testDeleteTweet2(){
        String tweet="I am practicing APIa5909a1c-b54b-44e3-b61a-3756c7028b58";
        ValidatableResponse deleteResponse= this.tweetAPIClient.deleteTweet(1386871987042230274l);
        deleteResponse.statusCode(200);
        String actualTweet= deleteResponse.extract().body().path("text");
        Assert.assertEquals(tweet,actualTweet);

    }
    // Test #17
    @Test
    public void testGetFollowerList() {
        String expectedText = "Q";
        ValidatableResponse response = this.tweetAPIClient.getFollowerList(1385051480491712526l);
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("users[1].name");
        Assert.assertEquals(actualText, expectedText);


    }
    // Test #17
    @Test
    public void testGetFollower() {
        String expectedText = "TaniyaGawri";
        ValidatableResponse response = this.tweetAPIClient.getFollowerList(1385051480491712526l);
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("users[0].screen_name");
        Assert.assertEquals(actualText, expectedText);


    }
    // Test #18
    @Test
    public void testGetFollowerId() {
       long expectedId= 702779075094519808l;
        ValidatableResponse response = this.tweetAPIClient.getFollowerId(1385051480491712526l);
        System.out.println(response.extract().body().asPrettyString());
        long actualId = response.extract().body().path("ids[0].");
        Assert.assertEquals(actualId,expectedId);


    }
    // Test #19
    @Test
    public void testGetFavoriteList() {
        long expectedId= 1386712559148015616l;
        ValidatableResponse response = this.tweetAPIClient.getFavoriteList(1385051480491712526l);
        System.out.println(response.extract().body().asPrettyString());
        long actualId = response.extract().body().path("id[0].");
        Assert.assertEquals(actualId,expectedId);


    }
    // Test #20
    @Test
    public void testGetHelpLanguages() {
       String expectedId= "Urdu";
        ValidatableResponse response = this.tweetAPIClient.getHelpLanguages();
        System.out.println(response.extract().body().asPrettyString());
        String actualId = response.extract().body().path("[0].name");
        Assert.assertEquals(actualId,expectedId);

    }




}
