package Handelsakademin.FarmProgram;

    public class Crop extends Entity {

        private String cropType;
        private int quantity;
        static int nextCropId = 1;

        public Crop(String name, String cropType, int quantity) {
            super(nextCropId, name);
            nextCropId++;
            this.cropType = cropType;
            this.quantity = quantity;
        }

        public String getCropType() {
            return cropType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        @Override
        public String getDescription() {
            return super.getDescription() + ", Croptype: " + cropType + ", Quantity: " + quantity;
        }

        public String get_csv() {
            return getId() + "," + getName() + "," + getCropType() + "," + getQuantity();
        }
    }
