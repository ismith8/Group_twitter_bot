import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.*;

public class Bot {

   private TwitterFactory tf;
   private Twitter twitter;
   private List<College> colleges;
   private List<String> words;
   private boolean init = false;


   public Bot()  {
      // For effiecency, only initializes once
      if (!init) { init(); System.out.println("Loaded data\n"); }
      System.out.println("Created a new bot");
   }


 /*
  * Called by constructor to initialize needed fields
  */
   private void init() {

      Authentication auth = new Authentication();
      // Authenticates to Twitter account
      ConfigurationBuilder cb = new ConfigurationBuilder();
      cb.setDebugEnabled(true)
             .setOAuthConsumerKey(auth.getConsumerKey())
             .setOAuthConsumerSecret(auth.getConsumerSecret())
             .setOAuthAccessToken(auth.getAccessToken())
             .setOAuthAccessTokenSecret(auth.getAccessTokenSecret());
      tf = new TwitterFactory(cb.build());
      twitter = tf.getInstance();


      // Builds word bank
      words = new ArrayList();
      words.add("COVID");
      words.add("COVID19");
      words.add("coronavirus");
      words.add("pandemic");
      words.add("Pandemic");
      words.add("Coronavirus");
      words.add("COVID-19");
      words.add("SARS-CoV-2");
      words.add("covid");
      words.add("covid-19");

      // VA College Bank
      College UVA = new College("University of Virginia", "UVA", "https://hooshealthcheck.virginia.edu/sign-in", "Open", -1);
      College WM = new College("The College of William & Mary", "williamandmary", "https://m.wm.edu/default/healthy_together", "Open", -1);
      College VT = new College("Virginia Polytechnic Institute and State University", "virginia_tech", "NONE", "Open", -1);
      College JMU = new College("James Madison University", "JMU", "https://www.jmu.edu/stop-the-spread/livesafe.shtml", "Open", -1);
      College VCU = new College("Virginia Commonwealth University", "VCU", "https://login.vcu.edu/cas/login?service=https%3a%2f%2fdailyhealth.vcu.edu%2f", "Open", -1);
      College GMU = new College("George Mason Universty", "GeorgeMasonU", "www.test.com", "Open", -1);
      College CNU = new College("Christopher Newport University", "CNUcaptains", "https://cnu.edu/relaunch/tracker/", "Open", -1);
      College UMW = new College("University of Mary Washington", "MaryWash", "https://www.vdh.virginia.gov/covidwise/", "Open", -1);
      College ODU = new College("Old Dominion University", "ODU", "https://live.origamirisk.com/Origami/Incidents/New?_collectionLinkItemID=4&IncidentTypeID=10005", "Open", -1);
      College RU = new College("Radford University", "radfordu", "https://www.radford.edu/content/radfordcore/home/reopening/updates/symptom-tracking.html", "Open", -1);
      College LU = new College("Longwood University", "longwoodu", "http://www.longwood.edu/covid19/planning/#healthycampus", "Open", -1);
      College NSU = new College("Norfolk State University", "Norfolkstate", "NONE", "Open", -1);
      College VSU = new College("Virginia State University", "VSU_1882", "http://www.vsu.edu/files/docs/academics/Fall2020/COVID_19_Self_Monitoring.pdf", "Open", -1);
      College TTU = new College("Covid Test University", "CovidTestUnive1");

      colleges = new ArrayList();
      colleges.add(UVA);
      colleges.add(WM);
      colleges.add(VT);
      colleges.add(JMU);
      colleges.add(VCU);
      colleges.add(GMU);
      colleges.add(CNU);
      colleges.add(UMW);
      colleges.add(ODU);
      colleges.add(RU);
      colleges.add(LU);
      colleges.add(NSU);
      colleges.add(VSU);
      colleges.add(TTU);

      // setup timer
      init = true;
   }
   

 /*
  * Runs a test tweet
  */
   public void testTweet() throws TwitterException {
      // Updates Twitter Account with new Tweet.
      String latestStatus = "Test Tweet";
      Status status = twitter.updateStatus(latestStatus);
      System.out.println("Successfully updated the status to [" + status.getText() + "].");
   }
   




