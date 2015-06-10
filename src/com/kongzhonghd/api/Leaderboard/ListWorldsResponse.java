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
public class ListWorldsResponse extends AbstractResponse {

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
				if (rowElement.element("World") != null)
					row.world = rowElement.element("World").getTextTrim();
				rowList.add(row);
			}
	}

	public class Row {

		private String world;

		/**
		 * @return the world
		 */
		public String getWorld() {
			return world;
		}

		/**
		 * @param world
		 *            the world to set
		 */
		public void setWorld(String world) {
			this.world = world;
		}

	}
}
