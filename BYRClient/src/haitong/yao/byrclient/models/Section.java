package haitong.yao.byrclient.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 元数据 分区
 * 
 * @author Mr.Yao
 * 
 */
public class Section implements Serializable {

	private static final long serialVersionUID = -7743120210511170455L;

	private String name; // 分区名称
	private String description; // 分区表述
	private boolean is_root; // 是否是根分区
	private String parent; // 该分区所属根分区名称
	private List<String> subSection = new ArrayList<String>();
	private List<Board> board = new ArrayList<Board>();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParent() {
		return parent;
	}

	public void setIsRoot(boolean is_root) {
		this.is_root = is_root;
	}

	public boolean getIsRoot() {
		return is_root;
	}

	public List<String> getSubSection() {
		return subSection;
	}

	public void setSubSection(List<String> subSection) {
		this.subSection = subSection;
	}

	public List<Board> getBoard() {
		return board;
	}

	public void setBoard(List<Board> board) {
		this.board = board;
	}

	public static Section parseSection(String json) {
		Section section = new Section();
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			section.setName(obj.optString("name"));
			section.setDescription(obj.optString("description"));
			section.setIsRoot(obj.optBoolean("is_root"));
			section.setParent(obj.optString("parent"));
			JSONArray subSection = obj.optJSONArray("sub_section");
			List<String> sections = new ArrayList<String>();
			for (int i = 0; i < subSection.length(); i++) {
				sections.add(subSection.getString(i));
			}
			section.setSubSection(sections);
			JSONArray board = obj.optJSONArray("board");
			List<Board> boards = new ArrayList<Board>();
			for (int i = 0; i < board.length(); i++) {
				boards.add(Board.parseBoard(board.getString(i)));
			}
			section.setBoard(boards);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return section;
	}
}
