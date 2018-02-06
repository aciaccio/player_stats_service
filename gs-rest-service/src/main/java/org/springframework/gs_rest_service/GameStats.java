package org.springframework.gs_rest_service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import java.lang.Number;

public class GameStats {

   private static GameStatsObject stats;
   
   static
   {
      String transactionUrl = "http://api.fantasy.nfl.com/v1/game/stats";

      UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(transactionUrl)
            // Add query parameter
            .queryParam("format", "json");
      
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<GameStatsObject> statsResponse =
            restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<GameStatsObject>() {});
      stats = statsResponse.getBody();
   }
   
   public String toString()
   {
      StringBuilder builder = new StringBuilder();
      for(StatObject stat : stats.getStats())
      {
         builder.append(stat.toString());
      }
      
      return builder.toString();
   }
   
   public static String GetStatAbbr(String statId)
   {
      System.out.println("GameStats: " + stats.toString());
      return stats.getStats().get(new Integer(statId)).getAbbr();
   }
   
   public static String GetStatShortName(String statId)
   {
      return stats.getStats().get(new Integer(statId) - 1).getShortName();
   }
   
   public static int GetStatIdFromAbbr(String statAbbr)
   {
      for (int i = 0; i < stats.getStats().size(); i++)
      {
         if(stats.getStats().get(i).getAbbr().equals(statAbbr))
            return new Integer(i) + 1;
      }
      
      return 0;
   }

}
