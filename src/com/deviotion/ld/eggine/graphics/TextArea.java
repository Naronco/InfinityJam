package com.deviotion.ld.eggine.graphics;

public class TextArea {
    private int x, y, width, height;
    private Font font;
    private int ticksPerCharacter = 1;
    private int maxLineCount = 0;

    private String activeMessage;

    private boolean buildingMessage = false;

    private int lineHeight;
    private int currentFirstLine;
    private int lineOffset;

    private int visibleCharacters;
    private int ticksSinceLastCharacter;

    public TextArea(int x, int y, int width, int height, Font font) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;

        this.lineHeight = ((int)font.getCharacterSize().getHeight() + 1);
    }

    public void showText(String text) {
        String[] words = text.split(" ");

        StringBuilder messageBuilder = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            int newLength = currentLine.length() + word.length();
            boolean overBounds = newLength * Font.standard.getCharacterSize().getWidth() >= width;

            if (overBounds) {
                messageBuilder.append(currentLine).append('\n');
                currentLine = new StringBuilder();
            }

            currentLine.append(word).append(' ');
        }

        if (currentLine.length() > 0) {
            messageBuilder.append(currentLine);
        }

        activeMessage = messageBuilder.toString();
        visibleCharacters = 1;
        ticksSinceLastCharacter = 0;
        buildingMessage = true;
        currentFirstLine = 0;
        lineOffset = 0;
    }

    public void update() {
        if (buildingMessage) {
            ++ticksSinceLastCharacter;

            if (ticksSinceLastCharacter >= ticksPerCharacter) {
                ++visibleCharacters;

                if (visibleCharacters == activeMessage.length()) {
                    buildingMessage = false;
                } else {
                    if (activeMessage.charAt(visibleCharacters) == '\n') {
                        if (currentFirstLine >= maxLineCount - 1) {
                            lineOffset = (currentFirstLine - maxLineCount + 1) * lineHeight + 1;
                        }
                        ++currentFirstLine;
                    }
                }

                ticksSinceLastCharacter = 0;
            }

            if (lineOffset % lineHeight != 0) {
                ++lineOffset;
            }
        }
    }

    public void render(Screen screen) {
        if (activeMessage != null) {
            String partialMessage = activeMessage.substring(0, visibleCharacters);
            screen.renderClippedText(x, y - lineOffset, x, y, width, height, Font.standard, partialMessage);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Font getFont() {
        return font;
    }

    public int getTicksPerCharacter() {
        return ticksPerCharacter;
    }

    public void setTicksPerCharacter(int ticksPerCharacter) {
        this.ticksPerCharacter = ticksPerCharacter;
    }

    public int getMaxLineCount() {
        return maxLineCount;
    }

    public void setMaxLineCount(int maxLineCount) {
        this.maxLineCount = maxLineCount;
    }
}
