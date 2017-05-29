package implementations;

import com.google.gson.Gson;

import java.util.Date;
import java.util.UUID;

public class Task implements JSONMessage {
    private String id;
    private String hostname;
    private String delivery_info;
    private String command;
    private String errback;
    private Date expires;
    private static Gson gson = new Gson();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDelivery_info() {
        return delivery_info;
    }

    public void setDelivery_info(String delivery_info) {
        this.delivery_info = delivery_info;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getErrback() {
        return errback;
    }

    public void setErrback(String errback) {
        this.errback = errback;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public Task() {
        id = UUID.randomUUID().toString();
        hostname = "";
        delivery_info = "";
        command = "";
        errback = "";
        expires = new Date();
    }

    public String toJSON() {
        return gson.toJson(this);
    }

    public static Task fromJSON(String msg) {
        return (Task) gson.fromJson(msg, Task.class);
    }

    public String toString() {
        return "ID: " + getId() + " Hostname: " + getHostname() + " Delivery Info: " + getDelivery_info() + " Callbacks: " + getCommand() + " Expires: " + getExpires().toString();
    }
}
