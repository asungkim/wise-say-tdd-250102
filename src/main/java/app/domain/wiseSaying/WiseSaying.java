package app.domain.wiseSaying;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class WiseSaying {

    private int id;

    private String content;

    private String author;

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

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

    public Map<String,Object> toMap() {
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("id",id);
        map.put("content",content);
        map.put("author",author);

        return map;
    }
}
