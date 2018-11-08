package cc.mrbird.job.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.job.dao.AdvertisingMapper;
import cc.mrbird.job.domain.Advertising;
import cc.mrbird.job.service.AdvertisingService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.i18nformatter.qual.I18nChecksFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("advertisingService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdvertisingServiceImpl extends BaseService<Advertising> implements AdvertisingService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AdvertisingMapper advertisingMapper;

    @Override
    public Advertising findAdvertising(Long advertising_id) {
        log.info(selectByKey(advertising_id).getAdvertisingUpdatetime());
        return this.selectByKey(advertising_id);
    }


    @Override
    public boolean findByName(String advertising_name, String merchant_id) {
        Example example = new Example(Advertising.class);
        Criteria criteria = example.createCriteria();
        criteria.andCondition("advertising_name =", advertising_name);
        criteria.andCondition("merchant_id =", merchant_id);
        List<Advertising> list = this.selectByExample(example);
        return list.isEmpty() ? true : false;
    }

    @Override
    public List<Advertising> findAllAdvertising() {
        return this.selectAll();
    }

    @Override
    public List<Advertising> findAllAdvertising(Advertising advertising) {
        List<Advertising> findAdvertisingWithAdvertisingType = advertisingMapper.findAdvertisingWithAdvertisingType(advertising);
        return findAdvertisingWithAdvertisingType;
    }

    @Override
    public PageInfo<Advertising> PageList(QueryRequest request, Advertising advertising) {
        Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
        if (StringUtils.isNotBlank(advertising.getAdvertisingUpdatetime())) {
            String[] timeArr = advertising.getAdvertisingUpdatetime().split("~");
            advertising.setAdvertisingAddtime(timeArr[0]);
            advertising.setAdvertisingUpdateuser(timeArr[1]);
        }
        if (StringUtils.isNotBlank(request.getSort())) {
            if (request.getSort().equals("advertisingHits")) {
                advertising.setSort("advertising_hits " + request.getSortOrder());
            } else {
                advertising.setSort("advertising_updatetime " + request.getSortOrder());
            }

        } else {
            advertising.setSort("advertising_updatetime desc");
        }
        List<Advertising> findAdvertising = advertisingMapper.findAdvertisingWithAdvertisingType(advertising);
        PageInfo<Advertising> pageInfo = new PageInfo<>(findAdvertising);
        return pageInfo;
    }


    @Override
    @Transactional
    public void addAdvertising(Advertising advertising) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        advertising.setAdvertisingAddtime(dateFormat);
        advertising.setAdvertisingUpdatetime(dateFormat);
        advertising.setAdvertisingHits("0");
        this.save(advertising);
    }

    @Override
    @Transactional
    public void deleteAdvertising(String advertisingIds) {
        List<String> list = Arrays.asList(advertisingIds.split(","));
        this.batchDelete(list, "advertisingId", Advertising.class);
    }

    @Override
    @Transactional
    public void updateAdvertising(Advertising advertising) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        advertising.setAdvertisingUpdatetime(dateFormat);
        this.updateNotNull(advertising);
    }

    @Override
    public List<Advertising> scappGetAdvertisingByMerchant_id(String merchant_id, Long advertisingTypeId, String num) {
        Example example = new Example(Advertising.class);
        Criteria criteria = example.createCriteria();
        criteria.andCondition("merchant_id =", merchant_id);
        String page = " limit 10";
        if (num != null && !num.equals("")) {
          //  if (Integer.valueOf(num) > 1) {
                page = " limit " + Integer.valueOf(num) * 10 + ", " + (Integer.valueOf(num)+1) * 10;
           // }
        }

        if (advertisingTypeId != null && !advertisingTypeId.equals("")) {
            criteria.andCondition("advertisingtype_id =", advertisingTypeId);
            example.setOrderByClause("advertising_updatetime desc " + page);
        } else {
            example.setOrderByClause("advertising_updatetime desc " + page);
        }

        List<Advertising> list = this.selectByExample(example);
        return list;
    }

    @Override
    public List<Advertising> scappGetAdvertisingByMerchant_idTop10(String merchant_id) {
        Example example = new Example(Advertising.class);
        Criteria criteria = example.createCriteria();
        criteria.andCondition("merchant_id =", merchant_id);
        example.setOrderByClause("advertising_updatetime desc limit 10");
        List<Advertising> list = this.selectByExample(example);
        return list;
    }


}
