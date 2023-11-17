package Handelsakademin.FarmProgram;
    public class Animal extends Entity {

        private String species;
        static int nextAnimalId = 1;

        public Animal(String name, String species) {
            super(nextAnimalId, name);
            nextAnimalId++;
            this.species = species;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        @Override
        public String getDescription() {
            return super.getDescription() + ", Species: " + species;
        }

        public void feed(Crop crop) {
            if (crop.getQuantity() > 0) {
                crop.setQuantity(crop.getQuantity() - 1);
                System.out.println(name + " the " + species + " has been fed with " + crop.getName());
            } else {
                System.out.println("You have no " + crop.getName() + " left to feed. ");
            }
        }

        public String get_csv() {
            return getId() + "," + getName() + "," + getSpecies();
        }
    }