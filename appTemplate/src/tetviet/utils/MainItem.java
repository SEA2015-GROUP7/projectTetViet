/**
 * 
 */
package tetviet.utils;

/**
 * @author ThanhPN
 *
 */
public class MainItem {
	public int id;
	public int MenuId ;
	public String MenuName;
	public int MenuIcon;
	
	public MainItem() {
		// TODO Auto-generated constructor stub
		super();
		
	}

	public MainItem(int id, int menuId, String menuName, int menuIcon) {
		super();
		this.id = id;
		MenuId = menuId;
		MenuName = menuName;
		MenuIcon = menuIcon;
	}
	
}
