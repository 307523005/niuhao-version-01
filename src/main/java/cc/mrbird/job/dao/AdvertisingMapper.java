package cc.mrbird.job.dao;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.job.domain.Advertising;

import java.util.List;

public interface AdvertisingMapper extends MyMapper<Advertising> {
    List<Advertising> findAdvertisingWithAdvertisingType(Advertising advertising);
}
