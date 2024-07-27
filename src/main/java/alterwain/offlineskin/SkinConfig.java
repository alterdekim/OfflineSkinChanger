package alterwain.offlineskin;

import java.awt.image.BufferedImage;

public class SkinConfig {
	private BufferedImage skin;
	private BufferedImage cape;
	private Boolean modelType;

	public SkinConfig(BufferedImage skin, BufferedImage cape, Boolean modelType) {
		this.skin = skin;
		this.cape = cape;
		this.modelType = modelType;
	}

	public BufferedImage getSkin() {
		return skin;
	}

	public BufferedImage getCape() {
		return cape;
	}

	public Boolean getModelType() {
		return modelType;
	}
}
