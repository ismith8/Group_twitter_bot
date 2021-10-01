public class Authentication {

    private static final String CONSUMER_KEY = "TJOmAjU6GX3Te8HiLSefpe1oT";
    private static final String CONSUMER_SECRET = "oMICnlKUSNYbSK03WI960pefnDhAeRazFdsGnDXK4e2ivOU22V";
    private static final String ACCESS_TOKEN = "1304107129989726208-T84EEYmXzXjrRpoORpRECXUyi3oenc";
    private static final String ACCESS_TOKEN_SECRET = "wKurnqbNGVnq4dWMW8aRVoKaZNh5tKXikGDBSYNnwYCOG";
    private static final String BOT_ID = "1304107129989726208";

    protected Authentication() {}

    protected static String getConsumerKey() {
        return CONSUMER_KEY;
    }

    protected static String getConsumerSecret() {
        return CONSUMER_SECRET;
    }

    protected static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    protected static String getAccessTokenSecret() {
        return ACCESS_TOKEN_SECRET;
    }
    
    protected static String getBotId() {
        return BOT_ID;
    }
   
}