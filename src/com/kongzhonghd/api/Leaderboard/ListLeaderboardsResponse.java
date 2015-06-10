package com.kongzhonghd.api.Leaderboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.util.CommonUtil;

/**
 * @author stefanie zhao
 * @date 2014-8-14 上午10:32:07
 */
public class ListLeaderboardsResponse extends AbstractResponse {

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
				if (rowElement.element("SnapshotInterval") != null)
					row.snapshotInterval = rowElement.element("SnapshotInterval").getTextTrim();
				if (rowElement.element("SnapshotTimestamp") != null)
					row.snapshotTimestamp = CommonUtil.parseDate(rowElement.element("SnapshotTimestamp").getTextTrim());
				if (rowElement.element("RowTotal") != null)
					row.rowTotal = rowElement.element("RowTotal").getTextTrim();
				if (rowElement.element("InProgress") != null)
					row.inProgress = rowElement.element("InProgress").getTextTrim();

				rowList.add(row);
			}
	}

	public class Row {
		private String name;
		private String snapshotInterval;
		private Date snapshotTimestamp;
		private String rowTotal;
		private String inProgress;

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

		/**
		 * @return the snapshotInterval
		 */
		public String getSnapshotInterval() {
			return snapshotInterval;
		}

		/**
		 * @param snapshotInterval
		 *            the snapshotInterval to set
		 */
		public void setSnapshotInterval(String snapshotInterval) {
			this.snapshotInterval = snapshotInterval;
		}

		/**
		 * @return the snapshotTimestamp
		 */
		public Date getSnapshotTimestamp() {
			return snapshotTimestamp;
		}

		/**
		 * @param snapshotTimestamp
		 *            the snapshotTimestamp to set
		 */
		public void setSnapshotTimestamp(Date snapshotTimestamp) {
			this.snapshotTimestamp = snapshotTimestamp;
		}

		/**
		 * @return the rowTotal
		 */
		public String getRowTotal() {
			return rowTotal;
		}

		/**
		 * @param rowTotal
		 *            the rowTotal to set
		 */
		public void setRowTotal(String rowTotal) {
			this.rowTotal = rowTotal;
		}

		/**
		 * @return the inProgress
		 */
		public String getInProgress() {
			return inProgress;
		}

		/**
		 * @param inProgress
		 *            the inProgress to set
		 */
		public void setInProgress(String inProgress) {
			this.inProgress = inProgress;
		}

	}
}
