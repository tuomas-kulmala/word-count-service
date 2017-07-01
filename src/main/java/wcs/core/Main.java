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
        String redisAddress = "jdbc:sqlite:opiskelijat.db";
                if (System.getenv("REDIS_URL") != null) {
            redisAddress = System.getenv("REDIS_URL");
        } 
                
        //Connecting to Redis server on localhost 
        Jedis jedis = new Jedis(redisAddress);
        
                
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine()); 


        
    }
}
