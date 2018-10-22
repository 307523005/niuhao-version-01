package cc.mrbird.job.service.impl;

import cc.mrbird.common.annotation.CronTag;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.RoundUtil;
import cc.mrbird.job.dao.CronMapper;
import cc.mrbird.job.domain.Cron;
import cc.mrbird.job.service.CronService;
import cc.mrbird.job.util.ScheduleUtils;
import cc.mrbird.scapp.domain.Bannerimg;
import cc.mrbird.system.domain.Merchant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

@Service("CronService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CronServiceImpl extends BaseService<Cron> implements CronService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public PageInfo<Cron> PageList(QueryRequest request, Cron cron) {
        try {
            PageHelper.startPage(request.getPageNum(), request.getPageSize());
            Example example = new Example(Cron.class);
           // example.setOrderByClause("merchant_addtime desc");
            List<Cron> merchants = this.selectByExample(example);
            PageInfo<Cron> pageInfo = new PageInfo<>(merchants);
            return pageInfo;
        } catch (Exception e) {
            log.error("获取表达式列表失败", e);
            return new PageInfo<>(null);
        }
    }


}
