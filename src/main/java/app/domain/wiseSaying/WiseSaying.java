package app.domain.wiseSaying;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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


    public boolean isNew() {
        return this.id == 0;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("content", content);
        map.put("author", author);

        return map;
    }

    public static WiseSaying fromMap(Map<String, Object> map) {
        int id = (int) map.get("id");
        String content=(String) map.get("content");
        String author=(String) map.get("author");

        return new WiseSaying(id,content,author);
    }
}
