package alterwain.offlineskin.mixin;

import alterwain.offlineskin.OfflineSkinMod;
import net.minecraft.client.render.ImageParser;
import net.minecraft.client.render.PlayerSkinParser;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingRenderer<EntityPlayer> {

	@Shadow(remap = false)
	private ModelBiped modelBipedMain;

	@Final
	@Shadow(remap = false)
	private ModelPlayer modelSlim;

	@Final
	@Shadow(remap = false)
	private ModelPlayer modelThick;


	public PlayerRendererMixin(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	@Override
	public void loadEntityTexture(EntityPlayer entity) {
		this.loadDownloadableTexture("offlineSkinLocal:"+entity.username, entity.getEntityTexture(), PlayerSkinParser.instance);
	}

	public void drawFirstPersonHand(EntityPlayer player) {
		player.skinURL = "offlineSkinLocal:"+player.username;
		if(OfflineSkinMod.skins.containsKey(player.username)) {
			this.mainModel = OfflineSkinMod.skins.get(player.username).getModelType() ? this.modelSlim : this.modelThick;
			this.modelBipedMain = OfflineSkinMod.skins.get(player.username).getModelType() ? this.modelSlim : this.modelThick;
		} else {
			this.mainModel = player.slimModel ? this.modelSlim : this.modelThick;
			this.modelBipedMain = player.slimModel ? this.modelSlim : this.modelThick;
		}
		this.modelBipedMain.onGround = 0.0F;
		this.modelBipedMain.isRiding = false;
		this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		this.modelBipedMain.bipedRightArm.render(0.0625F);
		if (this.modelBipedMain instanceof ModelPlayer) {
			((ModelPlayer)this.modelBipedMain).bipedRightArmOverlay.render(0.0625F);
		}
	}

	protected void renderSpecials(EntityPlayer entity, float f) {
		ItemStack itemstack = entity.inventory.armorItemInSlot(3);
		if (itemstack != null && itemstack.getItem().id < Block.blocksList.length) {
			GL11.glPushMatrix();
			this.modelBipedMain.bipedHead.postRender(0.0625F);
			if (((BlockModel) BlockModelDispatcher.getInstance().getDispatch(Block.blocksList[itemstack.itemID])).shouldItemRender3d()) {
				float f1 = 0.625F;
				GL11.glTranslatef(0.0F, -0.25F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f1, -f1, f1);
			}

			this.renderDispatcher.itemRenderer.renderItem(entity, itemstack);
			GL11.glPopMatrix();
		}

		boolean renderCape = this.loadDownloadableTexture("offlineCapeLocal:"+entity.username, (String)null, (ImageParser)null);

		if (renderCape) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.0F, 0.125F);
			double d = entity.field_20066_r + (entity.field_20063_u - entity.field_20066_r) * (double)f - (entity.xo + (entity.x - entity.xo) * (double)f);
			double d1 = entity.field_20065_s + (entity.field_20062_v - entity.field_20065_s) * (double)f - (entity.yo + (entity.y - entity.yo) * (double)f);
			double d2 = entity.field_20064_t + (entity.field_20061_w - entity.field_20064_t) * (double)f - (entity.zo + (entity.z - entity.zo) * (double)f);
			float f8 = entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset) * f;
			double d3 = (double) MathHelper.sin(f8 * 3.141593F / 180.0F);
			double d4 = (double)(-MathHelper.cos(f8 * 3.141593F / 180.0F));
			float f9 = (float)d1 * 10.0F;
			if (f9 < -6.0F) {
				f9 = -6.0F;
			}

			if (f9 > 32.0F) {
				f9 = 32.0F;
			}

			float f10 = (float)(d * d3 + d2 * d4) * 100.0F;
			float f11 = (float)(d * d4 - d2 * d3) * 100.0F;
			if (f10 < 0.0F) {
				f10 = 0.0F;
			}

			float f12 = entity.field_775_e + (entity.field_774_f - entity.field_775_e) * f;
			f9 += MathHelper.sin((entity.walkDistO + (entity.walkDist - entity.walkDistO) * f) * 6.0F) * 32.0F * f12;
			if (entity.isSneaking()) {
				f9 += 25.0F;
			}

			GL11.glRotatef(6.0F + f10 / 2.0F + f9, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(f11 / 2.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-f11 / 2.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			this.modelBipedMain.renderCloak(0.0625F);
			GL11.glPopMatrix();
		}

		ItemStack itemstack1 = entity.inventory.getCurrentItem();
		if (itemstack1 != null) {
			GL11.glPushMatrix();
			this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			if (entity.fishEntity != null) {
				itemstack1 = new ItemStack(Item.stick);
			}

			float f4;
			if (itemstack1.itemID < Block.blocksList.length && ((BlockModel)BlockModelDispatcher.getInstance().getDispatch(Block.blocksList[itemstack1.itemID])).shouldItemRender3d()) {
				f4 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				f4 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f4, -f4, f4);
			} else if (itemstack1.itemID == Item.toolBow.id) {
				f4 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f4, -f4, f4);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if (Item.itemsList[itemstack1.itemID].isFull3D()) {
				f4 = 0.625F;
				if (Item.itemsList[itemstack1.itemID].shouldRotateAroundWhenRendering()) {
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}

				if (Item.itemsList[itemstack1.itemID].shouldPointInFrontOfPlayer()) {
					GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}

				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(f4, -f4, f4);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				f4 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(f4, f4, f4);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			this.renderDispatcher.itemRenderer.renderItem(entity, itemstack1);
			GL11.glPopMatrix();
		}
	}
}
