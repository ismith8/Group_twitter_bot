public class College {

    private String college;
    private String twitter;
    private String question;
    private String openClose;
    private int cases;

    College(String college, String twitter){
        this.college = college;
        this.twitter = twitter;
        this.question = "None";
        this.cases = 0;
    }

    College(String college, String twitter, String question){
        this.college = college;
        this.twitter = twitter;
        this.question = question;
        this.cases = 0;
    }


    College(String college, String twitter, String question, String openClose, int cases){
        this.college = college;
        this.twitter = twitter;
        this.question = question;
        this.openClose = openClose;
        this.cases = cases;
    }

    public String getCollege() {
        return college;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getQuestion() {
        return question;
    }

    public String getOpenClose(){return openClose;}

    public int getCases() {
        return cases;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setTwitter(String acronym) {
        this.twitter = acronym;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOpenClose(String openClose) {this.openClose = openClose;}

    public void setCases(int cases) {
        this.cases = cases;
    }
}
