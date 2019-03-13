package cc.mrbird.job.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.common.util.RoundUtil;
import cc.mrbird.job.dao.AdvertisingMessageMapper;
import cc.mrbird.job.domain.AdvertisingMessage;
import cc.mrbird.job.service.AdvertisingMessageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("AdvertisingMessageService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdvertisingMessageServiceImpl extends BaseService<AdvertisingMessage> implements AdvertisingMessageService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AdvertisingMessageMapper advertisingMessageInfoMapper;

    @Override
    public PageInfo<AdvertisingMessage> PageList(QueryRequest request, AdvertisingMessage advertisingMessage) {
        Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
        if (StringUtils.isNotBlank(advertisingMessage.getAdvmessageAddtime())) {
            String[] timeArr = advertisingMessage.getAdvmessageAddtime().split("~");
            advertisingMessage.setAddBeginTime(timeArr[0]);
            advertisingMessage.setAddEndTime(timeArr[1]);
        }
        if (StringUtils.isNotBlank(request.getSort())) {
            advertisingMessage.setSort("advmessage_addtime " + request.getSortOrder());
        } else {
            advertisingMessage.setSort("advmessage_addtime desc");
        }
        List<AdvertisingMessage> findAdvertising = advertisingMessageInfoMapper.findAdvertisingMessageInfo(advertisingMessage);
        PageInfo<AdvertisingMessage> pageInfo = new PageInfo<>(findAdvertising);
        return pageInfo;
    }

    @Override
    @Transactional
    public void addAdvertisingMessage(AdvertisingMessage advertisingMessage) {
        String s = advertisingMessage.getAdvertisingId() + "-" + advertisingMessage.getMerchantId() + "-" + RoundUtil.getUUID12();
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        advertisingMessage.setAdvmessageId(s);
        advertisingMessage.setAdvmessageAddtime(dateFormat);
        advertisingMessage.setAdvmessageShow(0);
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

    @Override
    public void deleteAdvertisingMessage(String advmessageIds) {
        List<String> list = Arrays.asList(advmessageIds.split(","));
        this.batchDelete(list, "advmessageId", AdvertisingMessage.class);
    }

    @Override
    public void replyAdvertisingMessage(AdvertisingMessage advmessage) {
        advmessage.setAdvmessageShow(1);
        this.updateNotNull(advmessage);
    }

    @Override
    public AdvertisingMessage findAdvertisingmessage(String advmessageId) {
        return this.selectByKey(advmessageId);
    }

}
