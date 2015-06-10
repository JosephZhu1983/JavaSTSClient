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
public class GetLeadersUnauthResponse extends AbstractResponse {

	private String leaderboard;
	private String snapshotInterval;
	private Date snapshotTimestamp;
	private String snapshotId;
	private String sortComponent;
	private String sortDirection;
	private Filter filter;
	private String rowStart;
	private String rowCount;
	private String rowTotal;
	private String colCount;
	private List<Column> columnList = new ArrayList<Column>();
	private List<Row> rowList = new ArrayList<Row>();

	@Override
	public void parseFromXmlDocument(Document xml) throws Exception {
		Element reply = xml.getRootElement();
		if (reply.element("Leaderboard") != null)
			this.leaderboard = reply.element("Leaderboard").getTextTrim();
		if (reply.element("SnapshotInterval") != null)
			this.snapshotInterval = reply.element("SnapshotInterval").getTextTrim();
		if (reply.element("SnapshotTimestamp") != null)
			this.snapshotTimestamp = CommonUtil.parseDate(reply.element("SnapshotTimestamp").getTextTrim());
		if (reply.element("SnapshotId") != null)
			this.snapshotId = reply.element("SnapshotId").getTextTrim();
		if (reply.element("SortComponent") != null)
			this.sortComponent = reply.element("SortComponent").getTextTrim();
		if (reply.element("SortDirection") != null)
			this.sortDirection = reply.element("SortDirection").getTextTrim();
		if (reply.element("RowStart") != null)
			this.rowStart = reply.element("RowStart").getTextTrim();
		if (reply.element("RowCount") != null)
			this.rowCount = reply.element("RowCount").getTextTrim();
		if (reply.element("RowTotal") != null)
			this.rowTotal = reply.element("RowTotal").getTextTrim();
		if (reply.element("ColCount") != null)
			this.colCount = reply.element("ColCount").getTextTrim();

		if (reply.element("Filter") != null) {
			Element filterElement = reply.element("Filter");
			Filter filter = new Filter();
			if (filterElement.attributeValue("type") != null)
				filter.setType(filterElement.attributeValue("type"));
			if (filterElement.attributeValue("value") != null)
				filter.setValue(filterElement.attributeValue("value"));
			this.filter = filter;
		}

		if (reply.element("Columns") != null) {
			Element columns = reply.element("Columns");
			for (Object item : columns.elements()) {
				Element colElement = (Element) item;
				Column column = new Column();
				column.column = colElement.getTextTrim();
				columnList.add(column);
			}
		}

		if (reply.element("Rows") != null) {
			Element rows = reply.element("Rows");
			for (Object item : rows.elements()) {
				Element rowElement = (Element) item;
				Row row = new Row();
				if (rowElement.element("Leader") != null)
					row.leader = rowElement.element("Leader").getTextTrim();
				if (rowElement.element("LeaderName") != null)
					row.leaderName = rowElement.element("LeaderName").getTextTrim();
				if (rowElement.element("PortalHandle") != null)
					row.portalHandle = rowElement.element("PortalHandle").getTextTrim();
				if (rowElement.element("World") != null)
					row.world = rowElement.element("World").getTextTrim();

				if (rowElement.element("Cells") != null) {
					Element cells = reply.element("Cells");
					for (Object item1 : cells.elements()) {
						Element cellElement = (Element) item1;
						Row.Cell cell = row.new Cell();
						if (cellElement.element("Value") != null)
							cell.value = rowElement.element("Value").getTextTrim();
						if (cellElement.element("Delta") != null)
							cell.delta = rowElement.element("Delta").getTextTrim();
						if (cellElement.element("Timestamp") != null)
							cell.timestamp = CommonUtil.parseDate(cellElement.element("Timestamp").getTextTrim());
						if (cellElement.element("Rank") != null)
							cell.rank = rowElement.element("Rank").getTextTrim();
						if (cellElement.element("RankDelta") != null)
							cell.rankDelta = rowElement.element("RankDelta").getTextTrim();
						if (cellElement.element("RankTimestamp") != null)
							cell.rankTimestamp = CommonUtil.parseDate(cellElement.element("RankTimestamp").getTextTrim());
						if (cellElement.element("Percentile") != null)
							cell.percentile = rowElement.element("Percentile").getTextTrim();
						if (cellElement.element("PercentileDelta") != null)
							cell.percentileDelta = rowElement.element("PercentileDelta").getTextTrim();

						row.cellList.add(cell);
					}
				}

				rowList.add(row);
			}

		}

	}

	/**
	 * @return the leaderboard
	 */
	public String getLeaderboard() {
		return leaderboard;
	}

	/**
	 * @return the snapshotInterval
	 */
	public String getSnapshotInterval() {
		return snapshotInterval;
	}

