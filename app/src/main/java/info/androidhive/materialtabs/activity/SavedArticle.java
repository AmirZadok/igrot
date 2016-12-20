package info.androidhive.materialtabs.activity;

/**
 * Created by AZ on 12/19/2016.
 */

public  class SavedArticle {

    private String bookName;
    private String articleName;
    private int bookNum;
    private int articleNum;
    private boolean checked = false ;

    public String getBookName() {
        return bookName;
    }

    public String getArticleName() {
        return articleName;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }

    public int getArticleNum() {
        return articleNum;

    }

    public SavedArticle(String bookName , String articleName, int bookNum , int articleNum ) {
        this.bookName = bookName;
        this.articleName = articleName;
        this.bookNum = bookNum;

        this.articleNum = articleNum;
    }


    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String toString() {
        return (bookName+"-"+articleName+" "+bookNum+" "+articleNum) ;
    }
    public void toggleChecked() {
        checked = !checked ;
    }
}