import java.util.*;
import twitter4j.*;

public class MessageBotTimer extends Bot {
	
	private Timer messageTimer;
   private Bot b;
	
   /*
    * initialize internal timer and schedule the timer task for every "sec" seconds
    */
    
    public MessageBotTimer() { b = new Bot(); }
    
    public void runTimer() throws TwitterException {
      
      System.out.println("Instanciated Retweet Bot");
      messageTimer = new Timer("Message_timer");
      
      // Setup TimerTasks
      TimerTask messageTimer = new TimerTask() {
         public void run() { 
            try { b.getMessages(); }
            catch(Exception e) { 
               System.out.println("Failure");
               return; 
            }
         }
      };
      
           
      System.out.println("Created Retweet Timer and Task");
      System.out.println("Running...");
      
      // Runs every second
      messageTimer.scheduleAtFixedRate(retweetTask, 0, 1000);    
    }
    
    public static void main(String args[]) throws TwitterException {
      messageBotTimer bBot = new messageBotTimer();
      bBot.runTimer();
    }
}
