package app.domain.wiseSaying;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WiseSaying {

    private int id;

    private String author;

    private String content;


    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return id + " / " + author + " / " + content;
    }

    public boolean isNew() {
        return this.id == 0;
    }
}
