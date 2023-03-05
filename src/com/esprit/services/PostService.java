/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.esprit.entities.Post;
import com.esprit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author b.zraier
 */
public class PostService {

    private ConnectionRequest req;
    public static boolean resultOk = true;

    public static PostService instance = null;

    public static PostService getInstance() {
        if (instance == null) {
            instance = new PostService();
        }
        return instance;
    }

    public PostService() {
        req = new ConnectionRequest();
    }

    //AJOUT
    public void ajoutPost(Post post) {

        String url = Statics.BASE_URL + "/APIaddP?user_id=" + post.getId_user() + "&nom_utilisateur="
                + post.getNom_utilisateur() + "&description=" + post.getDescription()+"&publication=" + post.getPublication()+ "&created_at=" + post.getCreated_at() + "&updated_at=" + post.getUpdated_at();
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.err.println("data=" + str);

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    //AFFICHAGE
    public ArrayList<Post> affichePost() {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date createdAt;
        ArrayList<Post> posts = new ArrayList<>();

        String url = Statics.BASE_URL +"/APIafficheP";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;

                jsonp = new JSONParser();

                Map<String, Object> mapPost;
                try {
                    mapPost = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) mapPost.get("root");
                    for (Map<String, Object> obj : list) {
                        Post post = new Post();
  String likes = "0" ;
   String dislike = "0" ;
                        float id = Float.parseFloat(obj.get("id").toString());
                        float id_user = Float.parseFloat(obj.get("id_user").toString());
                        String description = obj.get("description").toString();
                        String publication = obj.get("publication").toString();
                        String nom_utilisateur = obj.get("nom_utilisateur").toString();
                        
                       
                        String created_at = obj.get("created_at").toString();
                        String updated_at = obj.get("updated_at").toString();

                        post.setId((int) id);
                        post.setId_user((int) id_user);
                        post.setDescription(description);
                        post.setPublication(publication);
                        post.setNom_utilisateur(nom_utilisateur);
                        post.setLikes(likes);
                        post.setDislike(dislike);
                        post.setCreated_at(created_at);
                        post.setUpdated_at(updated_at);

                     
                        
                        posts.add(post);

                    }

                } catch (IOException ex) {
                }

            }

        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);


        return posts;
    }
    
    
    //Update 
    public boolean modifierPost(Post post, int id) {
         String url = Statics.BASE_URL + "/APIupdatePost/"+id+"?user_id=" + post.getId_user() + "&nom_utilisateur="
                + post.getNom_utilisateur() + "&description=" + post.getDescription()+"&publication=" + post.getPublication() ;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    
       //Delete 
    public boolean deletePost(int id ) {
        String url = Statics.BASE_URL +"/APISuppPost?id="+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    

}
