package domain;

/**
 *
 * @author hemn
 */
public class Post {
    private String post_id;
    private String title;
    private String content;
    private String author_id;
    private String name;
    private String time;
    private String contentDescription;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        if(content.length() > 10) {
        	this.contentDescription = content.substring(0, 10);
        	this.contentDescription += "...";
        } else {
        	this.contentDescription = content;
        }
    }
    
    public String getContentDescription() {
        return contentDescription;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
