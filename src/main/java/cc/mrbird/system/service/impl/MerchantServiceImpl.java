package cc.mrbird.system.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.common.util.RoundUtil;
import cc.mrbird.system.domain.Merchant;
import cc.mrbird.system.service.MerchantService;
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

@Service("merchantService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MerchantServiceImpl extends BaseService<Merchant> implements MerchantService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Merchant findMerchant(Long id) {
        return this.selectByKey(id);
    }

    @Override
    public List<Merchant> findAllMerchant(QueryRequest request, Merchant merchant) {
        try {
            Example example = new Example(Merchant.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(merchant.getMerchant_name())) {
                criteria.andCondition("merchant_name like ", "%" + merchant.getMerchant_name() + "%");
            }
            if (StringUtils.isNotBlank(merchant.getMerchant_addtime())) {
                String[] timeArr = merchant.getMerchant_addtime().split("~");
                criteria.andCondition("date_format(merchant_addtime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(merchant_addtime,'%Y-%m-%d') <=", timeArr[1]);
            }
                example.setOrderByClause("merchant_updatetime desc");
            List<Merchant> merchants = this.selectByExample(example);
            return merchants;
        } catch (Exception e) {
            log.error("获取商户失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public PageInfo<Merchant> PageList(QueryRequest request, Merchant merchant) {
        try {
            Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
            System.out.println(request.getSort() + "--merchant--" + objects.toString());
            Example example = new Example(Merchant.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(merchant.getMerchant_name())) {
                criteria.andCondition("merchant_name  like ", "%" + merchant.getMerchant_name() + "%");
            }
            if (StringUtils.isNotBlank(merchant.getMerchant_addtime())) {
                String[] timeArr = merchant.getMerchant_addtime().split("~");
                criteria.andCondition("date_format(merchant_addtime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(merchant_addtime,'%Y-%m-%d') <=", timeArr[1]);
            }
            if (StringUtils.isNotBlank(request.getSort())) {
                example.setOrderByClause("merchant_addtime " + request.getSortOrder());
            } else {
                example.setOrderByClause("merchant_addtime desc");
            }
            List<Merchant> merchants = this.selectByExample(example);
            PageInfo<Merchant> pageInfo = new PageInfo<>(merchants);
            return pageInfo;
        } catch (Exception e) {
            log.error("获取商户信息失败", e);
            return new PageInfo<>(null);
        }
    }


    @Override
    @Transactional
    public void addMerchant(Merchant merchant) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        merchant.setMerchant_addtime(dateFormat);
        merchant.setMerchant_id(RoundUtil.getUUID12());
        this.save(merchant);
    }

    @Override
    @Transactional
    public void deleteMerchant(String id) {
        List<String> list = Arrays.asList(id.split(","));
        this.batchDelete(list, "id", Merchant.class);
    }

    @Override
    @Transactional
    public void updateMerchant(Merchant merchant) {
        this.updateNotNull(merchant);
    }


}
