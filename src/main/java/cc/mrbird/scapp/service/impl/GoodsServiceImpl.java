package cc.mrbird.scapp.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.common.util.RoundUtil;
import cc.mrbird.scapp.domain.Goods;
import cc.mrbird.scapp.service.GoodsService;
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

@Service("goodsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GoodsServiceImpl extends BaseService<Goods> implements GoodsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Goods findGoods(String goods_id) {
        return this.selectByKey(goods_id);
    }

    @Override
    public Goods findByName(String goods_name,String merchant_id) {
        Example example = new Example(Goods.class);
        Criteria criteria = example.createCriteria();
        criteria.andCondition("goods_name =", goods_name);
        criteria.andCondition("merchant_id =", merchant_id);
        List<Goods> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Goods> findAllGoods(QueryRequest request, Goods goods) {
        try {
            Example example = new Example(Goods.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(goods.getGoods_name())) {
                criteria.andCondition("goods_name like ", "%" + goods.getGoods_name() + "%");
            }
            if (StringUtils.isNotBlank(goods.getGoods_updatetime())) {
                String[] timeArr = goods.getGoods_updatetime().split("~");
                criteria.andCondition("date_format(goods_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(goods_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            example.setOrderByClause("goods_updatetime desc");
            List<Goods> goodss = this.selectByExample(example);
            return goodss;
        } catch (Exception e) {
            log.error("获取商品失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Goods> getGoods(String merchant_id,String goodstype_id) {
        Example example = new Example(Goods.class);
        Criteria criteria = example.createCriteria();
        criteria.andCondition("merchant_id =", merchant_id);
        criteria.andCondition("goodstype_id =", goodstype_id);
        example.setOrderByClause("goods_num ASC");
        List<Goods> list = this.selectByExample(example);
        return list;
    }

    @Override
    public PageInfo<Goods> PageList(QueryRequest request, Goods goods) {
        try {
            Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
            Example example = new Example(Goods.class);
            Criteria criteria = example.createCriteria();
            //按照商户id
            criteria.andCondition("merchant_id  = ", goods.getMerchant_id());

            if (StringUtils.isNotBlank(goods.getGoods_name())) {
                criteria.andCondition("goods_name  like ", "%" + goods.getGoods_name() + "%");
            }
            if (StringUtils.isNotBlank(goods.getGoods_updatetime())) {
                String[] timeArr = goods.getGoods_updatetime().split("~");
                criteria.andCondition("date_format(goods_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(goods_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            if (StringUtils.isNotBlank(request.getSort())) {
                example.setOrderByClause(request.getSort()+" " + request.getSortOrder());
            } else {
                example.setOrderByClause("goods_num ASC");
            }
            List<Goods> goodss = this.selectByExample(example);
            PageInfo<Goods> pageInfo = new PageInfo<>(goodss);
            return pageInfo;
        } catch (Exception e) {
            log.error("获取商品信息失败", e);
            return new PageInfo<>(null);
        }
    }


    @Override
    @Transactional
    public void addGoods(Goods goods) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        goods.setGoods_addtime(dateFormat);
        goods.setGoods_updatetime(dateFormat);
        goods.setGoods_id(RoundUtil.getUUID12());
        this.save(goods);
    }

    @Override
    @Transactional
    public void deleteGoods(String goodsIds) {
        List<String> list = Arrays.asList(goodsIds.split(","));
        this.batchDelete(list, "goods_id", Goods.class);
    }

    @Override
    @Transactional
    public void updateGoods(Goods goods) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        goods.setGoods_updatetime(dateFormat);
        this.updateNotNull(goods);
    }


}
