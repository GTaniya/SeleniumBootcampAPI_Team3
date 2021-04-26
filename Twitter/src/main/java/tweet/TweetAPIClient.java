package tweet;

import base.RestAPI;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class TweetAPIClient extends RestAPI {

    //Enter EndPoints Below
    private final String CREATE_TWEET_ENDPOINT = "/statuses/update.json";
    private final String DELETE_TWEET_ENDPOINT = "/statuses/destroy.json";
    private final String GET_USER_TWEET_ENDPOINT = "/statuses/home_timeline.json";
    private final String GET_STATUS_SHOW_ID = "/statuses/show.json";
    private final String GET_TRENDS_PLACE = "/trends/place.json";
    private final String GET_ACCOUNT_SETTINGS = "/account/settings.json";
    private final String POST_ACCOUNT_SETTINGS = "/account/settings.json";
    private final String GET_FRIEND_LIST = "/friends/list.json";
    private final String POST_DIRECT_MESSAGE = "/direct_messages/events/new.json";
    private final String POST_UPDATE_PP_ENDPOINT = "/account/update_profile_image.json";
    private final String GET_SEARCH_TWEET = "/search/tweets.json";
    private final String GET_FOLLOWERS_ID = "/followers/ids.json";
    private final String GET_FRIENDS_ID = "/friends/ids.json";
    private final String GET_FOLLOWERS_LIST = "/followers/list.json";
    private final String GET_TREND_AVAILABLE = "/trends/available.json";
    private final String POST_DIRECT_MESSAGES = "/direct_messages/events/new.json";
    public final String directMsgWithPath = "../Twitter/jsonFiles/jsonMessageWithImage.json";

    ////////////////////////////////////-> Action Methods Below <-/////////////////////////////////////////

    // GET all Tweet Information
    public ValidatableResponse getUserTimeTweet() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_USER_TWEET_ENDPOINT).then().statusCode(200);
    }

    // Create a Tweet from user twitter
    public ValidatableResponse createTweet(String tweet) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("status", tweet)
                .when().post(this.baseUrl + this.CREATE_TWEET_ENDPOINT)
                .then();
    }

    // Delete a tweet from user twitter
    public ValidatableResponse deleteTweet(Long tweetId) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("id", tweetId)
                .when().post(this.baseUrl + this.DELETE_TWEET_ENDPOINT).then().statusCode(200);
    }


    // Response Time check
    public ValidatableResponse responseTime() {
        System.out.println(given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_USER_TWEET_ENDPOINT)
                .timeIn(TimeUnit.MILLISECONDS));
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_USER_TWEET_ENDPOINT)
                .then();

    }

    // Header Value
    public void headerValue() {
        System.out.println(given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_USER_TWEET_ENDPOINT)
                .then().extract().headers());

    }

    public void checkProperty() {
        Response response = given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_USER_TWEET_ENDPOINT);
        JsonPath pathEvaluator = response.jsonPath();
        String createdAt = pathEvaluator.get("id");
        System.out.println(createdAt);
    }

    public ValidatableResponse getStatusShowByID(long tweetId) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("id", tweetId)
                .when().get(this.baseUrl + this.GET_STATUS_SHOW_ID).then();
    }

    public ValidatableResponse getTrendsByPlaceId(int id) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("id", id)
                .when().get(this.baseUrl + this.GET_TRENDS_PLACE).then();
    }

    public ValidatableResponse getAccountSettings() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_ACCOUNT_SETTINGS).then();
    }

    public ValidatableResponse postAccountSettings() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().post(this.baseUrl + this.POST_ACCOUNT_SETTINGS).then();
    }


    public ValidatableResponse postDirectMessage(String type, String receipt_id, String message_data) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .header("type", type)
                .header("message_create.target.recipient_id", receipt_id)
                .header("message_create.message_data", message_data)
                .when().post(this.baseUrl + this.POST_DIRECT_MESSAGE).then();
    }

    public ValidatableResponse uploadProfilePicture(String ppimage){
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("image", ppimage)
                .when().post(this.baseUrl+this.POST_UPDATE_PP_ENDPOINT)
                .then();
    }

    public ValidatableResponse messageCreate() throws FileNotFoundException {
        FileInputStream jsonMessage = new FileInputStream("C:\\Users\\gawri\\IdeaProjects\\SeleniumBootcampAPI_Team3\\Twitter\\jsonFiles\\jsonMessage.json");
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .header("Content-Type", "application/json")
                .body(jsonMessage)
                .when().post(this.baseUrl + this.POST_DIRECT_MESSAGES)
                .then();
    }


    public ValidatableResponse messageCreateWithPicture() throws FileNotFoundException {
        FileInputStream jsonMessage = new FileInputStream(directMsgWithPath);
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .header("Content-Type","application/json")
                .body(jsonMessage)
                .when().post(this.baseUrl + this.POST_DIRECT_MESSAGES)
                .then();
    }

    public ValidatableResponse getStatusTweets(String id){
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("q",id)
                .when().get(this.baseUrl + this.GET_SEARCH_TWEET).then().statusCode(200);
    }

    public ValidatableResponse getFollowersID(long id){
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("user_id",id)
                .when().get(this.baseUrl + this.GET_FOLLOWERS_ID).then().statusCode(200);
    }

    public ValidatableResponse getFriendList(long id) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("user_id",id)
                .when().get(this.baseUrl + this.GET_FRIEND_LIST).then();

    }

    public ValidatableResponse getFriendID(long id) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("user_id",id)
                .when().get(this.baseUrl + this.GET_FRIENDS_ID).then();

    }

    public ValidatableResponse getFollowersList(long id) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("user_id",id)
                .when().get(this.baseUrl + this.GET_FOLLOWERS_LIST).then();

    }

    public ValidatableResponse getTrentAvailable() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_TREND_AVAILABLE).then();

    }

}
