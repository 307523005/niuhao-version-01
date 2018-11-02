package cc.mrbird.common.util;


import cc.mrbird.scapp.domain.TemplateMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/** 
 * 发送模板消息工具类
 * @author 钮豪 
 * @date  2018年1月31日 
 * 
 */  
public class SendTemplateUtil {
	private static Object SendTemplate;

	public static String SendTemplates(String openId,String form_id,String access_token,List<TemplateMessage> templateMessage) {
/*	String getH5OpenidUrl = DefinedChars.getXCXccessToken();
	String rec = httpGet(getH5OpenidUrl);
	JSONObject json = JSON.parseObject(rec);
	String access_token = json.getString("access_token");
	System.out.println("---"+access_token);*/
		
    /*	String openId="ow1c95cNcEuukFbO5Q6sXtYjA50Y";
    	String template_id="M1YJVuvm9Po91L9mlhmuiUx5P9K3ibRy6ZdkNguDhQ8";
    	String form_id="1517365450606";*/
		String responseStr="";
    	String accessToken=access_token;
    	//发送模板消息
        String urlStr="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+accessToken;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false); 
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Charset", "UTF-8"); 
            urlConnection.connect();
            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
        String first = "";
        String keyword1 = "";
        String keyword2 = "";
        String keyword3 = "";
        String keyword4 = "";
        String keyword5 = "";
        String keyword6 = "";
      /*  if (templateMessage.get(0).getTemplateMessage_first()!="") {
        	first =  "\"first\": {"+
                    "\"value\":\""+templateMessage.get(0).getTemplateMessage_first()+"\","+
                    "\"color\":\""+templateMessage.get(0).getTemplateMessage_firstcolor()+"\""+
                    "},";
		}*/
        if (templateMessage.get(0).getTemplateMessage_keyword1()!="") {
        	keyword1 =  "\"keyword1\":{"+
                    "\"value\":\""+templateMessage.get(0).getTemplateMessage_keyword1()+"\","+
                    "\"color\":\""+templateMessage.get(0).getTemplateMessage_keyword1color()+"\""+
                    "}";
        }
        if (templateMessage.get(0).getTemplateMessage_keyword2()!="") {
        	keyword2 = ","+ "\"keyword2\":{"+
                    "\"value\":\""+templateMessage.get(0).getTemplateMessage_keyword2()+"\","+
                    "\"color\":\""+templateMessage.get(0).getTemplateMessage_keyword2color()+"\""+
                    "}";
        }
        if (templateMessage.get(0).getTemplateMessage_keyword3()!="") {
        	keyword3 = ","+ "\"keyword3\":{"+
                    "\"value\":\""+templateMessage.get(0).getTemplateMessage_keyword3()+"\","+
                    "\"color\":\""+templateMessage.get(0).getTemplateMessage_keyword3color()+"\""+
                    "}";
        }
        if (templateMessage.get(0).getTemplateMessage_keyword4()!="") {
        	keyword4 = ","+ "\"keyword4\":{"+
                    "\"value\":\""+templateMessage.get(0).getTemplateMessage_keyword4()+"\","+
                    "\"color\":\""+templateMessage.get(0).getTemplateMessage_keyword4color()+"\""+
                    "}";
        }
        if (templateMessage.get(0).getTemplateMessage_keyword5()!="") {
        	keyword5 =","+  "\"keyword5\":{"+
                    "\"value\":\""+templateMessage.get(0).getTemplateMessage_keyword5()+"\","+
                    "\"color\":\""+templateMessage.get(0).getTemplateMessage_keyword5color()+"\""+
                    "}";
        }
    /*    if (templateMessage.get(0).getTemplateMessage_keyword6()!=""&&!templateMessage.get(0).getTemplateMessage_keyword6().equals("null")) {
        	keyword6 =  "\"keyword6\":{"+
                    "\"value\":\""+templateMessage.get(0).getTemplateMessage_keyword6()+"\","+
                    "\"color\":\""+templateMessage.get(0).getTemplateMessage_keyword6color()+"\""+
                    "},";
        }*/
            String params="{"+
                            "\"touser\":\""+openId+"\","+
                            "\"template_id\":\""+templateMessage.get(0).getTemplateMessage_template_id()+"\","+
                            "\"page\":\"pages/index/index\","+
                            "\"form_id\":\""+form_id+"\","+
                            "\"data\":{"+
                            first+keyword1+keyword2+keyword3+keyword4+keyword5+keyword6+
                             "}"+
                            "}";        
            System.out.println(params);
            out.write(params.getBytes("UTF-8"));
            out.flush();
            out.close(); 
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            String line;
            
            while ((line = reader.readLine()) != null){
                responseStr+=line;
            }
            System.out.println(responseStr);
            reader.close();
            urlConnection.disconnect();

        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return responseStr; 
    }
}  