 /* #TIMER
  *
  * This should be run every few seconds to check for new messages
  * This will queue all the requests and call the action_handler() to
  * determine what to do with the message
  *
  */
 public void getMessages() throws TwitterException {

   List<DirectMessage> messages = twitter.getDirectMessages(25);

   // If list is empty continue
   if (messages == null) return;
   else for (int i = 0; i < messages.size(); i++) { action_handler(messages.get(i)); }
 }

 /*
  * Function to assist action_handler
  * @param Long id
  * @return String message
  */
 private String getMessageById(Long id) throws TwitterException {
   List<DirectMessage> messages = twitter.getDirectMessages();
   System.out.println("\n\n\nGot response: ");
   System.out.println(messages.get(0).getText());
   return messages.get(0).getText();
 }


 /*
  * This method is designed to decipher the string of messages provided
  * @param message of type DirectMessage from users
  */
 private void action_handler(DirectMessage msg) throws TwitterException {
   
   // Only checks incoming messages
   Long usrId = msg.getSenderId();
   Long botId = Long.parseLong(Authentication.getBotId());
      
   int subindex = 0;
   if (usrId.equals(botId)) return;
   
   System.out.println("\n-------------------------------------------");
   System.out.println(msg.getText());
   System.out.println("BotIDL " + twitter.showUser("19_watcher").getId());
   System.out.println("UserID: " + usrId);
   System.out.println("-------------------------------------------\n");
   
   ArrayList<String> keywords = new ArrayList<String>();

   // Logic tree
   String[] message_words = msg.getText().split(" ");
   for (int i = 0; i < message_words.length; i++) {
   
      // Keywords for school open/closed & health assessment
      if (message_words[i].equalsIgnoreCase("open")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("closed")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("school")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("quiz")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("assessment")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("test")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("testing")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("status")) keywords.add(message_words[i].toUpperCase());

      // Helps to define the question
      if (message_words[i].equalsIgnoreCase("what")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("what's")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("whats")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("where")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("wheres")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("where's")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("how")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("can")) keywords.add(message_words[i].toUpperCase());
      if (message_words[i].equalsIgnoreCase("is")) keywords.add(message_words[i].toUpperCase());
   }

   System.out.println("Selected Keywords: ");
   System.out.println(keywords.toString());
   
   // Sends message to user that Bot did not understand the question
   if (keywords.size() == 0) {
      this.sendMessage(usrId, "Sorry, I don't understand your question. Try using more specific wording"); // ru6St4Y
   }  

   // Logic Tree
      for (int e = 0; e < message_words.length; e++) {
      
         // Schools testing info
         if (message_words[e].toUpperCase().equals("WHERE") || message_words[e].toUpperCase().equals("WHERE'S") || message_words[e].toUpperCase().equals("WHERES")) {
            subindex = 0;
            
            while (subindex < message_words.length) {
               if (message_words[subindex].toUpperCase().equals("TESTING") || message_words[subindex].toUpperCase().equals("TEST") || message_words[subindex].toUpperCase().equals("QUIZ") || message_words[subindex].toUpperCase().equals("ASSESSMENT")) {
   
                  System.out.println("School testing info");
   
                  // Send user question
                  this.sendMessage(usrId, "What are your school's initials?");
                  try { Thread.sleep(5000); } // waits 5 seconds for responce
                  catch(Exception except) {
                     System.out.println("Wait failed");
                     return;
                  }
                  
                  String usr_msg = getMessageById(usrId);
                  String[] arr = usr_msg.split(" ");
                  System.out.println("sent");

                  // Send msg w results, return
                  for (int a = 0; a < arr.length; a++) {
                     for (int b = 0; b < colleges.size(); b++) {
                        if (arr[a].equals(colleges.get(b))) {
                           String returner = "";
                           if (colleges.get(b).getQuestion().equals("NONE")) {
                              returner += "There is no link for your school. Boo :(";
                           }
                           else {
                              returner = "The link for ";
                              returner += colleges.get(b).getCollege();
                              returner += "'s COVID test is: ";
                              returner += colleges.get(b).getQuestion();
                           }
                           System.out.println("Sending results: " + returner);
                           this.sendMessage(usrId, returner);
                           return;
                        }
                     }
                  }
               } subindex++;
            }
         }

         // School open/closed
         if (message_words[e].toUpperCase().equals("WHAT") || message_words[e].toUpperCase().equals("WHATS") || message_words[e].toUpperCase().equals("WHAT'S") || message_words[e].toUpperCase().equals("IS")) {
            subindex = 0;
            
            while (subindex < message_words.length) {
               if (message_words[subindex].toUpperCase().equals("OPEN") || message_words[subindex].toUpperCase().equals("CLOSED")) {

                  System.out.println("School open/close");

                  // Send user question
                  this.sendMessage(usrId, "What are your school's initials?");
                  try { Thread.sleep(5000); } // waits 5 seconds for responce
                  catch(Exception except) {
                     System.out.println("Wait failed");
                     return;
                  }                  
                  
                  String usr_msg = getMessageById(usrId);
                  String[] arr = usr_msg.split(" ");
                  
                  // Send msg w results, return
                  for (int a = 0; a < arr.length; a++) {
                     for (int b = 0; b < colleges.size(); b++) {
                        if (arr[a].equals(colleges.get(b))) {
                           String returner = "";
                           returner += colleges.get(b).getCollege();
                           returner += " current status: ";
                           returner += colleges.get(b).getOpenClose();
                           System.out.println("Sending message: " + returner);
                           this.sendMessage(usrId, returner);
                           return;
                        }
                     }
                  }
               } subindex++;
            }
         }
         

         // School count
         if (message_words[e].toUpperCase().equals("HOW") || message_words[e].toUpperCase().equals("WHAT") || message_words[e].toUpperCase().equals("WHATS") || message_words[e].toUpperCase().equals("WHAT'S")) {
            subindex = 0;

            while (subindex < message_words.length) {
               if (message_words[subindex].toUpperCase().equals("MANY") || message_words[subindex].toUpperCase().equals("COUNT") || message_words[subindex].toUpperCase().equals("CASES") || message_words[subindex].toUpperCase().equals("STATUS")) {

                  System.out.println("Case count");

                  // Send user question
                  this.sendMessage(usrId, "What are your school's initials?");
                  try { Thread.sleep(5000); } // waits 5 seconds for responce
                  catch(Exception except) {
                     System.out.println("Wait failed");
                     return;
                  }
                  
                  String usr_msg = getMessageById(usrId);
                  String[] arr = usr_msg.split(" ");
                  System.out.println("Completed sleep");
                  
                  // Send msg w results, return
                  for (int a = 0; a < arr.length; a++) {
                     for (int b = 0; b < colleges.size(); b++) {
                        if (arr[a].equals(colleges.get(b))) {
                           System.out.println("school count");
                           String returner = "";
                           returner += colleges.get(b).getCollege();
                           returner += " current has ";
                           returner += colleges.get(b).getCases();
                           returner += "cases. Stay safe.";
                           System.out.println("Sending message: " + returner);
                           this.sendMessage(usrId, returner);
                           return;
                        }
                     }
                  }
               } subindex++;
            }
         }
      }
   }


