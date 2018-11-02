package cc.mrbird.job.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.job.domain.Advertising;
import cc.mrbird.job.service.AdvertisingService;
import cc.mrbird.system.domain.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("advertisingService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdvertisingServiceImpl extends BaseService<Advertising> implements AdvertisingService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Advertising findAdvertising(Long advertising_id) {
        return this.selectByKey(advertising_id);
    }

    @Override
    public List<Advertising> findAllAdvertising() {
        return this.selectAll();
    }

    @Override
    public List<Advertising> findAllAdvertising(Advertising advertising) {
        try {
            Example example = new Example(Advertising.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(advertising.getAdvertising_title())) {
                criteria.andCondition("advertising_title like ", "%" + advertising.getAdvertising_title() + "%");
            }
            if (StringUtils.isNotBlank(advertising.getAdvertising_updatetime())) {
                String[] timeArr = advertising.getAdvertising_updatetime().split("~");
                criteria.andCondition("date_format(advertising_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(advertising_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            example.setOrderByClause("advertising_updatetime desc");
            List<Advertising> advertisings = this.selectByExample(example);
            return advertisings;
        } catch (Exception e) {
            log.error("获取广告失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public PageInfo<Advertising> PageList(QueryRequest request, Advertising advertising) {
        try {
            Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
            Example example = new Example(Advertising.class);
            Criteria criteria = example.createCriteria();
            //按照商户id
            criteria.andCondition("merchant_id  = ", advertising.getMerchant_id());

            if (StringUtils.isNotBlank(advertising.getAdvertising_title())) {
                criteria.andCondition("advertising_title  like ", "%" + advertising.getAdvertising_title() + "%");
            }
            if (StringUtils.isNotBlank(advertising.getAdvertising_updatetime())) {
                String[] timeArr = advertising.getAdvertising_updatetime().split("~");
                criteria.andCondition("date_format(advertising_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(advertising_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            if (StringUtils.isNotBlank(request.getSort())) {
                example.setOrderByClause(request.getSort() + " " + request.getSortOrder());
            } else {
                example.setOrderByClause("advertising_updatetime desc");
            }
            List<Advertising> advertisings = this.selectByExample(example);
            PageInfo<Advertising> pageInfo = new PageInfo<>(advertisings);
            return pageInfo;
        } catch (Exception e) {
            log.error("获取广告信息失败", e);
            return new PageInfo<>(null);
        }
    }


    @Override
    @Transactional
    public void addAdvertising(Advertising advertising) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        advertising.setAdvertising_addtime(dateFormat);
        advertising.setAdvertising_updatetime(dateFormat);
        this.save(advertising);
    }

    @Override
    @Transactional
    public void deleteAdvertising(String advertisingIds) {
        List<String> list = Arrays.asList(advertisingIds.split(","));
        this.batchDelete(list, "advertising_id", Advertising.class);
    }

    @Override
    @Transactional
    public void updateAdvertising(Advertising advertising) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        advertising.setAdvertising_updatetime(dateFormat);
        this.updateNotNull(advertising);
    }

    @Override
    public List<Advertising> scappGetAdvertisingByMerchant_id(String merchant_id) {
        Example example = new Example(Advertising.class);
        if (StringUtils.isNotBlank(merchant_id)) {
            example.createCriteria().andCondition("merchant_id=", merchant_id);
        }
        example.setOrderByClause("advertising_updatetime desc");
        List<Advertising> list = this.selectByExample(example);
        return list;
    }


}
