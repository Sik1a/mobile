/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.entities.Comments;
import com.esprit.entities.Post;
import com.esprit.services.CommentService;

/**
 *
 * @author b.zraier
 */
public class addComment extends BaseForm {
    
    
    private Post post ; 

    public addComment(Post post) {
        this.post = post;
    }
    

    public addComment(Resources res,Post post) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        TextField id_user = new TextField(String.valueOf(post.getId_user()), "id_user", 20, TextField.ANY);
        TextField reponse = new TextField("", "reponse", 20, TextField.ANY);
        TextField date = new TextField("", "date", 20, TextField.ANY);
        TextField commentaire_id = new TextField(String.valueOf(post.getId()), "commentaire_id", 20, TextField.ANY);
        
       
        Button signIn = new Button("headback");
        signIn.addActionListener(e -> previous.showBack());
        id_user.setSingleLineTextArea(false);
        reponse.setSingleLineTextArea(false);
        date.setSingleLineTextArea(false);
        commentaire_id.setSingleLineTextArea(false);
        Button next = new Button("add");
        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(id_user),
                createLineSeparator(),
                new FloatingHint(commentaire_id),
                createLineSeparator(),
                new FloatingHint(reponse),
                createLineSeparator(),
                new FloatingHint(date),
                createLineSeparator()
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,signIn
        ));
        next.requestFocus();
         next.addActionListener(e -> {
            Comments comm = new Comments();
            comm.setId_user(Integer.valueOf(id_user.getText()));
            comm.setCommentaire_id(Integer.valueOf(commentaire_id.getText()));
            comm.setResponse(reponse.getText());
            comm.setDate(date.getText());
        
           
   
            
            CommentService cs = new CommentService();
            
            cs.ajoutComm(comm);

            new PostForm(res).show();
        });
    }
    
    
}
