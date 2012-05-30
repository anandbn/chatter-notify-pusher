package controllers;

import play.libs.WS.HttpResponse;
import play.modules.pusher.Pusher;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.mvc.Controller;

public class Application extends Controller {

	public static void chatterNotify(String jsonMsg){
		if(System.getenv("PUSHER_URL")!=null && System.getenv("PUSHER_URL").length()>0){
			
			Pattern HEROKU_PUSHER_URL_PATTERN = Pattern.compile("^http://([^:]*):([^@]*)@api.pusherapp.com/apps/([0-9]*)?");
			System.out.println(String.format(">>>>>>>>>>>>>>>>PUSHER_URL=%s", System.getenv("PUSHER_URL")));
			Matcher matcher = HEROKU_PUSHER_URL_PATTERN.matcher(System.getenv("PUSHER_URL"));
			matcher.matches();
            System.out.println(String.format(">>>>>>>>>> Using PUSHER parameters:App Key=%s,App Secret=%s,App Id=%s",matcher.group(1),matcher.group(2),matcher.group(3)));
			Pusher pusher = new Pusher(matcher.group(3),matcher.group(1),matcher.group(2));
			
			System.out.println(">>>>>>>>>> Sending JSON message:"+jsonMsg);
			HttpResponse response = pusher.trigger("chatter_notify", "new_chatter",jsonMsg);
	    	System.out.println(String.format("Sent pusher message successfully. Response :%s",response.getString()));	
	    	System.out.println("");
	    	renderText("Push notification sent successfully !!!");
		}

	}
    public static void index() {
        render();
    }

}