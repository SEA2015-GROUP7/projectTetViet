package net.pocketmagic.android.carousel;

public class CarouselDataItem {
	long itemId;
	int imageId;
	String itemName;

	public CarouselDataItem(long id, int imgId, String name) {
		imageId = imgId;
		itemId = id;
		itemName = name;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
