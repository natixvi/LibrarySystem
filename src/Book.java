public class Book {
    private String title;
    private String author;
    private Boolean borrowed = false;
    private String borrower = "";
    public Book(String bookTitle, String bookAuthor){
        super();
        title = bookTitle;
        author = bookAuthor;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String toString(){
        return author + "           " + title + "           Borrowed:  " + borrowed;
    }
}
