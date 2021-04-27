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
        String tweet = "Twitter is toxic"+ UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
       // response.statusCode(200);
        String actualTweet=response.extract().body().path("text");
        Assert.assertEquals(actualTweet,tweet,"Tweet is not match");
    }


    //Test #2
    @Test
    public void testUserCanNotTweetTheSameTweetTwiceInARow() {
        // User sent a tweet
        String tweet = "Twitter is toxic";
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        // Verify that the tweet is successful
       //response.statusCode(403);
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
        String tweet="Twitter is toxic486f9bf7-fbac-45f7-8acb-4d28e63119ad";
        ValidatableResponse deleteResponse= this.tweetAPIClient.deleteTweet(1386816791188582404L);
        deleteResponse.statusCode(200);
        String actualTweet= deleteResponse.extract().body().path("text");
        Assert.assertEquals(tweet,actualTweet);

    }

    //Test #4
    @Test(enabled = true)
    public void testResponseTime() {
        String expectedResponse = "Mon Apr 26 22:58:18 +0000 2021";
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
        String expectedSetting = "Taylor06048619";
        ValidatableResponse response = this.tweetAPIClient.getAccountSettings();
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("screen_name");
        Assert.assertEquals(actualSetting,expectedSetting,"Setting Not Found");

    }
    //Test #9
    @Test
    public void testPostAccountSettings(){
        String expectedSetting = "Taylor06048619";
        ValidatableResponse response = this.tweetAPIClient.postAccountSettings();
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("screen_name");
        Assert.assertEquals(actualSetting,expectedSetting,"Setting Not Found");
    }

    @Test
    public void testGetFriendsList(){
       String expectedSetting = "Gawri Taniya";
        ValidatableResponse response = this.tweetAPIClient.getFriendsList(1386811544831463430L);
        System.out.println(response.extract().body().asPrettyString());
        String actualSetting = response.extract().body().path("users[0].name");
        Assert.assertEquals(actualSetting,expectedSetting,"Friend not found");
    }

    @Test
    public void testGetFriendsIDS(){
        long expectedId = 702779075094519808L;
        ValidatableResponse response = this.tweetAPIClient.getFriendsIDS(1386811544831463430L);
        System.out.println(response.extract().body().asPrettyString());
        long actualId = response.extract().body().path("ids[0].");
        Assert.assertEquals(actualId,expectedId,"ID not found");
    }

    @Test
    public void testGetSearchTweet(){
       String expectedTweet = "@MMCrypto Guys Im in SpaceX rocket flying to the Moon with @Elon Musk and @DavinciJ15 and I found somethin interesting on charts!!!!!!!!";
        ValidatableResponse response = this.tweetAPIClient.getSearchTweet("@Elon Musk");
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("statuses[0].text");
        Assert.assertEquals(actualTweet,expectedTweet,"Tweet not found");
    }

    @Test
    public void testGetTrendsAvailable(){
        String expectedTweet = "Worldwide";
        ValidatableResponse response = this.tweetAPIClient.getTrendsAvailable();
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("[0].name");
        Assert.assertEquals(actualTweet,expectedTweet,"Tweet not found");
    }

    @Test
    public void testGetUsersSearch(){
        String expectedTweet = "Wendy's";
        ValidatableResponse response = this.tweetAPIClient.getUsersSearch("@Wendy's");
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("[0].name");
        Assert.assertEquals(actualTweet,expectedTweet,"User not found");
    }

    @Test
    public void testGetUsersShow(){
       String expectedTweet = "NintendoAmerica";
        ValidatableResponse response = this.tweetAPIClient.getUsersShow(5162861L);
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("screen_name");
        Assert.assertEquals(actualTweet,expectedTweet,"User not found");
    }

    @Test
    public void testGetUsersLookup(){
        long expectedTweet = 702779075094519808L;
        ValidatableResponse response = this.tweetAPIClient.getUsersLookup(702779075094519808L);
        System.out.println(response.extract().body().asPrettyString());
        long actualTweet = response.extract().body().path("[0].id");
        Assert.assertEquals(actualTweet,expectedTweet,"User not found");
    }

    @Test
    public void testGetTrendPLace(){
        String expectedTweet = "#جامعه_زايد";
        ValidatableResponse response = this.tweetAPIClient.getTrendPlace(1940330);
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("[0].trends[0].name");
        Assert.assertEquals(actualTweet,expectedTweet,"User not found");
    }

    @Test
    public void testGetHelpLanguages(){
        String expectedTweet = "French";
        ValidatableResponse response = this.tweetAPIClient.getHelpLanguage();
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("[9].name");
        Assert.assertEquals(actualTweet,expectedTweet,"User not found");
    }

    @Test
    public void testGetHomeTimeline(){
      String expectedTweet = "A free demo for #Miitopia is available now on #NintendoSwitch #eShop! Download and begin your comedy-filled adventu… https://t.co/txpjcTzj5Q";
        ValidatableResponse response = this.tweetAPIClient.getHomeTimeline();
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("[0].text");
        Assert.assertEquals(actualTweet,expectedTweet,"User not found");
    }

    @Test
    public void testGetFavoriteList(){
        String expectedTweet = "Wherever life plants you bloom with grace68327be3-1d81-48ba-918c-b10e6138d4e4";
        ValidatableResponse response = this.tweetAPIClient.getFavoritesList(1386811544831463430L);
        System.out.println(response.extract().body().asPrettyString());
        String actualTweet = response.extract().body().path("[1].text");
        Assert.assertEquals(actualTweet,expectedTweet,"User not found");
    }


}
