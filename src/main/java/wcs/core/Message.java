/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wcs.core;

/**
 *
 * @author Tuomas
 */
public class Message {
    private String key;
    private String value;
    private String time;
    
    public Message(String key, String value, String time){
        this.key = key;
        this.value = value;
        this.time = time;
        
    }
    public String getKey(){
        return this.key;
    }
    public String getValue(){
        return this.value;
    }
    public String getTime(){
        return this.time;
    }
}
