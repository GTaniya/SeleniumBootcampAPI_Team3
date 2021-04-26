package tweettest;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Mypayload;
import tweet.TweetAPIClient;

import java.io.FileNotFoundException;
import java.util.List;
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
        String tweet = "Wherever life plants you bloom with grace"+ UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
       // response.statusCode(200);
        String actualTweet=response.extract().body().path("text");
        Assert.assertEquals(actualTweet,tweet,"Tweet is not match");
    }


    //Test #2
    @Test
    public void testUserCanNotTweetTheSameTweetTwiceInARow() {

        String tweet = "wherever life plants you bloom with grace";
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);

       // response.statusCode(403);

        System.out.println(response.extract().body().asPrettyString());
        String expectedMessage="Status is a duplicate.";
        String actualTweet=response.extract().body().path("errors[0].message");
        Assert.assertEquals(actualTweet,expectedMessage,"Tweet is match");
        Assert.assertNotEquals("403",200);
    }

     //Test #3
    @Test
    public void testDeleteTweet(){
        String tweet="Wherever life plants you bloom with grace7bcab4d3-6599-4af6-8304-359ca4616a4b";
        ValidatableResponse deleteResponse= this.tweetAPIClient.deleteTweet(1386557154010730499L);
        deleteResponse.statusCode(200);
        String actualTweet= deleteResponse.extract().body().path("text");
        Assert.assertEquals(tweet,actualTweet);

    }

    //Test #4
    @Test(enabled = true)
    public void testResponseTime() {
        String expectedResponse = "Mon Apr 26 16:28:10 +0000 2021";
        ValidatableResponse response = this.tweetAPIClient.responseTime();
        System.out.println(response.extract().body().asPrettyString());
        String actualResponse = response.extract().body().path("[0].created_at");
        Assert.assertEquals(actualResponse,expectedResponse);
    }
    @Test(enabled = false)
    public void testHeaderValue() {
        this.tweetAPIClient.headerValue();
    }

    //Test #5
    @Test(enabled = false)
    public void testPropertyFromResponse() {
        //1. User send a tweet
        String tweet = "Hello Tweeter" + UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(actualTweet, tweet, "Tweet is not match");
    }

    //Test #6
    @Test
    public void testGetStatusShowByID(){
        String expectedTweet = "Hello Tweeter";
        ValidatableResponse response = this.tweetAPIClient.getStatusShowByID(1385821720733437952L);
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet=response.extract().body().path("text");
        Assert.assertEquals(actualTweet,expectedTweet,"Tweet Not Found");
    }

    //Test #7
    @Test
    public void testGetTrendsByPlaceID(){
        String expectedTrend = "Girl From Rio";
        ValidatableResponse response = this.tweetAPIClient.getTrendsByPlaceId(1);
        System.out.println(response.extract().body().asPrettyString());
        String actualTrend = response.extract().body().path("[0].trends[19].name");
        Assert.assertEquals(actualTrend,expectedTrend,"Tweet Not Found");
    }

    //Test #8
    @Test
    public void testGetAccountSettings(){
        String expectedSetting = "TaniyaGawri";
        ValidatableResponse response = this.tweetAPIClient.getAccountSettings();
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("screen_name");
        Assert.assertEquals(actualSetting,expectedSetting,"Setting Not Found");

    }
    //Test #9
    @Test(enabled = false)
    public void testPostAccountSettings(){
        String expectedSetting = "TaniyaGawri";
        ValidatableResponse response = this.tweetAPIClient.postAccountSettings();
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("screen_name");
        Assert.assertEquals(actualSetting,expectedSetting,"Setting Not Found");
    }


    //Test #10
    @Test
    public void testTweet() {
        // User sent a tweet tweet
        String tweet = "Hello World"+ UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        // response.statusCode(200);
        String actualTweet=response.extract().body().path("text");
        Assert.assertEquals(actualTweet,tweet,"Tweet is not match");
    }

    //Test #11
    @Test
    public void testDeleteTweetHello(){
        String tweet="Hello Worldeb721f2f-644a-4cc3-b614-c55e8b18fcf6";
        ValidatableResponse deleteResponse= this.tweetAPIClient.deleteTweet(1386463764925534208L);
       // deleteResponse.statusCode(403);
        String actualTweet= deleteResponse.extract().body().path("text");
        Assert.assertEquals(tweet,actualTweet);

    }

    //Test #12
    @Test
    public void testPostMessages(){
        ValidatableResponse message = this.tweetAPIClient.postDirectMessage("Hello", "1375997679377977346", "Hi");
        System.out.println(message.extract().body().asPrettyString());
    }

    //Test #13
    @Test
    public void testUserCanUploadProfilePic() {
        ValidatableResponse response = this.tweetAPIClient.uploadProfilePicture(Mypayload.profilepic());
        System.out.println(response.extract().body().asPrettyString());
        response.statusCode(HttpStatus.SC_OK);

    }
    //Test #14
    @Test
    public void testDirectMessage() throws FileNotFoundException {
        ValidatableResponse response = this.tweetAPIClient.messageCreate();
        response.statusCode(200);
        System.out.println(response.extract().body().asPrettyString());
    }

//    @Test
//    public void testUserCanSendDirectMsgWithPicture() throws FileNotFoundException {
//        ValidatableResponse response = this.tweetAPIClient.messageCreateWithPicture();
//        response.statusCode(200);
//        System.out.println(response.extract().body().asPrettyString());
//    }

    //Test #15
    @Test
    public void testGetSearchStatus(){

        String expectedText = "#flower #bloom #spring #April #SundayMotivation\n#photography #landscape #nature #wildlife #birdfood #photooftheday… https://t.co/Qzj1YDA5Na";
        ValidatableResponse response = this.tweetAPIClient.getStatusTweets("@flowers");
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("statuses[0].text");
        Assert.assertEquals(actualText,expectedText);
    }

    //Test #16
    @Test
    public void testGetFollowersID() throws FileNotFoundException {
        long expectedID = 1375997679377977346L;
        ValidatableResponse response = this.tweetAPIClient.getFollowersID(702779075094519808L);
        System.out.println(response.extract().body().asPrettyString());
        long actualID = response.extract().body().path("ids[0].");
        Assert.assertEquals(actualID,expectedID);
    }

    //Test #17
    @Test
    public void testGetFriendList(){
        String expectedSetting = "Pritam";
        ValidatableResponse response = this.tweetAPIClient.getFriendList(702779075094519808L);
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("users[0].name");
        Assert.assertEquals(actualSetting,expectedSetting,"Friend Not Found");
    }

    //Test #18
    @Test
    public void testGetFriendID(){
        long expectedID = 1375997679377977346L;
        ValidatableResponse response = this.tweetAPIClient.getFriendID(702779075094519808L);
        System.out.println(response.extract().body().asPrettyString());
        long actualID = response.extract().body().path("ids[0].");
        Assert.assertEquals(actualID,expectedID,"ID Not Found");
    }

    //Test #19
    @Test
    public void testGetFollowersList(){
        String expectedText = "prabash liyanage";
        ValidatableResponse response = this.tweetAPIClient.getFollowersList(702779075094519808L);
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("users[3].name");
        Assert.assertEquals(actualText,expectedText,"Follower Not Found");
    }

    //Test #20
    @Test
    public void testTrendAvailable()  {
        String expectedText = "Worldwide";
        ValidatableResponse response = this.tweetAPIClient.getTrentAvailable();
        System.out.println(response.extract().body().asPrettyString());
        String actualText = response.extract().body().path("[0].name");
        Assert.assertEquals(actualText,expectedText,"Follower Not Found");
    }
}

