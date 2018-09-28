package cc.mrbird.scapp.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.DateUtil;
import cc.mrbird.common.util.RoundUtil;
import cc.mrbird.scapp.domain.Bannerimg;
import cc.mrbird.scapp.service.BannerimgService;
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

@Service("bannerimgService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BannerimgServiceImpl extends BaseService<Bannerimg> implements BannerimgService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Bannerimg findBannerimg(String bannerimg_id) {
        return this.selectByKey(bannerimg_id);
    }

    @Override
    public Bannerimg findByName(String bannerimg_name,String merchant_id) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("bannerimg_name=", bannerimg_name);
        example.createCriteria().andCondition("merchant_id=", merchant_id);
        List<Bannerimg> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Bannerimg> findAllBannerimg(QueryRequest request, Bannerimg bannerimg) {
        try {
            Example example = new Example(Bannerimg.class);
            Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(bannerimg.getBannerimg_name())) {
                criteria.andCondition("bannerimg_name like ", "%" + bannerimg.getBannerimg_name() + "%");
            }
            if (StringUtils.isNotBlank(bannerimg.getBannerimg_updatetime())) {
                String[] timeArr = bannerimg.getBannerimg_updatetime().split("~");
                criteria.andCondition("date_format(bannerimg_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(bannerimg_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            example.setOrderByClause("bannerimg_updatetime desc");
            List<Bannerimg> bannerimgs = this.selectByExample(example);
            return bannerimgs;
        } catch (Exception e) {
            log.error("获取轮播图失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Bannerimg> getBannerimgTop3(String merchant_id) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("merchant_id=", merchant_id);
        example.setOrderByClause("bannerimg_num ASC");
        List<Bannerimg> list = this.selectByExample(example);
        return list;
    }

    @Override
    public PageInfo<Bannerimg> PageList(QueryRequest request, Bannerimg bannerimg) {
        try {
            Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
            System.out.println(request.getSort() + "--bannerimg--" + objects.toString());
            Example example = new Example(Bannerimg.class);
            Criteria criteria = example.createCriteria();
            //按照商户id
            criteria.andCondition("merchant_id  = ", bannerimg.getMerchant_id());

            if (StringUtils.isNotBlank(bannerimg.getBannerimg_name())) {
                criteria.andCondition("bannerimg_name  like ", "%" + bannerimg.getBannerimg_name() + "%");
            }
            if (StringUtils.isNotBlank(bannerimg.getBannerimg_updatetime())) {
                String[] timeArr = bannerimg.getBannerimg_updatetime().split("~");
                criteria.andCondition("date_format(bannerimg_updatetime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(bannerimg_updatetime,'%Y-%m-%d') <=", timeArr[1]);
            }
            if (StringUtils.isNotBlank(request.getSort())) {
                example.setOrderByClause(request.getSort()+" " + request.getSortOrder());
            } else {
                example.setOrderByClause("bannerimg_num ASC");
            }
            List<Bannerimg> bannerimgs = this.selectByExample(example);
            System.out.println("bannerimgs---**" + bannerimgs);
            PageInfo<Bannerimg> pageInfo = new PageInfo<>(bannerimgs);
            return pageInfo;
        } catch (Exception e) {
            log.error("获取轮播图信息失败", e);
            return new PageInfo<>(null);
        }
    }


    @Override
    @Transactional
    public void addBannerimg(Bannerimg bannerimg) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        bannerimg.setBannerimg_addtime(dateFormat);
        bannerimg.setBannerimg_updatetime(dateFormat);
        bannerimg.setBannerimg_id(RoundUtil.getUUID12());
        this.save(bannerimg);
    }

    @Override
    @Transactional
    public void deleteBannerimg(String bannerimgIds) {
        List<String> list = Arrays.asList(bannerimgIds.split(","));
        this.batchDelete(list, "bannerimg_id", Bannerimg.class);
    }

    @Override
    @Transactional
    public void updateBannerimg(Bannerimg bannerimg) {
        String dateFormat = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
        bannerimg.setBannerimg_updatetime(dateFormat);
        this.updateNotNull(bannerimg);
    }


}