 /*
  * Takes in
  * @param message - a String to be used as the message
  * @param user - long for User ID
  */
   public void sendMessage(Long usrId, String message) throws TwitterException {
      System.out.println(usrId);
      //twitter.sendDirectMessage(usrId, message); # FIXME
      System.out.println("Sent direct message " + message + " to " + usrId + " - UNCOMMENT ME LATER");
   }

 /*
  * Takes in
  * @param message - a String to be used as the message
  */
   public void sendTweet(String message) throws TwitterException {
      twitter.updateStatus(message);
      System.out.println("Tweeted.");
   }



 /*
  * #TIMER
  *
  * Controls the retweeting functionalities for college feeds
  */
   public void retweetCollege() throws TwitterException {
      for(College col : colleges) {
         List<Status> statuses = twitter.getUserTimeline(col.getTwitter());
         for (Status status : statuses) {
            for (String word : words) {
               if (status.getText().contains(word)) {
                  try {
                     twitter.retweetStatus(status.getId());
                     System.out.println(status.getUser().getName() + ": " + status.getText());
                  } catch (Exception e) {
                      System.out.println("Already Retweeted: " + status.getId());
                  }
               }
            }
         }
      }
   }

   public static void main(String[] args) throws TwitterException {
      System.out.println("Running");
      Bot a = new Bot();
      a.getMessages();
   }
}