	/**
	 * @return the snapshotTimestamp
	 */
	public Date getSnapshotTimestamp() {
		return snapshotTimestamp;
	}

	/**
	 * @return the snapshotId
	 */
	public String getSnapshotId() {
		return snapshotId;
	}

	/**
	 * @return the sortComponent
	 */
	public String getSortComponent() {
		return sortComponent;
	}

	/**
	 * @return the sortDirection
	 */
	public String getSortDirection() {
		return sortDirection;
	}

	/**
	 * @return the filter
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * @return the rowStart
	 */
	public String getRowStart() {
		return rowStart;
	}

	/**
	 * @return the rowCount
	 */
	public String getRowCount() {
		return rowCount;
	}

	/**
	 * @return the rowTotal
	 */
	public String getRowTotal() {
		return rowTotal;
	}

	/**
	 * @return the colCount
	 */
	public String getColCount() {
		return colCount;
	}

	/**
	 * @return the columnList
	 */
	public List<Column> getColumnList() {
		return columnList;
	}

	/**
	 * @return the rowList
	 */
	public List<Row> getRowList() {
		return rowList;
	}

	public class Filter {

		private String type;
		private String value;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	public class Column {
		private String column;

		public String getColumn() {
			return column;
		}

		public void setColumn(String column) {
			this.column = column;
		}

	}

	public class Row {
		private String leader;
		private String leaderName;
		private String portalHandle;
		private String world;
		private List<Cell> cellList = new ArrayList<Cell>();

		/**
		 * @return the leader
		 */
		public String getLeader() {
			return leader;
		}

		/**
		 * @param leader
		 *            the leader to set
		 */
		public void setLeader(String leader) {
			this.leader = leader;
		}

		/**
		 * @return the leaderName
		 */
		public String getLeaderName() {
			return leaderName;
		}

		/**
		 * @param leaderName
		 *            the leaderName to set
		 */
		public void setLeaderName(String leaderName) {
			this.leaderName = leaderName;
		}

		/**
		 * @return the portalHandle
		 */
		public String getPortalHandle() {
			return portalHandle;
		}

		/**
		 * @param portalHandle
		 *            the portalHandle to set
		 */
		public void setPortalHandle(String portalHandle) {
			this.portalHandle = portalHandle;
		}

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

		/**
		 * @return the cellList
		 */
		public List<Cell> getCellList() {
			return cellList;
		}

		/**
		 * @param cellList
		 *            the cellList to set
		 */
		public void setCellList(List<Cell> cellList) {
			this.cellList = cellList;
		}

		public class Cell {
			private String value;
			private String delta;
			private Date timestamp;
			private String rank;
			private String rankDelta;
			private Date rankTimestamp;
			private String percentile;
			private String percentileDelta;

			/**
			 * @return the value
			 */
			public String getValue() {
				return value;
			}

			/**
			 * @param value
			 *            the value to set
			 */
			public void setValue(String value) {
				this.value = value;
			}

			/**
			 * @return the delta
			 */
			public String getDelta() {
				return delta;
			}

			/**
			 * @param delta
			 *            the delta to set
			 */
			public void setDelta(String delta) {
				this.delta = delta;
			}

			/**
			 * @return the timestamp
			 */
			public Date getTimestamp() {
				return timestamp;
			}

			/**
			 * @param timestamp
			 *            the timestamp to set
			 */
			public void setTimestamp(Date timestamp) {
				this.timestamp = timestamp;
			}

			/**
			 * @return the rank
			 */
			public String getRank() {
				return rank;
			}

			/**
			 * @param rank
			 *            the rank to set
			 */
			public void setRank(String rank) {
				this.rank = rank;
			}

			/**
			 * @return the rankDelta
			 */
			public String getRankDelta() {
				return rankDelta;
			}

			/**
			 * @param rankDelta
			 *            the rankDelta to set
			 */
			public void setRankDelta(String rankDelta) {
				this.rankDelta = rankDelta;
			}

			/**
			 * @return the rankTimestamp
			 */
			public Date getRankTimestamp() {
				return rankTimestamp;
			}

			/**
			 * @param rankTimestamp
			 *            the rankTimestamp to set
			 */
			public void setRankTimestamp(Date rankTimestamp) {
				this.rankTimestamp = rankTimestamp;
			}

			/**
			 * @return the percentile
			 */
			public String getPercentile() {
				return percentile;
			}

			/**
			 * @param percentile
			 *            the percentile to set
			 */
			public void setPercentile(String percentile) {
				this.percentile = percentile;
			}

			/**
			 * @return the percentileDelta
			 */
			public String getPercentileDelta() {
				return percentileDelta;
			}

			/**
			 * @param percentileDelta
			 *            the percentileDelta to set
			 */
			public void setPercentileDelta(String percentileDelta) {
				this.percentileDelta = percentileDelta;
			}

		}

	}

}
