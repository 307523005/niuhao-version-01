package cc.mrbird.job.service.impl;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.common.util.RoundUtil;
import cc.mrbird.job.domain.AdvertisingMessage;
import cc.mrbird.job.service.AdvertisingMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service("AdvertisingMessageService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdvertisingMessageServiceImpl extends BaseService<AdvertisingMessage> implements AdvertisingMessageService {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    @Transactional
    public void addAdvertisingMessage(AdvertisingMessage advertisingMessage) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        advertisingMessage.setAdvmessageId(advertisingMessage.getAdvertisingId() + "-" + advertisingMessage.getMerchantId() + "-" + RoundUtil.getUUID12());
        advertisingMessage.setAdvmessageAddtime(dateFormat);
        advertisingMessage.setAdvmessageShow(1);
        this.save(advertisingMessage);
    }

    @Override
    public List<AdvertisingMessage> getAdvertisingMessageByadvertisingId(AdvertisingMessage advertisingMessage) {
        Example example = new Example(AdvertisingMessage.class);
        Example.Criteria criteria = example.createCriteria();
        String advertisingId = advertisingMessage.getAdvertisingId();
        if (advertisingId != null && !advertisingId.equals("")) {
            criteria.andCondition("advertising_id =", advertisingId);
        }
        criteria.andCondition("advmessage_show =", 1);
        example.setOrderByClause("advmessage_addtime asc ");
        List<AdvertisingMessage> list = this.selectByExample(example);
        return list;
    }


}
