package model;

import java.util.List;

public class Resource {
    public static class Texture{
        public static class Background{
            private String path;
            private Double clarity;
            private int x;
            private int y;
            private int width;
            private int height;

            public Background(String path, Double clarity, int x, int y, int width, int height) {
                this.path = path;
                this.clarity = clarity;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
            }

            public String getPath() {
                return path;
            }

            public Double getClarity() {
                return clarity;
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
        }
        private Background background;

        public static class Broad {
            private String path;
            private Double clarity;
            private int x;
            private int y;
            private int width;
            private int height;

            public static class Chest {
                private Double x;
                private Double y;
                private Double side;
                private Double interval;

                public Chest(Double x, Double y, Double side, Double interval) {
                    this.x = x;
                    this.y = y;
                    this.side = side;
                    this.interval = interval;
                }

                public Double getX() {
                    return x;
                }

                public Double getY() {
                    return y;
                }

                public Double getSide() {
                    return side;
                }

                public Double getInterval() {
                    return interval;
                }
            }

            private Chest chest;

            public Broad(String path, Double clarity, int x, int y, int width, int height, Chest chest) {
                this.path = path;
                this.clarity = clarity;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                this.chest = chest;
            }

            public String getPath() {
                return path;
            }

            public Double getClarity() {
                return clarity;
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

            public Chest getChest() {
                return chest;
            }
        }

            private Broad broad;

        public static class Chest{
            private String path;
            private Double clarity;
            private Double width,height;

            public Chest(String path, Double clarity, Double width, Double height) {
                this.path = path;
                this.clarity = clarity;
                this.width = width;
                this.height = height;
            }

            public String getPath() {
                return path;
            }

            public Double getClarity() {
                return clarity;
            }

            public Double getWidth() {
                return width;
            }

            public Double getHeight() {
                return height;
            }
        }
        private List<Chest> chests;

        public Texture(Background background, Broad broad, List<Chest> chests) {
            this.background = background;
            this.broad = broad;
            this.chests = chests;
        }

        public Background getBackground() {
            return background;
        }

        public List<Chest> getChests() {
            return chests;
        }

        public Broad getBroad() {
            return broad;
        }
    }
    private Texture texture;
    public static class GameConfig{
        private int length;
        private Double rate;
        private Long seed;

        public GameConfig(int length, Double rate) {
            this(length,rate,System.currentTimeMillis());
        }

        public GameConfig(int length, Double rate, Long seed) {
            this.length = length;
            this.rate = rate;
            this.seed = seed;
        }
    }
    private GameConfig gameConfig;

    public Resource(Texture texture, GameConfig gameConfig) {
        this.texture = texture;
        this.gameConfig = gameConfig;
    }

    public Texture getTexture() {
        return texture;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }
}
