package com.kongzhonghd.api.SSO;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;

/**
 * @author stefanie zhao
 * @date 2014-2-19 下午06:00:04
 */
public class UpgradeRequest extends AbstractRequest {
	private String whitelistIp;
	private String userId;
	private Map<String,String> authPromptInputs;

	public UpgradeRequest(String whitelistIp, String userId, Map<String,String> authPromptInputs) {
		this.whitelistIp = whitelistIp;
		this.userId = userId;
		this.authPromptInputs = authPromptInputs;
	}

	@Override
	protected String getProtocol() {
		return "Game.kz.Auth";
	}

	@Override
	protected String getCommand() {
		return "Upgrade";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return UpgradeResponse.class;
	}

	@Override
	public Document buildToXmlDocument() throws Exception {
		Document xml = DocumentHelper.createDocument();
		Element request = xml.addElement(XML_ROOT);
		if(authPromptInputs != null){
			for(Map.Entry<String, String> input : authPromptInputs.entrySet()){
				String inputId = input.getKey();
				String inputValue = input.getValue();
				Element authPromptInput = request.addElement("AuthPromptInput");
				(authPromptInput.addElement("InputId")).setText(inputId);
				(authPromptInput.addElement("InputValue")).setText(inputValue);
			}
		}
		(request.addElement("WhitelistIp")).setText(whitelistIp);
		(request.addElement("UserId")).setText(userId);
		return xml;
	}
}
