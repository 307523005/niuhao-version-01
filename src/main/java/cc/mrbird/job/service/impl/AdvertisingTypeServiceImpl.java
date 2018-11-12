package cc.mrbird.job.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.job.domain.AdvertisingType;
import cc.mrbird.job.service.AdvertisingTypeService;
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

@Service("advertisingtypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdvertisingTypeServiceImpl extends BaseService<AdvertisingType> implements AdvertisingTypeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public AdvertisingType findAdvertisingType(Long advertisingtype_id) {
        return this.selectByKey(advertisingtype_id);
    }


    @Override
    public boolean findByName(String advertisingtype_name, String merchant_id) {
        Example example = new Example(AdvertisingType.class);
        Criteria criteria = example.createCriteria();
        criteria.andCondition("advertisingtype_name =", advertisingtype_name);
        criteria.andCondition("merchant_id =", merchant_id);
        List<AdvertisingType> list = this.selectByExample(example);
        return list.isEmpty() ? true : false;
    }

    @Override
    public List<AdvertisingType> findAllAdvertisingType(AdvertisingType advertisingtype) {
        try {
            Example example = new Example(AdvertisingType.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(advertisingtype.getAdvertisingtype_updatetime())) {
                String[] timeArr = advertisingtype.getAdvertisingtype_updatetime().split("~");
                criteria.andCondition("date_format(advertisingtype_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(advertisingtype_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            example.setOrderByClause("advertisingtype_updatetime desc");
            List<AdvertisingType> advertisingtypes = this.selectByExample(example);
            return advertisingtypes;
        } catch (Exception e) {
            log.error("获取广告类型失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public PageInfo<AdvertisingType> PageList(QueryRequest request, AdvertisingType advertisingtype) {
        try {
            Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
            Example example = new Example(AdvertisingType.class);
            Criteria criteria = example.createCriteria();
            //按照商户id
            criteria.andCondition("merchant_id  = ", advertisingtype.getMerchant_id());
            if (StringUtils.isNotBlank(advertisingtype.getAdvertisingtype_name())) {
                criteria.andCondition("advertisingtype_name  like ", "%" + advertisingtype.getAdvertisingtype_name() + "%");
            }
            if (StringUtils.isNotBlank(advertisingtype.getAdvertisingtype_updatetime())) {
                String[] timeArr = advertisingtype.getAdvertisingtype_updatetime().split("~");
                criteria.andCondition("date_format(advertisingtype_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(advertisingtype_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            if (StringUtils.isNotBlank(request.getSort())) {
                example.setOrderByClause(request.getSort() + " " + request.getSortOrder());
            } else {
                example.setOrderByClause("advertisingtype_updatetime desc");
            }
            List<AdvertisingType> advertisingtypes = this.selectByExample(example);
            PageInfo<AdvertisingType> pageInfo = new PageInfo<>(advertisingtypes);
            return pageInfo;
        } catch (Exception e) {
            log.error("获取广告类型信息失败", e);
            return new PageInfo<>(null);
        }
    }


    @Override
    @Transactional
    public void addAdvertisingType(AdvertisingType advertisingtype) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        advertisingtype.setAdvertisingtype_addtime(dateFormat);
        advertisingtype.setAdvertisingtype_updatetime(dateFormat);
        this.save(advertisingtype);
    }

    @Override
    @Transactional
    public void deleteAdvertisingType(String advertisingtypeIds) {
        List<String> list = Arrays.asList(advertisingtypeIds.split(","));
        this.batchDelete(list, "advertisingtype_id", AdvertisingType.class);
    }

    @Override
    @Transactional
    public void updateAdvertisingType(AdvertisingType advertisingtype) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        advertisingtype.setAdvertisingtype_updatetime(dateFormat);
        this.updateNotNull(advertisingtype);
    }

    @Override
    public List<AdvertisingType> scappGetAdvertisingTypeByMerchant_id(String merchant_id) {
        Example example = new Example(AdvertisingType.class);
        if (StringUtils.isNotBlank(merchant_id)) {
            example.createCriteria().andCondition("merchant_id=", merchant_id);
        }
        example.setOrderByClause("advertisingtype_num ASC");
        List<AdvertisingType> list = this.selectByExample(example);
        return list;
    }


}
