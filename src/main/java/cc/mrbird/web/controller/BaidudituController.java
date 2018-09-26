package cc.mrbird.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.HttpUtils;
import cc.mrbird.common.util.FebsConstant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class BaidudituController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Log("获取地图")
    @RequestMapping("baiduditu")
    @RequiresPermissions("baiduditu:list")
    public String baiduditu() {
        return "web/baiduditu/baiduditu";
    }
    @Log("获取定位")
    @RequestMapping("dingwei")
    @RequiresPermissions("dingwei:list")
    public String dingwei() {
        return "web/baiduditu/dingwei";
    }
    @RequestMapping("xiuzheng")
    @ResponseBody
    public ResponseBo xiuzheng(Double longitude,Double latitude) throws Exception{
        HttpURLConnection conn = (HttpURLConnection) new URL("http://api.map.baidu.com/geoconv/v1/?coords=" + longitude + "," + latitude + "&from=3&to=5&ak=mC89ltNwqr0BMBPPGNcEiE74").openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        JSONObject json = JSON.parseObject(br.readLine()).getJSONArray("result").getJSONObject(0);
        System.out.println(json.toJSONString());
        double x = json.getDoubleValue("x");
        double y = json.getDoubleValue("y");
        String data = x+","+y;
        return ResponseBo.ok(data);
    }

  /*  @RequestMapping("baiduditu/query")
    @ResponseBody
    public ResponseBo queryWeather(String areaId) {
        try {
            String data = HttpUtils.sendPost(FebsConstant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return ResponseBo.ok(data);
        } catch (Exception e) {
            log.error("获取地图失败", e);
            return ResponseBo.error("获取地图失败，请联系网站管理员！");
        }
    }*/
}
