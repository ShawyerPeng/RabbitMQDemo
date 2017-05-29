package implementations;

import com.google.gson.Gson;

public class Message implements JSONMessage {
    private int msgNo;
    private String from;
    private String to;
    private String header;
    private String content;
    private static Gson gson = new Gson();

    public int getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(int msgNo) {
        this.msgNo = msgNo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJSON() {
        return gson.toJson(this);
    }

    public static Message fromJSON(String msg) {
        Gson gson = new Gson();
        return (Message) gson.fromJson(msg, Message.class);
    }

    public String toString() {
        return String.format("Message No: %d From: %s To: %s " +"Header: %s Content: %s", msgNo, from, to, header, content);
    }
}
