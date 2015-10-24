package sbs.web.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
 
public class VerifyCaptcha {
 
    public static final String url = "https://www.google.com/recaptcha/api/siteverify";
    public static final String secret = "6LcHCw8TAAAAAPc4R7m7MRBXN8mv31mvHXeY7QoL";
 
    public static boolean verify(String gCaptchaResponse) throws IOException 
    {
        if (gCaptchaResponse == null || "".equals(gCaptchaResponse)) {
            return false;
        }
         
        try{
	        URL new_url = new URL(url);
	        HttpsURLConnection urlConn = (HttpsURLConnection) new_url.openConnection();
	 
	        // Add a request Header
	        urlConn.setRequestMethod("POST");
	 
	        String postParams = "secret=" + secret + "&response="
	                + gCaptchaResponse;
	 
	        // Make a post call
	        urlConn.setDoOutput(true);
	        DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream());
	        wr.writeBytes(postParams);
	        wr.flush();
	        wr.close();
	 
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	        		urlConn.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	  
	        //As the Response is an JSON parse it and return 'success' value
	        JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
	        JsonObject jsonObject = jsonReader.readObject();
	        jsonReader.close();
	         
	        return jsonObject.getBoolean("success");
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}