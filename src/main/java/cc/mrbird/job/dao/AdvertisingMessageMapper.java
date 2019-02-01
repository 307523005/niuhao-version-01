package cc.mrbird.job.dao;


import cc.mrbird.common.config.MyMapper;
import cc.mrbird.job.domain.AdvertisingMessage;

import java.util.List;

public interface AdvertisingMessageMapper extends MyMapper<AdvertisingMessage> {
    List<AdvertisingMessage> findAdvertisingMessageInfo(AdvertisingMessage advertisingMessage);

}
