package com.kongzhonghd.api.Leaderboard;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.kongzhonghd.sts.business.AbstractResponse;

/**
 * @author stefanie zhao
 * @date 2014-8-14 上午10:32:07
 */
public class ListComponentsResponse extends AbstractResponse {

	private List<Row> rowList = new ArrayList<Row>();

	/**
	 * @return the rowList
	 */
	public List<Row> getRowList() {
		return rowList;
	}

	@Override
	public void parseFromXmlDocument(Document xml) throws Exception {
		Element reply = xml.getRootElement();
		if (null != reply.elements())
			for (Object item : reply.elements()) {
				Element rowElement = (Element) item;
				Row row = new Row();
				if (rowElement.element("Name") != null)
					row.name = rowElement.element("Name").getTextTrim();
				rowList.add(row);
			}
	}

	public class Row {
		private String name;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

	}
}
