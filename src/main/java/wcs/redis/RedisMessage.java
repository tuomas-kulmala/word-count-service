/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wcs.redis;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;
import wcs.core.Message;
/**
 *
 * @author Tuomas
 */
public class RedisMessage {
    private Jedis jedis;
    
    public RedisMessage(Jedis conn) {
        this.jedis = conn;
    } 
    public void saveMessage(String key, String value){
        jedis.set(key, value);
    }
    public Message getMessage(String key){
        String value = jedis.get(key);
        return new Message(key,value);
    }
    public List<Message> findAll(){
       List<Message> messages = new List<>();
       // Find all keys
       Set<String> list = jedis.keys("*");
       // Create a messaage object for all keys
       // and add to a list
       for(String k : list){
           messages.add(new Message(k,jedis.get(k)));
       }
       return messages;
    } 
}
