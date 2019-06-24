package cc.mrbird.common.util;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;

@Component
public class AddressUtils {

    /**
     * 当前对象实例
     */
    private static AddressUtils global;

    private AddressUtils() {
    }

    /**
     * 静态工厂方法
     */
    public static synchronized AddressUtils getInstance() {
        if (global == null) {
            global = new AddressUtils();
        }
        return global;
    }

    private static Logger log = LoggerFactory.getLogger(AddressUtils.class);
    //当前未用到
    private static String ip2regionUrl;
    @Value("${niuhaoone.ip2regionUrl}")
    private String url;

    @PostConstruct//将yml中配置的值赋给本地的静态变量
    public void getApiToken() {
        ip2regionUrl = this.url;
    }

    public static String getIp2regionUrl() {
        return ip2regionUrl;
    }

    public static String getCityInfo(String ip) {
        try {
            //获取资源文件
            Resource resource = new ClassPathResource("ip2region/ip2region.db");
            File file = resource.getFile();
            if (!file.exists()) {
                log.error("Error: Invalid ip2region.db file");
            }
            int algorithm = DbSearcher.BTREE_ALGORITHM;
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, file.getPath());
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                log.error("Error: Invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            log.error("获取地址信息异常：{}", e);
        }
        return "";
    }

}