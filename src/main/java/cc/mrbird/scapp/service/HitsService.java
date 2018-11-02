package cc.mrbird.scapp.service;


import org.springframework.scheduling.annotation.Async;

import java.util.Map;
import java.util.concurrent.Future;

public interface HitsService {
    @Async(value = "asyncServiceExecutor")
    Future<Map<String, Object>> addHitsAdvertising(String advertising_id, String cip) throws Exception;
}
