package net.pmkjun.mineplanetplus.fishhelper.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.util.Earning;
import net.pmkjun.mineplanetplus.fishhelper.util.FishCounterMode;
import org.joml.Matrix3x2fStack;

import java.util.Arrays;

public class FishCounterGui {
    private final Minecraft mc;
    private final FishHelperClient client;

    public FishCounterGui(){
        this.mc = Minecraft.getInstance();
        this.client = FishHelperClient.getInstance();
    }

    // 26.1: renderTick -> extractRenderState
    public void extractRenderState(GuiGraphicsExtractor guiGraphics) {
        String[] fishCounterData = new String[10];
        for(int i = 0; i < 6; i++) {
            if(client.data.toggleCounterMode == FishCounterMode.PERCENTAGE){
                fishCounterData[i] = String.format("%.2f",
                        (double)client.data.fish_Count[i]/Arrays.stream(client.data.fish_Count).sum()*100)+"%";
            } else if(client.data.toggleCounterMode == FishCounterMode.COUNT){
                fishCounterData[i] = String.format("%d", client.data.fish_Count[i])+"마리";
            } else{
                fishCounterData[i] = String.format("%.2f",
                        (double)client.data.fish_Count[i]/Arrays.stream(client.data.fish_Count).sum()*100)+
                        "(" + String.format("%d", client.data.fish_Count[i])+")"+"%";
            }
        }
        fishCounterData[6] = Arrays.stream(client.data.fish_Count).sum() + "마리";

        fishCounterData[7] = " ";
        fishCounterData[8] = String.format("%.1f", Earning.getMoney())+"원";
        fishCounterData[9] = String.format("%.1f", Earning.getEntropy())+"E";


        if(client.data.toggleFishCounter || client.data.toggleEarningCalculator)
            extract(guiGraphics, fishCounterData);
    }

    // 26.1: render -> extract
    private void extract(GuiGraphicsExtractor guiGraphics, String[] fishCounterData) {
        int line = 0, x, y, elements_counts = 0;
        int start_point = 0, end_point = 10;
        String[] left_fields = {"커먼","언커먼","레어","에픽","레전더리","신화", "합", " ", "골드 환산", "엔트로피 환산"};

        if(client.data.toggleFishCounter) {
            elements_counts += 6;
            end_point = 7;
        }
        if(client.data.toggleEarningCalculator) {
            elements_counts += 2;
            start_point = 8;
        }
        if(client.data.toggleFishCounter && client.data.toggleEarningCalculator) {
            elements_counts += 1;
            start_point = 0;
            end_point = 10;
        }

        x = 2 + (mc.getWindow().getGuiScaledWidth() - (116)) * client.data.Counter_xpos / 1000;
        y = 2 + (mc.getWindow().getGuiScaledHeight() - (12) * (elements_counts+1) - 4) * client.data.Counter_ypos / 1000;

        for(int i = start_point; i < end_point ; i++){
            extractWithoutTexture(line++, guiGraphics, x, y, left_fields[i], fishCounterData[i]);
        }
    }

    // 26.1: renderWithoutTexture -> extractWithoutTexture
    private void extractWithoutTexture(int i, GuiGraphicsExtractor guiGraphics, int x, int y, String left_field, String right_field) {
        Matrix3x2fStack poseStack = guiGraphics.pose();
        Font font = this.mc.font;
        int width = 50, text_width;

        text_width = font.width(left_field);

        poseStack.pushMatrix();
        poseStack.translate((x + 2), y+4 + (12) * i);
        poseStack.scale(1f/1.1f, 1f/1.1f);
        guiGraphics.text(font, Component.literal(left_field), width - text_width, 0, ARGB.white(255)); // drawString -> text
        poseStack.scale(1.1f, 1.1f);
        poseStack.popMatrix();

        text_width = font.width(right_field);
        poseStack.pushMatrix();
        poseStack.translate((x + 2 + 50 + 16), y+4 + (12) * i);
        poseStack.scale(1f/1.1f, 1f/1.1f);
        guiGraphics.text(font, Component.literal(right_field), width - text_width, 0, ARGB.white(255)); // drawString -> text
        poseStack.scale(1.1f, 1.1f);
        poseStack.popMatrix();
    }
}