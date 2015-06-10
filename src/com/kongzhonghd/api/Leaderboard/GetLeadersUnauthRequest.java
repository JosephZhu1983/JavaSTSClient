package com.kongzhonghd.api.Leaderboard;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import com.kongzhonghd.sts.business.AbstractDigestRouteRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.sts.business.ToDigest;

/**
 * @author stefanie zhao
 * @date 2014-8-14 上午10:31:02
 */
public class GetLeadersUnauthRequest extends AbstractDigestRouteRequest {
	private String leaderboard;
	private String sortComponent;
	private String sortDirection;
	private String rowStart;
	private String rowCount;
	private String snapshotId;
	private Filter filter;

	public GetLeadersUnauthRequest(ToDigest toDigest, String leaderboard, String sortComponent, String sortDirection, String rowStart,
			String rowCount, String snapshotId, Filter filter) {
		super(toDigest);
		this.leaderboard = leaderboard;
		this.sortComponent = sortComponent;
		this.sortDirection = sortDirection;
		this.rowStart = rowStart;
		this.rowCount = rowCount;
		this.snapshotId = snapshotId;
		this.filter = filter;
	}

	/**
	 * @return the leaderboard
	 */
	public String getLeaderboard() {
		return leaderboard;
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
	 * @return the snapshotId
	 */
	public String getSnapshotId() {
		return snapshotId;
	}

	/**
	 * @return the filter
	 */
	public Filter getFilter() {
		return filter;
	}
	
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	@Override
	protected String getProtocol() {
		return "Leaderboard";
	}

	@Override
	protected String getCommand() {
		return "GetLeadersUnauth";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return GetLeadersUnauthResponse.class;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Document buildToXmlDocument() throws Exception {
		Document xml = DocumentHelper.createDocument();
		Element request = xml.addElement(XML_ROOT);
		(request.addElement("Leaderboard")).setText(leaderboard);
		(request.addElement("SortComponent")).setText(sortComponent);
		if(StringUtils.hasText(sortDirection))
			(request.addElement("SortDirection")).setText(sortDirection);
		if(StringUtils.hasText(rowStart))
			(request.addElement("RowStart")).setText(rowStart);
		(request.addElement("RowCount")).setText(rowCount);
		if(StringUtils.hasText(snapshotId))
			(request.addElement("SnapshotId")).setText(snapshotId);
		if(filter != null){
			Element filterElement = request.addElement("Filter");
			filterElement.setAttributeValue("type", filter.type);
			filterElement.setAttributeValue("value", filter.value);
		}

		return xml;
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

}
