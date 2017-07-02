/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wcs.redis;

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
}
