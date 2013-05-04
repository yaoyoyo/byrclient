package haitong.yao.byrclient.net;

import haitong.yao.byrclient.constant.Api;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.models.Board;
import haitong.yao.byrclient.models.Mail;
import haitong.yao.byrclient.models.RequestError;
import haitong.yao.byrclient.models.Section;
import haitong.yao.byrclient.models.Subject;
import haitong.yao.byrclient.models.User;
import haitong.yao.byrclient.utils.Base64;
import haitong.yao.byrclient.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetUtil {

	private final static String POST_METHOD = "POST";
	private final static String GET_METHOD = "GET";
	private final static String ENCODE_TYPE_JSON = ".json";
	private final static String CONNECTION = "keep-alive";
	private final static String GZIP = "gzip, deflate";
	private final static String APP_KEY = "78e223c052793f0b";

	private static HttpURLConnection getHttpGetConnection(String url,
			String method, boolean isDefaultAcceptEncoding) {
		URL tempUrl;
		HttpURLConnection connection = null;
		try {
			tempUrl = new URL(url);
			connection = (HttpURLConnection) tempUrl.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Host", Api.BYR_HOST);
			connection.setRequestProperty("Connection", CONNECTION);
			if (!isDefaultAcceptEncoding) {
				connection.setRequestProperty("Accept-Encoding", GZIP);
			}
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public static String getStrFromStream(InputStream inStream) {
		try {
			GZIPInputStream gzipStream;
			gzipStream = new GZIPInputStream(inStream);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = gzipStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			byte[] data = outStream.toByteArray();

			outStream.close();
			gzipStream.close();
			inStream.close();

			return new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 从网络获取Bitmap
	 * 
	 * @return
	 */
	public static Bitmap getBitmapFromUrl(Context context, String url) {
		Bitmap result = null;
		InputStream is = null;
		HttpURLConnection connection = getHttpGetConnection(url, GET_METHOD,
				false);
		try {
			connection.connect();
			is = connection.getInputStream();
			if (null != is) {
				result = BitmapFactory.decodeStream(is);
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * 登录接口，采用Base64加密
	 * 
	 * @return
	 */
	public static Object login(Context context, String username, String password) {
		User user = null;
		String url = Api.BYR_API + Api.USER_LOGIN + ENCODE_TYPE_JSON
				+ "?appkey=" + APP_KEY;
		HttpURLConnection connection = getHttpGetConnection(url, POST_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(username).append(':').append(password);
		byte[] bytes = null;
		try {
			bytes = sb.toString().getBytes("ISO-8859-1");
			String encryptedData = Base64.encode(bytes);
			connection.setRequestProperty("Authorization", "Basic "
					+ encryptedData);
			String result = getStrFromStream(connection.getInputStream());
			if (result.startsWith("{\"request\":")) {
				return RequestError.parseRequestError(result);
			}
			user = User.parseUser(result);
			if (null != user && null != user.getId()) {
				Utils.saveContent(context, Utils.KEY_USER_TOKEN, encryptedData);
				Utils.saveContent(context, Utils.KEY_USERNAME, username);
				Utils.saveContent(context, Utils.KEY_PASSWORD, password);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	/**
	 * 通过本地保存的token直接登录
	 * 
	 * @return
	 */
	public static Object login(Context context, String token) {
		User user = null;
		String url = Api.BYR_API + Api.USER_LOGIN + ENCODE_TYPE_JSON
				+ "?appkey=" + APP_KEY;
		HttpURLConnection connection = getHttpGetConnection(url, POST_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		try {
			connection.setRequestProperty("Authorization", "Basic " + token);
			String result = getStrFromStream(connection.getInputStream());
			if (result.startsWith("{\"request\":")) {
				return RequestError.parseRequestError(result);
			}
			user = User.parseUser(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	/**
	 * 登出接口
	 * 
	 * @return
	 */
	public static void logout(Context context) {
		Utils.clearPrefs(context);
	}

	/**
	 * 查询用户接口，需要登录认证状态
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	public static Object queryUser(Context context, String id) {
		User user = null;
		String url = Api.BYR_API + Api.USER_QUERY + id + ENCODE_TYPE_JSON
				+ "?appkey=" + APP_KEY;
		HttpURLConnection connection = getHttpGetConnection(url, GET_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		String token = Utils.getContent(context, Utils.KEY_USER_TOKEN);
		try {
			if (null != token) {
				connection
						.setRequestProperty("Authorization", "Basic " + token);
			}
			String result = getStrFromStream(connection.getInputStream());
			if (result.startsWith("{\"request\":")) {
				return RequestError.parseRequestError(result);
			}
			user = User.parseUser(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	/**
	 * 获取文章列表接口
	 * 
	 * @param type
	 *            文章类型（十大、热门、普通……）
	 * @return
	 */
	public static Object getArticles(Context context, String type) {
		List<Article> articles = new ArrayList<Article>(0);
		String url = null;
		if (type.equals("topten")) {
			url = Api.BYR_API + Api.WIDGET_TOPTEN + ENCODE_TYPE_JSON
					+ "?appkey=" + APP_KEY;
		} else if (type.equals("hot")) {
			url = Api.BYR_API + Api.WIDGET_FOCUS + ENCODE_TYPE_JSON
					+ "?appkey=" + APP_KEY;
		} else if (type.equals("recommended")) {
			url = Api.BYR_API + Api.WIDGET_RECOMMEND + ENCODE_TYPE_JSON
					+ "?appkey=" + APP_KEY;
		}
		if (null == url) {
			return null;
		}
		HttpURLConnection connection = getHttpGetConnection(url, GET_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		String token = Utils.getContent(context, Utils.KEY_USER_TOKEN);
		connection.setRequestProperty("Authorization", "Basic " + token);

		try {
			String result = getStrFromStream(connection.getInputStream());
			JSONObject obj = new JSONObject(result);
			JSONArray articleList = obj.getJSONArray("article");
			int length = articleList.length();
			for (int i = 0; i < length; i++) {
				Article article = Article.parseArticle(articleList
						.getJSONObject(i).toString());
				if (null != article) {
					articles.add(article);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (articles.size() == 0) {
			return null;
		}
		return articles;
	}

	/**
	 * 获取收藏版面列表接口
	 * 
	 * @param level
	 *            收藏夹层数，顶层为0
	 * @return
	 */
	public static Object getFavourites(Context context, int level) {
		List<Board> boards = new ArrayList<Board>(0);
		String url = Api.BYR_API + Api.FAVOURITE + level + ENCODE_TYPE_JSON
				+ "?appkey=" + APP_KEY;
		HttpURLConnection connection = getHttpGetConnection(url, GET_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		String token = Utils.getContent(context, Utils.KEY_USER_TOKEN);
		connection.setRequestProperty("Authorization", "Basic " + token);

		try {
			String result = getStrFromStream(connection.getInputStream());
			JSONObject obj = new JSONObject(result);
			JSONArray boardList = obj.getJSONArray("board");
			int length = boardList.length();
			for (int i = 0; i < length; i++) {
				Board board = Board.parseBoard(boardList.getJSONObject(i)
						.toString());
				if (null != board) {
					boards.add(board);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (boards.size() == 0) {
			return null;
		}
		return boards;
	}

	/**
	 * 获取信件列表接口
	 * 
	 * @param type
	 *            收件箱、发件箱、垃圾箱
	 * @return
	 */
	public static Object getMails(Context context, String type) {
		List<Mail> mails = new ArrayList<Mail>(0);
		String url = null;
		if (type.equals("inbox") || type.equals("outbox")
				|| type.equals("deleted")) {
			url = Api.BYR_API + Api.MAILBOX + type + ENCODE_TYPE_JSON
					+ "?appkey=" + APP_KEY;
		}
		if (null == url) {
			return null;
		}

		HttpURLConnection connection = getHttpGetConnection(url, GET_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		String token = Utils.getContent(context, Utils.KEY_USER_TOKEN);
		connection.setRequestProperty("Authorization", "Basic " + token);

		try {
			String result = getStrFromStream(connection.getInputStream());
			JSONObject obj = new JSONObject(result);
			JSONArray mailList = obj.getJSONArray("mail");
			int length = mailList.length();
			for (int i = 0; i < length; i++) {
				Mail mail = Mail
						.parseMail(mailList.getJSONObject(i).toString());
				if (null != mail) {
					mails.add(mail);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (mails.size() == 0) {
			return null;
		}
		return mails;
	}

	/**
	 * 获取分区版面列表接口
	 * 
	 * @param name
	 *            分区名称
	 * @return
	 */
	public static Section getSection(Context context, String name) {
		Section section = new Section();
		String url = Api.BYR_API + Api.SECTION_NAME + name + ENCODE_TYPE_JSON
				+ "?appkey=" + APP_KEY;
		HttpURLConnection connection = getHttpGetConnection(url, GET_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		String token = Utils.getContent(context, Utils.KEY_USER_TOKEN);
		connection.setRequestProperty("Authorization", "Basic " + token);

		try {
			String result = getStrFromStream(connection.getInputStream());
			section = Section.parseSection(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return section;
	}

	/**
	 * 获取分区版面帖子列表接口
	 * 
	 * @param name
	 *            版面名称
	 * @return
	 */
	public static Board getBoard(Context context, String name, int page) {
		Board board = new Board();
		String url = Api.BYR_API + Api.BOARD_NAME + name + ENCODE_TYPE_JSON
				+ "?appkey=" + APP_KEY + "&page=" + page;
		HttpURLConnection connection = getHttpGetConnection(url, GET_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		String token = Utils.getContent(context, Utils.KEY_USER_TOKEN);
		connection.setRequestProperty("Authorization", "Basic " + token);

		try {
			String result = getStrFromStream(connection.getInputStream());
			board = Board.parseBoard(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return board;
	}

	public static Subject getSubject(Context context, String name, int id,
			int page) {
		Subject subject = new Subject();
		String url = Api.BYR_API + Api.THREAD_INFO + name + "/" + id
				+ ENCODE_TYPE_JSON + "?appkey=" + APP_KEY + "&page=" + page;
		HttpURLConnection connection = getHttpGetConnection(url, GET_METHOD,
				false);
		if (null == connection) {
			return null;
		}
		String token = Utils.getContent(context, Utils.KEY_USER_TOKEN);
		connection.setRequestProperty("Authorization", "Basic " + token);

		try {
			String result = getStrFromStream(connection.getInputStream());
			subject = Subject.parseArticle(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return subject;
	}

}
