package cc.mrbird.scapp.dao;


import cc.mrbird.scapp.domain.TemplateMessage;

import java.util.List;
import java.util.Map;

public interface TemplateMessageMapper {

	/**
	 * 获取数量
	 */
	public int  getCount(Map map);

	/**
	 * 添加信息
	 * @param templateMessage
	 * @return
	 */
	public int add(TemplateMessage templateMessage);
	/**
	 * 修改信息
	 * @return
	 */
	public int update(TemplateMessage templateMessage);
	/**
	 * 根据id获取信息
	 * @param TemplateMessage_id
	 * @return
	 */
	public List<TemplateMessage> getById(String TemplateMessage_id);
}
