package cc.mrbird.system.service.impl;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.domain.Dict;
import cc.mrbird.system.service.DictService;

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
import java.util.List;

@Service("dictService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl extends BaseService<Dict> implements DictService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public PageInfo<Dict> PageList(QueryRequest request, Dict dict) {
		try {
			Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
			System.out.println(request.getSort()+"--111--"+objects.toString());
			Example example = new Example(Dict.class);
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(dict.getKeyy())) {
				criteria.andCondition("keyy=", Long.valueOf(dict.getKeyy()));
			}
			if (StringUtils.isNotBlank(dict.getValuee())) {
				criteria.andCondition("valuee=", dict.getValuee());
			}
			if (StringUtils.isNotBlank(dict.getTableName())) {
				criteria.andCondition("table_name=", dict.getTableName());
			}
			if (StringUtils.isNotBlank(dict.getFieldName())) {
				criteria.andCondition("field_name=", dict.getFieldName());
			}


			if (StringUtils.isNotBlank(request.getSort())){
				example.setOrderByClause("DICT_ID "+request.getSortOrder());
			}else {
				example.setOrderByClause("DICT_ID ASC");
			}
			List<Dict> dicts = this.selectByExample(example);
			System.out.println("dicts---**"+dicts);
			PageInfo<Dict> pageInfo = new PageInfo<>(dicts);
			return pageInfo;
		} catch (Exception e) {
			log.error("获取字典信息失败", e);
			return new PageInfo<>(null);
		}
	}
	@Override
	public List<Dict> findAllDicts(Dict dict, QueryRequest request) {
		try {
			Example example = new Example(Dict.class);
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(dict.getKeyy())) {
				criteria.andCondition("keyy=", Long.valueOf(dict.getKeyy()));
			}
			if (StringUtils.isNotBlank(dict.getValuee())) {
				criteria.andCondition("valuee=", dict.getValuee());
			}
			if (StringUtils.isNotBlank(dict.getTableName())) {
				criteria.andCondition("table_name=", dict.getTableName());
			}
			if (StringUtils.isNotBlank(dict.getFieldName())) {
				criteria.andCondition("field_name=", dict.getFieldName());
			}
			example.setOrderByClause("dict_id");
			List<Dict> dicts = this.selectByExample(example);
			System.out.println("dicts---**"+dicts);
			return dicts;
		} catch (Exception e) {
			log.error("获取字典信息失败", e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	public void addDict(Dict dict) {
		this.save(dict);
	}

	@Override
	@Transactional
	public void deleteDicts(String dictIds) {
		List<String> list = Arrays.asList(dictIds.split(","));
		this.batchDelete(list, "dictId", Dict.class);
	}

	@Override
	@Transactional
	public void updateDict(Dict dict) {
		this.updateNotNull(dict);
	}

	@Override
	public Dict findById(Long dictId) {
		return this.selectByKey(dictId);
	}

}
