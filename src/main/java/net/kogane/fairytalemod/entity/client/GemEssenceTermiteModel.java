package net.kogane.fairytalemod.entity.client;

// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class GemEssenceTermiteModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart section_2;
    private final ModelPart section_0;
    private final ModelPart section_1;
    private final ModelPart section_3;

    public GemEssenceTermiteModel(ModelPart root) {
        this.section_2 = root.getChild("section_2");
        this.section_0 = this.section_2.getChild("section_0");
        this.section_1 = this.section_2.getChild("section_1");
        this.section_3 = this.section_2.getChild("section_3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition section_2 = partdefinition.addOrReplaceChild("section_2", CubeListBuilder.create().texOffs(0, 14).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 2.5F));

        PartDefinition section_0 = section_2.addOrReplaceChild("section_0", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -4.4F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.5F));

        PartDefinition section_1 = section_2.addOrReplaceChild("section_1", CubeListBuilder.create().texOffs(0, 5).addBox(-3.0F, -4.0F, -2.4F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.5F));

        PartDefinition section_3 = section_2.addOrReplaceChild("section_3", CubeListBuilder.create().texOffs(0, 18).addBox(-0.5F, -2.0F, 3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.5F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        section_2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
