/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wcs.core;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import redis.clients.jedis.Jedis; 
import wcs.redis.RedisMessage;

/**
 *
 * @author Tuomas Kulmala
 */
public class Main {
        public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
 
        // Redis address from Heroku variable if available
        String redisAddress = "localhost";
        if (System.getenv("REDIS_URL") != null) {
            redisAddress = System.getenv("REDIS_URL");
        } 
                 
        //Connecting to Redis server
        Jedis jedis = new Jedis(redisAddress);
        // Delete all records from cache
        jedis.flushAll();
        // New redismessage object
        RedisMessage redismessage = new RedisMessage(jedis);
        
        
        // Handle request to main page       
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine()); 
        
        // Handle request to receiver
        get("/receiver", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("messages",redismessage.findAll());
            return new ModelAndView(map, "receiver");
        }, new ThymeleafTemplateEngine()); 
        
        // Handle post messages to receiver
        post("/receiver", (req, res) -> {
            if(!req.queryParams("message").isEmpty()){
                redismessage.saveMessage(req.queryParams("message"));
            }
            res.redirect("/receiver");
            return "ok";
        });
        
    }
}
