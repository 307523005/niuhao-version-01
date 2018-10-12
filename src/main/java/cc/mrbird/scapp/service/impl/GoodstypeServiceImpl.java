package cc.mrbird.scapp.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.common.util.RoundUtil;
import cc.mrbird.scapp.domain.Goodstype;
import cc.mrbird.scapp.service.GoodstypeService;
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

@Service("goodstypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GoodstypeServiceImpl extends BaseService<Goodstype> implements GoodstypeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Goodstype findGoodstype(String goodstype_id) {
        return this.selectByKey(goodstype_id);
    }

    @Override
    public Goodstype findByName(String goodstype_name,String merchant_id) {
        Example example = new Example(Goodstype.class);
        Criteria criteria = example.createCriteria();
        criteria.andCondition("goodstype_name=", goodstype_name);
        criteria.andCondition("merchant_id=", merchant_id);
        List<Goodstype> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Goodstype> findAllGoodstype(QueryRequest request, Goodstype goodstype) {
        try {
            Example example = new Example(Goodstype.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(goodstype.getGoodstype_name())) {
                criteria.andCondition("goodstype_name like ", "%" + goodstype.getGoodstype_name() + "%");
            }
            if (StringUtils.isNotBlank(goodstype.getGoodstype_updatetime())) {
                String[] timeArr = goodstype.getGoodstype_updatetime().split("~");
                criteria.andCondition("date_format(goodstype_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(goodstype_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            example.setOrderByClause("goodstype_updatetime desc");
            List<Goodstype> goodstypes = this.selectByExample(example);
            return goodstypes;
        } catch (Exception e) {
            log.error("获取轮播图失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Goodstype> getGoodstype(String merchant_id) {
        Example example = new Example(Goodstype.class);
        example.createCriteria().andCondition("merchant_id=", merchant_id);
        example.setOrderByClause("goodstype_num ASC");
        List<Goodstype> list = this.selectByExample(example);
        return list;
    }

    @Override
    public PageInfo<Goodstype> PageList(QueryRequest request, Goodstype goodstype) {
        try {
            Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
            System.out.println(request.getSort() + "--goodstype--" + objects.toString());
            Example example = new Example(Goodstype.class);
            Criteria criteria = example.createCriteria();
            //按照商户id
            criteria.andCondition("merchant_id  = ", goodstype.getMerchant_id());

            if (StringUtils.isNotBlank(goodstype.getGoodstype_name())) {
                criteria.andCondition("goodstype_name  like ", "%" + goodstype.getGoodstype_name() + "%");
            }
            if (StringUtils.isNotBlank(goodstype.getGoodstype_updatetime())) {
                String[] timeArr = goodstype.getGoodstype_updatetime().split("~");
                criteria.andCondition("date_format(goodstype_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(goodstype_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            if (StringUtils.isNotBlank(request.getSort())) {
                example.setOrderByClause(request.getSort()+" " + request.getSortOrder());
            } else {
                example.setOrderByClause("goodstype_num ASC");
            }
            List<Goodstype> goodstypes = this.selectByExample(example);
            System.out.println("goodstypes---**" + goodstypes);
            PageInfo<Goodstype> pageInfo = new PageInfo<>(goodstypes);
            return pageInfo;
        } catch (Exception e) {
            log.error("获取商品类型失败", e);
            return new PageInfo<>(null);
        }
    }


    @Override
    @Transactional
    public void addGoodstype(Goodstype goodstype) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        goodstype.setGoodstype_addtime(dateFormat);
        goodstype.setGoodstype_updatetime(dateFormat);
        goodstype.setGoodstype_id(RoundUtil.getUUID12());
        this.save(goodstype);
    }

    @Override
    @Transactional
    public void deleteGoodstype(String goodstypeIds) {
        List<String> list = Arrays.asList(goodstypeIds.split(","));
        this.batchDelete(list, "goodstype_id", Goodstype.class);
    }

    @Override
    @Transactional
    public void updateGoodstype(Goodstype goodstype) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        goodstype.setGoodstype_updatetime(dateFormat);
        this.updateNotNull(goodstype);
    }


}
