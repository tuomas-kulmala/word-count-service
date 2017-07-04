/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wcs.redis;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
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
    public void saveMessage(String key){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date);
        
        if(!jedis.exists(key)){
            jedis.lpush(key, "1");
            jedis.lpush(key, time);
        }else{
            List<String> valueList = jedis.lrange(key, 0 ,1);   
            jedis.lset(key, 0, time);
            jedis.lset(key, 1, Integer.parseInt(valueList.get(1))+1+"");
            
        }
        //jedis.incr(key);
        
         
    }
    public Message getMessage(String key){
        String value = jedis.get(key);
        return new Message(key,value,"time");
    }
    public List<Message> findAll(){
       List<Message> messageList = new ArrayList<>();
       // Find all keys
       Set<String> keyList = jedis.keys("*");
       // Create a messaage object for all keys
       // and add to a list
       for(String key : keyList){
           List<String> valueList = jedis.lrange(key, 0 ,1);
           messageList.add(new Message(key,valueList.get(1),valueList.get(0)));     
       }
       return messageList;
    } 
}
