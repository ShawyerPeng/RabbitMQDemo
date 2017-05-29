package implementations;

import com.google.gson.Gson;

public class FileMessage implements JSONMessage {
    private int msgNo;
    private String from;
    private String to;
    private String header;
    private byte[] file_content;
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

    public byte[] getFile_content() {
        return file_content;
    }

    public void setFile_content(byte[] file_content) {
        this.file_content = file_content;
    }

    public String toJSON() {
        return gson.toJson(this);
    }

    public static FileMessage fromJSON(String msg) {
        return (FileMessage) gson.fromJson(msg, FileMessage.class);
    }

    public String toString() {
        try {
            return String.format("Message No: % From: %s To: %s " + "Header: %s File Content: \n %s UTF-8", msgNo,from, to, header, getFile_content());
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
