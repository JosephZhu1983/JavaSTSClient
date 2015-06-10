package com.kongzhonghd;

import com.kongzhonghd.api.Auth.*;
import com.kongzhonghd.api.Exchange.DepositGemsRequest;
import com.kongzhonghd.api.Exchange.DepositGemsResponse;
import com.kongzhonghd.api.Game.GetAccountFeaturesRequest;
import com.kongzhonghd.api.Game.GetAccountFeaturesResponse;
import com.kongzhonghd.api.Kongzhong.*;
import com.kongzhonghd.api.Leaderboard.DeleteLeaderRequest;
import com.kongzhonghd.api.Leaderboard.DeleteLeaderResponse;
import com.kongzhonghd.api.Leaderboard.ForceSnapshotRequest;
import com.kongzhonghd.api.Leaderboard.ForceSnapshotResponse;
import com.kongzhonghd.api.Leaderboard.GetLeadersRequest;
import com.kongzhonghd.api.Leaderboard.GetLeadersResponse;
import com.kongzhonghd.api.Leaderboard.GetLeadersResponse.Column;
import com.kongzhonghd.api.Leaderboard.GetLeadersResponse.Row.Cell;
import com.kongzhonghd.api.Leaderboard.GetLeadersUnauthRequest;
import com.kongzhonghd.api.Leaderboard.GetLeadersUnauthResponse;
import com.kongzhonghd.api.Leaderboard.ListComponentsRequest;
import com.kongzhonghd.api.Leaderboard.ListComponentsResponse;
import com.kongzhonghd.api.Leaderboard.ListLeaderboardsRequest;
import com.kongzhonghd.api.Leaderboard.ListLeaderboardsResponse;
import com.kongzhonghd.api.Leaderboard.ListWorldsRequest;
import com.kongzhonghd.api.Leaderboard.ListWorldsResponse;
import com.kongzhonghd.api.Leaderboard.ListWorldsResponse.Row;
import com.kongzhonghd.api.Leaderboard.PostLeaderNameRequest;
import com.kongzhonghd.api.Leaderboard.PostLeaderNameResponse;
import com.kongzhonghd.api.Leaderboard.PostRatingsRequest;
import com.kongzhonghd.api.Leaderboard.PostRatingsResponse;
import com.kongzhonghd.api.LogQuery.SearchTablesRequest;
import com.kongzhonghd.api.LogQuery.SearchTablesResponse;
import com.kongzhonghd.api.Partner.*;
import com.kongzhonghd.api.SSO.GetIdByLoginNameRequest;
import com.kongzhonghd.api.SSO.GetIdByLoginNameResponse;
import com.kongzhonghd.api.SSO.LoginRequest;
import com.kongzhonghd.api.SSO.LoginResponse;
import com.kongzhonghd.api.SSO.UpgradeRequest;
import com.kongzhonghd.api.SSO.UpgradeResponse;
import com.kongzhonghd.api.presence.GetUserInfoResponse.User;
import com.kongzhonghd.sts.business.ToDigest;
import com.kongzhonghd.sts.business.ToDigestType;
import com.kongzhonghd.sts.business.ToUser;
import com.kongzhonghd.sts.business.ToUserType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
	static final STSClient client = new STSClient();
	private static final Log log = LogFactory.getLog(Main.class);
	private static final Random r = new Random();
	private static final AtomicLong success = new AtomicLong();
	private static final AtomicLong fail = new AtomicLong();
	private static final AtomicLong messageId = new AtomicLong();

	private static String generateString(Random rng, String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static void CreateAccountTest(STSClient client) throws Exception {
		String username = generateString(r, "abcdefghijklmnopqrstuvwxyz", 4);
		String password = generateString(r, "abcdefghijklmnopqrstuvwxyz", 4);
		String loginname = username + "@test.com";

		GetProspectiveUserIdResponse getProspectiveUserIdResponse = client
				.requestAndWaitForResponse(new GetProspectiveUserIdRequest());
		String userId = getProspectiveUserIdResponse.getProspectiveUserId();

		CreateAccountTrustedResponse createAccountResponse = client.requestAndWaitForResponse(new CreateAccountTrustedRequest(
				userId, loginname, username, "10", ""));
		if (createAccountResponse.getError() != null)
			throw new Exception("CreateAccount出错，错误信息为：" + createAccountResponse.getError().getText());

		GetUserInfoResponse getUserInfoResponse = client.requestAndWaitForResponse(new GetUserInfoRequest(new ToUser(
				ToUserType.Id, userId)));
		if (getUserInfoResponse.getError() != null)
			throw new Exception("GetUserInfo出错，错误信息为：" + createAccountResponse.getError().getText());
		else if (!userId.equals(getUserInfoResponse.getUserId()))
			throw new Exception("GetUserInfo返回结果错误！");

		CreateGameAccountTrustedResponse createGameAccountTrustedResponse = client
				.requestAndWaitForResponse(new CreateGameAccountTrustedRequest(new ToUser(ToUserType.Id, userId), "Pvg", "6", "2"));

		if (createGameAccountTrustedResponse.getError() != null)
			throw new Exception("CreateGameAccountTruested出错，错误信息为：" + createGameAccountTrustedResponse.getError().getText());

		ListGameAccountsResponse listGameAccountsResponse = client.requestAndWaitForResponse(new ListGameAccountsRequest(
				new ToUser(ToUserType.Id, userId)));
		if (listGameAccountsResponse.getError() != null)
			throw new Exception("ListGameAccounts出错，错误信息为：" + createAccountResponse.getError().getText());
		else if (!"Guild Wars 2".equals(listGameAccountsResponse.getGameAccountInfoList().get(0).getAlias()))
			throw new Exception("ListGameAccounts返回结果错误！");

		System.out.println("创建账号成功，登录名：" + loginname + "，密码：" + password);
	}

	public static void addProductKeyTest(STSClient client, String userId, String key) throws Exception {
		AddProductKeyResponse addProductKeyResponse = client.requestAndWaitForResponse(new AddProductKeyRequest(new ToUser(
				ToUserType.Id, userId), key));

	}

	public static void loginTest(STSClient client, String loginName, String password, String userId) throws Exception {
		GetIdByLoginNameResponse getIdByLoginNameResponse = client.requestAndWaitForResponse(new GetIdByLoginNameRequest(
				new ToUser(ToUserType.Id, userId), loginName));
		if (getIdByLoginNameResponse.getError() != null) {
			throw new Exception("GetIdByLoginName出错，错误信息为：" + getIdByLoginNameResponse.getError().getText());
		}
		String id = getIdByLoginNameResponse.getId();
		if (id == null || "".equals(id)) {
			throw new Exception("GetIdByLoginName没有获取到userId");
		}
		LoginResponse loginResponse = client.requestAndWaitForResponse(new LoginRequest(loginName, password, ""));
		com.kongzhonghd.api.SSO.GetUserInfoResponse getUserInfoResponse = client
				.requestAndWaitForResponse(new com.kongzhonghd.api.SSO.GetUserInfoRequest(id, ""));
		/*
		 * Map<String,String> map = new LinkedHashMap<String,String>();
		 * map.put("E805B775-9B61-E211-81DA-00224D566B64", "59");
		 * map.put("47F592DF-B9C7-E211-85F3-00224D566B64", "38");
		 * map.put("C341D941-C3C7-E211-85F3-00224D566B64", "94");
		 * UpgradeResponse upgradeResponse =
		 * client.requestAndWaitForResponse(new UpgradeRequest("",id,map));
		 */
	}

	public static void listGameAccounts(STSClient client, String userId) throws Exception {
		ListGameAccountsResponse listGameAccountsResponse = client.requestAndWaitForResponse(new ListGameAccountsRequest(
				new ToUser(ToUserType.Id, userId)));
		if (listGameAccountsResponse.getError() != null)
			throw new Exception("ListGameAccounts出错，错误信息为：" + listGameAccountsResponse.getError());
		else if (!"Guild Wars 2".equals(listGameAccountsResponse.getGameAccountInfoList().get(0).getAlias()))
			throw new Exception("ListGameAccounts返回结果错误！");
	}

	public static void getWorldId(STSClient client, String userId, String alias) throws Exception {
		GetWorldIdResponse getWorldIdResponse = client.requestAndWaitForResponse(new GetWorldIdRequest(new ToUser(ToUserType.Id,
				userId), alias));
		if (getWorldIdResponse.getError() != null)
			throw new Exception("getWorldId出错，错误信息为：" + getWorldIdResponse.getError());
	}

	public static void getWorldIdFromLoginName(STSClient client, String loginName, String alias) throws Exception {
		GetWorldIdResponse getWorldIdResponse = client.requestAndWaitForResponse(new GetWorldIdRequest(new ToUser(
				ToUserType.LoginName, loginName), alias));
		if (getWorldIdResponse.getError() != null)
			throw new Exception("getWorldIdFromLoginName出错，错误信息为：" + getWorldIdResponse.getError());
	}

	public static void getUserInfoTest(STSClient client, String userId) {

		try {
			GetUserInfoResponse getUserInfoResponse1 = client.requestAndWaitForResponse(new GetUserInfoRequest(new ToUser(
					ToUserType.Id, userId)));
			// com.kongzhonghd.api.Presence.GetUserInfoResponse
			// getUserInfoResponse2 = client.requestAndWaitForResponse(new
			// com.kongzhonghd.api.Presence.GetUserInfoRequest(new
			// ToUser(ToUserType.Id, userId)));
			// client.requestAndWaitForResponse(new LogoutMyClientRequest(new
			// ToUser(ToUserType.Id, userId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getPresenceUserInfoTest(STSClient client, String userId) {

		try {
			com.kongzhonghd.api.presence.GetUserInfoResponse getUserInfoResponse = client.requestAndWaitForResponse(new com.kongzhonghd.api.presence.GetUserInfoRequest(new ToUser(
					ToUserType.Id, userId)));
			
			List<User> user = getUserInfoResponse.getUser();
			for(User u : user){
				System.out.println(u.getUserId());
				System.out.println(u.getUserName());
				System.out.println(u.getRelation());
				System.out.println("----------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeUserNameTest(STSClient client, String userId, String userName) throws Exception {
		ChangeUserNameResponse reponse = client.requestAndWaitForResponse(new ChangeUserNameRequest(new ToUser(ToUserType.Id,
				userId), userName));
		return;
	}

	public static String getProspectiveUserIdTest(STSClient client) throws Exception {
		GetProspectiveUserIdRequest getProspectiveUserIdRequest = new GetProspectiveUserIdRequest();
		GetProspectiveUserIdResponse reponse = client.requestAndWaitForResponse(getProspectiveUserIdRequest);
		return reponse.getProspectiveUserId();
	}

	public static void predictUserNameTest(STSClient client, String userName, String loginName) throws Exception {
		PredictUserNameResponse reponse = client.requestAndWaitForResponse(new PredictUserNameRequest(userName, loginName));

	}

	public static void checkUserNameTest(STSClient client, String loginName, String username) throws Exception {
		CheckUserNameResponse checkUserNameResponse = client.requestAndWaitForResponse(new CheckUserNameRequest(new ToUser(
				ToUserType.LoginName, loginName), username));
	}

	public static void LargeDataTest(STSClient client) throws Exception {
		StringBuilder parm1 = new StringBuilder();
		for (int i = 0; i < 5000; i++) {
			parm1.append("0123456789");
		}
		StringBuilder parm2 = new StringBuilder();
		for (int i = 0; i < 5000; i++) {
			parm2.append("0123456789");
		}
		TestResponse testResponse = client.requestAndWaitForResponse(new TestRequest(parm1.toString(), parm2.toString()));
		if (testResponse == null)
			throw new Exception("LargeDataTest没有得到结果！");
		if (testResponse.getError() != null)
			throw new Exception("LargeDataTest出错，错误信息为：" + testResponse.getError().getText());
		else if (!testResponse.getParm1().equals("yes" + parm1) || !testResponse.getParm2().equals("yes" + parm2))
			throw new Exception("LargeDataTest返回结果错误！");
	}

	public static void MathTest(STSClient client, int num) throws Exception {
		int a = 0;
		MathResponse mathResponse = client.requestAndWaitForResponse(new MathRequest(messageId.incrementAndGet(), "加", a, num));
		if (mathResponse == null)
			throw new Exception("MathTest没有得到结果！");
		else if (mathResponse.getError() != null)
			throw new Exception("MathTest出错，错误信息为：" + mathResponse.getError().getText());
		else if (mathResponse.getMath().getResult() != a + num)
			throw new Exception("MathTest返回结果错误！");
	}

	private static void ReadonlyTest(STSClient client) throws Exception {
		String loginName = "hddev001@kongzhong.com";
		ListGameAccountsResponse listGameAccountsResponse = client.requestAndWaitForResponse(new ListGameAccountsRequest(
				new ToUser(ToUserType.LoginName, loginName)));
		if (listGameAccountsResponse == null)
			throw new Exception("ListGameAccounts没有得到结果！");
		if (listGameAccountsResponse.getError() != null)
			throw new Exception("ListGameAccounts出错，错误信息为：" + listGameAccountsResponse.getError().getText());
		else if (!listGameAccountsResponse.getGameAccountInfoList().get(0).getGameAccount()
				.equals("C17C5FC2-08CD-E211-A3E9-6C3BE5BB970D"))
			throw new Exception("ListGameAccounts返回结果错误！");
	}

	private static void AsyncReadonlyTest(STSClient client) {
		final String loginName = "hddev001@kongzhong.com";
		client.requestAndExecuteCallbackWhenGetResponse(new ListGameAccountsRequest(new ToUser(ToUserType.LoginName, loginName)),
				new STSCallback<ListGameAccountsResponse>() {
					@Override
					public void whenSuccess(ListGameAccountsResponse response) {
						success.getAndIncrement();
						if (response == null)
							System.out.println("结果为空！");
					}

					@Override
					public void whenFail(Exception ex) {
						fail.getAndIncrement();
						ex.printStackTrace();
					}
				});
	}

	private static void AsyncMathTest(STSClient client, int num) throws Exception {
		client.requestAndExecuteCallbackWhenGetResponse(new MathRequest(messageId.incrementAndGet(), "加", 10, num),
				new STSCallback<MathResponse>() {
					@Override
					public void whenSuccess(MathResponse response) {
						success.getAndIncrement();
						if (response == null)
							System.out.println("结果为空！");
					}

					@Override
					public void whenFail(Exception ex) {
						fail.getAndIncrement();
						ex.printStackTrace();
					}
				});
	}

	private static void OtherTest(STSClient client) throws Exception {
		String loginName = "hddev001@kongzhong.com";
		String gameAccount = "C17C5FC2-08CD-E211-A3E9-6C3BE5BB970D";

		CheckUserNameResponse checkUserNameResponse = client.requestAndWaitForResponse(new CheckUserNameRequest(new ToUser(
				ToUserType.LoginName, loginName), "hddev"));
		GetAccountFeaturesResponse getAccountFeaturesResponse = client.requestAndWaitForResponse(new GetAccountFeaturesRequest(
				gameAccount));
		GetAccountSkusResponse getAccountSkusResponse = client.requestAndWaitForResponse(new GetAccountSkusRequest(gameAccount));
		GetProductKeyInfoResponse getProductKeyInfoResponse = client
				.requestAndWaitForResponse(new GetProductKeyInfoRequest("aa"));

		DepositGemsResponse depositGemsResponse = client.requestAndWaitForResponse(new DepositGemsRequest(new ToUser(
				ToUserType.LoginName, loginName), 1, 1, 100));
	}

	private static void productKeyTest(STSClient client, String productKey) throws Exception {
		GetProductKeyInfoResponse getProductKeyInfoResponse = client.requestAndWaitForResponse(new GetProductKeyInfoRequest(
				productKey));
	}

	private static void LogQyeryTest(STSClient client) throws Exception {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -5);

		SearchTablesResponse response = client.requestAndWaitForResponse(new SearchTablesRequest(cal.getTime(), new Date()));
	}

	public static void printGCStats() {
		long totalGarbageCollections = 0;
		long garbageCollectionTime = 0;

		for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {

			long count = gc.getCollectionCount();

			if (count >= 0) {
				totalGarbageCollections += count;
			}

			long time = gc.getCollectionTime();

			if (time >= 0) {
				garbageCollectionTime += time;
			}
		}

		System.out.println("Total Garbage Collections: " + totalGarbageCollections);
		System.out.println("Total Garbage Collection Time (ms): " + garbageCollectionTime);
	}

	public static void createAccountTruestedTest(STSClient client, String loginname, String username, String userCenter,
			String datacenter, String territory) throws Exception {
		GetProspectiveUserIdResponse getProspectiveUserIdResponse = client
				.requestAndWaitForResponse(new GetProspectiveUserIdRequest());
		String userId = getProspectiveUserIdResponse.getProspectiveUserId();
		CreateAccountTrustedRequest createAccountTrustedRequest = new CreateAccountTrustedRequest(userId, loginname, username,
				userCenter, "");
		CreateAccountTrustedResponse response = client.requestAndWaitForResponse(createAccountTrustedRequest);
		CreateGameAccountTrustedResponse createGameAccountTrustedResponse = client
				.requestAndWaitForResponse(new CreateGameAccountTrustedRequest(new ToUser(ToUserType.Id, userId), datacenter,
						territory, "2"));

	}

	public static void postLeaderNameTest(STSClient client, String id, String leaderboard, String leader, String leaderName)
			throws Exception {

		PostLeaderNameResponse response = client.requestAndWaitForResponse(new PostLeaderNameRequest(
				new ToUser(ToUserType.Id, id), leaderboard, leader, leaderName));

		if (response.getError() != null)
			throw new Exception("PostLeaderNameRequest出错，错误信息为：" + response.getError());

	}

	public static void postRatingsTest(STSClient client, String id, String leaderboard, String leader, String word)
			throws Exception {
		Map<String, String> ratings = new HashMap<String, String>();
		ratings.put("Rating", "341.9195");
		ratings.put("Wins", "4353.8198");
		ratings.put("Losses", "380.7712");
		PostRatingsResponse response = client.requestAndWaitForResponse(new PostRatingsRequest(new ToUser(ToUserType.Id, id),
				leaderboard, leader, word, ratings));

		if (response.getError() != null)
			throw new Exception("PostRatingsRequest出错，错误信息为：" + response.getError());

	}

	public static void listWorldsTest(STSClient client, String id) throws Exception {
		ListWorldsResponse response = client.requestAndWaitForResponse(new ListWorldsRequest(new ToUser(ToUserType.Id, id)));

		if (response.getError() != null)
			throw new Exception("PostLeaderNameRequest出错，错误信息为：" + response.getError());

		List<Row> rowList = response.getRowList();
		for (Row row : rowList) {
			System.out.println(row.getWorld());
		}
	}

	public static void listLeaderboardsTest(STSClient client, String id) throws Exception {
		ListLeaderboardsResponse response = client.requestAndWaitForResponse(new ListLeaderboardsRequest(new ToUser(
				ToUserType.Id, id)));

		if (response.getError() != null)
			throw new Exception("PostLeaderNameRequest出错，错误信息为：" + response.getError());

		List<ListLeaderboardsResponse.Row> rowList = response.getRowList();
		for (ListLeaderboardsResponse.Row row : rowList) {
			System.out.println(row.getInProgress());
			System.out.println(row.getName());
			System.out.println(row.getRowTotal());
			System.out.println(row.getSnapshotInterval());
			System.out.println(row.getSnapshotTimestamp());
		}
	}

	public static void listComponentsRequestTest(STSClient client, String id) throws Exception {

		ListComponentsResponse response = client.requestAndWaitForResponse(new ListComponentsRequest(
				new ToUser(ToUserType.Id, id)));

		if (response.getError() != null)
			throw new Exception("PostLeaderNameRequest出错，错误信息为：" + response.getError());

		List<ListComponentsResponse.Row> rowList = response.getRowList();
		for (ListComponentsResponse.Row row : rowList) {
			System.out.println(row.getName());
		}
	}

	public static void deleteLeaderTest(STSClient client, String id, String leader) throws Exception {

		DeleteLeaderResponse response = client.requestAndWaitForResponse(new DeleteLeaderRequest(new ToUser(ToUserType.Id, id),
				leader));

		if (response.getError() != null)
			throw new Exception("PostLeaderNameRequest出错，错误信息为：" + response.getError());
	}

	public static void forceSnapshotTest(STSClient client, String id, String leaderboard) throws Exception {

		ForceSnapshotResponse response = client.requestAndWaitForResponse(new ForceSnapshotRequest(new ToUser(ToUserType.Id, id),
				leaderboard));

		if (response.getError() != null)
			throw new Exception("PostLeaderNameRequest出错，错误信息为：" + response.getError());
	}

	public static void getLeadersTest(STSClient client, String id, String leaderboard, String sortComponent,
			String sortDirection, String rowStart, String rowCount, String snapshotId) throws Exception {

		GetLeadersRequest getLeadersRequest = new GetLeadersRequest(new ToDigest(ToDigestType.Digest, id), leaderboard,
				sortComponent, sortDirection, rowStart, rowCount, snapshotId, null);
	/*	GetLeadersRequest.Filter fi = getLeadersRequest.new Filter();
		fi.setType("World");
		fi.setValue("8001");
		getLeadersRequest.setFilter(fi);*/

		GetLeadersResponse response = client.requestAndWaitForResponse(getLeadersRequest);

		if (response.getError() != null)
			throw new Exception("PostLeaderNameRequest出错，错误信息为：" + response.getError());

		System.out.println(response.getColCount());
		System.out.println(response.getLeaderboard());
		System.out.println(response.getRowCount());
		System.out.println(response.getRowStart());
		System.out.println(response.getRowTotal());
		System.out.println(response.getSnapshotId());
		System.out.println(response.getSnapshotInterval());
		System.out.println(response.getSnapshotTimestamp());
		System.out.println(response.getSortComponent());
		System.out.println(response.getSortDirection());
		System.out.println(response.getFilter().getType());
		System.out.println(response.getFilter().getValue());
		List<Column> coList = response.getColumnList();
		for (Column c : coList) {
			System.out.println(c.getColumn());
		}

		List<com.kongzhonghd.api.Leaderboard.GetLeadersResponse.Row> rowList = response.getRowList();
		for (com.kongzhonghd.api.Leaderboard.GetLeadersResponse.Row c : rowList) {
			System.out.println(c.getLeader());
			System.out.println(c.getLeaderName());
			System.out.println(c.getPortalHandle());
			System.out.println(c.getWorld());
			List<Cell> cellList = c.getCellList();
			for (Cell cell : cellList) {
				System.out.println(cell.getDelta());
				System.out.println(cell.getPercentile());
				System.out.println(cell.getPercentileDelta());
				System.out.println(cell.getRank());
				System.out.println(cell.getRankDelta());
				System.out.println(cell.getValue());
				System.out.println(cell.getRankTimestamp());
				System.out.println(cell.getTimestamp());
			}
		}

	}
	
	
	public static void getLeadersUnauthRequestTest(STSClient client, String id, String leaderboard, String sortComponent,
			String sortDirection, String rowStart, String rowCount, String snapshotId) throws Exception {

		GetLeadersUnauthRequest getLeadersUnauthRequest = new GetLeadersUnauthRequest(new ToDigest(ToDigestType.Digest, id), leaderboard,
				sortComponent, sortDirection, rowStart, rowCount, snapshotId, null);
	/*	GetLeadersUnauthRequest.Filter fi = getLeadersUnauthRequest.new Filter();
		fi.setType("World");
		fi.setValue("1011");
		getLeadersUnauthRequest.setFilter(fi);*/

		GetLeadersUnauthResponse response = client.requestAndWaitForResponse(getLeadersUnauthRequest);

		if (response.getError() != null)
			throw new Exception("PostLeaderNameRequest出错，错误信息为：" + response.getError());

		System.out.println(response.getColCount());
		System.out.println(response.getLeaderboard());
		System.out.println(response.getRowCount());
		System.out.println(response.getRowStart());
		System.out.println(response.getRowTotal());
		System.out.println(response.getSnapshotId());
		System.out.println(response.getSnapshotInterval());
		System.out.println(response.getSnapshotTimestamp());
		System.out.println(response.getSortComponent());
		System.out.println(response.getSortDirection());
		System.out.println(response.getFilter().getType());
		System.out.println(response.getFilter().getValue());
		List<com.kongzhonghd.api.Leaderboard.GetLeadersUnauthResponse.Column> coList = response.getColumnList();
		for (com.kongzhonghd.api.Leaderboard.GetLeadersUnauthResponse.Column c : coList) {
			System.out.println(c.getColumn());
		}

		List<com.kongzhonghd.api.Leaderboard.GetLeadersUnauthResponse.Row> rowList = response.getRowList();
		for (com.kongzhonghd.api.Leaderboard.GetLeadersUnauthResponse.Row c : rowList) {
			System.out.println(c.getLeader());
			System.out.println(c.getLeaderName());
			System.out.println(c.getPortalHandle());
			System.out.println(c.getWorld());
			List<com.kongzhonghd.api.Leaderboard.GetLeadersUnauthResponse.Row.Cell> cellList = c.getCellList();
			for (com.kongzhonghd.api.Leaderboard.GetLeadersUnauthResponse.Row.Cell cell : cellList) {
				System.out.println(cell.getDelta());
				System.out.println(cell.getPercentile());
				System.out.println(cell.getPercentileDelta());
				System.out.println(cell.getRank());
				System.out.println(cell.getRankDelta());
				System.out.println(cell.getValue());
				System.out.println(cell.getRankTimestamp());
				System.out.println(cell.getTimestamp());
			}
		}

	}

	public static void main(String[] args) {
		try {
			//client.connect("ChinaDev.xml");
			client.connect("ChinaStage.xml");
			// client.connect("ChinaLive.xml");

		} catch (Exception ex) {
			return;
		}

		try {

			// postLeaderNameTest(client,"29C766FB-2E6F-E211-8EA1-00224D69A4D4","pvp","B0B8B23C-A398-4035-860A-4677C08AFB6F","John Smith");
			// postRatingsTest(client,"29C766FB-2E6F-E211-8EA1-00224D69A4D4","pvp","B0B8B23C-A398-4035-860A-4677C08AFB6F","1004");
			// deleteLeaderTest(client,"29C766FB-2E6F-E211-8EA1-00224D69A4D4","B0B8B23C-A398-4035-860A-4677C08AFB6F");
			// forceSnapshotTest(client,"29C766FB-2E6F-E211-8EA1-00","pvp");
			//getLeadersUnauthRequestTest(client, "B564C660", "PvP", "Rating", "Desc", "0", "5", "51074882-8626-4EF2-B8DA-EAD9CD55F4A0");
			
			 //listComponentsRequestTest(client,"29C766FB-2E6F-E211-8EA1-00224D69A4D4");
			 //listWorldsTest(client,"29C766FB-2E6F-E211-8EA1-00224D69A4D4");
			 //listLeaderboardsTest(client,"1");
			// getLeadersTest(client, "sdfa", "WvW", "Volatility", "Desc", "0", "5", "D73954E1-E429-E411-8AA4-2C768A519044");
			 
			// loginTest(client, "gw2tuser0000087", "kongzhong", "");
			// LogQyeryTest(client);
			// CreateAccountTest(client);
			// 10 11 12 Pvg Sjw Can 6 7 8
			// createAccountTruestedTest(client,"qatrialpvg","qatpvg","10","Pvg","6");
			// createAccountTruestedTest(client,"qatrialsjw","qatsjw","11","Sjw","7");
			// createAccountTruestedTest(client,"qatrialcan","qatcan","12","Can","8");
			//
			// createAccountTruestedTest(client,"devtrialpvg","devtpvg","10","Pvg","6");
			// createAccountTruestedTest(client,"devtrialsjw","devtsjw","11","Sjw","7");
			// createAccountTruestedTest(client,"devtrialcan","devtcan","12","Can","8");

			// createAccountTruestedTest(client,"devtrial111","devtdfd","12","Can","8");
			// createAccountTruestedTest(client,"devtrial113","devtdfd","12","Can","8");

			// createAccountTruestedTest(client,"qatrialpvg01","qatpvgo","10","Pvg","6");
			// createAccountTruestedTest(client,"qatrialpvg02","qatpvgt","10","Pvg","6");
			// createAccountTruestedTest(client,"qatrialsjw01","qatsjwo","11","Sjw","7");
			// createAccountTruestedTest(client,"qatrialsjw02","qatsjwt","11","Sjw","7");
			// createAccountTruestedTest(client,"qatrialcan01","qatcano","12","Can","8");
			// createAccountTruestedTest(client,"qatrialcan02","qatcant","12","Can","8");

			// checkUserNameTest(client,"809967178@qq.com","亲爱的玥蓝");
			// productKeyTest(client,"6GGR4H-3LN7N-9P42-G1M-BTLB42F");
			// getUserInfoTest(client,"ED658D56-65C8-4A87-857B-AF0F0B840777");
			// addProductKeyTest(client,"DF284B36-813F-465C-B4E2-D430BE69714C",
			// "3996L4-SQHKM-SPV1-RXG-FQSBFXH");
			// createAccountTruestedTest(client,userId,"wefe234wdfdrer","regfe",1);
			//getUserInfoTest(client,"81BE9C7A-D52E-461D-B990-2779B536DA90");
			//getPresenceUserInfoTest(client,"DBCBF947-FA38-4AE0-AB12-19A7DFBC57EC");
			//listGameAccounts(client, "7FBDD090-59AD-4E81-8677-7B55FFF39EB7");
			// getWorldId(client, "81BE9C7A-D52E-461D-B990-2779B536DA90",
			// "Guild Wars 2");
			// getWorldIdFromLoginName(client,"EB87220C-7CEC-4180-BD86-AA1F3E44F8BD@KZ","Guild Wars2");
			 predictUserNameTest(client,"黢asdf","809967178@qq.com");
			// checkUserNameTest(client,"tesshishi1a@sd.com","搞一个不重复的上限abc");
			// changeUserNameTest(client,"8B61FCCF-1EB8-418A-8BD9-FEF62476F81F","搞一个不重复的上限abc");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		/*
		 * final long begin = System.currentTimeMillis(); final int tCount =
		 * 10000; final int iCount = 100; final int total = tCount * iCount;
		 * 
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { while (true) { try {
		 * Thread.sleep(1000); } catch (InterruptedException e) {
		 * 
		 * }
		 * 
		 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); long
		 * pass = System.currentTimeMillis() - begin; long totalMemory =
		 * Runtime.getRuntime().totalMemory(); long freeMemory =
		 * Runtime.getRuntime().freeMemory(); long usedMemory = totalMemory -
		 * freeMemory; java.text.NumberFormat format = new
		 * java.text.DecimalFormat("###,###"); String memoryInfo =
		 * format.format(usedMemory) + "/" + format.format(totalMemory);
		 * //printGCStats(); log.warn("\r\nMemory:" + memoryInfo + ",Time:" +
		 * df.format(new Date()) + ",Time passed：" + pass + ",Total：" + total +
		 * ",Success：" + success.get() + "（Average TPS：" + (success.get() * 1000
		 * / pass) + "),Fail：" + fail.get()); if (success.get() + fail.get() ==
		 * total) break;
		 * 
		 * } } }).start();
		 * 
		 * for (int t = 0; t < 1000; t++) { new Thread() { public void run() {
		 * for (int x = 0; x < 10000; x++) { try { // MathTest(client, x); //
		 * CreateAccountTest(client);
		 * 
		 * //LargeDataTest(client); //ReadonlyTest(client); //OtherTest(client);
		 * String userId = getProspectiveUserIdTest(client);
		 * createAccountTruestedTest(client,userId,"aaa"+x+"@kongzh.com");
		 * String userId = getProspectiveUserIdTest(client);
		 * createAccountTruestedTest(client,userId,"dfd22f@ds.com");
		 * success.incrementAndGet(); } catch (Exception ex) {
		 * log.warn("Main：执行任务出错：" + LangUtils.getExceptionContent(ex));
		 * fail.incrementAndGet(); } } } }.start(); }
		 */

		// for (int t = 0; t < tCount; t++)
		// {
		// new Thread()
		// {
		// public void run()
		// {
		// for (int x = 0; x < iCount; x++)
		// {
		// AsyncReadonlyTest(client);
		// }
		// }
		// }.start();
		// }

		/*
		 * while (true) { if (success.get() + fail.get() == total) {
		 * log.warn("Main完成：" + (System.currentTimeMillis() - begin)); break; }
		 * Thread.sleep(10); }
		 */
	}
}
