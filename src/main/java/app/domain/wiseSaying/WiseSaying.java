package app.domain.wiseSaying;

public class WiseSaying {
    private int id;

    private String author;

    private String content;


    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return id + " / " + author + " / " + content;
    }

    public boolean isNew() {
        return this.id == 0;
    }
}
