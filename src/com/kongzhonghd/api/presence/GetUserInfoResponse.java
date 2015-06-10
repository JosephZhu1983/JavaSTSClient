package com.kongzhonghd.api.presence;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.kongzhonghd.sts.business.AbstractResponse;

/**
 * @author stefanie zhao
 * @date 2015-1-6 下午01:29:25
 */
public class GetUserInfoResponse extends AbstractResponse {

	private List<User> userList = new ArrayList<User>();

	public List<User> getUser() {
		return userList;
	}

	@Override
	public void parseFromXmlDocument(Document xml) throws Exception {
		Element reply = xml.getRootElement();
		if (reply.element("Contacts") != null) {
			Element contacts = reply.element("Contacts");
			if (null != contacts) {
				for (Object items : contacts.elements()) {
					Element item = (Element) items;
					User user = new User();
					if(item.element("Relation") != null){
						Element re = item.element("Relation");
						if(re.element("Follow") != null && re.element("Follower") != null){
							//互粉
							user.setRelation((short)1);
						}else if(re.element("Follow") != null && re.element("Follower") == null){
							//关注
							user.setRelation((short)2);
						}else if(re.element("Follow") == null && re.element("Follower") != null){
							//粉丝
							user.setRelation((short)3);
						}
					}
					if (item.element("UserId") != null)
						user.setUserId(item.element("UserId").getTextTrim());
					if (item.element("UserName") != null)
						user.setUserName(item.element("UserName").getTextTrim().substring(1));

					userList.add(user);
				}
			}
		}
	}

	public class User {
		private String userId;
		private String userName;
		private Short relation;

		/**
		 * @return the userId
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * @param userId
		 *            the userId to set
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}

		/**
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}
		
		

		/**
		 * @return the relation
		 */
		public Short getRelation() {
			return relation;
		}

		/**
		 * @param relation the relation to set
		 */
		public void setRelation(Short relation) {
			this.relation = relation;
		}

		/**
		 * @param userName
		 *            the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}

	}
}
